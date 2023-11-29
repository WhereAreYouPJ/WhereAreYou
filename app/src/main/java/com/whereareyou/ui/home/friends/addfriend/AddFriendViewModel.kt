package com.whereareyou.ui.home.friends.addfriend

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.whereareyou.domain.entity.apimessage.friend.SendFriendRequestRequest
import com.whereareyou.domain.usecase.friend.SendFriendRequestUseCase
import com.whereareyou.domain.usecase.signin.GetAccessTokenUseCase
import com.whereareyou.domain.usecase.signin.GetMemberDetailsUseCase
import com.whereareyou.domain.usecase.signin.GetMemberIdUseCase
import com.whereareyou.domain.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddFriendViewModel @Inject constructor(
    private val application: Application,
    private val getAccessTokenUseCase: GetAccessTokenUseCase,
    private val getMemberIdUseCase: GetMemberIdUseCase,
    private val getMemberDetailsUseCase: GetMemberDetailsUseCase,
    private val sendFriendRequestUseCase: SendFriendRequestUseCase
) : AndroidViewModel(application) {

    private val _inputId = MutableStateFlow("")
    val inputId: StateFlow<String> = _inputId
    private val _imageUrl = MutableStateFlow("")
    val imageUrl: StateFlow<String> = _imageUrl
    private val _userName = MutableStateFlow("")
    val userName: StateFlow<String> = _userName
    private val _buttonState = MutableStateFlow(ButtonState.SEARCH)
    val buttonState: StateFlow<ButtonState> = _buttonState

    fun updateInputId(inputId: String) {
        _inputId.update { inputId }
        if (_buttonState.value == ButtonState.REQUEST) {
            _buttonState.update { ButtonState.SEARCH }
        }
    }

    fun clearInputId() {
        _inputId.update { "" }
        if (_buttonState.value == ButtonState.REQUEST) {
            _buttonState.update { ButtonState.SEARCH }
        }
    }

    // 친구 검색
    fun searchFriend() {
        viewModelScope.launch {
            val accessToken = getAccessTokenUseCase().first()
            val networkResult = getMemberDetailsUseCase(accessToken, _inputId.value)
            when (networkResult) {
                is NetworkResult.Success -> {
                    Toast.makeText(application, "성공적으로 검색되었습니다", Toast.LENGTH_SHORT).show()
                    _buttonState.update { ButtonState.REQUEST }
                }
                is NetworkResult.Error -> {
                    when (networkResult.code) {
                        401 -> { Toast.makeText(application, "401 Error, Unauthorized", Toast.LENGTH_SHORT).show() }
                        else -> { Toast.makeText(application, "${networkResult.code} Error, ${networkResult.errorData?.message}", Toast.LENGTH_SHORT).show() }
                    }
                    Log.e("error", "${networkResult.code}, ${networkResult.errorData}")
                }
                is NetworkResult.Exception -> { Log.e("exception", "${networkResult.e.printStackTrace()}") }
            }
        }
    }

    // 친구 요청
    fun sendFriendRequest() {
        viewModelScope.launch {
            val accessToken = getAccessTokenUseCase().first()
            val memberId = getMemberIdUseCase().first()
            val request = SendFriendRequestRequest(_inputId.value, memberId)
            val networkResult = sendFriendRequestUseCase(accessToken, request)
            when (networkResult) {
                is NetworkResult.Success -> {
                    Toast.makeText(application, "성공적으로 요청되었습니다", Toast.LENGTH_SHORT).show()
                }
                is NetworkResult.Error -> {
                    when (networkResult.code) {
                        401 -> { Toast.makeText(application, "401 Error, Unauthorized", Toast.LENGTH_SHORT).show() }
                        else -> { Toast.makeText(application, "${networkResult.code} Error, ${networkResult.errorData?.message}", Toast.LENGTH_SHORT).show() }
                    }
                    Log.e("error", "${networkResult.code}, ${networkResult.errorData}")
                }
                is NetworkResult.Exception -> { Log.e("exception", "exception") }
            }
        }
    }

    enum class ButtonState {
        SEARCH, REQUEST
    }
}