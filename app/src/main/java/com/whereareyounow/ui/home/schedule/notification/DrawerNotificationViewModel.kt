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
import com.whereareyounow.domain.util.LogUtil
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
            val response = getFriendRequestListUseCase(accessToken, memberId)
            LogUtil.printNetworkLog(response, "친구 요청 조회")
            when (response) {
                is NetworkResult.Success -> {
                    response.data?.let {  data ->
                        requestList.addAll(response.data!!.friendsRequestList)
                        loadNames(requestList)
                    }
                }
                is NetworkResult.Error -> {  }
                is NetworkResult.Exception -> {  }
            }
        }
    }

    fun loadScheduleRequests() {
        viewModelScope.launch(Dispatchers.Default) {
            val accessToken = getAccessTokenUseCase().first()
            val memberId = getMemberIdUseCase().first()
            val response = getScheduleRequestListUseCase(accessToken, memberId)
            LogUtil.printNetworkLog(response, "일정 초대 조회")
            when (response) {
                is NetworkResult.Success -> {
                    response.data?.let { data ->
                        withContext(Dispatchers.Main) {
                            _scheduleRequestList.clear()
                            _scheduleRequestList.addAll(data.inviteList)
                        }
                    }
                }
                is NetworkResult.Error -> {  }
                is NetworkResult.Exception -> {  }
            }
        }
    }

    private suspend fun loadNames(list: List<FriendRequest>) {
        val accessToken = getAccessTokenUseCase().first()
        val requestList = mutableListOf<Pair<FriendRequest, Friend>>()
        for (friendRequest in list) {
            val response = getMemberDetailsUseCase(accessToken, friendRequest.senderId)
            LogUtil.printNetworkLog(response, "memberId로 유저 정보 조회")
            when (response) {
                is NetworkResult.Success -> {
                    response.data?.let { data ->
                        requestList.add(friendRequest to Friend(0, friendRequest.senderId, data.userName, data.userId, data.profileImage))
                    }
                }
                is NetworkResult.Error -> {  }
                is NetworkResult.Exception -> {  }
            }
        }
        withContext(Dispatchers.Main) {
            _friendRequestList.clear()
            _friendRequestList.addAll(requestList)
        }
    }

    fun acceptFriendRequest(friendRequest: FriendRequest) {
        viewModelScope.launch(Dispatchers.Default) {
            val accessToken = getAccessTokenUseCase().first()
            val memberId = getMemberIdUseCase().first()
            val request = AcceptFriendRequestRequest(friendRequest.friendRequestId, memberId, friendRequest.senderId)
            val response = acceptFriendRequestUseCase(accessToken, request)
            LogUtil.printNetworkLog(response, "친구 요청 수락")
            when (response) {
                is NetworkResult.Success -> {

                }
                is NetworkResult.Error -> {  }
                is NetworkResult.Exception -> {  }
            }
            loadFriendRequests()
            getFriendList()
        }
    }

    fun refuseFriendRequest(friendRequest: FriendRequest) {
        viewModelScope.launch(Dispatchers.Default) {
            val accessToken = getAccessTokenUseCase().first()
            val request = RefuseFriendRequestRequest(friendRequest.friendRequestId)
            val response = refuseFriendRequestUseCase(accessToken, request)
            LogUtil.printNetworkLog(response, "친구 요청 거절")
            when (response) {
                is NetworkResult.Success -> {

                }
                is NetworkResult.Error -> {  }
                is NetworkResult.Exception -> {  }
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