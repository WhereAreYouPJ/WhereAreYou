package com.whereareyounow.data.scheduleedit

import com.whereareyounow.domain.entity.schedule.Friend

data class ScheduleEditScreenUIState(
    val selectedFriendsList: List<Friend> = emptyList(),
    val scheduleName: String = "",
    val scheduleYear: Int = 0,
    val scheduleMonth: Int = 0,
    val scheduleDate: Int = 0,
    val scheduleHour: Int = 0,
    val scheduleMinute: Int = 0,
    val destinationName: String = "",
    val destinationAddress: String = "",
    val destinationLatitude: Double = 0.0,
    val destinationLongitude: Double = 0.0,
    val memo: String = ""
)