package com.whereareyounow.ui.signup

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.whereareyounow.domain.entity.apimessage.signup.AuthenticateEmailCodeRequest
import com.whereareyounow.domain.entity.apimessage.signup.AuthenticateEmailRequest
import com.whereareyounow.domain.entity.apimessage.signup.CheckIdDuplicateResponse
import com.whereareyounow.domain.entity.apimessage.signup.SignUpRequest
import com.whereareyounow.domain.usecase.signup.AuthenticateEmailCodeUseCase
import com.whereareyounow.domain.usecase.signup.AuthenticateEmailUseCase
import com.whereareyounow.domain.usecase.signup.CheckEmailDuplicateUseCase
import com.whereareyounow.domain.usecase.signup.CheckIdDuplicateUseCase
import com.whereareyounow.domain.usecase.signup.SignUpUseCase
import com.whereareyounow.domain.util.LogUtil
import com.whereareyounow.domain.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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
    private val signUpUseCase: SignUpUseCase
) : AndroidViewModel(application) {

    private val nameCondition = Regex("^[\\uAC00-\\uD7A3a-zA-Z]{4,10}\$")
    private val idCondition = Regex("^[a-z][a-z0-9]{3,9}\$")
    private val passwordCondition = Regex("^(?=[A-Za-z])(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])[A-Za-z0-9]{4,10}\$")
    private val emailCondition = Regex("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}\$")

    private val _inputUserName = MutableStateFlow("")
    val inputUserName: StateFlow<String> = _inputUserName
    private val _inputUserNameState = MutableStateFlow(UserNameState.EMPTY)
    val inputUserNameState: StateFlow<UserNameState> = _inputUserNameState

    private val _inputUserId = MutableStateFlow("")
    val inputUserId: StateFlow<String> = _inputUserId
    private val _inputUserIdState = MutableStateFlow(UserIdState.EMPTY)
    val inputUserIdState: StateFlow<UserIdState> = _inputUserIdState

    private val _inputPassword = MutableStateFlow("")
    val inputPassword: StateFlow<String> = _inputPassword
    private val _inputPasswordState = MutableStateFlow(PasswordState.EMPTY)
    val inputPasswordState: StateFlow<PasswordState> = _inputPasswordState

    private val _inputPasswordForChecking = MutableStateFlow("")
    val inputPasswordForChecking: StateFlow<String> = _inputPasswordForChecking
    private val _inputPasswordForCheckingState = MutableStateFlow(PasswordCheckingState.EMPTY)
    val inputPasswordForCheckingState: StateFlow<PasswordCheckingState> = _inputPasswordForCheckingState

    private val _inputEmail = MutableStateFlow("")
    val inputEmail: StateFlow<String> = _inputEmail
    private val _inputEmailState = MutableStateFlow(EmailState.EMPTY)
    val inputEmailState: StateFlow<EmailState> = _inputEmailState

    private val _inputVerificationCode = MutableStateFlow("")
    val inputVerificationCode: StateFlow<String> = _inputVerificationCode
    private val _inputVerificationCodeState = MutableStateFlow(VerificationCodeState.EMPTY)
    val inputVerificationCodeState: StateFlow<VerificationCodeState> = _inputVerificationCodeState

    private val _isEmailDuplicationChecked = MutableStateFlow(false)
    val isEmailDuplicationChecked: StateFlow<Boolean> = _isEmailDuplicationChecked

    private val _isEmailVerificationInProgress = MutableStateFlow(false)
    val isEmailVerificationInProgress: StateFlow<Boolean> = _isEmailVerificationInProgress

    fun updateInputUserName(userName: String) {
        _inputUserName.update { userName }
        _inputUserNameState.update {
            if (userName == "") {
                UserNameState.EMPTY
            } else {
                if (userName.matches(nameCondition)) UserNameState.SATISFIED else UserNameState.UNSATISFIED
            }
        }
    }

    fun updateInputUserId(userId: String) {
        _inputUserId.update { userId }
        _inputUserIdState.update {
            if (userId == "") {
                UserIdState.EMPTY
            } else {
                if (userId.matches(idCondition)) UserIdState.SATISFIED else UserIdState.UNSATISFIED
            }
        }
    }

    fun updateInputPassword(password: String) {
        _inputPassword.update { password }
        _inputPasswordState.update {
            if (password == "") {
                PasswordState.EMPTY
            } else {
                if (password.matches(passwordCondition)) PasswordState.SATISFIED else PasswordState.UNSATISFIED
            }
        }
    }

    fun updateInputPasswordForChecking(password: String) {
        _inputPasswordForChecking.update { password }
        _inputPasswordForCheckingState.update {
            if (password == "") {
                PasswordCheckingState.EMPTY
            } else {
                if (password == _inputPassword.value) PasswordCheckingState.SATISFIED else PasswordCheckingState.UNSATISFIED
            }
        }
    }

    fun updateInputEmail(email: String) {
        _inputEmail.update { email }
        _inputEmailState.update {
            if (email == "") {
                EmailState.EMPTY
            } else {
                if (email.matches(emailCondition)) EmailState.SATISFIED else EmailState.UNSATISFIED
            }
        }
        _isEmailDuplicationChecked.update { false }
        _isEmailVerificationInProgress.update { false }
    }

    fun updateInputVerificationCode(code: String) {
        _inputVerificationCode.update { code }
    }

    fun checkIdDuplicate() {
        viewModelScope.launch {
            if (_inputUserIdState.value == UserIdState.SATISFIED) {
                val response = checkIdDuplicateUseCase(_inputUserId.value)
                LogUtil.printNetworkLog(response, "아이디 중복 체크")
                when (response) {
                    is NetworkResult.Success -> {
                        _inputUserIdState.update { UserIdState.UNIQUE }
                    }
                    is NetworkResult.Error -> {
                        _inputUserIdState.update { UserIdState.DUPLICATED }
                    }
                    is NetworkResult.Exception -> {

                    }
                }
            } else {
                withContext(Dispatchers.Main) {
                    Toast.makeText(application, "아이디를 확인해주세요.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun checkEmailDuplicate() {
            viewModelScope.launch {
                if (_inputEmailState.value == EmailState.SATISFIED) {
                    val response = checkEmailDuplicateUseCase(_inputEmail.value)
                    LogUtil.printNetworkLog(response, "이메일 중복 확인")
                    when (response) {
                        is NetworkResult.Success -> {
                            _inputEmailState.update { EmailState.UNIQUE }
                            _isEmailDuplicationChecked.update { true }
                        }
                        is NetworkResult.Error -> {
                            _inputEmailState.update { EmailState.DUPLICATED }
                            _isEmailDuplicationChecked.update { false }
                        }
                        is NetworkResult.Exception -> {

                        }
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(application, "이메일을 확인해주세요.", Toast.LENGTH_SHORT).show()
                    }
                }
            }
    }

    fun verifyEmail() {
        viewModelScope.launch {
            val request = AuthenticateEmailRequest(_inputEmail.value)
            val response = authenticateEmailUseCase(request)
            LogUtil.printNetworkLog(response, "이메일 인증 코드 전송")
            when (response) {
                is NetworkResult.Success -> {
                    _isEmailVerificationInProgress.update { true }
                }
                is NetworkResult.Error -> {

                }
                is NetworkResult.Exception -> {

                }
            }
        }
    }

    fun verifyEmailCode() {
        viewModelScope.launch {
            val request = AuthenticateEmailCodeRequest(_inputEmail.value, _inputVerificationCode.value)
            val response = authenticateEmailCodeUseCase(request)
            LogUtil.printNetworkLog(response, "이메일 인증 코드 확인")
            when (response) {
                is NetworkResult.Success -> {
                    _inputVerificationCodeState.update { VerificationCodeState.SATISFIED }
                }
                is NetworkResult.Error -> {
                    _inputVerificationCodeState.update { VerificationCodeState.UNSATISFIED }
                }
                is NetworkResult.Exception -> {

                }
            }
        }
    }

    fun signUp(
        moveToSignUpSuccessScreen: () -> Unit
    ) {
        if (_inputUserNameState.value != UserNameState.SATISFIED) {
            Toast.makeText(application, "사용자명을 확인해주세요.", Toast.LENGTH_SHORT).show()
            return
        }
        if (_inputUserIdState.value != UserIdState.UNIQUE) {
            Toast.makeText(application, "아이디를 확인해주세요.", Toast.LENGTH_SHORT).show()
            return
        }
        if (_inputPasswordState.value != PasswordState.SATISFIED) {
            Toast.makeText(application, "비밀번호를 확인해주세요.", Toast.LENGTH_SHORT).show()
            return
        }
        if (_inputPasswordForCheckingState.value != PasswordCheckingState.SATISFIED) {
            Toast.makeText(application, "비밀번호 확인을 다시해주세요.", Toast.LENGTH_SHORT).show()
            return
        }
        if (_inputEmailState.value != EmailState.UNIQUE) {
            Toast.makeText(application, "이메일을 확인해주세요.", Toast.LENGTH_SHORT).show()
            return
        }
        if (_inputVerificationCodeState.value != VerificationCodeState.SATISFIED) {
            Toast.makeText(application, "인증코드를 확인해주세요.", Toast.LENGTH_SHORT).show()
            return
        }
        viewModelScope.launch {
            val request = SignUpRequest(
                userName = _inputUserName.value,
                userId = _inputUserId.value,
                password = _inputPassword.value,
                email = _inputEmail.value
            )
            val response = signUpUseCase(request)
            LogUtil.printNetworkLog(response, "회원가입")
            when (response) {
                is NetworkResult.Success -> {
                    withContext(Dispatchers.Main) {
                        moveToSignUpSuccessScreen()
                    }
                }
                is NetworkResult.Error -> {

                }
                is NetworkResult.Exception -> {

                }
            }
        }
    }
}