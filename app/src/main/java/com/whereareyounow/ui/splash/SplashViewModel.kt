package com.whereareyounow.ui.splash

import android.app.Application
import androidx.annotation.Keep
import androidx.compose.ui.util.fastMap
import androidx.compose.ui.util.fastMapIndexed
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.whereareyounow.data.cached.AuthData
import com.whereareyounow.data.cached.FriendList
import com.whereareyounow.domain.entity.schedule.Friend
import com.whereareyounow.domain.request.friend.GetFriendListRequest
import com.whereareyounow.domain.usecase.datastore.GetMemberCodeUseCase
import com.whereareyounow.domain.usecase.datastore.GetMemberSeqUseCase
import com.whereareyounow.domain.usecase.datastore.GetRefreshTokenUseCase
import com.whereareyounow.domain.usecase.datastore.SaveAccessTokenUseCase
import com.whereareyounow.domain.usecase.datastore.SaveRefreshTokenUseCase
import com.whereareyounow.domain.usecase.friend.GetFriendListUseCase
import com.whereareyounow.domain.util.onError
import com.whereareyounow.domain.util.onException
import com.whereareyounow.domain.util.onSuccess
import com.whereareyounow.globalvalue.type.SplashCheckingState
import com.whereareyounow.util.NetworkManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val application: Application,
    private val networkManager: NetworkManager,
    private val getRefreshTokenUseCase: GetRefreshTokenUseCase,
    private val saveAccessTokenUseCase: SaveAccessTokenUseCase,
    private val saveRefreshTokenUseCase: SaveRefreshTokenUseCase,
    private val getMemberSeqUseCase: GetMemberSeqUseCase,
    private val getMemberCodeUseCase: GetMemberCodeUseCase,
    private val getFriendListUseCase: GetFriendListUseCase,
//    private val getRefreshTokenUseCase: GetRefreshTokenUseCase,
//    private val saveRefreshTokenUseCase: SaveRefreshTokenUseCase,
//    private val saveAccessTokenUseCase: SaveAccessTokenUseCase,
//    private val reissueAccessTokenUseCase: ReissueAccessTokenUseCase,
//    private val getAccessTokenUseCase: GetAccessTokenUseCase,
//    private val getMemberIdUseCase: GetMemberIdUseCase,
//    private val getFriendIdsListUseCase: GetFriendIdsListUseCase,
) : AndroidViewModel(application) {

    private val _checkingState = MutableStateFlow(SplashCheckingState.Network)
    val checkingState: StateFlow<SplashCheckingState> = _checkingState
    private val _isNetworkConnectionErrorDialogShowing = MutableStateFlow(false)
    val isNetworkConnectionErrorDialogShowing: StateFlow<Boolean> = _isNetworkConnectionErrorDialogShowing
    private val _screenState = MutableStateFlow(ScreenState.Splash)
    val screenState: StateFlow<ScreenState> = _screenState

    fun updateCheckingState(state: SplashCheckingState) {
        _checkingState.update { state }
    }

    fun updateScreenState(state: ScreenState) {
        _screenState.update { state }
    }

    fun checkNetworkState() {
        if (networkManager.checkNetworkState()) {
            _checkingState.update { SplashCheckingState.LocationPermission }
            _isNetworkConnectionErrorDialogShowing.update { false }
        } else {
            _isNetworkConnectionErrorDialogShowing.update { true }
        }
    }

    suspend fun checkIsSignedIn(): Boolean {
        var isSignedIn = false
        viewModelScope.launch {
            val refreshToken = getRefreshTokenUseCase()
            if (!refreshToken.isNullOrEmpty()) {
                isSignedIn = true
                initialize()
            } else {
//                reissueAccessTokenUseCase(refreshToken)
//                    .onEach { networkResult ->
//                        networkResult.onSuccess { code, message, data ->
//                            data?.let {
//                                saveAccessTokenUseCase(data.accessToken ?: "")
//                                saveRefreshTokenUseCase(data.refreshToken ?: "")
//                                getUserData()
//                            }
//                            moveToMainScreen()
//                        }.onError { code, message ->
//                            when (code) {
//                                in 400 .. 499 -> {
//                                    moveToSignInScreen()
//                                }
//                                else -> {
//
//                                }
//                            }
//                        }.onException {
//
//                        }
//                    }
//                    .catch { LogUtil.e("flow error", "reissueAccessTokenUseCase") }
//                    .launchIn(viewModelScope)
            }
        }.join()
        return isSignedIn
    }

    private fun initialize() {
        AuthData.memberSeq = (getMemberSeqUseCase() ?: "0").toInt()
        AuthData.memberCode = getMemberCodeUseCase() ?: ""
        val requestData = GetFriendListRequest(AuthData.memberSeq)
        getFriendListUseCase(requestData)
            .onEach { networkResult ->  
                networkResult.onSuccess { code, message, data ->
                    data?.let {
                        FriendList.list.addAll((data).fastMapIndexed { idx, item ->
                            Friend(
                                number = idx,
                                memberSeq = item.memberSeq,
                                name = item.userName,
                                profileImgUrl = item.profileImage
                            )
                        })
                    }
                }.onError { code, message ->

                }.onException {  }
            }
            .catch {  }
            .launchIn(viewModelScope)
    }

    private suspend fun reissueToken(
        refreshToken: String
    ) {
//        val request = com.whereareyounow.domain.request.signin.ReissueTokenRequest(refreshToken)
//        val response = reissueAccessTokenUseCase(request)
//        LogUtil.printNetworkLog(request, response, "토큰 재발급")
//        when (response) {
//            is NetworkResult.Success -> {
//                response.data?.let { data ->
//                    saveRefreshTokenUseCase(data.refreshToken)
//                    saveAccessTokenUseCase("Bearer " + data.accessToken)
//                }
//            }
//            is NetworkResult.Error -> {
//                isSignedIn = false
//            }
//            is NetworkResult.Exception -> {
//                withContext(Dispatchers.Main) {
//                    Toast.makeText(application, "오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
//                }
//            }
//        }
    }

    @Keep
    private suspend fun getFriendList(
        accessToken: String,
        memberId: String
    ) {
//        val request = com.whereareyounow.domain.request.friend.GetFriendIdsListRequest(memberId)
//        val response = getFriendIdsListUseCase(accessToken, request)
//        LogUtil.printNetworkLog(request, response, "친구 memberId 리스트 획득")
//        when (response) {
//            is NetworkResult.Success -> {
//                response.data?.let { data ->
//                    getFriendsList(
//                        accessToken = accessToken,
//                        friendIdsList = data.friendsIdList
//                    )
//                }
//            }
//            is NetworkResult.Error -> {
//
//            }
//            is NetworkResult.Exception -> {
//                withContext(Dispatchers.Main) {
//                    Toast.makeText(application, "오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
//                }
//            }
//        }
    }

    private suspend fun getFriendsList(
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
//                    }
//                }
//            }
//            is NetworkResult.Error -> {
//
//            }
//            is NetworkResult.Exception -> {
//                withContext(Dispatchers.Main) {
//                    Toast.makeText(application, "오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
//                }
//            }
//        }
    }



    enum class ScreenState {
        Splash, Permission
    }
}