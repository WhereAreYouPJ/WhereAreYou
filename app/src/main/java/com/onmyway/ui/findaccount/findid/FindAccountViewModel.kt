package com.onmyway.ui.findaccount.findid

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.onmyway.data.findaccount.FindAccountEmailVerificationScreenSideEffect
import com.onmyway.data.findaccount.FindAccountEmailVerificationScreenUIState
import com.onmyway.domain.request.member.SendEmailCodeRequest
import com.onmyway.domain.usecase.member.SendEmailCodeUseCase
import com.onmyway.domain.util.LogUtil
import com.onmyway.domain.util.onError
import com.onmyway.domain.util.onException
import com.onmyway.domain.util.onSuccess
import com.onmyway.globalvalue.type.EmailButtonState
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
class FindAccountViewModel @Inject constructor(
    private val application: Application,
    private val inputTextValidator: InputTextValidator,
    private val sendEmailCodeUseCase: SendEmailCodeUseCase,
//    private val authenticateEmailUseCase: AuthenticateEmailUseCase,
//    private val authenticateEmailCodeUseCase: AuthenticateEmailCodeUseCase,
//    private val findIdUseCase: FindIdUseCase

) : AndroidViewModel(application) {

    private val _uiState = MutableStateFlow(FindAccountEmailVerificationScreenUIState())
    val uiState = _uiState.asStateFlow()
    val sideEffectFlow = MutableSharedFlow<FindAccountEmailVerificationScreenSideEffect>()
    private var startTimer: Job? = null

    fun updateInputEmail(email: String) {
        _uiState.update {
            it.copy(
                inputEmail = email,
                inputEmailState = if (inputTextValidator.validateEmail(email).result) EmailState.Idle else EmailState.Invalid
            )
        }
    }

    fun updateInputEmailCode(code: String) {
        _uiState.update {
            it.copy(inputEmailCode = code)
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
                            sideEffectFlow.emit(FindAccountEmailVerificationScreenSideEffect.Toast("${_uiState.value.emailCodeLeftTime - 120}초 후에 다시 발송할 수 있습니다."))
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

    fun verifyEmailCode() {

    }
}