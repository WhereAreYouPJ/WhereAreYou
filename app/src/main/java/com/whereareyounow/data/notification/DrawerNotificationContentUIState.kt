package com.whereareyounow.data.notification

import com.whereareyounow.domain.entity.friend.FriendRequest
import com.whereareyounow.domain.entity.schedule.Friend

data class DrawerNotificationContentUIState(
    val todayScheduleCount: Int = 0,
    val friendRequestsList: List<Pair<FriendRequest, Friend>> = emptyList(),
    val scheduleRequestsList: List<ScheduleInvitationInfo> = emptyList()
)
