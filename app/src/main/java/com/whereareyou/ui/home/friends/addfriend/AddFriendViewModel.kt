package com.whereareyou.ui.home.friends.addfriend

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.whereareyou.domain.entity.apimessage.friend.SendFriendRequestRequest
import com.whereareyou.domain.usecase.friend.SendFriendRequestUseCase
import com.whereareyou.domain.usecase.signin.GetAccessTokenUseCase
import com.whereareyou.domain.usecase.signin.GetMemberIdUseCase
import com.whereareyou.domain.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddFriendViewModel @Inject constructor(
    private val application: Application,
    private val getAccessTokenUseCase: GetAccessTokenUseCase,
    private val getMemberIdUseCase: GetMemberIdUseCase,
    private val sendFriendRequestUseCase: SendFriendRequestUseCase
) : AndroidViewModel(application) {

    private val _inputId = MutableStateFlow("")
    val inputId: StateFlow<String> = _inputId

    fun updateInputId(inputId: String) {
        _inputId.value = inputId
    }

    fun sendFriendRequest() {
        viewModelScope.launch {
            val accessToken = getAccessTokenUseCase().first()
            val memberId = getMemberIdUseCase().first()
            val request = SendFriendRequestRequest(_inputId.value, memberId)
            val networkResult = sendFriendRequestUseCase(accessToken, request)
            when (networkResult) {
                is NetworkResult.Success -> {

                }
                is NetworkResult.Error -> { Log.e("error", "${networkResult.code}, ${networkResult.errorData}") }
                is NetworkResult.Exception -> { Log.e("exception", "exception") }
            }
        }
    }
}