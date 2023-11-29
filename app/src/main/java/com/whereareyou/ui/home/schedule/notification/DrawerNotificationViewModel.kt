package com.whereareyou.ui.home.schedule.notification

import android.app.Application
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.whereareyou.domain.entity.apimessage.friend.AcceptFriendRequestRequest
import com.whereareyou.domain.entity.apimessage.friend.RefuseFriendRequestRequest
import com.whereareyou.domain.entity.friend.FriendRequest
import com.whereareyou.domain.entity.schedule.Friend
import com.whereareyou.domain.usecase.friend.AcceptFriendRequestUseCase
import com.whereareyou.domain.usecase.friend.GetFriendRequestListUseCase
import com.whereareyou.domain.usecase.friend.RefuseFriendRequestUseCase
import com.whereareyou.domain.usecase.signin.GetAccessTokenUseCase
import com.whereareyou.domain.usecase.signin.GetMemberDetailsUseCase
import com.whereareyou.domain.usecase.signin.GetMemberIdUseCase
import com.whereareyou.domain.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DrawerNotificationViewModel @Inject constructor(
    private val getAccessTokenUseCase: GetAccessTokenUseCase,
    private val getMemberIdUseCase: GetMemberIdUseCase,
    private val getFriendRequestListUseCase: GetFriendRequestListUseCase,
    private val getMemberDetailsUseCase: GetMemberDetailsUseCase,
    private val acceptFriendRequestUseCase: AcceptFriendRequestUseCase,
    private val refuseFriendRequestUseCase: RefuseFriendRequestUseCase,
    private val application: Application
) : AndroidViewModel(application) {

    private val _friendRequestList = mutableStateListOf<Pair<FriendRequest, Friend>>()
    val friendRequestList: List<Pair<FriendRequest, Friend>> = _friendRequestList

    private fun loadFriendRequests() {
        viewModelScope.launch(Dispatchers.Default) {
            val accessToken = getAccessTokenUseCase().first()
            val memberId = getMemberIdUseCase().first()
            val requestList = mutableListOf<FriendRequest>()
            val networkResult = getFriendRequestListUseCase(accessToken, memberId)
            when (networkResult) {
                is NetworkResult.Success -> {
                    if (networkResult.data != null) {
                        Log.e("loadFriendRequests Success", networkResult.data!!.friendsRequestList.toString())
                        requestList.addAll(networkResult.data!!.friendsRequestList)
                        loadNames(requestList)
                    }
                }
                is NetworkResult.Error -> { Log.e("error", "${networkResult.code} ${networkResult.errorData}") }
                is NetworkResult.Exception -> { Log.e("exception", "exception") }
            }
        }
    }

    private fun loadNames(list: List<FriendRequest>) {
        viewModelScope.launch(Dispatchers.Default) {
            val accessToken = getAccessTokenUseCase().first()
            val requestList = mutableListOf<Pair<FriendRequest, Friend>>()
            for (friendRequest in list) {
                val networkResult = getMemberDetailsUseCase(accessToken, friendRequest.senderId)
                when (networkResult) {
                    is NetworkResult.Success -> {
                        if (networkResult.data!= null) {
                            Log.e("loadNames Success", networkResult.data!!.toString())
                            requestList.add(friendRequest to Friend(0, networkResult.data!!.userName, networkResult.data!!.userId, networkResult.data!!.profileImage))
                        }
                    }
                    is NetworkResult.Error -> { Log.e("error", "${networkResult.code} ${networkResult.errorData}") }
                    is NetworkResult.Exception -> { Log.e("exception", "exception") }
                }
            }
            withContext(Dispatchers.Main) {
                _friendRequestList.clear()
                _friendRequestList.addAll(requestList)
            }
        }
    }

    fun acceptFriendRequest(friendRequest: FriendRequest) {
        viewModelScope.launch(Dispatchers.Default) {
            val accessToken = getAccessTokenUseCase().first()
            val memberId = getMemberIdUseCase().first()
            val request = AcceptFriendRequestRequest(friendRequest.friendRequestId, memberId, friendRequest.senderId)
            val networkResult = acceptFriendRequestUseCase(accessToken, request)
            when (networkResult) {
                is NetworkResult.Success -> {
//                    if (networkResult.data != null) {
//                        Log.e("acceptFriendRequest Success", networkResult.data!!.toString())
//                    }
                }
                is NetworkResult.Error -> { Log.e("error", "${networkResult.code} ${networkResult.errorData}") }
                is NetworkResult.Exception -> { Log.e("exception", "exception") }
            }
            loadFriendRequests()
        }
    }

    fun refuseFriendRequest(friendRequest: FriendRequest) {
        viewModelScope.launch(Dispatchers.Default) {
            val accessToken = getAccessTokenUseCase().first()
            val request = RefuseFriendRequestRequest(friendRequest.friendRequestId)
            val networkResult = refuseFriendRequestUseCase(accessToken, request)
            when (networkResult) {
                is NetworkResult.Success -> {
//                    if (networkResult.data != null) {
//                        Log.e("refuseFriendRequest Success", networkResult.data!!.toString())
//                    }
                }
                is NetworkResult.Error -> { Log.e("error", "${networkResult.code} ${networkResult.errorData}") }
                is NetworkResult.Exception -> { Log.e("exception", "exception") }
            }
            loadFriendRequests()
        }
    }

    init {
        loadFriendRequests()
    }
}