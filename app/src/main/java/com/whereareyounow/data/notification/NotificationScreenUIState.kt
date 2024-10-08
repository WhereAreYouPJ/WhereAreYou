package com.whereareyounow.data.notification

import com.whereareyounow.domain.entity.friend.FriendRequest
import com.whereareyounow.domain.entity.schedule.Friend
import com.whereareyounow.domain.entity.schedule.InvitedSchedule

data class NotificationScreenUIState(
    val friendRequestList: List<FriendRequest> = emptyList(),
    val scheduleRequestList: List<InvitedSchedule> = emptyList()
)
