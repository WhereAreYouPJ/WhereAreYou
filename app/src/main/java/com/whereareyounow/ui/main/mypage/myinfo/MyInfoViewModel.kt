package com.whereareyounow.ui.main.mypage.myinfo

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.whereareyounow.data.cached.AuthData
import com.whereareyounow.data.myinfo.MyInfoScreenSideEffect
import com.whereareyounow.data.myinfo.MyInfoScreenUIState
import com.whereareyounow.data.signup.SignUpScreenSideEffect
import com.whereareyounow.data.signup.SignUpUserNameState
import com.whereareyounow.domain.request.member.GetUserInfoByMemberSeqRequest
import com.whereareyounow.domain.request.member.UpdateUserNameRequest
import com.whereareyounow.domain.usecase.member.GetUserInfoByMemberSeqUseCase
import com.whereareyounow.domain.usecase.member.UpdateUserNameUseCase
import com.whereareyounow.domain.util.onError
import com.whereareyounow.domain.util.onException
import com.whereareyounow.domain.util.onSuccess
import com.whereareyounow.util.InputTextValidator
import dagger.hilt.android.lifecycle.HiltViewModel
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
class MyInfoViewModel @Inject constructor(
    private val getUserInfoByMemberSeqUseCase: GetUserInfoByMemberSeqUseCase,
    private val updateUserNameUseCase: UpdateUserNameUseCase,
    private val inputTextValidator: InputTextValidator,
) : ViewModel() {

    private val _uiState = MutableStateFlow(MyInfoScreenUIState())
    val uiState = _uiState.asStateFlow()
    val sideEffectFlow = MutableSharedFlow<MyInfoScreenSideEffect>()

    fun getUserInfo() {
        val requestData = GetUserInfoByMemberSeqRequest(
            memberSeq = AuthData.memberSeq
        )
        getUserInfoByMemberSeqUseCase(requestData)
            .onEach { networkResult ->
                networkResult.onSuccess { code, message, data ->
                    data?.let {
                        _uiState.update {
                            it.copy(
                                name = data.userName,
                                email = data.email
                            )
                        }
                        AuthData.userName = data.userName
                    }
                }.onError { code, message ->

                }.onException {  }
            }
            .catch {  }
            .launchIn(viewModelScope)
    }

    fun updateInputUserName(name: String) {
        _uiState.update {
            it.copy(
                name = name,
                nameState = if (inputTextValidator.validateUserName(name).result) SignUpUserNameState.Valid else SignUpUserNameState.Invalid
            )
        }
    }

    fun updateUserName(
        moveToBackScreen: () -> Unit,
    ) {
        viewModelScope.launch {
            if (_uiState.value.nameState == SignUpUserNameState.Invalid) {
                sideEffectFlow.emit(MyInfoScreenSideEffect.Toast("이름을 확인해주세요."))
                return@launch
            }
            val requestData = UpdateUserNameRequest(
                memberSeq = AuthData.memberSeq,
                userName = _uiState.value.name
            )
            updateUserNameUseCase(requestData)
                .onEach { networkResult ->
                    networkResult.onSuccess { code, message, data ->
                        moveToBackScreen()
                    }.onError { code, message ->

                    }.onException {  }
                }
                .catch {  }
                .launchIn(viewModelScope)
        }
    }
}