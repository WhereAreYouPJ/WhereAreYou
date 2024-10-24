package com.whereareyounow.ui.signin

import android.app.Application
import androidx.compose.ui.util.fastMapIndexed
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.whereareyounow.data.cached.AuthData
import com.whereareyounow.data.cached.FriendList
import com.whereareyounow.data.signin.SignInScreenSideEffect
import com.whereareyounow.data.signin.SignInScreenUIState
import com.whereareyounow.domain.entity.schedule.Friend
import com.whereareyounow.domain.request.friend.GetFriendListRequest
import com.whereareyounow.domain.request.member.GetUserInfoByMemberSeqRequest
import com.whereareyounow.domain.request.member.SignInRequest
import com.whereareyounow.domain.usecase.datastore.SaveAccessTokenUseCase
import com.whereareyounow.domain.usecase.datastore.SaveMemberCodeUseCase
import com.whereareyounow.domain.usecase.datastore.SaveMemberSeqUseCase
import com.whereareyounow.domain.usecase.datastore.SaveRefreshTokenUseCase
import com.whereareyounow.domain.usecase.friend.GetFriendListUseCase
import com.whereareyounow.domain.usecase.member.GetUserInfoByMemberSeqUseCase
import com.whereareyounow.domain.usecase.member.SignInUseCase
import com.whereareyounow.domain.util.LogUtil
import com.whereareyounow.domain.util.onError
import com.whereareyounow.domain.util.onException
import com.whereareyounow.domain.util.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val application: Application,
    private val signInUseCase: SignInUseCase,
    private val saveAccessTokenUseCase: SaveAccessTokenUseCase,
    private val saveRefreshTokenUseCase: SaveRefreshTokenUseCase,
    private val saveMemberSeqUseCase: SaveMemberSeqUseCase,
    private val saveMemberCodeUseCase: SaveMemberCodeUseCase,
    private val getFriendListUseCase: GetFriendListUseCase,
    private val getUserInfoByMemberSeqUseCase: GetUserInfoByMemberSeqUseCase
