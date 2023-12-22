package com.whereareyounow.ui.signin

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.whereareyounow.data.FriendProvider
import com.whereareyounow.domain.entity.apimessage.fcm.UpdateFCMTokenRequest
import com.whereareyounow.domain.entity.apimessage.friend.GetFriendIdsListRequest
import com.whereareyounow.domain.entity.apimessage.friend.GetFriendListRequest
import com.whereareyounow.domain.entity.apimessage.signin.SignInRequest
import com.whereareyounow.domain.usecase.fcm.UpdateFCMTokenUseCase
import com.whereareyounow.domain.usecase.friend.GetFriendIdsListUseCase
import com.whereareyounow.domain.usecase.friend.GetFriendListUseCase
import com.whereareyounow.domain.usecase.signin.SaveAccessTokenUseCase
import com.whereareyounow.domain.usecase.signin.SaveMemberIdUseCase
import com.whereareyounow.domain.usecase.signin.SaveRefreshTokenUseCase
import com.whereareyounow.domain.usecase.signin.SignInUseCase
import com.whereareyounow.domain.util.LogUtil
import com.whereareyounow.domain.util.NetworkResult
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
class SignInViewModel @Inject constructor(
    private val application: Application,
    private val signInUseCase: SignInUseCase,
    private val saveAccessTokenUseCase: SaveAccessTokenUseCase,
    private val saveRefreshTokenUseCase: SaveRefreshTokenUseCase,
    private val saveMemberIdUseCase: SaveMemberIdUseCase,
    private val updateFCMTokenUseCase: UpdateFCMTokenUseCase,
    private val getFriendIdsListUseCase: GetFriendIdsListUseCase,
    private val getFriendListUseCase: GetFriendListUseCase
) : AndroidViewModel(application) {

    private val _inputId = MutableStateFlow("")
    val inputId: StateFlow<String> = _inputId

    private val _inputPassword = MutableStateFlow("")
    val inputPassword: StateFlow<String> = _inputPassword

    fun updateInputId(inputId: String) {
        _inputId.update { inputId }
    }
    fun updateInputPassword(inputPassword: String) {
        _inputPassword.update { inputPassword }
    }

    fun signIn(
        moveToHomeScreen: () -> Unit
    ) {
        viewModelScope.launch(Dispatchers.Default) {
            val request = SignInRequest(_inputId.value, _inputPassword.value)
            val response = signInUseCase(request)
            LogUtil.printNetworkLog(response, "로그인")
            when (response) {
                is NetworkResult.Success -> {
                    response.data?.let { data ->
                        saveAccessTokenUseCase("Bearer " + data.accessToken)
                        saveRefreshTokenUseCase(data.refreshToken)
                        saveMemberIdUseCase(data.memberId)

                        getFriendIdsList(
                            accessToken = "Bearer " + data.accessToken,
                            memberId = data.memberId
                        )

                        FirebaseMessaging.getInstance().token.addOnCompleteListener(
                            OnCompleteListener { task ->
                                if (!task.isSuccessful) {
                                    Log.e("FCM", "Fetching FCM registration token failed")
                                    return@OnCompleteListener
                                }

                                updateFCMToken(
                                    fcmToken = task.result,
                                    accessToken = "Bearer " + data.accessToken,
                                    memberId = data.memberId
                                )
                            }
                        )
                    }

                    withContext(Dispatchers.Main) {
                        moveToHomeScreen()
                    }
                }
                is NetworkResult.Error -> {

                }
                is NetworkResult.Exception -> {

                }
            }
        }
    }

    private fun updateFCMToken(
        fcmToken: String,
        accessToken: String,
        memberId: String
    ) {
        viewModelScope.launch(Dispatchers.Default) {
            if (fcmToken != "") {
                val request = UpdateFCMTokenRequest(memberId, fcmToken)
                val response = updateFCMTokenUseCase(accessToken, request)
                LogUtil.printNetworkLog(response, "FCM 토큰 저장")
                when (response) {
                    is NetworkResult.Success -> {

                    }
                    is NetworkResult.Error -> {

                    }
                    is NetworkResult.Exception -> {

                    }
                }
            }
        }
    }

    private suspend fun getFriendIdsList(
        accessToken: String,
        memberId: String
    ) {
        val request = GetFriendIdsListRequest(memberId)
        val response = getFriendIdsListUseCase(accessToken, request)
        LogUtil.printNetworkLog(response, "친구 memberId 리스트 획득")
        when (response) {
            is NetworkResult.Success -> {
                response.data?.let {  data ->
                    getFriendsList(
                        accessToken = accessToken,
                        friendIdsList = data.friendsIdList
                    )
                }
            }
            is NetworkResult.Error -> {

            }
            is NetworkResult.Exception -> {

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
                    FriendProvider.friendsList.clear()
                    for (i in 0 until data.friendsList.size) {
                        FriendProvider.friendsList.add(data.friendsList[i].copy(number = i, userId = ""))
                    }
                }
            }
            is NetworkResult.Error -> {

            }
            is NetworkResult.Exception -> {

            }
        }
    }
}