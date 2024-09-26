package com.onmyway.ui.signup

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.onmyway.data.signup.SignUpPasswordCheckState
import com.onmyway.data.signup.SignUpPasswordState
import com.onmyway.data.signup.SignUpScreenSideEffect
import com.onmyway.data.signup.SignUpScreenUIState
import com.onmyway.data.signup.SignUpUserNameState
import com.onmyway.domain.request.member.SendEmailCodeRequest
import com.onmyway.domain.request.member.SignUpRequest
import com.onmyway.domain.request.member.VerifyEmailCodeRequest
import com.onmyway.domain.usecase.member.SendEmailCodeUseCase
import com.onmyway.domain.usecase.member.SignUpUseCase
import com.onmyway.domain.usecase.member.VerifyEmailCodeUseCase
import com.onmyway.domain.util.LogUtil
import com.onmyway.domain.util.onError
import com.onmyway.domain.util.onException
import com.onmyway.domain.util.onSuccess
import com.onmyway.globalvalue.type.EmailButtonState
import com.onmyway.globalvalue.type.EmailCodeButtonState
import com.onmyway.globalvalue.type.EmailCodeState
import com.onmyway.globalvalue.type.EmailState
import com.onmyway.util.InputTextValidator
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
class SignUpViewModel @Inject constructor(
    private val application: Application,
    private val sendEmailCodeUseCase: SendEmailCodeUseCase,
    private val verifyEmailCodeUseCase: VerifyEmailCodeUseCase,
//    private val checkIdDuplicateUseCase: CheckIdDuplicateUseCase,
//    private val checkEmailDuplicateUseCase: CheckEmailDuplicateUseCase,
//    private val authenticateEmailUseCase: AuthenticateEmailUseCase,
//    private val authenticateEmailCodeUseCase: AuthenticateEmailCodeUseCase,
//    private val signUpUseCase: SignUpUseCase,
    private val inputTextValidator: InputTextValidator,
    private val signUpUseCase: SignUpUseCase,
) : AndroidViewModel(application) {

    private val _uiState = MutableStateFlow(SignUpScreenUIState())
    val uiState = _uiState.asStateFlow()
    val sideEffectFlow = MutableSharedFlow<SignUpScreenSideEffect>()
    private var startTimer: Job? = null

    fun updateInputUserName(userName: String) {
        _uiState.update {
            it.copy(
                inputUserName = userName,
                inputUserNameState = if (inputTextValidator.validateUserName(userName).result) SignUpUserNameState.Valid else SignUpUserNameState.Invalid
            )

        }
    }

    fun updateInputEmail(email: String) {
        _uiState.update {
            it.copy(
                inputEmail = email,
                requestButtonState = EmailButtonState.Request,
                inputEmailState = EmailState.Idle
            )
        }
    }

    fun sendEmailCode() {
        if (inputTextValidator.validateEmail(_uiState.value.inputEmail).result) {
            _uiState.update {
                it.copy(
                    inputEmailState = EmailState.Valid
                )
            }
        } else {
            _uiState.update {
                it.copy(
                    inputEmailState = EmailState.Invalid
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
                            inputEmailState = EmailState.Valid
                        )
                    }
                    // 인증 코드를 발송하고 5분이 지나지 않았으면 다시 발송할 수 없다.
                    if (_uiState.value.emailCodeLeftTime > 300) {
                        viewModelScope.launch {
                            sideEffectFlow.emit(SignUpScreenSideEffect.Toast("${_uiState.value.emailCodeLeftTime - 120}초 후에 다시 발송할 수 있습니다."))
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

    fun updateInputVerificationCode(code: String) {
        _uiState.update {
            it.copy(
                inputEmailCode = code,
                inputEmailCodeState = EmailCodeState.Idle,
                emailCodeButtonState = if (code == "") EmailCodeButtonState.Inactive else EmailCodeButtonState.Active
            )
        }
    }

    fun updateInputPassword(password: String) {
        _uiState.update {
            it.copy(
                inputPassword = password,
                inputPasswordState = if (inputTextValidator.validatePassword(password).result) SignUpPasswordState.Valid else SignUpPasswordState.Invalid,
                inputPasswordCheckState = SignUpPasswordCheckState.Idle
            )
        }
    }

    fun updateInputPasswordForChecking(password: String) {
        _uiState.update {
            it.copy(
                inputPasswordCheck = password,
                inputPasswordCheckState = if (password == it.inputPassword) SignUpPasswordCheckState.Valid else SignUpPasswordCheckState.Invalid
            )
        }
    }
    fun verifyEmailCode() {
        if (_uiState.value.emailCodeLeftTime <= 0) {
            viewModelScope.launch {
                sideEffectFlow.emit(SignUpScreenSideEffect.Toast("유효시간이 만료되었습니다. 인증코드를 재전송해주세요."))
            }
            return
        }
        val requestData = VerifyEmailCodeRequest(
            email = _uiState.value.inputEmail,
            code = _uiState.value.inputEmailCode
        )
        verifyEmailCodeUseCase(requestData)
            .onEach { networkResult ->
                networkResult.onSuccess { code, message, data ->
                    _uiState.update {
                        it.copy(
                            inputEmailCodeState = EmailCodeState.Valid,
                            emailCodeButtonState = EmailCodeButtonState.Inactive
                        )
                    }
                }.onError { code, message ->
                    _uiState.update {
                        it.copy(
                            inputEmailCodeState = EmailCodeState.Invalid,
                        )
                    }
                }.onException {

                }
            }
            .catch { LogUtil.e("flow error", "verifyEmailCode") }
            .launchIn(viewModelScope)
    }

    fun signUp(
        moveToSignUpSuccessScreen: () -> Unit
    ) {
        viewModelScope.launch {
            if (_uiState.value.inputUserNameState != SignUpUserNameState.Valid) {
                sideEffectFlow.emit(SignUpScreenSideEffect.Toast("이름을 확인해주세요."))
                return@launch
            }
            if (_uiState.value.inputPasswordState != SignUpPasswordState.Valid) {
                sideEffectFlow.emit(SignUpScreenSideEffect.Toast("비밀번호를 확인해주세요."))
                return@launch
            }
            if (_uiState.value.inputPasswordCheckState != SignUpPasswordCheckState.Valid) {
                sideEffectFlow.emit(SignUpScreenSideEffect.Toast("비밀번호 확인을 다시해주세요."))
                return@launch
            }
            if (_uiState.value.inputEmailState != EmailState.Valid) {
                sideEffectFlow.emit(SignUpScreenSideEffect.Toast("이메일을 확인해주세요."))
                return@launch
            }
            if (_uiState.value.inputEmailCodeState != EmailCodeState.Valid) {
                sideEffectFlow.emit(SignUpScreenSideEffect.Toast("인증코드를 확인해주세요."))
                return@launch
            }
            val requestData = SignUpRequest(
                userName = _uiState.value.inputUserName,
                password = _uiState.value.inputPassword,
                email = _uiState.value.inputEmail
            )
            signUpUseCase(requestData)
                .onEach { networkResult ->
                    networkResult.onSuccess { code, message, data ->
                        moveToSignUpSuccessScreen()
                    }.onError { code, message ->

                    }.onException {  }
                }
                .catch { LogUtil.e("flow error", "signUpUseCase") }
                .launchIn(viewModelScope)
        }
    }
}