package com.whereareyounow.ui.home.friends.addfriend

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.whereareyounow.domain.entity.apimessage.friend.SendFriendRequestRequest
import com.whereareyounow.domain.usecase.friend.SendFriendRequestUseCase
import com.whereareyounow.domain.usecase.signin.GetAccessTokenUseCase
import com.whereareyounow.domain.usecase.signin.GetMemberDetailsByUserIdUseCase
import com.whereareyounow.domain.usecase.signin.GetMemberIdUseCase
import com.whereareyounow.domain.usecase.signin.GetRefreshTokenUseCase
import com.whereareyounow.domain.usecase.signin.SaveAccessTokenUseCase
import com.whereareyounow.domain.usecase.signin.SaveRefreshTokenUseCase
import com.whereareyounow.domain.util.NetworkResult
import com.whereareyounow.util.TokenManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AddFriendViewModel @Inject constructor(
    private val application: Application,
    private val getAccessTokenUseCase: GetAccessTokenUseCase,
    private val getMemberIdUseCase: GetMemberIdUseCase,
    private val getMemberDetailsByUserIdUseCase: GetMemberDetailsByUserIdUseCase,
    private val sendFriendRequestUseCase: SendFriendRequestUseCase,
    private val getRefreshTokenUseCase: GetRefreshTokenUseCase,
    private val saveAccessTokenUseCase: SaveAccessTokenUseCase,
    private val saveRefreshTokenUseCase: SaveRefreshTokenUseCase,
    private val tokenManager: TokenManager
) : AndroidViewModel(application) {

    private val _inputId = MutableStateFlow("")
    val inputId: StateFlow<String> = _inputId
    private val _imageUrl = MutableStateFlow<String?>("")
    val imageUrl: StateFlow<String?> = _imageUrl
    private val _userName = MutableStateFlow("")
    val userName: StateFlow<String> = _userName
    private var friendMemberId: String? = ""
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

    fun searchFriend() {
        viewModelScope.launch {
            friendMemberId = searchMemberIdByUserId()
            if (friendMemberId != null) {
                getMemberDetailsByMemberId()
            }
            else return@launch
        }
    }

    // UserId로 MemberId를 검색한다.
    private suspend fun searchMemberIdByUserId(): String? {
        val accessToken = getAccessTokenUseCase().first()
        return when (val response = getMemberDetailsByUserIdUseCase(accessToken, _inputId.value)) {
            is NetworkResult.Success -> {
                Log.e("Success", "getMemberDetailsByUserIdUseCase\n${response.code}\n${response.data}")
                return response.data?.let { data ->
                    withContext(Dispatchers.Main) { Toast.makeText(application, "성공적으로 검색되었습니다.", Toast.LENGTH_SHORT).show() }
                    _buttonState.update { ButtonState.REQUEST }
                    data.memberId
                } ?: withContext(Dispatchers.Main) {
                    Toast.makeText(application, "null data", Toast.LENGTH_SHORT).show()
                    null
                }
            }
            is NetworkResult.Error -> {
                Log.e("Error", "getMemberDetailsByUserIdUseCase\n${response.code}\n${response.errorData}")
                withContext(Dispatchers.Main) {
                    when (response.code) {
                        401 -> { Toast.makeText(application, "인증 오류가 발생했습니다.", Toast.LENGTH_SHORT).show() }
                        404 -> { Toast.makeText(application, "회원 정보를 찾을 수 없습니다.", Toast.LENGTH_SHORT).show() }
                        500 -> { Toast.makeText(application, "서버 오류가 발생했습니다.", Toast.LENGTH_SHORT).show() }
                        else -> { Toast.makeText(application, "알 수 없는 네트워크 오류가 발생했습니다.", Toast.LENGTH_SHORT).show() }
                    }
                    null
                }
            }
            is NetworkResult.Exception -> {
                Log.e("Exception", "getMemberDetailsByUserIdUseCase\n${response.e.printStackTrace()}")
                withContext(Dispatchers.Main) {
                    Toast.makeText(application, "알 수 없는 예외가 발생했습니다.", Toast.LENGTH_SHORT).show()
                    null
                }
            }
        }
    }

    private suspend fun getMemberDetailsByMemberId() {
        val accessToken = getAccessTokenUseCase().first()

    }

    // 친구 요청
    fun sendFriendRequest() {
        viewModelScope.launch(Dispatchers.Default) {
            val accessToken = getAccessTokenUseCase().first()
            val memberId = getMemberIdUseCase().first()
            if (friendMemberId == null) return@launch
            Log.e("sendFriendRequestRequest", "$friendMemberId")
            val request = SendFriendRequestRequest(friendMemberId!!, memberId)
            val networkResult = sendFriendRequestUseCase(accessToken, request)
            when (networkResult) {
                is NetworkResult.Success -> {
                    Toast.makeText(application, "성공적으로 요청되었습니다", Toast.LENGTH_SHORT).show()
                }
                is NetworkResult.Error -> {
                    when (networkResult.code) {
                        401 -> {
                            withContext(Dispatchers.Main) {
                                Toast.makeText(application, "401 Error, Unauthorized", Toast.LENGTH_SHORT).show()
                            }
                        }
                        else -> {
                            withContext(Dispatchers.Main) {
                                Toast.makeText(application, "${networkResult.code} Error, ${networkResult.errorData?.message}", Toast.LENGTH_SHORT).show()
                            }
                        }
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