package com.whereareyounow.ui.main.friend.addfriend

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.whereareyounow.data.cached.AuthData
import com.whereareyounow.domain.entity.schedule.Friend
import com.whereareyounow.domain.request.friend.SendFriendRequestRequest
import com.whereareyounow.domain.request.member.GetUserInfoByMemberCodeRequest
import com.whereareyounow.domain.usecase.friend.SendFriendRequestUseCase
import com.whereareyounow.domain.usecase.member.GetUserInfoByMemberCodeUseCase
import com.whereareyounow.domain.util.LogUtil
import com.whereareyounow.domain.util.onError
import com.whereareyounow.domain.util.onException
import com.whereareyounow.domain.util.onSuccess
import com.whereareyounow.ui.main.mypage.mapper.toModel
import com.whereareyounow.ui.main.mypage.model.OtherUserInfoModel
import com.whereareyounow.util.TokenManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddFriendViewModel @Inject constructor(
    private val application: Application,
//    private val getMemberIdUseCase: GetMemberIdUseCase,
//    private val getMemberIdByUserIdUseCase: GetMemberIdByUserIdUseCase,
//    private val getMemberDetailsUseCase: GetMemberDetailsUseCase,
//    private val sendFriendRequestUseCase: SendFriendRequestUseCase,
    private val tokenManager: TokenManager,
    private val getUserInfoByMemberCodeUseCase: GetUserInfoByMemberCodeUseCase,
    private val sendFriendRequestUseCase: SendFriendRequestUseCase,


    ) : AndroidViewModel(application) {


    private val _searchedUserInfo = MutableStateFlow<OtherUserInfoModel?>(null)
    val searcedUserInfo: StateFlow<OtherUserInfoModel?> = _searchedUserInfo


    fun requestAddFriend(
        friendSeq: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            sendFriendRequestUseCase(
                SendFriendRequestRequest(
                    memberSeq = AuthData.memberSeq.toString(),
                    friendSeq = friendSeq
                )
            ).onEach { networkResult ->
                networkResult.onSuccess { code, message, data ->
                    Log.d("sendFriendRequestUseCase", data.toString())

                }.onError { code, message ->

                }.onException {

                }
            }
                .catch {
                    LogUtil.e(
                        "flow error",
                        "sendFriendRequestUseCase\n${it.message}\n${it.stackTrace}"
                    )
                }
                .launchIn(this)
        }
    }

    fun searchUser(memberCode: String) {

        viewModelScope.launch(Dispatchers.IO) {
            getUserInfoByMemberCodeUseCase(
                GetUserInfoByMemberCodeRequest(
                    memberCode = memberCode
                )
            ).onEach { networkResult ->
                networkResult.onSuccess { code, message, data ->
                    Log.d("Sflsjfleifnsfsfsfsfslf", data.toString())
                    Log.d("Sflsjfleifnsfsfsfsfslf", AuthData.memberCode)
                    val otherUserModel = data?.toModel()
                    _searchedUserInfo.value = otherUserModel

                }.onError { code, message ->
                    Log.d("Sflsjfleifnsfsfsfsfslf", code.toString())
                    Log.d("Sflsjfleifnsfsfsfsfslf", AuthData.memberCode)

                }.onException {
                    Log.d("Sflsjfleifnsfsfsfsfslf", it.message ?: "Unknown exception")
                    Log.d("Sflsjfleifnsfsfsfsfslf", AuthData.memberCode)

                }
            }
                .catch {
                    LogUtil.e(
                        "flow error",
                        "getFeedBookMarkUseCase\n${it.message}\n${it.stackTrace}"
                    )
                }
                .launchIn(this)
        }


    }


    private val _friendInfo = MutableStateFlow<Friend?>(null)
    val friendInfo: StateFlow<Friend?> = _friendInfo

    private val _inputId = MutableStateFlow("")
    val inputId: StateFlow<String> = _inputId
    private var friendMemberId: String = ""
    private val _buttonState = MutableStateFlow(ButtonState.Search)
    val buttonState: StateFlow<ButtonState> = _buttonState

    fun updateInputId(inputId: String) {
        _inputId.update { inputId }
        if (_buttonState.value == ButtonState.Request) {
            _buttonState.update { ButtonState.Search }
        }
    }

    fun clearInputId() {
        _inputId.update { "" }
        if (_buttonState.value == ButtonState.Request) {
            _buttonState.update { ButtonState.Search }
        }
    }

    fun searchFriend() {
        viewModelScope.launch {
            friendMemberId = searchMemberIdByUserId()
            if (friendMemberId != "") {
                getMemberDetailsByMemberId()
            } else return@launch
        }
    }

    // UserId로 MemberId를 검색한다.
    private suspend fun searchMemberIdByUserId(): String {
//        val accessToken = tokenManager.getAccessToken()
//        val response = getMemberIdByUserIdUseCase(accessToken, _inputId.value)
//        LogUtil.printNetworkLog("userId = ${_inputId.value}", response, "getMemberIdByUserIdUseCase")
//        return when (response) {
//            is NetworkResult.Success -> {
//                response.data?.let { data ->
////                    withContext(Dispatchers.Main) { Toast.makeText(application, "성공적으로 검색되었습니다.", Toast.LENGTH_SHORT).show() }
//                    data.memberId
//                } ?: withContext(Dispatchers.Main) {
//                    Toast.makeText(application, "null data", Toast.LENGTH_SHORT).show()
//                    ""
//                }
//            }
//            is NetworkResult.Error -> {
//                withContext(Dispatchers.Main) {
//                    when (response.code) {
//                        401 -> { Toast.makeText(application, "인증 오류가 발생했습니다.", Toast.LENGTH_SHORT).show() }
//                        404 -> { Toast.makeText(application, "회원 정보를 찾을 수 없습니다.", Toast.LENGTH_SHORT).show() }
//                        500 -> { Toast.makeText(application, "서버 오류가 발생했습니다.", Toast.LENGTH_SHORT).show() }
//                        else -> { Toast.makeText(application, "알 수 없는 네트워크 오류가 발생했습니다.", Toast.LENGTH_SHORT).show() }
//                    }
//                    ""
//                }
//            }
//            is NetworkResult.Exception -> {
//                withContext(Dispatchers.Main) {
//                    Toast.makeText(application, "오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
//                    ""
//                }
//            }
//        }
        return ""
    }

    private suspend fun getMemberDetailsByMemberId() {
//        val accessToken = tokenManager.getAccessToken()
//        val response = getMemberDetailsUseCase(accessToken, friendMemberId)
//        LogUtil.printNetworkLog("memberId = $friendMemberId", response, "getMemberDetailsUseCase")
//        when (response) {
//            is NetworkResult.Success -> {
//                response.data?.let { data ->
//                    withContext(Dispatchers.Main) { Toast.makeText(application, "성공적으로 검색되었습니다.", Toast.LENGTH_SHORT).show() }
//                    _friendInfo.update {
//                        Friend(
//                            memberId = friendMemberId,
//                            name = data.userName,
//                            userId = data.userId,
//                            profileImgUrl = data.profileImage
//                        )
//                    }
//                    _buttonState.update { ButtonState.Request }
//                } ?: withContext(Dispatchers.Main) { Toast.makeText(application, "null data", Toast.LENGTH_SHORT).show() }
//            }
//            is NetworkResult.Error -> {
//                withContext(Dispatchers.Main) {
//                    when (response.code) {
//                        401 -> { Toast.makeText(application, "인증 오류가 발생했습니다.", Toast.LENGTH_SHORT).show() }
//                        404 -> { Toast.makeText(application, "회원 정보를 찾을 수 없습니다.", Toast.LENGTH_SHORT).show() }
//                        500 -> { Toast.makeText(application, "서버 오류가 발생했습니다.", Toast.LENGTH_SHORT).show() }
//                        else -> { Toast.makeText(application, "알 수 없는 네트워크 오류가 발생했습니다.", Toast.LENGTH_SHORT).show() }
//                    }
//                }
//            }
//            is NetworkResult.Exception -> {
//                withContext(Dispatchers.Main) {
//                    Toast.makeText(application, "오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
//                }
//            }
//        }
    }

    // 친구 요청
    fun sendFriendRequest() {
//        viewModelScope.launch(Dispatchers.Default) {
//            if (friendMemberId == "") return@launch
//            val accessToken = tokenManager.getAccessToken()
//            val memberId = getMemberIdUseCase().first()
//            val request = com.whereareyounow.domain.request.friend.SendFriendRequestRequest(
//                friendMemberId,
//                memberId
//            )
//            val response = sendFriendRequestUseCase(accessToken, request)
//            LogUtil.printNetworkLog(request, response, "sendFriendRequestUseCase")
//            when (response) {
//                is NetworkResult.Success -> {
//                    withContext(Dispatchers.Main) { Toast.makeText(application, "성공적으로 요청되었습니다.", Toast.LENGTH_SHORT).show() }
//                }
//                is NetworkResult.Error -> {
//                    withContext(Dispatchers.Main) {
//                        when (response.code) {
//                            400 -> { Toast.makeText(application, "이미 친구요청된 멤버입니다.", Toast.LENGTH_SHORT).show() }
//                            401 -> { Toast.makeText(application, "인증 오류가 발생했습니다.", Toast.LENGTH_SHORT).show() }
//                            404 -> { Toast.makeText(application, "회원 정보를 찾을 수 없습니다.", Toast.LENGTH_SHORT).show() }
//                            500 -> { Toast.makeText(application, "서버 오류가 발생했습니다.", Toast.LENGTH_SHORT).show() }
//                            else -> { Toast.makeText(application, "알 수 없는 네트워크 오류가 발생했습니다.", Toast.LENGTH_SHORT).show() }
//                        }
//                    }
//                }
//                is NetworkResult.Exception -> {
//                    withContext(Dispatchers.Main) {
//                        Toast.makeText(application, "오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
//                    }
//                }
//            }
//        }
    }

    enum class ButtonState {
        Search, Request
    }
}