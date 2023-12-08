package com.whereareyounow.ui.splash

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.whereareyounow.data.FriendProvider
import com.whereareyounow.domain.entity.apimessage.friend.GetFriendIdsListRequest
import com.whereareyounow.domain.entity.apimessage.friend.GetFriendListRequest
import com.whereareyounow.domain.entity.apimessage.signin.ReissueTokenRequest
import com.whereareyounow.domain.usecase.friend.GetFriendIdsListUseCase
import com.whereareyounow.domain.usecase.friend.GetFriendListUseCase
import com.whereareyounow.domain.usecase.signin.GetAccessTokenUseCase
import com.whereareyounow.domain.usecase.signin.GetMemberIdUseCase
import com.whereareyounow.domain.usecase.signin.GetRefreshTokenUseCase
import com.whereareyounow.domain.usecase.signin.ReissueTokenUseCase
import com.whereareyounow.domain.usecase.signin.SaveAccessTokenUseCase
import com.whereareyounow.domain.usecase.signin.SaveRefreshTokenUseCase
import com.whereareyounow.domain.util.NetworkResult
import com.whereareyounow.util.NetworkManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val application: Application,
    private val getRefreshTokenUseCase: GetRefreshTokenUseCase,
    private val saveRefreshTokenUseCase: SaveRefreshTokenUseCase,
    private val saveAccessTokenUseCase: SaveAccessTokenUseCase,
    private val reissueTokenUseCase: ReissueTokenUseCase,
    private val getAccessTokenUseCase: GetAccessTokenUseCase,
    private val getMemberIdUseCase: GetMemberIdUseCase,
    private val getFriendIdsListUseCase: GetFriendIdsListUseCase,
    private val getFriendListUseCase: GetFriendListUseCase,
) : AndroidViewModel(application) {

    private val _checkingState = MutableStateFlow(CheckingState.NETWORK)
    val checkingState: StateFlow<CheckingState> = _checkingState
    private val _isNetworkConnectionErrorDialogShowing = MutableStateFlow(false)
    val isNetworkConnectionErrorDialogShowing: StateFlow<Boolean> = _isNetworkConnectionErrorDialogShowing
    private val _screenState = MutableStateFlow(ScreenState.SPLASH)
    val screenState: StateFlow<ScreenState> = _screenState
    private var isSignedIn = false

    fun updateCheckingState(state: CheckingState) {
        _checkingState.update { state }
    }

    fun updateIsNetworkConnectionErrorDialogShowing(isShowing: Boolean) {
        _isNetworkConnectionErrorDialogShowing.update { isShowing }
    }

    fun updateScreenState(state: ScreenState) {
        _screenState.update { state }
    }

    fun checkNetworkState(): Boolean {
        return NetworkManager.checkNetworkState()
    }

    suspend fun checkIsSignedIn(): Boolean {
        isSignedIn = getRefreshTokenUseCase().first().isNotEmpty()
        if (isSignedIn) {
            val init = CoroutineScope(Dispatchers.Default).async {
                initialize()
            }
            init.await()
        }
        return isSignedIn
    }

    private suspend fun initialize() {
        reissueToken()
        getFriendList()
    }

    private suspend fun reissueToken() {
        val refreshToken = getRefreshTokenUseCase().first()
        val reissueTokenRequest = ReissueTokenRequest(refreshToken)
        when (val reissueTokenResponse = reissueTokenUseCase(reissueTokenRequest)) {
            is NetworkResult.Success -> {
                reissueTokenResponse.data?.let { data ->
                    saveRefreshTokenUseCase(data.refreshToken)
                    saveAccessTokenUseCase("Bearer " + data.accessToken)
                }
                Log.e("reissueToken Success", "${reissueTokenResponse.data}")
            }
            is NetworkResult.Error -> {
                Log.e("reissueToken", "${reissueTokenResponse.code}, ${reissueTokenResponse.errorData}")
                isSignedIn = false
                return
            }
            is NetworkResult.Exception -> { Log.e("reissueToken", "${reissueTokenResponse.e}") }
        }
    }

    private suspend fun getFriendList() {
        val accessToken = getAccessTokenUseCase().first()
        val memberId = getMemberIdUseCase().first()
        val getFriendIdsListRequest = GetFriendIdsListRequest(memberId)
        when (val getFriendIdsListResponse = getFriendIdsListUseCase(accessToken, getFriendIdsListRequest)) {
            is NetworkResult.Success -> {
                getFriendIdsListResponse.data?.let { data ->
                    val getFriendListRequest = GetFriendListRequest(data.friendsIdList)
                    when (val getFriendListResponse = getFriendListUseCase(accessToken, getFriendListRequest)) {
                        is NetworkResult.Success -> {
                            getFriendListResponse.data?.let { data ->
                                FriendProvider.friendsList.clear()
                                FriendProvider.friendsList.addAll(data.friendsList)
                            }
                        }
                        is NetworkResult.Error -> { Log.e("reissueToken", "${getFriendListResponse.code}, ${getFriendListResponse.errorData}") }
                        is NetworkResult.Exception -> { Log.e("reissueToken", "${getFriendListResponse.e}") }
                    }
                }
            }
            is NetworkResult.Error -> { Log.e("reissueToken", "${getFriendIdsListResponse.code}, ${getFriendIdsListResponse.errorData}") }
            is NetworkResult.Exception -> { Log.e("reissueToken", "${getFriendIdsListResponse.e}") }
        }
    }

    enum class CheckingState {
        NETWORK, LOCATION_PERMISSION, SIGN_IN
    }

    enum class ScreenState {
        SPLASH, PERMISSION
    }
}