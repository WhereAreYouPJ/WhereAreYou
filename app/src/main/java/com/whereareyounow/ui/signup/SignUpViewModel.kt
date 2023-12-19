package com.whereareyounow.ui.signup

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val application: Application
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

    private val _isVerificationInProgress = MutableStateFlow(false)
    val isVerificationInProgress: StateFlow<Boolean> = _isVerificationInProgress

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
        _isVerificationInProgress.update { false }
    }

    fun updateInputVerificationCode(code: String) {
        _inputVerificationCode.update { code }
    }
}