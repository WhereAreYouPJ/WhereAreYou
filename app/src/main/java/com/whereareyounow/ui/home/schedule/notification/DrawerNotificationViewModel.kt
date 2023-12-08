package com.whereareyounow.ui.home.schedule.notification

import android.app.Application
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.whereareyounow.data.FriendProvider
import com.whereareyounow.domain.entity.apimessage.friend.AcceptFriendRequestRequest
import com.whereareyounow.domain.entity.apimessage.friend.GetFriendIdsListRequest
import com.whereareyounow.domain.entity.apimessage.friend.GetFriendListRequest
import com.whereareyounow.domain.entity.apimessage.friend.RefuseFriendRequestRequest
import com.whereareyounow.domain.entity.apimessage.schedule.AcceptScheduleRequest
import com.whereareyounow.domain.entity.apimessage.schedule.RefuseOrQuitScheduleRequest
import com.whereareyounow.domain.entity.apimessage.schedule.ScheduleInvitation
import com.whereareyounow.domain.entity.friend.FriendRequest
import com.whereareyounow.domain.entity.schedule.Friend
import com.whereareyounow.domain.usecase.friend.AcceptFriendRequestUseCase
import com.whereareyounow.domain.usecase.friend.GetFriendIdsListUseCase
import com.whereareyounow.domain.usecase.friend.GetFriendListUseCase
import com.whereareyounow.domain.usecase.friend.GetFriendRequestListUseCase
import com.whereareyounow.domain.usecase.friend.RefuseFriendRequestUseCase
import com.whereareyounow.domain.usecase.schedule.AcceptScheduleUseCase
import com.whereareyounow.domain.usecase.schedule.GetScheduleInvitationUseCase
import com.whereareyounow.domain.usecase.schedule.RefuseOrQuitScheduleUseCase
import com.whereareyounow.domain.usecase.signin.GetAccessTokenUseCase
import com.whereareyounow.domain.usecase.signin.GetMemberDetailsUseCase
import com.whereareyounow.domain.usecase.signin.GetMemberIdUseCase
import com.whereareyounow.domain.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DrawerNotificationViewModel @Inject constructor(
    private val application: Application,
    private val getAccessTokenUseCase: GetAccessTokenUseCase,
    private val getMemberIdUseCase: GetMemberIdUseCase,
    private val getFriendRequestListUseCase: GetFriendRequestListUseCase,
    private val getMemberDetailsUseCase: GetMemberDetailsUseCase,
    private val acceptFriendRequestUseCase: AcceptFriendRequestUseCase,
    private val refuseFriendRequestUseCase: RefuseFriendRequestUseCase,
    private val getScheduleRequestListUseCase: GetScheduleInvitationUseCase,
    private val acceptScheduleUseCase: AcceptScheduleUseCase,
    private val refuseOrQuitScheduleUseCase: RefuseOrQuitScheduleUseCase,
    private val getFriendIdsListUseCase: GetFriendIdsListUseCase,
    private val getFriendListUseCase: GetFriendListUseCase,
) : AndroidViewModel(application) {

    private val _friendRequestList = mutableStateListOf<Pair<FriendRequest, Friend>>()
    val friendRequestList: List<Pair<FriendRequest, Friend>> = _friendRequestList
    private val _scheduleRequestList = mutableStateListOf<ScheduleInvitation>()
    val scheduleRequestList: List<ScheduleInvitation> = _scheduleRequestList


    fun loadFriendRequests() {
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

    fun loadScheduleRequests() {
        viewModelScope.launch(Dispatchers.Default) {
            val accessToken = getAccessTokenUseCase().first()
            val memberId = getMemberIdUseCase().first()
            when (val getScheduleRequestResponse = getScheduleRequestListUseCase(accessToken, memberId)) {
                is NetworkResult.Success -> {
                    getScheduleRequestResponse.data?.let { data ->
                        Log.e("loadScheduleRequests Success", "${data}")
                        withContext(Dispatchers.Main) {
                            _scheduleRequestList.clear()
                            _scheduleRequestList.addAll(data.inviteList)
                        }
                    }
                }
                is NetworkResult.Error -> { Log.e("error", "${getScheduleRequestResponse.code} ${getScheduleRequestResponse.errorData}") }
                is NetworkResult.Exception -> { Log.e("exception", "${getScheduleRequestResponse.e}") }
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
                        networkResult.data?.let { data ->
                            requestList.add(friendRequest to Friend(0, friendRequest.senderId, data.userName, data.userId, data.profileImage))
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
            getFriendList()
        }
    }

    fun refuseFriendRequest(friendRequest: FriendRequest) {
        viewModelScope.launch(Dispatchers.Default) {
            val accessToken = getAccessTokenUseCase().first()
            val request = RefuseFriendRequestRequest(friendRequest.friendRequestId)
            val networkResult = refuseFriendRequestUseCase(accessToken, request)
            when (networkResult) {
                is NetworkResult.Success -> {

                }
                is NetworkResult.Error -> { Log.e("error", "${networkResult.code} ${networkResult.errorData}") }
                is NetworkResult.Exception -> { Log.e("exception", "exception") }
            }
            loadFriendRequests()
            getFriendList()
        }
    }

    fun acceptScheduleRequest(
        scheduleId: String,
        updateCalendar: () -> Unit,
        updateBriefCalendar: () -> Unit,
    ) {
        viewModelScope.launch(Dispatchers.Default) {
            val accessToken = getAccessTokenUseCase().first()
            val memberId = getMemberIdUseCase().first()
            val acceptScheduleRequestRequest = AcceptScheduleRequest(memberId, scheduleId)
            when (val acceptScheduleResponse = acceptScheduleUseCase(accessToken, acceptScheduleRequestRequest)) {
                is NetworkResult.Success -> {
                    Log.e("success", "${acceptScheduleResponse.data}")
                }
                is NetworkResult.Error -> { Log.e("error", "${acceptScheduleResponse.code} ${acceptScheduleResponse.errorData}") }
                is NetworkResult.Exception -> { Log.e("exception", "exception") }
            }
            loadScheduleRequests()
            updateCalendar()
            updateBriefCalendar()
        }
    }

    fun refuseScheduleRequest(
        scheduleId: String,
        updateCalendar: () -> Unit,
        updateBriefCalendar: () -> Unit,
    ) {
        viewModelScope.launch(Dispatchers.Default) {
            val accessToken = getAccessTokenUseCase().first()
            val memberId = getMemberIdUseCase().first()
            val refuseOrQuitScheduleRequest = RefuseOrQuitScheduleRequest(memberId, scheduleId)
            when (val refuseOrQuitScheduleResponse = refuseOrQuitScheduleUseCase(accessToken, refuseOrQuitScheduleRequest)) {
                is NetworkResult.Success -> {
                    Log.e("success", "${refuseOrQuitScheduleResponse.data}")
                }
                is NetworkResult.Error -> { Log.e("error", "${refuseOrQuitScheduleResponse.code} ${refuseOrQuitScheduleResponse.errorData}") }
                is NetworkResult.Exception -> { Log.e("exception", "exception") }
            }
            loadScheduleRequests()
            updateCalendar()
            updateBriefCalendar()
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

    init {
        loadFriendRequests()
        loadScheduleRequests()
    }
}