//    private val saveMemberIdUseCase: SaveMemberIdUseCase,
//    private val updateFCMTokenUseCase: UpdateFCMTokenUseCase,
//    private val getFriendIdsListUseCase: GetFriendIdsListUseCase,
//    private val getFriendListUseCase: GetFriendListUseCase
) : AndroidViewModel(application) {

    private val _uiState = MutableStateFlow(SignInScreenUIState())
    val uiState = _uiState.asStateFlow()
    val sideEffectFlow = MutableSharedFlow<SignInScreenSideEffect>()

    fun updateInputUserId(userId: String) {
        _uiState.update {
            it.copy(inputEmail = userId)
        }
    }

    fun updateInputPassword(password: String) {
        _uiState.update {
            it.copy(inputPassword = password)
        }
    }

    fun updateIsSignInFailed(isSignInFailed: Boolean) {
        _uiState.update {
            it.copy(isSignInFailed = isSignInFailed)
        }
    }

    fun normalSignIn(
        moveToHomeScreen: () -> Unit
    ) {
        val requestData = SignInRequest(
            email = _uiState.value.inputEmail,
            password = _uiState.value.inputPassword,
            loginType = "normal"
        )
        signInUseCase(requestData)
            .onEach { networkResult ->
                networkResult.onSuccess { code, message, data ->
                    data?.let {
                        saveAccessTokenUseCase(data.accessToken)
                        saveRefreshTokenUseCase(data.refreshToken)
                        saveMemberSeqUseCase(data.memberSeq.toString())
                        saveMemberCodeUseCase(data.memberCode)
                        AuthData.memberSeq = data.memberSeq
                        AuthData.memberCode = data.memberCode
                        initialize()
                    }
                    moveToHomeScreen()
                }.onError { code, message ->
                    _uiState.update {
                        it.copy(
                            isSignInFailed = true
                        )
                    }
                }.onException {  }
            }
            .catch {
                LogUtil.e("flow error", "signInUseCase\n${it.message}\n${it.stackTrace}")
            }
            .launchIn(viewModelScope)
    }

    fun kakaoSignIn(
        email: String,
        moveToKakaoSignUpScreen: () -> Unit,
        moveToHomeScreen: () -> Unit
    ) {
        val requestData = SignInRequest(
            email = email,
            password = "bmnv4a35d38x4jhz${email.replace("@", "").replace(".", "")}qxidfmaia21cq1p3",
            loginType = "kakao"
        )
        signInUseCase(requestData)
            .onEach { networkResult ->
                networkResult.onSuccess { code, message, data ->
                    data?.let {
                        saveAccessTokenUseCase(data.accessToken)
                        saveRefreshTokenUseCase(data.refreshToken)
                        saveMemberSeqUseCase(data.memberSeq.toString())
                        saveMemberCodeUseCase(data.memberCode)
                        AuthData.memberSeq = data.memberSeq
                        AuthData.memberCode = data.memberCode
                    }
                    moveToHomeScreen()
                }.onError { code, message ->
                    moveToKakaoSignUpScreen()
                }.onException {  }
            }
            .catch {  }
            .launchIn(viewModelScope)
    }

    private fun initialize() {
        val requestData = GetFriendListRequest(AuthData.memberSeq)
        getFriendListUseCase(requestData)
            .onEach { networkResult ->
                networkResult.onSuccess { code, message, data ->
                    data?.let {
                        FriendList.list.clear()
                        FriendList.list.addAll((data).fastMapIndexed { idx, item ->
                            Friend(
                                number = idx,
                                memberSeq = item.memberSeq,
                                name = item.userName,
                                profileImgUrl = item.profileImage,
                                isFavorite = item.Favorites
                            )
                        })
                    }
                }.onError { code, message ->

                }.onException {  }
            }
            .catch {  }
            .launchIn(viewModelScope)

        val requestData2 = GetUserInfoByMemberSeqRequest(AuthData.memberSeq)
        getUserInfoByMemberSeqUseCase(requestData2)
            .onEach { networkResult ->
                networkResult.onSuccess { code, message, data ->
                    data?.let {
                        AuthData.userName = data.userName
                        AuthData.email = data.email
                        AuthData.profileImage = data.profileImage
                    }
                }.onError { code, message ->

                }.onException {  }
            }
            .catch {  }
            .launchIn(viewModelScope)
    }

    private fun updateFCMToken(
        fcmToken: String,
        accessToken: String,
        memberId: String
    ) {
//        viewModelScope.launch(Dispatchers.Default) {
//            if (fcmToken != "") {
//                val request = com.whereareyounow.domain.request.fcm.UpdateFCMTokenRequest(
//                    memberId,
//                    fcmToken
//                )
//                val response = updateFCMTokenUseCase(accessToken, request)
//                LogUtil.printNetworkLog(request, response, "FCM 토큰 저장")
//                when (response) {
//                    is NetworkResult.Success -> {
//
//                    }
//                    is NetworkResult.Error -> {
//
//                    }
//                    is NetworkResult.Exception -> { signInScreenSideEffectFlow.emit(SignInScreenSideEffect.Toast("오류가 발생했습니다.")) }
//                }
//            }
//        }
    }
//
    private suspend fun getFriendIdsList(
        accessToken: String,
        memberId: String
    ) {
//        val request = com.whereareyounow.domain.request.friend.GetFriendIdsListRequest(memberId)
//        val response = getFriendIdsListUseCase(accessToken, request)
//        LogUtil.printNetworkLog(request, response, "친구 memberId 리스트 획득")
//        when (response) {
//            is NetworkResult.Success -> {
//                response.data?.let {  data ->
//                    getFriendsList(
//                        accessToken = accessToken,
//                        friendIdsList = data.friendsIdList
//                    )
//                }
//            }
//            is NetworkResult.Error -> {
//
//            }
//            is NetworkResult.Exception -> { signInScreenSideEffectFlow.emit(SignInScreenSideEffect.Toast("오류가 발생했습니다.")) }
//        }
    }
//
    private suspend fun getFriendsList(
        accessToken: String,
        friendIdsList: List<String>
    ) {
//        val request =
//            com.whereareyounow.domain.request.friend.GetFriendListRequest(friendIdsList)
//        val response = getFriendListUseCase(accessToken, request)
//        LogUtil.printNetworkLog(request, response, "친구 리스트 획득")
//        when (response) {
//            is NetworkResult.Success -> {
//                response.data?.let { data ->
//                    val sortedList = data.friendsList.sortedBy { it.name }
//                    FriendProvider.friendsList.clear()
//                    for (i in sortedList.indices) {
//                        FriendProvider.friendsList.add(sortedList[i].copy(number = i, userId = ""))
//                        FriendProvider.friendsMap[sortedList[i].memberId] = sortedList[i]
//                    }
//                }
//            }
//            is NetworkResult.Error -> {
//
//            }
//            is NetworkResult.Exception -> { signInScreenSideEffectFlow.emit(SignInScreenSideEffect.Toast("오류가 발생했습니다.")) }
//        }
    }
}