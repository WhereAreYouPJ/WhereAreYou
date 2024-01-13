package com.whereareyounow.ui.findpw

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.whereareyounow.domain.entity.apimessage.signin.ResetPasswordRequest
import com.whereareyounow.domain.entity.apimessage.signin.VerifyPasswordResetCodeRequest
import com.whereareyounow.domain.entity.apimessage.signup.AuthenticateEmailRequest
import com.whereareyounow.domain.usecase.signin.ResetPasswordUseCase
import com.whereareyounow.domain.usecase.signin.VerifyPasswordResetCodeUseCase
import com.whereareyounow.domain.usecase.signup.AuthenticateEmailUseCase
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
class FindPasswordViewModel @Inject constructor(
    private val application: Application,
    private val authenticateEmailUseCase: AuthenticateEmailUseCase,
    private val verifyPasswordResetCodeUseCase: VerifyPasswordResetCodeUseCase,
    private val resetPasswordUseCase: ResetPasswordUseCase
) : AndroidViewModel(application) {

    private val _screenState = MutableStateFlow(ScreenState.EmailAuthentication)
    val screenState: StateFlow<ScreenState> = _screenState
    private val _inputUserId = MutableStateFlow("")
    val inputUserId: StateFlow<String> = _inputUserId
    private val _inputEmail = MutableStateFlow("")
    val inputEmail: StateFlow<String> = _inputEmail
    private val _authCode = MutableStateFlow("")
    val authCode: StateFlow<String> = _authCode
    private val  _inputPassword = MutableStateFlow("")
    val inputPassword: StateFlow<String> = _inputPassword
    private val _inputPasswordChecking = MutableStateFlow("")
    val inputPasswordChecking: StateFlow<String> = _inputPasswordChecking

    fun updateScreenState(screenState: ScreenState) {
        _screenState.update { screenState }
    }

    fun updateInputUserId(id: String) {
        _inputUserId.update { id }
    }

    fun updateInputEmail(email: String) {
        _inputEmail.update { email }
    }

    fun updateAuthCode(authCode: String) {
        _authCode.update { authCode }
    }

    fun updateInputPassword(password: String) {
        _inputPassword.update { password }
    }

    fun updateInputPasswordForChecking(passwordForChecking: String) {
        _inputPasswordChecking.update { passwordForChecking }
    }

    fun authenticateEmail() {
        viewModelScope.launch(Dispatchers.Default) {
            val request = AuthenticateEmailRequest(inputEmail.value)
            val response = authenticateEmailUseCase(request)
            LogUtil.printNetworkLog(response, "이메일 인증")
            when (response) {
                is NetworkResult.Success -> {}
                is NetworkResult.Error -> {}
                is NetworkResult.Exception -> {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(application, "오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
                    }}
            }
        }
    }

    fun verifyPasswordResetCode(
        moveToPasswordResettingScreen: () -> Unit
    ) {
        viewModelScope.launch(Dispatchers.Default) {
            val request = VerifyPasswordResetCodeRequest(inputUserId.value, _inputEmail.value, authCode.value)
            val response = verifyPasswordResetCodeUseCase(request)
            LogUtil.printNetworkLog(response, "비밀번호 재설정 코드 인증")
            when (response) {
                is NetworkResult.Success -> {
                    moveToPasswordResettingScreen()
                }
                is NetworkResult.Error -> {

                }
                is NetworkResult.Exception -> {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(application, "오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    fun resetPassword(
        moveToSignInScreen: () -> Unit
    ) {
        viewModelScope.launch(Dispatchers.Default) {
            val request = ResetPasswordRequest(_inputUserId.value, _inputPassword.value, _inputPasswordChecking.value)
            val response = resetPasswordUseCase(request)
            LogUtil.printNetworkLog(response, "비밀번호 재설정")
            when (response) {
                is NetworkResult.Success -> {
                    withContext(Dispatchers.Main) {
                        moveToSignInScreen()
                    }
                }
                is NetworkResult.Error -> {

                }
                is NetworkResult.Exception -> {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(application, "오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    enum class ScreenState {
        EmailAuthentication,
        ResettingPassword
    }
}