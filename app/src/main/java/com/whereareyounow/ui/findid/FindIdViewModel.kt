package com.whereareyounow.ui.findid

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.whereareyounow.domain.entity.apimessage.signin.FindIdRequest
import com.whereareyounow.domain.entity.apimessage.signup.AuthenticateEmailRequest
import com.whereareyounow.domain.usecase.signin.FindIdUseCase
import com.whereareyounow.domain.usecase.signup.AuthenticateEmailUseCase
import com.whereareyounow.domain.util.LogUtil
import com.whereareyounow.domain.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class FindIdViewModel @Inject constructor(
    private val application: Application,
    private val authenticateEmailUseCase: AuthenticateEmailUseCase,
    private val findIdUseCase: FindIdUseCase

) : AndroidViewModel(application) {
    private val emailCondition = Regex("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}\$")

    private val _screenState = MutableStateFlow(ScreenState.EmailAuthentication)
    val screenState: StateFlow<ScreenState> = _screenState
    private val _inputEmail = MutableStateFlow("")
    val inputEmail: StateFlow<String> = _inputEmail
    private val _inputEmailState = MutableStateFlow(EmailState.EMPTY)
    val inputEmailState: StateFlow<EmailState> = _inputEmailState
    private val _inputVerificationCode = MutableStateFlow("")
    val inputVerificationCode: StateFlow<String> = _inputVerificationCode
    private val _inputVerificationCodeState = MutableStateFlow(VerificationCodeState.EMPTY)
    val inputVerificationCodeState: StateFlow<VerificationCodeState> = _inputVerificationCodeState
    private val _isVerificationCodeSent = MutableStateFlow(false)
    val isVerificationCodeSent: StateFlow<Boolean> = _isVerificationCodeSent
    private val _searchedUserId = MutableStateFlow("")
    val searchedUserId: StateFlow<String> = _searchedUserId
    private val _emailVerificationLeftTime = MutableStateFlow(0)
    val emailVerificationLeftTime = _emailVerificationLeftTime
    private var startTimer: Job? = null

    fun updateInputEmail(email: String) {
        _inputEmail.update { email }
        _inputEmailState.update {
            if (email == "") {
                EmailState.EMPTY
            } else {
                if (email.matches(emailCondition)) EmailState.SATISFIED else EmailState.UNSATISFIED
            }
        }
    }

    fun updateVerificationCode(code: String) {
        _inputVerificationCode.update { code }
    }

    fun updateScreenState(screenState: ScreenState) {
        _screenState.update { screenState }
    }

    fun verifyEmail() {
        if (_inputEmail.value == "") {
            Toast.makeText(application, "이메일을 입력해주세요", Toast.LENGTH_SHORT).show()
            return
        }
        if (_emailVerificationLeftTime.value > 120) {
            Toast.makeText(application, "${_emailVerificationLeftTime.value - 120}초 후에 다시 발송할 수 있습니다.", Toast.LENGTH_SHORT).show()
            return
        }
        viewModelScope.launch(Dispatchers.Default) {
            val request = AuthenticateEmailRequest(inputEmail.value)
            val response = authenticateEmailUseCase(request)
            LogUtil.printNetworkLog(response, "이메일 인증")
            when (response) {
                is NetworkResult.Success -> {
                    _isVerificationCodeSent.update { true }
                    withContext(Dispatchers.Main) {
                        Toast.makeText(application, "인증 코드가 발송되었습니다.", Toast.LENGTH_SHORT).show()
                    }
                    startTimer?.cancel()
                    startTimer = viewModelScope.launch {
                        _emailVerificationLeftTime.update { 180 }
                        while (_emailVerificationLeftTime.value > 0) {
                            _emailVerificationLeftTime.update { it - 1 }
                            delay(1000)
                        }
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

    fun findId(moveToUserIdCheckingScreen: () -> Unit) {
        // 유효시간이 지나면 인증을 다시 받아야 한다.
        if (_emailVerificationLeftTime.value <= 0) {
            Toast.makeText(application, "유효시간이 만료되었습니다. 인증 코드를 재전송해주세요.", Toast.LENGTH_SHORT).show()
            return
        }
        viewModelScope.launch(Dispatchers.Default) {
            val request = FindIdRequest(_inputEmail.value, _inputVerificationCode.value)
            val response = findIdUseCase(request)
            LogUtil.printNetworkLog(response, "이메일 인증 코드 확인")
            when (response) {
                is NetworkResult.Success -> {
                    response.data?.let { data ->
                        _searchedUserId.update { data.userId }
                    }
                    moveToUserIdCheckingScreen()
                }
                is NetworkResult.Error -> {
                    when (response.code) {
                        400 -> {
                            _inputVerificationCodeState.update { VerificationCodeState.UNSATISFIED }
                        }
                        404 -> {
                            moveToUserIdCheckingScreen()
                        }
                    }
                }
                is NetworkResult.Exception -> {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(application, "오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
                    }}
            }
        }
    }

    enum class ScreenState {
        EmailAuthentication, ShowingUserId
    }
}