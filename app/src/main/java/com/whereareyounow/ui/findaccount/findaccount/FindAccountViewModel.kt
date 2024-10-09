package com.whereareyounow.ui.findaccount.findaccount

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.whereareyounow.data.findaccount.FindAccountEmailVerificationScreenSideEffect
import com.whereareyounow.data.findaccount.FindAccountEmailVerificationScreenUIState
import com.whereareyounow.domain.request.member.CheckEmailDuplicateRequest
import com.whereareyounow.domain.request.member.SendEmailCodeRequest
import com.whereareyounow.domain.request.member.VerifyEmailCodeRequest
import com.whereareyounow.domain.request.member.VerifyPasswordResetCodeRequest
import com.whereareyounow.domain.usecase.member.CheckEmailDuplicateUseCase
import com.whereareyounow.domain.usecase.member.SendEmailCodeUseCase
import com.whereareyounow.domain.usecase.member.VerifyEmailCodeUseCase
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
class FindAccountViewModel @Inject constructor(
    private val application: Application,
    private val inputTextValidator: InputTextValidator,
    private val sendEmailCodeUseCase: SendEmailCodeUseCase,
    private val checkEmailDuplicateUseCase: CheckEmailDuplicateUseCase,
    private val verifyPasswordResetCodeUseCase: VerifyPasswordResetCodeUseCase
) : AndroidViewModel(application) {

    private val _uiState = MutableStateFlow(FindAccountEmailVerificationScreenUIState())
    val uiState = _uiState.asStateFlow()
    val sideEffectFlow = MutableSharedFlow<FindAccountEmailVerificationScreenSideEffect>()
    private var startTimer: Job? = null

    fun updateInputEmail(email: String) {
        _uiState.update {
            it.copy(
                inputEmail = email,
                inputEmailState = if (inputTextValidator.validateEmail(email).result) FindAccountEmailState.Idle else FindAccountEmailState.Invalid
            )
        }
    }

    fun updateInputEmailCode(code: String) {
        _uiState.update {
            it.copy(inputEmailCode = code)
        }
    }

    fun checkEmailDuplicate() {
        val requestData = CheckEmailDuplicateRequest(
            email = _uiState.value.inputEmail
        )
        checkEmailDuplicateUseCase(requestData)
            .onEach { networkResult ->
                networkResult.onSuccess { code, message, data ->
                    data?.let {
                        if (data.type.isEmpty()) {
                            _uiState.update {
                                it.copy(
                                    inputEmailState = FindAccountEmailState.NonExist
                                )
                            }
                        } else {
                            _uiState.update {
                                it.copy(
                                    email = data.email,
                                    typeList = data.type
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
        if (inputTextValidator.validateEmail(_uiState.value.inputEmail).result) {
            _uiState.update {
                it.copy(
                    inputEmailState = FindAccountEmailState.Valid
                )
            }
        } else {
            _uiState.update {
                it.copy(
                    inputEmailState = FindAccountEmailState.Invalid
                )
            }
            return
        }
        val requestData = SendEmailCodeRequest(email = _uiState.value.inputEmail)
        sendEmailCodeUseCase(requestData)
            .onEach { networkResult ->
                networkResult.onSuccess { code, message, data ->
                    _uiState.update {
                        it.copy(
                            requestButtonState = EmailButtonState.RequestDone,
                            inputEmailState = FindAccountEmailState.Valid
                        )
                    }
                    // 인증 코드를 발송하고 5분이 지나지 않았으면 다시 발송할 수 없다.
                    if (_uiState.value.emailCodeLeftTime > 300) {
                        viewModelScope.launch {
                            sideEffectFlow.emit(FindAccountEmailVerificationScreenSideEffect.Toast("${_uiState.value.emailCodeLeftTime - 120}초 후에 다시 발송할 수 있습니다."))
                        }
                        return@onEach
                    }
                    startTimer?.cancel()
                    startTimer = viewModelScope.launch {
                        _uiState.update {
                            it.copy(emailCodeLeftTime = 300)
                        }
                        while (_uiState.value.emailCodeLeftTime > 0) {
                            _uiState.update {
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
            email = _uiState.value.inputEmail,
            code = _uiState.value.inputEmailCode
        )
        verifyPasswordResetCodeUseCase(requestData)
            .onEach { networkResult ->
                networkResult.onSuccess { code, message, data ->
                    _uiState.update {
                        it.copy(
                            emailCodeButtonState = EmailCodeButtonState.Inactive,
                            inputEmailCodeState = EmailCodeState.Valid
                        )
                    }
                }.onError { code, message ->
                    _uiState.update {
                        it.copy(
                            inputEmailCodeState = EmailCodeState.Invalid
                        )
                    }
                }.onException {  }
            }
            .catch {  }
            .launchIn(viewModelScope)
    }
}