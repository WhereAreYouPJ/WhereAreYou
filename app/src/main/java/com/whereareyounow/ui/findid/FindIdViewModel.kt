package com.whereareyounow.ui.findid

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.whereareyounow.domain.entity.apimessage.signin.FindIdRequest
import com.whereareyounow.domain.entity.apimessage.signup.AuthenticateEmailCodeRequest
import com.whereareyounow.domain.entity.apimessage.signup.AuthenticateEmailRequest
import com.whereareyounow.domain.usecase.signin.FindIdUseCase
import com.whereareyounow.domain.usecase.signup.AuthenticateEmailCodeUseCase
import com.whereareyounow.domain.usecase.signup.AuthenticateEmailUseCase
import com.whereareyounow.domain.util.LogUtil
import com.whereareyounow.domain.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FindIdViewModel @Inject constructor(
    private val application: Application,
    private val authenticateEmailUseCase: AuthenticateEmailUseCase,
    private val findIdUseCase: FindIdUseCase

) : AndroidViewModel(application) {

    private val _screenState = MutableStateFlow(ScreenState.EmailAuthentication)
    val screenState: StateFlow<ScreenState> = _screenState
    private val _inputEmail = MutableStateFlow("")
    val inputEmail: StateFlow<String> = _inputEmail
    private val _authCode = MutableStateFlow("")
    val authCode: StateFlow<String> = _authCode
    private val _searchedUserId = MutableStateFlow("")
    val searchedUserId: StateFlow<String> = _searchedUserId

    fun updateInputEmail(email: String) {
        _inputEmail.update { email }
    }

    fun updateAuthCode(authCode: String) {
        _authCode.update { authCode }
    }

    fun updateScreenState(screenState: ScreenState) {
        _screenState.update { screenState }
    }

    fun authenticateEmail() {
        viewModelScope.launch(Dispatchers.Default) {
            val request = AuthenticateEmailRequest(inputEmail.value)
            val response = authenticateEmailUseCase(request)
            LogUtil.printNetworkLog(response, "이메일 인증")
            when (response) {
                is NetworkResult.Success -> {}
                is NetworkResult.Error -> {}
                is NetworkResult.Exception -> {}
            }
        }
    }

    fun findId(moveToUserIdCheckingScreen: () -> Unit) {
        viewModelScope.launch(Dispatchers.Default) {
            val request = FindIdRequest(_inputEmail.value, _authCode.value)
            val response = findIdUseCase(request)
            LogUtil.printNetworkLog(response, "이메일 인증 코드 확인")
            when (response) {
                is NetworkResult.Success -> {
                    response.data?.let { data ->
                        _searchedUserId.update { data.userId }
                    }
                    moveToUserIdCheckingScreen()
                }
                is NetworkResult.Error -> {}
                is NetworkResult.Exception -> {}
            }
        }
    }

    enum class ScreenState {
        EmailAuthentication, ShowingUserId
    }
}