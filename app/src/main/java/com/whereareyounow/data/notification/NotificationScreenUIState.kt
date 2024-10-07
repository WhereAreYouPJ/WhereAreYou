package com.whereareyounow.data.notification

import com.whereareyounow.domain.entity.friend.FriendRequest
import com.whereareyounow.domain.entity.schedule.Friend

data class NotificationScreenUIState(
    val todayScheduleCount: Int = 0,
    val friendRequestsList: List<Pair<FriendRequest, Friend>> = emptyList(),
    val scheduleRequestsList: List<ScheduleInvitationInfo> = emptyList()
)
