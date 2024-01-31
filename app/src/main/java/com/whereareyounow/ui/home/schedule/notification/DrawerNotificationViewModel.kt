package com.whereareyounow.ui.home.schedule.notification

import android.app.Application
import android.util.Log
import android.widget.Toast
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
    private val _scheduleRequestList = mutableStateListOf<ScheduleInvitationInfo>()
    val scheduleRequestList: List<ScheduleInvitationInfo> = _scheduleRequestList


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
                        requestList.addAll(data.friendsRequestList)
                        loadNames(requestList)
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
                            data.inviteList.forEach {
                                _scheduleRequestList.add(
                                    ScheduleInvitationInfo(
                                        scheduleId = it.scheduleId,
                                        title = it.title,
                                        userName = it.userName,
                                        year = it.start.split("-")[0],
                                        month = it.start.split("-")[1],
                                        date = it.start.split("-")[2].split("T")[0],
                                        hour = it.start.split("T")[1].split(":")[0],
                                        minute = it.start.split("T")[1].split(":")[1]
                                    )
                                )
                            }
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
                is NetworkResult.Exception -> {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(application, "오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
                    }
                }
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
                is NetworkResult.Exception -> {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(application, "오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
                    }
                }
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
                is NetworkResult.Exception -> {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(application, "오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
                    }
                }
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
            val request = AcceptScheduleRequest(memberId, scheduleId)
            val response = acceptScheduleUseCase(accessToken, request)
            LogUtil.printNetworkLog(response, "일정 수락")
            when (response) {
                is NetworkResult.Success -> {
                }
                is NetworkResult.Error -> {  }
                is NetworkResult.Exception -> {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(application, "오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
                    }
                }
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
            val request = RefuseOrQuitScheduleRequest(memberId, scheduleId)
            val response = refuseOrQuitScheduleUseCase(accessToken, request)
            LogUtil.printNetworkLog(response, "일정 거절")
            when (response) {
                is NetworkResult.Success -> {
                }
                is NetworkResult.Error -> {  }
                is NetworkResult.Exception -> {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(application, "오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            loadScheduleRequests()
            updateCalendar()
            updateBriefCalendar()
        }
    }

    private suspend fun getFriendList() {
        val accessToken = getAccessTokenUseCase().first()
        val memberId = getMemberIdUseCase().first()
        val request = GetFriendIdsListRequest(memberId)
        val response = getFriendIdsListUseCase(accessToken, request)
        LogUtil.printNetworkLog(response, "친구  memberId목록 가져오기")
        when (response) {
            is NetworkResult.Success -> {
                response.data?.let { data ->
                    val getFriendListRequest = GetFriendListRequest(data.friendsIdList)
                    val getFriendListResponse = getFriendListUseCase(accessToken, getFriendListRequest)
                    LogUtil.printNetworkLog(getFriendListResponse, "친구 목록 가져오기")
                    when (getFriendListResponse) {
                        is NetworkResult.Success -> {
                            getFriendListResponse.data?.let { data ->
                                FriendProvider.friendsList.clear()
                                FriendProvider.friendsList.addAll(data.friendsList)
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
            }
            is NetworkResult.Error -> {  }
            is NetworkResult.Exception -> {
                withContext(Dispatchers.Main) {
                    Toast.makeText(application, "오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    init {
        loadFriendRequests()
        loadScheduleRequests()
    }
}

data class ScheduleInvitationInfo(
    val scheduleId: String,
    val title: String,
    val userName: String,
    val year: String,
    val month: String,
    val date: String,
    val hour: String,
    val minute: String
)