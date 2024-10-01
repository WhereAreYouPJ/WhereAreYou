package com.whereareyounow.data.scheduleedit

import com.whereareyounow.domain.entity.schedule.Friend
import com.whereareyounow.globalvalue.type.ScheduleColor

data class ScheduleEditScreenUIState(
    val selectedFriendsList: List<Friend> = emptyList(),
    val scheduleName: String = "",
    val startYear: Int = 2000,
    val startMonth: Int = 1,
    val startDate: Int = 1,
    val startHour: Int = 12,
    val startMinute: Int = 0,
    val endYear: Int = 2000,
    val endMonth: Int = 1,
    val endDate: Int = 1,
    val endHour: Int = 12,
    val endMinute: Int = 0,
    val isAllDay: Boolean = false,
    val color: ScheduleColor = ScheduleColor.Red,
    val destinationName: String = "",
    val destinationAddress: String = "",
    val destinationLatitude: Double = 0.0,
    val destinationLongitude: Double = 0.0,
    val memo: String = ""
)