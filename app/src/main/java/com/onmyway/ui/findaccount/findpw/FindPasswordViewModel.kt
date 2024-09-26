package com.onmyway.ui.findaccount.findpw

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.onmyway.data.findpw.EmailState
import com.onmyway.data.findpw.FindPasswordScreenSideEffect
import com.onmyway.data.findpw.FindPasswordScreenUIState
import com.onmyway.data.findpw.PasswordCheckingState
import com.onmyway.data.findpw.PasswordResettingScreenSideEffect
import com.onmyway.data.findpw.PasswordResettingScreenUIState
import com.onmyway.data.findpw.PasswordState
import com.onmyway.data.findpw.ResultState
import com.onmyway.util.InputTextValidator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class FindPasswordViewModel @Inject constructor(
    private val application: Application,
    private val inputTextValidator: InputTextValidator,
//    private val authenticateEmailUseCase: AuthenticateEmailUseCase,
//    private val verifyPasswordResetCodeUseCase: VerifyPasswordResetCodeUseCase,
//    private val resetPasswordUseCase: ResetPasswordUseCase
) : AndroidViewModel(application) {

    private val _findPasswordScreenUIState = MutableStateFlow(FindPasswordScreenUIState())
    val findPasswordScreenUIState = _findPasswordScreenUIState.asStateFlow()
    private val _passwordResettingScreenUIState = MutableStateFlow(PasswordResettingScreenUIState())
    val passwordResettingScreenUIState = _passwordResettingScreenUIState.asStateFlow()
    val findPasswordScreenSideEffectFlow = MutableSharedFlow<FindPasswordScreenSideEffect>()
    val passwordResettingScreenSideEffectFlow = MutableSharedFlow<PasswordResettingScreenSideEffect>()
    private var startTimer: Job? = null

    fun updateInputUserId(id: String) {
        _findPasswordScreenUIState.update {
            it.copy(inputUserId = id)
        }
    }

    fun updateInputEmail(email: String) {
        _findPasswordScreenUIState.update {
            it.copy(
                inputEmail = email,
                inputEmailState = if (inputTextValidator.validateEmail(email).result) EmailState.Satisfied else EmailState.Unsatisfied
            )
        }
    }

    fun updateInputVerificationCode(code: String) {
        _findPasswordScreenUIState.update {
            it.copy(inputVerificationCode = code)
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

    fun sendEmailVerificationCode() {
//        viewModelScope.launch(Dispatchers.Default) {
//            when (_findPasswordScreenUIState.value.inputEmailState) {
//                EmailState.Empty -> { findPasswordScreenSideEffectFlow.emit(FindPasswordScreenSideEffect.Toast("이메일을 입력해주세요.")) }
//                EmailState.Unsatisfied -> { findPasswordScreenSideEffectFlow.emit(FindPasswordScreenSideEffect.Toast("이메일을 확인해주세요.")) }
//                EmailState.Satisfied -> {
//                    if (_findPasswordScreenUIState.value.emailVerificationLeftTime > 120) {
//                        findPasswordScreenSideEffectFlow.emit(FindPasswordScreenSideEffect.Toast("${_findPasswordScreenUIState.value.emailVerificationLeftTime - 120}초 후에 다시 발송할 수 있습니다."))
//                        return@launch
//                    }
//                    startTimer?.cancel()
//                    startTimer = launch {
//                        _findPasswordScreenUIState.update {
//                            it.copy(
//                                isVerificationCodeSent = true,
//                                emailVerificationLeftTime = 180
//                            )
//                        }
//                        while (_findPasswordScreenUIState.value.emailVerificationLeftTime > 0) {
//                            _findPasswordScreenUIState.update {
//                                it.copy(emailVerificationLeftTime = it.emailVerificationLeftTime - 1)
//                            }
//                            delay(1000)
//                        }
//                    }
//                    val request =
//                        com.whereareyounow.domain.request.signup.AuthenticateEmailRequest(
//                            _findPasswordScreenUIState.value.inputEmail
//                        )
//                    val response = authenticateEmailUseCase(request)
//                    LogUtil.printNetworkLog(request, response, "이메일 인증")
//                    when (response) {
//                        is NetworkResult.Success -> { findPasswordScreenSideEffectFlow.emit(FindPasswordScreenSideEffect.Toast("인증 코드가 발송되었습니다.")) }
//                        is NetworkResult.Error -> {}
//                        is NetworkResult.Exception -> { findPasswordScreenSideEffectFlow.emit(FindPasswordScreenSideEffect.Toast("오류가 발생했습니다.")) }
//                    }
//                }
//            }
//        }
    }

    fun verifyPasswordResetCode(
        moveToPasswordResettingScreen: (String, ResultState) -> Unit
    ) {
//        viewModelScope.launch(Dispatchers.Default) {
//            // 유효시간이 지나면 인증을 다시 받아야 한다.
//            if (_findPasswordScreenUIState.value.emailVerificationLeftTime <= 0) {
//                findPasswordScreenSideEffectFlow.emit(FindPasswordScreenSideEffect.Toast("유효시간이 만료되었습니다. 인증 코드를 재전송해주세요."))
//                return@launch
//            }
//            val request =
//                VerifyPasswordResetCodeRequest(
//                    userId = _findPasswordScreenUIState.value.inputUserId,
//                    email = _findPasswordScreenUIState.value.inputEmail,
//                    code = _findPasswordScreenUIState.value.inputVerificationCode
//                )
//            val response = verifyPasswordResetCodeUseCase(request)
//            LogUtil.printNetworkLog(request, response, "비밀번호 재설정 코드 인증")
//            when (response) {
//                is NetworkResult.Success -> {
//                    withContext(Dispatchers.Main) {
//                        moveToPasswordResettingScreen(
//                            _findPasswordScreenUIState.value.inputUserId,
//                            ResultState.OK
//                        )
//                    }
//                }
//                is NetworkResult.Error -> {
//                    when (response.code) {
//                        400 -> {
//                            withContext(Dispatchers.Main) {
//                                moveToPasswordResettingScreen(
//                                    "",
//                                    ResultState.MemberMismatch
//                                )
//                            }
//                        }
//                        404 -> {
//                            withContext(Dispatchers.Main) {
//                                moveToPasswordResettingScreen(
//                                    "",
//                                    ResultState.EmailNotFound
//                                )
//                            }
//                        }
//                    }
//                }
//                is NetworkResult.Exception -> { findPasswordScreenSideEffectFlow.emit(FindPasswordScreenSideEffect.Toast("오류가 발생했습니다.")) }
//            }
//        }
    }

    fun resetPassword(
        userId: String,
        moveToSignInScreen: () -> Unit
    ) {
//        viewModelScope.launch(Dispatchers.Default) {
//            when (_passwordResettingScreenUIState.value.inputPasswordState) {
//                PasswordState.Empty -> {
//                    passwordResettingScreenSideEffectFlow.emit(PasswordResettingScreenSideEffect.Toast("비밀번호를 입력해주세요."))
//                    return@launch
//                }
//                PasswordState.Unsatisfied -> {
//                    passwordResettingScreenSideEffectFlow.emit(PasswordResettingScreenSideEffect.Toast("비밀번호를 확인해주세요."))
//                    return@launch
//                }
//                PasswordState.Satisfied -> {}
//            }
//            when (_passwordResettingScreenUIState.value.passwordCheckingState) {
//                PasswordCheckingState.Empty -> {
//                    passwordResettingScreenSideEffectFlow.emit(PasswordResettingScreenSideEffect.Toast("비밀번호를 다시 한 번 입력해주세요."))
//                    return@launch
//                }
//                PasswordCheckingState.Unsatisfied -> {
//                    passwordResettingScreenSideEffectFlow.emit(PasswordResettingScreenSideEffect.Toast("비밀번호가 일치하지 않습니다."))
//                    return@launch
//                }
//                PasswordCheckingState.Satisfied -> {
//                    val request = ResetPasswordRequest(
//                        userId = userId,
//                        password = _passwordResettingScreenUIState.value.inputPassword,
//                        checkPassword = _passwordResettingScreenUIState.value.inputPasswordForChecking
//                    )
//                    val response = resetPasswordUseCase(request)
//                    LogUtil.printNetworkLog(request, response, "비밀번호 재설정")
//                    when (response) {
//                        is NetworkResult.Success -> {
//                            withContext(Dispatchers.Main) {
//                                moveToSignInScreen()
//                            }
//                        }
//                        is NetworkResult.Error -> {
//
//                        }
//                        is NetworkResult.Exception -> { passwordResettingScreenSideEffectFlow.emit(PasswordResettingScreenSideEffect.Toast("오류가 발생했습니다.")) }
//                    }
//                }
//            }
//        }
    }
}