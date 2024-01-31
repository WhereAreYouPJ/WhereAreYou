package com.whereareyounow.ui.splash

import android.app.Application
import android.widget.Toast
import androidx.annotation.Keep
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
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
import com.whereareyounow.domain.util.LogUtil
import com.whereareyounow.domain.util.NetworkResult
import com.whereareyounow.util.NetworkManager
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
            val init = viewModelScope.launch(Dispatchers.Default) {
                initialize()
            }
            init.join()
        }
        return isSignedIn
    }

    private suspend fun initialize() {
        val refreshToken = getRefreshTokenUseCase().first()
        reissueToken(refreshToken)
        val accessToken = getAccessTokenUseCase().first()
        val memberId = getMemberIdUseCase().first()
        getFriendIdsList(
            accessToken = accessToken,
            memberId = memberId
        )
    }

    private suspend fun reissueToken(
        refreshToken: String
    ) {
        val request = ReissueTokenRequest(refreshToken)
        val response = reissueTokenUseCase(request)
        LogUtil.printNetworkLog(response, "토큰 재발급")
        when (response) {
            is NetworkResult.Success -> {
                response.data?.let { data ->
                    saveRefreshTokenUseCase(data.refreshToken)
                    saveAccessTokenUseCase("Bearer " + data.accessToken)
                }
            }
            is NetworkResult.Error -> {
                isSignedIn = false
            }
            is NetworkResult.Exception -> {
                withContext(Dispatchers.Main) {
                    Toast.makeText(application, "오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    @Keep
    private suspend fun getFriendIdsList(
        accessToken: String,
        memberId: String
    ) {
        val request = GetFriendIdsListRequest(memberId)
        val response = getFriendIdsListUseCase(accessToken, request)
        LogUtil.printNetworkLog(response, "친구 memberId 리스트 획득")
        when (response) {
            is NetworkResult.Success -> {
                response.data?.let { data ->
                    getFriendsList(
                        accessToken = accessToken,
                        friendIdsList = data.friendsIdList
                    )
                }
            }
            is NetworkResult.Error -> {

            }
            is NetworkResult.Exception -> {
                withContext(Dispatchers.Main) {
                    Toast.makeText(application, "오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private suspend fun getFriendsList(
        accessToken: String,
        friendIdsList: List<String>
    ) {
        val request = GetFriendListRequest(friendIdsList)
        val response = getFriendListUseCase(accessToken, request)
        LogUtil.printNetworkLog(response, "친구 리스트 획득")
        when (response) {
            is NetworkResult.Success -> {
                response.data?.let { data ->
                    val sortedList = data.friendsList.sortedBy { it.name }
                    FriendProvider.friendsList.clear()
                    for (i in sortedList.indices) {
                        FriendProvider.friendsList.add(sortedList[i].copy(number = i, userId = ""))
                    }
                }
            }
            is NetworkResult.Error -> {

            }
            is NetworkResult.Exception -> {
                withContext(Dispatchers.Main) {
                    Toast.makeText(application, "오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    enum class CheckingState {
        NETWORK, LOCATION_PERMISSION, SIGN_IN
    }

    enum class ScreenState {
        SPLASH, PERMISSION
    }
}