package com.whereareyounow.ui.signup

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.whereareyounow.data.signup.EmailState
import com.whereareyounow.data.signup.EmailVerificationProgressState
import com.whereareyounow.data.signup.PasswordCheckingState
import com.whereareyounow.data.signup.PasswordState
import com.whereareyounow.data.signup.SignUpScreenSideEffect
import com.whereareyounow.data.signup.SignUpScreenUIState
import com.whereareyounow.data.signup.UserIdState
import com.whereareyounow.data.signup.UserNameState
import com.whereareyounow.data.signup.VerificationCodeState
import com.whereareyounow.domain.entity.apimessage.signup.AuthenticateEmailCodeRequest
import com.whereareyounow.domain.entity.apimessage.signup.AuthenticateEmailRequest
import com.whereareyounow.domain.entity.apimessage.signup.SignUpRequest
import com.whereareyounow.domain.usecase.signup.AuthenticateEmailCodeUseCase
import com.whereareyounow.domain.usecase.signup.AuthenticateEmailUseCase
import com.whereareyounow.domain.usecase.signup.CheckEmailDuplicateUseCase
import com.whereareyounow.domain.usecase.signup.CheckIdDuplicateUseCase
import com.whereareyounow.domain.usecase.signup.SignUpUseCase
import com.whereareyounow.domain.util.LogUtil
import com.whereareyounow.domain.util.NetworkResult
import com.whereareyounow.util.InputTextValidator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val application: Application,
    private val checkIdDuplicateUseCase: CheckIdDuplicateUseCase,
    private val checkEmailDuplicateUseCase: CheckEmailDuplicateUseCase,
    private val authenticateEmailUseCase: AuthenticateEmailUseCase,
    private val authenticateEmailCodeUseCase: AuthenticateEmailCodeUseCase,
    private val signUpUseCase: SignUpUseCase,
    private val inputTextValidator: InputTextValidator,
) : AndroidViewModel(application) {

    private val _signUpScreenUIState = MutableStateFlow(SignUpScreenUIState())
    val signUpScreenUIState = _signUpScreenUIState.asStateFlow()
    val signUpScreenSideEffectFlow = MutableSharedFlow<SignUpScreenSideEffect>()
    private var startTimer: Job? = null

    fun updateInputUserName(userName: String) {
        _signUpScreenUIState.update {
            it.copy(
                inputUserName = userName,
                inputUserNameState = if (inputTextValidator.validateUserName(userName).result) UserNameState.SATISFIED else UserNameState.UNSATISFIED
            )
        }
    }

    fun updateInputUserId(userId: String) {
        _signUpScreenUIState.update {
            it.copy(
                inputUserId = userId,
                inputUserIdState = if (inputTextValidator.validateUserId(userId).result) UserIdState.SATISFIED else UserIdState.UNSATISFIED
            )
        }
    }

    fun updateInputPassword(password: String) {
        _signUpScreenUIState.update {
            it.copy(
                inputPassword = password,
                inputPasswordState = if (inputTextValidator.validatePassword(password).result) PasswordState.SATISFIED else PasswordState.UNSATISFIED,
                inputPasswordForCheckingState = if (password == it.inputPasswordForChecking) PasswordCheckingState.SATISFIED else PasswordCheckingState.UNSATISFIED
            )
        }
    }

    fun updateInputPasswordForChecking(password: String) {
        _signUpScreenUIState.update {
            it.copy(
                inputPasswordForChecking = password,
                inputPasswordForCheckingState = if (password == it.inputPassword) PasswordCheckingState.SATISFIED else PasswordCheckingState.UNSATISFIED
            )
        }
    }

    fun updateInputEmail(email: String) {
        _signUpScreenUIState.update {
            it.copy(
                inputEmail = email,
                inputEmailState = if (inputTextValidator.validateEmail(email).result)  EmailState.SATISFIED else EmailState.UNSATISFIED,
                emailVerificationProgressState = EmailVerificationProgressState.DUPLICATE_UNCHECKED
            )
        }
    }

    fun updateInputVerificationCode(code: String) {
        _signUpScreenUIState.update {
            it.copy(
                inputVerificationCode = code
            )
        }
    }

    fun checkIdDuplicate() {
        viewModelScope.launch {
            when (_signUpScreenUIState.value.inputUserIdState) {
                UserIdState.EMPTY -> { signUpScreenSideEffectFlow.emit(SignUpScreenSideEffect.Toast("아이디를 입력해주세요.")) }
                UserIdState.UNSATISFIED -> { signUpScreenSideEffectFlow.emit(SignUpScreenSideEffect.Toast("아이디를 확인해주세요.")) }
                UserIdState.DUPLICATED -> { signUpScreenSideEffectFlow.emit(SignUpScreenSideEffect.Toast("중복된 아이디입니다.")) }
                UserIdState.UNIQUE -> { signUpScreenSideEffectFlow.emit(SignUpScreenSideEffect.Toast("이미 확인되었습니다. 다음 단계를 진행해주세요.")) }
                else -> {
                    val response = checkIdDuplicateUseCase(_signUpScreenUIState.value.inputUserId)
                    LogUtil.printNetworkLog("userId = ${_signUpScreenUIState.value.inputUserId}", response, "아이디 중복 체크")
                    when (response) {
                        is NetworkResult.Success -> {
                            _signUpScreenUIState.update {
                                it.copy(inputUserIdState = UserIdState.UNIQUE)
                            }
                        }
                        is NetworkResult.Error -> {
                            _signUpScreenUIState.update {
                                it.copy(inputUserIdState = UserIdState.DUPLICATED)
                            }
                        }
                        is NetworkResult.Exception -> { signUpScreenSideEffectFlow.emit(SignUpScreenSideEffect.Toast("오류가 발생했습니다.")) }
                    }
                }
            }
        }
    }

    fun checkEmailDuplicate() {
            viewModelScope.launch {
                if (_signUpScreenUIState.value.inputEmailState == EmailState.SATISFIED) {
                    val response = checkEmailDuplicateUseCase(_signUpScreenUIState.value.inputEmail)
                    LogUtil.printNetworkLog("email = ${_signUpScreenUIState.value.inputEmail}", response, "이메일 중복 확인")
                    when (response) {
                        is NetworkResult.Success -> {
                            _signUpScreenUIState.update {
                                it.copy(
                                    inputEmailState = EmailState.UNIQUE,
                                    emailVerificationProgressState = EmailVerificationProgressState.DUPLICATE_CHECKED
                                )
                            }
                        }
                        is NetworkResult.Error -> {
                            _signUpScreenUIState.update {
                                it.copy(
                                    inputEmailState = EmailState.DUPLICATED,
                                    emailVerificationProgressState = EmailVerificationProgressState.DUPLICATE_UNCHECKED
                                )
                            }
                        }
                        is NetworkResult.Exception -> { signUpScreenSideEffectFlow.emit(SignUpScreenSideEffect.Toast("오류가 발생했습니다.")) }
                    }
                } else { signUpScreenSideEffectFlow.emit(SignUpScreenSideEffect.Toast("이메일을 확인해주세요.")) }
            }
    }

    fun verifyEmail() {
        // 인증 코드를 발송하고 1분이 지나지 않았으면 다시 발송할 수 없다.
        if (_signUpScreenUIState.value.emailVerificationCodeLeftTime > 120) {
            viewModelScope.launch {
                signUpScreenSideEffectFlow.emit(SignUpScreenSideEffect.Toast("${_signUpScreenUIState.value.emailVerificationCodeLeftTime - 120}초 후에 다시 발송할 수 있습니다."))
            }
            return
        }
        startTimer?.cancel()
        startTimer = viewModelScope.launch {
            _signUpScreenUIState.update {
                it.copy(
                    emailVerificationProgressState = EmailVerificationProgressState.VERIFICATION_REQUESTED,
                    inputVerificationCodeState = VerificationCodeState.EMPTY
                )
            }
            _signUpScreenUIState.update {
                it.copy(emailVerificationCodeLeftTime = 180)
            }
            while (_signUpScreenUIState.value.emailVerificationCodeLeftTime > 0) {
                _signUpScreenUIState.update {
                    it.copy(emailVerificationCodeLeftTime = it.emailVerificationCodeLeftTime - 1)
                }
                delay(1000)
            }
        }

        viewModelScope.launch {
            val request = AuthenticateEmailRequest(_signUpScreenUIState.value.inputEmail)
            val response = authenticateEmailUseCase(request)
            LogUtil.printNetworkLog(request, response, "이메일 인증 코드 전송")
            when (response) {
                is NetworkResult.Success -> { signUpScreenSideEffectFlow.emit(SignUpScreenSideEffect.Toast("인증 코드가 발송되었습니다.")) }
                is NetworkResult.Error -> {

                }
                is NetworkResult.Exception -> { signUpScreenSideEffectFlow.emit(SignUpScreenSideEffect.Toast("오류가 발생했습니다.")) }
            }
        }
    }

    fun verifyEmailCode() {
        viewModelScope.launch {
            // 유효시간이 지나면 인증을 다시 받아야 한다.
            if (_signUpScreenUIState.value.emailVerificationCodeLeftTime <= 0) {
                signUpScreenSideEffectFlow.emit(SignUpScreenSideEffect.Toast("유효시간이 만료되었습니다. 인증코드를 재전송해주세요."))
                return@launch
            }
            if (_signUpScreenUIState.value.inputVerificationCodeState == VerificationCodeState.SATISFIED) {
                signUpScreenSideEffectFlow.emit(SignUpScreenSideEffect.Toast("이미 확인되었습니다. 다음 단계를 진행해주세요."))
                return@launch
            }
            val request = AuthenticateEmailCodeRequest(_signUpScreenUIState.value.inputEmail, _signUpScreenUIState.value.inputVerificationCode)
            val response = authenticateEmailCodeUseCase(request)
            LogUtil.printNetworkLog(request, response, "이메일 인증 코드 확인")
            when (response) {
                is NetworkResult.Success -> {
                    _signUpScreenUIState.update {
                        it.copy(
                            inputVerificationCodeState = VerificationCodeState.SATISFIED
                        )
                    }
                }
                is NetworkResult.Error -> {
                    _signUpScreenUIState.update {
                        it.copy(
                            inputVerificationCodeState = VerificationCodeState.UNSATISFIED
                        )
                    }
                }
                is NetworkResult.Exception -> { signUpScreenSideEffectFlow.emit(SignUpScreenSideEffect.Toast("오류가 발생했습니다.")) }
            }
        }
    }

    fun signUp(
        moveToSignUpSuccessScreen: () -> Unit
    ) {
        viewModelScope.launch {
            if (_signUpScreenUIState.value.inputUserNameState != UserNameState.SATISFIED) {
                signUpScreenSideEffectFlow.emit(SignUpScreenSideEffect.Toast("사용자명을 확인해주세요."))
                return@launch
            }
            if (_signUpScreenUIState.value.inputUserIdState != UserIdState.UNIQUE) {
                signUpScreenSideEffectFlow.emit(SignUpScreenSideEffect.Toast("아이디를 확인해주세요."))
                return@launch
            }
            if (_signUpScreenUIState.value.inputPasswordState != PasswordState.SATISFIED) {
                signUpScreenSideEffectFlow.emit(SignUpScreenSideEffect.Toast("비밀번호를 확인해주세요."))
                return@launch
            }
            if (_signUpScreenUIState.value.inputPasswordForCheckingState != PasswordCheckingState.SATISFIED) {
                signUpScreenSideEffectFlow.emit(SignUpScreenSideEffect.Toast("비밀번호 확인을 다시해주세요."))
                return@launch
            }
            if (_signUpScreenUIState.value.inputEmailState != EmailState.UNIQUE) {
                signUpScreenSideEffectFlow.emit(SignUpScreenSideEffect.Toast("이메일을 확인해주세요."))
                return@launch
            }
            if (_signUpScreenUIState.value.inputVerificationCodeState != VerificationCodeState.SATISFIED) {
                signUpScreenSideEffectFlow.emit(SignUpScreenSideEffect.Toast("인증코드를 확인해주세요."))
                return@launch
            }
            val request = SignUpRequest(
                userName = _signUpScreenUIState.value.inputUserName,
                userId = _signUpScreenUIState.value.inputUserId,
                password = _signUpScreenUIState.value.inputPassword,
                email = _signUpScreenUIState.value.inputEmail
            )
            val response = signUpUseCase(request)
            LogUtil.printNetworkLog(request, response, "회원가입")
            when (response) {
                is NetworkResult.Success -> {
                    withContext(Dispatchers.Main) {
                        moveToSignUpSuccessScreen()
                    }
                }
                is NetworkResult.Error -> {

                }
                is NetworkResult.Exception -> { signUpScreenSideEffectFlow.emit(SignUpScreenSideEffect.Toast("오류가 발생했습니다.")) }
            }
        }
    }
}