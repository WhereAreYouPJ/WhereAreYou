package com.onmyway.data.notification

import com.onmyway.domain.entity.friend.FriendRequest
import com.onmyway.domain.entity.schedule.Friend

data class DrawerNotificationContentUIState(
    val todayScheduleCount: Int = 0,
    val friendRequestsList: List<Pair<FriendRequest, Friend>> = emptyList(),
    val scheduleRequestsList: List<ScheduleInvitationInfo> = emptyList()
)
