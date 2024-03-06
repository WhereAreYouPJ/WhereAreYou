package com.whereareyounow.ui.findid

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.whereareyounow.data.findid.EmailState
import com.whereareyounow.data.findid.FindIdScreenSideEffect
import com.whereareyounow.data.findid.FindIdScreenUIState
import com.whereareyounow.data.findid.VerificationCodeState
import com.whereareyounow.domain.entity.apimessage.signin.FindIdRequest
import com.whereareyounow.domain.entity.apimessage.signup.AuthenticateEmailRequest
import com.whereareyounow.domain.usecase.signin.FindIdUseCase
import com.whereareyounow.domain.usecase.signup.AuthenticateEmailUseCase
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
class FindIdViewModel @Inject constructor(
    private val application: Application,
    private val inputTextValidator: InputTextValidator,
    private val authenticateEmailUseCase: AuthenticateEmailUseCase,
    private val findIdUseCase: FindIdUseCase

) : AndroidViewModel(application) {

    private val _findIdScreenUIState = MutableStateFlow(FindIdScreenUIState())
    val findIdScreenUIState = _findIdScreenUIState.asStateFlow()
    val findIdScreenSideEffectFlow = MutableSharedFlow<FindIdScreenSideEffect>()
    private var startTimer: Job? = null

    fun updateInputEmail(email: String) {
        _findIdScreenUIState.update {
            it.copy(
                inputEmail = email,
                inputEmailState = if (inputTextValidator.validateEmail(email).result) EmailState.SATISFIED else EmailState.UNSATISFIED
            )
        }
    }

    fun updateInputVerificationCode(code: String) {
        _findIdScreenUIState.update {
            it.copy(inputVerificationCode = code)
        }
    }

    fun sendEmailVerificationCode() {
        viewModelScope.launch(Dispatchers.Default) {
            when (_findIdScreenUIState.value.inputEmailState) {
                EmailState.EMPTY -> { findIdScreenSideEffectFlow.emit(FindIdScreenSideEffect.Toast("이메일을 입력해주세요.")) }
                EmailState.UNSATISFIED -> { findIdScreenSideEffectFlow.emit(FindIdScreenSideEffect.Toast("이메일을 확인해주세요.")) }
                EmailState.SATISFIED -> {
                    if (_findIdScreenUIState.value.emailVerificationLeftTime > 120) {
                        findIdScreenSideEffectFlow.emit(FindIdScreenSideEffect.Toast("${_findIdScreenUIState.value.emailVerificationLeftTime - 120}초 후에 다시 발송할 수 있습니다."))
                        return@launch
                    }
                    startTimer?.cancel()
                    startTimer = launch {
                        _findIdScreenUIState.update {
                            it.copy(
                                isVerificationCodeSent = true,
                                emailVerificationLeftTime = 180
                            )
                        }
                        while (_findIdScreenUIState.value.emailVerificationLeftTime > 0) {
                            _findIdScreenUIState.update {
                                it.copy(emailVerificationLeftTime = it.emailVerificationLeftTime - 1)
                            }
                            delay(1000)
                        }
                    }
                    val request = AuthenticateEmailRequest(_findIdScreenUIState.value.inputEmail)
                    val response = authenticateEmailUseCase(request)
                    LogUtil.printNetworkLog(request, response, "이메일 인증")
                    when (response) {
                        is NetworkResult.Success -> { findIdScreenSideEffectFlow.emit(FindIdScreenSideEffect.Toast("인증 코드가 발송되었습니다.")) }
                        is NetworkResult.Error -> {

                        }
                        is NetworkResult.Exception -> { findIdScreenSideEffectFlow.emit(FindIdScreenSideEffect.Toast("오류가 발생했습니다.")) }
                    }
                }
            }
        }
    }

    fun findId(moveToFindIdResultScreen: (String) -> Unit) {
        viewModelScope.launch(Dispatchers.Default) {
            // 유효시간이 지나면 인증을 다시 받아야 한다.
            if (_findIdScreenUIState.value.emailVerificationLeftTime <= 0) {
                findIdScreenSideEffectFlow.emit(FindIdScreenSideEffect.Toast("유효시간이 만료되었습니다. 인증 코드를 재전송해주세요."))
                return@launch
            }
            val request = FindIdRequest(_findIdScreenUIState.value.inputEmail, _findIdScreenUIState.value.inputVerificationCode)
            val response = findIdUseCase(request)
            LogUtil.printNetworkLog(request, response, "이메일 인증 코드 확인 및 아이디 찾기")
            when (response) {
                is NetworkResult.Success -> {
                    response.data?.let { data ->
                        withContext(Dispatchers.Main) {
                            moveToFindIdResultScreen(data.userId)
                        }
                    }
                }
                is NetworkResult.Error -> {
                    when (response.code) {
                        400 -> {
                            _findIdScreenUIState.update {
                                it.copy(inputVerificationCodeState = VerificationCodeState.UNSATISFIED)
                            }
                        }
                        404 -> {
                            withContext(Dispatchers.Main) {
                                moveToFindIdResultScreen("")
                            }
                        }
                    }
                }
                is NetworkResult.Exception -> { findIdScreenSideEffectFlow.emit(FindIdScreenSideEffect.Toast("오류가 발생했습니다.")) }
            }
        }
    }
}