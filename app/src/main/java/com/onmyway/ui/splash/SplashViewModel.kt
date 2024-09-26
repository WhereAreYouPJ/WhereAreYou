package com.onmyway.ui.splash

import android.app.Application
import androidx.annotation.Keep
import androidx.lifecycle.AndroidViewModel
import com.onmyway.domain.usecase.friend.GetFriendListUseCase
import com.onmyway.globalvalue.type.SplashCheckingState
import com.onmyway.util.NetworkManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val application: Application,
    private val networkManager: NetworkManager,
    private val getFriendListUseCase: GetFriendListUseCase,
//    private val getRefreshTokenUseCase: GetRefreshTokenUseCase,
//    private val saveRefreshTokenUseCase: SaveRefreshTokenUseCase,
//    private val saveAccessTokenUseCase: SaveAccessTokenUseCase,
//    private val reissueAccessTokenUseCase: ReissueAccessTokenUseCase,
//    private val getAccessTokenUseCase: GetAccessTokenUseCase,
//    private val getMemberIdUseCase: GetMemberIdUseCase,
//    private val getFriendIdsListUseCase: GetFriendIdsListUseCase,
//    private val getFriendListUseCase: GetFriendListUseCase,
) : AndroidViewModel(application) {

    private val _checkingState = MutableStateFlow(SplashCheckingState.Network)
    val checkingState: StateFlow<SplashCheckingState> = _checkingState
    private val _isNetworkConnectionErrorDialogShowing = MutableStateFlow(false)
    val isNetworkConnectionErrorDialogShowing: StateFlow<Boolean> = _isNetworkConnectionErrorDialogShowing
    private val _screenState = MutableStateFlow(ScreenState.Splash)
    val screenState: StateFlow<ScreenState> = _screenState
    private var isSignedIn = false

    fun updateCheckingState(state: SplashCheckingState) {
        _checkingState.update { state }
    }

    fun updateIsNetworkConnectionErrorDialogShowing(isShowing: Boolean) {
        _isNetworkConnectionErrorDialogShowing.update { isShowing }
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
//        isSignedIn = getRefreshTokenUseCase().first().isNotEmpty()
//        if (isSignedIn) {
//            val init = viewModelScope.launch(Dispatchers.Default) {
//                initialize()
//            }
//            init.join()
//        }
//        return isSignedIn
        return false
    }

    private suspend fun initialize() {
//        val refreshToken = getRefreshTokenUseCase().first()
//        reissueToken(refreshToken)
//        val accessToken = getAccessTokenUseCase().first()
//        val memberId = getMemberIdUseCase().first()
//        getFriendIdsList(
//            accessToken = accessToken,
//            memberId = memberId
//        )
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