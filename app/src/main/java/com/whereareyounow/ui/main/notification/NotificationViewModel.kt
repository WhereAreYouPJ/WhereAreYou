package com.whereareyounow.ui.main.notification

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.whereareyounow.data.cached.AuthData
import com.whereareyounow.data.notification.NotificationScreenUIState
import com.whereareyounow.domain.request.friend.AcceptFriendRequestRequest
import com.whereareyounow.domain.request.friend.GetFriendRequestListRequest
import com.whereareyounow.domain.request.friend.RefuseFriendRequestRequest
import com.whereareyounow.domain.request.schedule.AcceptScheduleInvitationRequest
import com.whereareyounow.domain.request.schedule.GetInvitedScheduleRequest
import com.whereareyounow.domain.request.schedule.RefuseScheduleInvitationRequest
import com.whereareyounow.domain.usecase.friend.AcceptFriendRequestUseCase
import com.whereareyounow.domain.usecase.friend.GetFriendRequestListUseCase
import com.whereareyounow.domain.usecase.friend.RefuseFriendRequestUseCase
import com.whereareyounow.domain.usecase.schedule.AcceptScheduleInvitationUseCase
import com.whereareyounow.domain.usecase.schedule.GetInvitedScheduleUseCase
import com.whereareyounow.domain.usecase.schedule.RefuseScheduleInvitationUseCase
import com.whereareyounow.domain.util.onError
import com.whereareyounow.domain.util.onException
import com.whereareyounow.domain.util.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val getInvitedScheduleUseCase: GetInvitedScheduleUseCase,
    private val getFriendRequestListUseCase: GetFriendRequestListUseCase,
    private val acceptFriendRequestUseCase: AcceptFriendRequestUseCase,
    private val refuseFriendRequestUseCase: RefuseFriendRequestUseCase,
    private val acceptScheduleInvitationUseCase: AcceptScheduleInvitationUseCase,
    private val refuseScheduleInvitationUseCase: RefuseScheduleInvitationUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(NotificationScreenUIState())
    val uiState = _uiState.asStateFlow()

    fun getInvitedSchedule() {
        val requestData = GetInvitedScheduleRequest(
            memberSeq = AuthData.memberSeq
        )
        getInvitedScheduleUseCase(requestData)
            .onEach { networkResult ->
                networkResult.onSuccess { code, message, data ->
                    data?.let {
                        _uiState.update {
                            it.copy(
                                scheduleRequestList = data
                            )
                        }
                    }
                }.onError { code, message ->

                }.onException {  }
            }
            .catch {  }
            .launchIn(viewModelScope)
    }

    fun getFriendRequestList() {
        val requestData = GetFriendRequestListRequest(
            memberSeq = AuthData.memberSeq
        )
        getFriendRequestListUseCase(requestData)
            .onEach { networkResult ->
                networkResult.onSuccess { code, message, data ->
                    data?.let {
                        _uiState.update {
                            it.copy(
                                friendRequestList = data
                            )
                        }
                    }
                }.onError { code, message ->

                }.onException {  }
            }
            .catch {  }
            .launchIn(viewModelScope)
    }

    fun acceptFriendRequest() {
        val requestData = AcceptFriendRequestRequest(
            friendRequestSeq = 1,
            memberSeq = 1,
            senderSeq = 1
        )
        acceptFriendRequestUseCase(requestData)
            .onEach { networkResult ->
                networkResult.onSuccess { code, message, data ->

                }.onError { code, message ->

                }.onException {  }
            }
            .catch {  }
            .launchIn(viewModelScope)
    }

    fun refuseFriendRequest() {
        val requestData = RefuseFriendRequestRequest(
            friendRequestSeq = 1
        )
        refuseFriendRequestUseCase(requestData)
            .onEach { networkResult ->
                networkResult.onSuccess { code, message, data ->

                }.onError { code, message ->

                }.onException {  }
            }
            .catch {  }
            .launchIn(viewModelScope)
    }

    fun acceptScheduleInvitation(
        scheduleSeq: Int,
    ) {
        val requestData = AcceptScheduleInvitationRequest(
            scheduleSeq = scheduleSeq,
            memberSeq = AuthData.memberSeq
        )
        acceptScheduleInvitationUseCase(requestData)
            .onEach { networkResult ->
                networkResult.onSuccess { code, message, data ->

                }.onError { code, message ->

                }.onException {  }
            }
            .catch {  }
            .launchIn(viewModelScope)
    }

    fun refuseScheduleInvitation(
        scheduleSeq: Int
    ) {
        val requestData = RefuseScheduleInvitationRequest(
            memberSeq = AuthData.memberSeq,
            scheduleSeq = scheduleSeq
        )
        refuseScheduleInvitationUseCase(requestData)
            .onEach { networkResult ->
                networkResult.onSuccess { code, message, data ->

                }.onError { code, message ->

                }.onException {  }
            }
            .catch {  }
            .launchIn(viewModelScope)
    }

    init {
        getInvitedSchedule()
        getFriendRequestList()
    }
}