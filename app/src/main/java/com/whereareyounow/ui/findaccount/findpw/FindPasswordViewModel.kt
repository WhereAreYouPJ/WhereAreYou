package com.whereareyounow.ui.findaccount.findpw

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.whereareyounow.data.findpw.FindPasswordScreenSideEffect
import com.whereareyounow.data.findpw.FindPasswordScreenUIState
import com.whereareyounow.data.findpw.PasswordCheckingState
import com.whereareyounow.data.findpw.PasswordResettingScreenSideEffect
import com.whereareyounow.data.findpw.PasswordResettingScreenUIState
import com.whereareyounow.data.findpw.PasswordState
import com.whereareyounow.domain.request.member.CheckEmailDuplicateRequest
import com.whereareyounow.domain.request.member.ResetPasswordRequest
import com.whereareyounow.domain.request.member.SendEmailCodeRequest
import com.whereareyounow.domain.request.member.VerifyPasswordResetCodeRequest
import com.whereareyounow.domain.usecase.member.CheckEmailDuplicateUseCase
import com.whereareyounow.domain.usecase.member.ResetPasswordUseCase
import com.whereareyounow.domain.usecase.member.SendEmailCodeUseCase
import com.whereareyounow.domain.usecase.member.VerifyPasswordResetCodeUseCase
import com.whereareyounow.domain.util.LogUtil
import com.whereareyounow.domain.util.onError
import com.whereareyounow.domain.util.onException
import com.whereareyounow.domain.util.onSuccess
import com.whereareyounow.globalvalue.type.EmailButtonState
import com.whereareyounow.globalvalue.type.EmailCodeButtonState
import com.whereareyounow.globalvalue.type.EmailCodeState
import com.whereareyounow.globalvalue.type.FindAccountEmailState
import com.whereareyounow.util.InputTextValidator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FindPasswordViewModel @Inject constructor(
    private val application: Application,
    private val inputTextValidator: InputTextValidator,
    private val checkEmailDuplicateUseCase: CheckEmailDuplicateUseCase,
    private val sendEmailCodeUseCase: SendEmailCodeUseCase,
    private val verifyPasswordResetCodeUseCase: VerifyPasswordResetCodeUseCase,
    private val resetPasswordUseCase: ResetPasswordUseCase,
) : AndroidViewModel(application) {

    private val _findPasswordScreenUIState = MutableStateFlow(FindPasswordScreenUIState())
    val uiState = _findPasswordScreenUIState.asStateFlow()
    private val _passwordResettingScreenUIState = MutableStateFlow(PasswordResettingScreenUIState())
    val passwordResettingScreenUIState = _passwordResettingScreenUIState.asStateFlow()
    val findPasswordScreenSideEffectFlow = MutableSharedFlow<FindPasswordScreenSideEffect>()
    val passwordResettingScreenSideEffectFlow = MutableSharedFlow<PasswordResettingScreenSideEffect>()
    private var startTimer: Job? = null

    fun updateInputEmail(email: String) {
        _findPasswordScreenUIState.update {
            it.copy(
                inputEmail = email,
                inputEmailState = if (inputTextValidator.validateEmail(email).result) FindAccountEmailState.Idle else FindAccountEmailState.Invalid
            )
        }
    }

    fun updateInputEmailCode(code: String) {
        _findPasswordScreenUIState.update {
            it.copy(inputEmailCode = code)
        }
    }

    fun updateInputPassword(password: String) {
        _passwordResettingScreenUIState.update {
            it.copy(
                inputPassword = password,
                inputPasswordState = if (inputTextValidator.validatePassword(password).result) PasswordState.Satisfied else PasswordState.Unsatisfied
            )
        }
    }

    fun updateInputPasswordForChecking(passwordForChecking: String) {
        _passwordResettingScreenUIState.update {
            it.copy(
                inputPasswordForChecking = passwordForChecking,
                passwordCheckingState = if (it.inputPassword == passwordForChecking) PasswordCheckingState.Satisfied else PasswordCheckingState.Unsatisfied
            )
        }
    }

    fun checkEmailDuplicate() {
        val requestData = CheckEmailDuplicateRequest(
            email = _findPasswordScreenUIState.value.inputEmail
        )
        checkEmailDuplicateUseCase(requestData)
            .onEach { networkResult ->
                networkResult.onSuccess { code, message, data ->
                    data?.let {
                        if (data.type.isEmpty()) {
                            _findPasswordScreenUIState.update {
                                it.copy(
                                    inputEmailState = FindAccountEmailState.NonExist
                                )
                            }
                        } else {
                            _findPasswordScreenUIState.update {
                                it.copy(
                                    email = data.email
                                )
                            }
                            sendEmailCode()
                        }
                    }
                }.onError { code, message ->

                }.onException {  }
            }
            .catch {  }
            .launchIn(viewModelScope)
    }

    fun sendEmailCode() {
        if (inputTextValidator.validateEmail(_findPasswordScreenUIState.value.inputEmail).result) {
            _findPasswordScreenUIState.update {
                it.copy(
                    inputEmailState = FindAccountEmailState.Valid
                )
            }
        } else {
            _findPasswordScreenUIState.update {
                it.copy(
                    inputEmailState = FindAccountEmailState.Invalid
                )
            }
            return
        }
        val requestData = SendEmailCodeRequest(email = _findPasswordScreenUIState.value.inputEmail)
        sendEmailCodeUseCase(requestData)
            .onEach { networkResult ->
                networkResult.onSuccess { code, message, data ->
                    _findPasswordScreenUIState.update {
                        it.copy(
                            requestButtonState = EmailButtonState.RequestDone,
                            inputEmailState = FindAccountEmailState.Valid
                        )
                    }
                    // 인증 코드를 발송하고 5분이 지나지 않았으면 다시 발송할 수 없다.
                    if (_findPasswordScreenUIState.value.emailCodeLeftTime > 300) {
                        viewModelScope.launch {
                            findPasswordScreenSideEffectFlow.emit(FindPasswordScreenSideEffect.Toast("${_findPasswordScreenUIState.value.emailCodeLeftTime - 120}초 후에 다시 발송할 수 있습니다."))
                        }
                        return@onEach
                    }
                    startTimer?.cancel()
                    startTimer = viewModelScope.launch {
                        _findPasswordScreenUIState.update {
                            it.copy(emailCodeLeftTime = 300)
                        }
                        while (_findPasswordScreenUIState.value.emailCodeLeftTime > 0) {
                            _findPasswordScreenUIState.update {
                                it.copy(emailCodeLeftTime = it.emailCodeLeftTime - 1)
                            }
                            delay(1000)
                        }
                    }
                }.onError { code, message ->

                }.onException {  }
            }
            .catch { LogUtil.e("flow error", "sendEmailCodeUseCase") }
            .launchIn(viewModelScope)
    }

    fun verifyEmailCode() {
        val requestData = VerifyPasswordResetCodeRequest(
            email = _findPasswordScreenUIState.value.inputEmail,
            code = _findPasswordScreenUIState.value.inputEmailCode
        )
        verifyPasswordResetCodeUseCase(requestData)
            .onEach { networkResult ->
                networkResult.onSuccess { code, message, data ->
                    _findPasswordScreenUIState.update {
                        it.copy(
                            emailCodeButtonState = EmailCodeButtonState.Inactive,
                            inputEmailCodeState = EmailCodeState.Valid
                        )
                    }
                }.onError { code, message ->
                    _findPasswordScreenUIState.update {
                        it.copy(
                            inputEmailCodeState = EmailCodeState.Invalid
                        )
                    }
                }.onException {  }
            }
            .catch {  }
            .launchIn(viewModelScope)
    }

    fun resetPassword(
        email: String,
        moveToResetPasswordSuccessScreen: () -> Unit
    ) {
        val requestData = ResetPasswordRequest(
            email = email,
            password = _passwordResettingScreenUIState.value.inputPassword,
            checkPassword = _passwordResettingScreenUIState.value.inputPasswordForChecking
        )
        resetPasswordUseCase(requestData)
            .onEach { networkResult ->
                networkResult.onSuccess { code, message, data ->
                    moveToResetPasswordSuccessScreen()
                }.onError { code, message ->

                }.onException {  }
            }
            .catch {  }
            .launchIn(viewModelScope)
    }
}