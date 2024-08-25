package com.whereareyounow.ui.main.schedule.notification

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.whereareyounow.data.FriendProvider
import com.whereareyounow.data.notification.DrawerNotificationContentSideEffect
import com.whereareyounow.data.notification.DrawerNotificationContentUIState
import com.whereareyounow.data.notification.ScheduleInvitationInfo
import com.whereareyounow.domain.entity.friend.FriendRequest
import com.whereareyounow.domain.entity.schedule.Friend
import com.whereareyounow.domain.usecase.signin.GetAccessTokenUseCase
import com.whereareyounow.domain.usecase.signin.GetMemberDetailsUseCase
import com.whereareyounow.domain.usecase.signin.GetMemberIdUseCase
import com.whereareyounow.domain.util.LogUtil
import com.whereareyounow.domain.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DrawerNotificationContentViewModel @Inject constructor(
    private val application: Application,
    private val getAccessTokenUseCase: GetAccessTokenUseCase,
    private val getMemberIdUseCase: GetMemberIdUseCase,
    private val getFriendRequestListUseCase: GetFriendRequestListUseCase,
    private val getMemberDetailsUseCase: GetMemberDetailsUseCase,
    private val acceptFriendRequestUseCase: AcceptFriendRequestUseCase,
    private val refuseFriendRequestUseCase: RefuseFriendRequestUseCase,
    private val getScheduleRequestListUseCase: GetScheduleInvitationUseCase,
    private val acceptScheduleUseCase: AcceptScheduleUseCase,
    private val getTodayScheduleCountUseCase: GetTodayScheduleCountUseCase,
    private val refuseOrQuitScheduleUseCase: RefuseOrQuitScheduleUseCase,
    private val getFriendIdsListUseCase: GetFriendIdsListUseCase,
    private val getFriendListUseCase: GetFriendListUseCase,
) : AndroidViewModel(application) {

    private val _drawerNotificationContentUIState = MutableStateFlow(DrawerNotificationContentUIState())
    val drawerNotificationUIState = _drawerNotificationContentUIState.asStateFlow()
    val drawerNotificationContentSideEffectFlow = MutableSharedFlow<DrawerNotificationContentSideEffect>()

    fun loadFriendRequests() {
        viewModelScope.launch(Dispatchers.Default) {
            val accessToken = getAccessTokenUseCase().first()
            val myMemberId = getMemberIdUseCase().first()
            val response = getFriendRequestListUseCase(accessToken, myMemberId)
            LogUtil.printNetworkLog("memberId = $myMemberId", response, "친구 요청 조회")
            when (response) {
                is NetworkResult.Success -> {
                    response.data?.let { data ->
                        loadNames(data.friendsRequestList)
                    }
                }
                is NetworkResult.Error -> {  }
                is NetworkResult.Exception -> { drawerNotificationContentSideEffectFlow.emit(DrawerNotificationContentSideEffect.Toast("오류가 발생했습니다.")) }
            }
        }
    }

    private suspend fun loadNames(list: List<FriendRequest>) {
        val accessToken = getAccessTokenUseCase().first()
        val requestList = mutableListOf<Pair<FriendRequest, Friend>>()
        for (friendRequest in list) {
            val response = getMemberDetailsUseCase(accessToken, friendRequest.senderId)
            LogUtil.printNetworkLog("senderId = ${friendRequest.senderId}", response, "memberId로 유저 정보 조회")
            when (response) {
                is NetworkResult.Success -> {
                    response.data?.let { data ->
                        requestList.add(friendRequest to Friend(0, friendRequest.senderId, data.userName, data.userId, data.profileImage))
                    }
                }
                is NetworkResult.Error -> {  }
                is NetworkResult.Exception -> { drawerNotificationContentSideEffectFlow.emit(DrawerNotificationContentSideEffect.Toast("오류가 발생했습니다.")) }
            }
        }
        _drawerNotificationContentUIState.update { state ->
            state.copy(friendRequestsList = requestList.sortedByDescending { it.first.requestedTime })
        }
    }

    fun loadScheduleRequests() {
        viewModelScope.launch(Dispatchers.Default) {
            val accessToken = getAccessTokenUseCase().first()
            val myMemberId = getMemberIdUseCase().first()
            val response = getScheduleRequestListUseCase(accessToken, myMemberId)
            LogUtil.printNetworkLog("memberId = $myMemberId", response, "일정 초대 조회")
            when (response) {
                is NetworkResult.Success -> {
                    response.data?.let { data ->
                        _drawerNotificationContentUIState.update { state ->
                            state.copy(
                                scheduleRequestsList = data.inviteList.map { invitation ->
                                    ScheduleInvitationInfo(
                                        scheduleId = invitation.scheduleId,
                                        title = invitation.title,
                                        inviterUserName = invitation.userName,
                                        year = invitation.start.split("-")[0],
                                        month = invitation.start.split("-")[1],
                                        date = invitation.start.split("-")[2].split("T")[0],
                                        hour = invitation.start.split("T")[1].split(":")[0],
                                        minute = invitation.start.split("T")[1].split(":")[1],
                                        invitedTime = invitation.createTime
                                    )
                                }.sortedByDescending { it.invitedTime }
                            )
                        }
                    }
                }
                is NetworkResult.Error -> {  }
                is NetworkResult.Exception -> { drawerNotificationContentSideEffectFlow.emit(DrawerNotificationContentSideEffect.Toast("오류가 발생했습니다.")) }
            }
        }
    }



    fun acceptFriendRequest(friendRequest: FriendRequest) {
        viewModelScope.launch(Dispatchers.Default) {
            val accessToken = getAccessTokenUseCase().first()
            val memberId = getMemberIdUseCase().first()
            val request = com.whereareyounow.domain.request.friend.AcceptFriendRequestRequest(
                friendRequest.friendRequestId,
                memberId,
                friendRequest.senderId
            )
            val response = acceptFriendRequestUseCase(accessToken, request)
            LogUtil.printNetworkLog(request, response, "친구 요청 수락")
            when (response) {
                is NetworkResult.Success -> { drawerNotificationContentSideEffectFlow.emit(DrawerNotificationContentSideEffect.Toast("친구 요청이 수락되었습니다.")) }
                is NetworkResult.Error -> {  }
                is NetworkResult.Exception -> { drawerNotificationContentSideEffectFlow.emit(DrawerNotificationContentSideEffect.Toast("오류가 발생했습니다.")) }
            }
            loadFriendRequests()
            getFriendList()
        }
    }

    fun refuseFriendRequest(friendRequest: FriendRequest) {
        viewModelScope.launch(Dispatchers.Default) {
            val accessToken = getAccessTokenUseCase().first()
            val request =
                com.whereareyounow.domain.request.friend.DeleteFriendRequest(friendRequest.friendRequestId)
            val response = refuseFriendRequestUseCase(accessToken, request)
            LogUtil.printNetworkLog(request, response, "친구 요청 거절")
            when (response) {
                is NetworkResult.Success -> { drawerNotificationContentSideEffectFlow.emit(DrawerNotificationContentSideEffect.Toast("친구 요청이 거절되었습니다.")) }
                is NetworkResult.Error -> {  }
                is NetworkResult.Exception -> { drawerNotificationContentSideEffectFlow.emit(DrawerNotificationContentSideEffect.Toast("오류가 발생했습니다.")) }
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
            val request = com.whereareyounow.domain.request.schedule.AcceptScheduleRequest(
                memberId,
                scheduleId
            )
            val response = acceptScheduleUseCase(accessToken, request)
            LogUtil.printNetworkLog(request, response, "일정 수락")
            when (response) {
                is NetworkResult.Success -> { drawerNotificationContentSideEffectFlow.emit(DrawerNotificationContentSideEffect.Toast("일정 초대가 수락되었습니다.")) }
                is NetworkResult.Error -> {  }
                is NetworkResult.Exception -> { drawerNotificationContentSideEffectFlow.emit(DrawerNotificationContentSideEffect.Toast("오류가 발생했습니다.")) }
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
            val request = com.whereareyounow.domain.request.schedule.RefuseOrQuitScheduleRequest(
                memberId,
                scheduleId
            )
            val response = refuseOrQuitScheduleUseCase(accessToken, request)
            LogUtil.printNetworkLog(request, response, "일정 거절")
            when (response) {
                is NetworkResult.Success -> { drawerNotificationContentSideEffectFlow.emit(DrawerNotificationContentSideEffect.Toast("일정 초대가 거절되었습니다.")) }
                is NetworkResult.Error -> {  }
                is NetworkResult.Exception -> { drawerNotificationContentSideEffectFlow.emit(DrawerNotificationContentSideEffect.Toast("오류가 발생했습니다.")) }
            }
            loadScheduleRequests()
            updateCalendar()
            updateBriefCalendar()
        }
    }

    private suspend fun getFriendList() {
        val accessToken = getAccessTokenUseCase().first()
        val memberId = getMemberIdUseCase().first()
        val request = com.whereareyounow.domain.request.friend.GetFriendIdsListRequest(memberId)
        val response = getFriendIdsListUseCase(accessToken, request)
        LogUtil.printNetworkLog(request, response, "친구 memberId목록 가져오기")
        when (response) {
            is NetworkResult.Success -> {
                response.data?.let { data ->
                    val getFriendListRequest =
                        com.whereareyounow.domain.request.friend.GetFriendListRequest(data.friendsIdList)
                    val getFriendListResponse = getFriendListUseCase(accessToken, getFriendListRequest)
                    LogUtil.printNetworkLog(getFriendListRequest, getFriendListResponse, "친구 목록 가져오기")
                    when (getFriendListResponse) {
                        is NetworkResult.Success -> {
                            getFriendListResponse.data?.let { data ->
                                FriendProvider.friendsList.clear()
                                FriendProvider.friendsList.addAll(data.friendsList)
                            }
                        }
                        is NetworkResult.Error -> {  }
                        is NetworkResult.Exception -> { drawerNotificationContentSideEffectFlow.emit(DrawerNotificationContentSideEffect.Toast("오류가 발생했습니다.")) }
                    }
                }
            }
            is NetworkResult.Error -> {  }
            is NetworkResult.Exception -> {
                withContext(Dispatchers.Main) {
                    Toast.makeText(application, "오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun getTodayScheduleCount() {
        viewModelScope.launch {
            val accessToken = getAccessTokenUseCase().first()
            val memberId = getMemberIdUseCase().first()
            val response = getTodayScheduleCountUseCase(accessToken, memberId)
            LogUtil.printNetworkLog("memberId = $memberId", response, "오늘 일정 개수")
            when (response) {
                is NetworkResult.Success -> {
                    response.data?.let { data ->
                        _drawerNotificationContentUIState.update {
                            it.copy(todayScheduleCount = data.todaySchedule)
                        }
                    }
                }
                is NetworkResult.Error -> {

                }
                is NetworkResult.Exception -> { drawerNotificationContentSideEffectFlow.emit(DrawerNotificationContentSideEffect.Toast("오류가 발생했습니다.")) }
            }
        }
    }
}
