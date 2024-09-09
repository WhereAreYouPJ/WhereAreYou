package com.whereareyounow.ui.signup

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.whereareyounow.data.signup.SignUpEmailButtonState
import com.whereareyounow.data.signup.SignUpEmailState
import com.whereareyounow.data.signup.SignUpScreenSideEffect
import com.whereareyounow.data.signup.SignUpScreenUIState
import com.whereareyounow.data.signup.SignUpUserNameState
import com.whereareyounow.data.signup.SignUpCodeOKButtonState
import com.whereareyounow.data.signup.SignUpPasswordState
import com.whereareyounow.data.signup.SignUpPasswordCheckState
import com.whereareyounow.domain.request.member.SendEmailCodeRequest
import com.whereareyounow.domain.request.member.VerifyEmailCodeRequest
import com.whereareyounow.domain.usecase.member.SendEmailCodeUseCase
import com.whereareyounow.domain.usecase.member.VerifyEmailCodeUseCase
import com.whereareyounow.domain.util.LogUtil
import com.whereareyounow.domain.util.onError
import com.whereareyounow.domain.util.onException
import com.whereareyounow.domain.util.onSuccess
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
) : AndroidViewModel(application) {

    private val _uiState = MutableStateFlow(SignUpScreenUIState())
    val uiState = _uiState.asStateFlow()
    val signUpScreenSideEffectFlow = MutableSharedFlow<SignUpScreenSideEffect>()
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
                requestButtonState = SignUpEmailButtonState.Request
            )
        }
    }

    fun sendEmailCode() {
        val requestData = SendEmailCodeRequest(email = "txepahs@naver.com")
        sendEmailCodeUseCase(requestData)
            .onEach { networkResult ->
                networkResult.onSuccess { code, message, data ->
                    _uiState.update {
                        it.copy(
                            requestButtonState = SignUpEmailButtonState.RequestDone
                        )
                    }
                    // 인증 코드를 발송하고 5분이 지나지 않았으면 다시 발송할 수 없다.
                    if (_uiState.value.emailCodeLeftTime > 300) {
                        viewModelScope.launch {
                            signUpScreenSideEffectFlow.emit(SignUpScreenSideEffect.Toast("${_uiState.value.emailCodeLeftTime - 120}초 후에 다시 발송할 수 있습니다."))
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
        _uiState.update {
            it.copy(
                inputEmailState =  if (inputTextValidator.validateEmail(it.inputEmail).result) SignUpEmailState.Valid else SignUpEmailState.Invalid
            )
        }
    }

    fun updateInputVerificationCode(code: String) {
        _uiState.update {
            it.copy(
                inputVerificationCode = code,
                verificationCodeButtonState = if (code == "") SignUpCodeOKButtonState.Inactive else SignUpCodeOKButtonState.Active
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

    fun checkEmailDuplicate() {
//            viewModelScope.launch {
//                if (_uiState.value.inputEmailState == EmailState.Satisfied) {
//                    val response = checkEmailDuplicateUseCase(_uiState.value.inputEmail)
//                    LogUtil.printNetworkLog("email = ${_uiState.value.inputEmail}", response, "이메일 중복 확인")
//                    when (response) {
//                        is NetworkResult.Success -> {
//                            _uiState.update {
//                                it.copy(
//                                    inputEmailState = EmailState.Unique,
//                                    emailVerificationProgressState = EmailVerificationProgressState.DuplicateChecked
//                                )
//                            }
//                        }
//                        is NetworkResult.Error -> {
//                            _uiState.update {
//                                it.copy(
//                                    inputEmailState = EmailState.Duplicated,
//                                    emailVerificationProgressState = EmailVerificationProgressState.DuplicateUnchecked
//                                )
//                            }
//                        }
//                        is NetworkResult.Exception -> { signUpScreenSideEffectFlow.emit(SignUpScreenSideEffect.Toast("오류가 발생했습니다.")) }
//                    }
//                } else { signUpScreenSideEffectFlow.emit(SignUpScreenSideEffect.Toast("이메일을 확인해주세요.")) }
//            }
    }

    fun verifyEmail() {
        // 인증 코드를 발송하고 1분이 지나지 않았으면 다시 발송할 수 없다.
//        if (_uiState.value.emailVerificationCodeLeftTime > 120) {
//            viewModelScope.launch {
//                signUpScreenSideEffectFlow.emit(SignUpScreenSideEffect.Toast("${_uiState.value.emailVerificationCodeLeftTime - 120}초 후에 다시 발송할 수 있습니다."))
//            }
//            return
//        }
//        startTimer?.cancel()
//        startTimer = viewModelScope.launch {
//            _uiState.update {
//                it.copy(
//                    emailVerificationProgressState = EmailVerificationProgressState.VerificationRequested,
//                    inputVerificationCodeState = VerificationCodeState.Empty
//                )
//            }
//            _uiState.update {
//                it.copy(emailVerificationCodeLeftTime = 180)
//            }
//            while (_uiState.value.emailVerificationCodeLeftTime > 0) {
//                _uiState.update {
//                    it.copy(emailVerificationCodeLeftTime = it.emailVerificationCodeLeftTime - 1)
//                }
//                delay(1000)
//            }
//        }

//        viewModelScope.launch {
//            val request = com.whereareyounow.domain.request.signup.AuthenticateEmailRequest(
//                _uiState.value.inputEmail
//            )
//            val response = authenticateEmailUseCase(request)
//            LogUtil.printNetworkLog(request, response, "이메일 인증 코드 전송")
//            when (response) {
//                is NetworkResult.Success -> { signUpScreenSideEffectFlow.emit(SignUpScreenSideEffect.Toast("인증 코드가 발송되었습니다.")) }
//                is NetworkResult.Error -> {
//
//                }
//                is NetworkResult.Exception -> { signUpScreenSideEffectFlow.emit(SignUpScreenSideEffect.Toast("오류가 발생했습니다.")) }
//            }
//        }
    }

    fun verifyEmailCode() {
        if (_uiState.value.emailCodeLeftTime <= 0) {
            viewModelScope.launch {
                signUpScreenSideEffectFlow.emit(SignUpScreenSideEffect.Toast("유효시간이 만료되었습니다. 인증코드를 재전송해주세요."))
            }
            return
        }
        val requestData = VerifyEmailCodeRequest(
            email = _uiState.value.inputEmail,
            code = _uiState.value.inputVerificationCode
        )
        verifyEmailCodeUseCase(requestData)
            .onEach { networkResult ->

            }
            .catch { LogUtil.e("flow error", "verifyEmailCode") }
            .launchIn(viewModelScope)
//        viewModelScope.launch {
//            // 유효시간이 지나면 인증을 다시 받아야 한다.
//            if (_uiState.value.emailVerificationCodeLeftTime <= 0) {
//                signUpScreenSideEffectFlow.emit(SignUpScreenSideEffect.Toast("유효시간이 만료되었습니다. 인증코드를 재전송해주세요."))
//                return@launch
//            }
//            if (_uiState.value.inputVerificationCodeState == VerificationCodeState.Satisfied) {
//                signUpScreenSideEffectFlow.emit(SignUpScreenSideEffect.Toast("이미 확인되었습니다. 다음 단계를 진행해주세요."))
//                return@launch
//            }
//            val request = com.whereareyounow.domain.request.signup.AuthenticateEmailCodeRequest(
//                _uiState.value.inputEmail,
//                _uiState.value.inputVerificationCode
//            )
//            val response = authenticateEmailCodeUseCase(request)
//            LogUtil.printNetworkLog(request, response, "이메일 인증 코드 확인")
//            when (response) {
//                is NetworkResult.Success -> {
//                    _uiState.update {
//                        it.copy(
//                            inputVerificationCodeState = VerificationCodeState.Satisfied
//                        )
//                    }
//                }
//                is NetworkResult.Error -> {
//                    _uiState.update {
//                        it.copy(
//                            inputVerificationCodeState = VerificationCodeState.Unsatisfied
//                        )
//                    }
//                }
//                is NetworkResult.Exception -> { signUpScreenSideEffectFlow.emit(SignUpScreenSideEffect.Toast("오류가 발생했습니다.")) }
//            }
//        }
    }

    fun signUp(
        moveToSignUpSuccessScreen: () -> Unit
    ) {
//        viewModelScope.launch {
//            if (_uiState.value.inputUserNameState != UserNameState.Satisfied) {
//                signUpScreenSideEffectFlow.emit(SignUpScreenSideEffect.Toast("사용자명을 확인해주세요."))
//                return@launch
//            }
//            if (_uiState.value.inputUserIdState != UserIdState.Unique) {
//                signUpScreenSideEffectFlow.emit(SignUpScreenSideEffect.Toast("아이디를 확인해주세요."))
//                return@launch
//            }
//            if (_uiState.value.inputPasswordState != PasswordState.Satisfied) {
//                signUpScreenSideEffectFlow.emit(SignUpScreenSideEffect.Toast("비밀번호를 확인해주세요."))
//                return@launch
//            }
//            if (_uiState.value.inputPasswordForCheckingState != PasswordCheckingState.Satisfied) {
//                signUpScreenSideEffectFlow.emit(SignUpScreenSideEffect.Toast("비밀번호 확인을 다시해주세요."))
//                return@launch
//            }
//            if (_uiState.value.inputEmailState != EmailState.Unique) {
//                signUpScreenSideEffectFlow.emit(SignUpScreenSideEffect.Toast("이메일을 확인해주세요."))
//                return@launch
//            }
//            if (_uiState.value.inputVerificationCodeState != VerificationCodeState.Satisfied) {
//                signUpScreenSideEffectFlow.emit(SignUpScreenSideEffect.Toast("인증코드를 확인해주세요."))
//                return@launch
//            }
//            val request = SignUpRequest(
//                userName = _uiState.value.inputUserName,
//                userId = _uiState.value.inputUserId,
//                password = _uiState.value.inputPassword,
//                email = _uiState.value.inputEmail
//            )
//            val response = signUpUseCase(request)
//            LogUtil.printNetworkLog(request, response, "회원가입")
//            when (response) {
//                is NetworkResult.Success -> {
//                    withContext(Dispatchers.Main) {
//                        moveToSignUpSuccessScreen()
//                    }
//                }
//                is NetworkResult.Error -> {
//
//                }
//                is NetworkResult.Exception -> { signUpScreenSideEffectFlow.emit(SignUpScreenSideEffect.Toast("오류가 발생했습니다.")) }
//            }
//        }
    }
}