package com.whereareyounow.data.detailschedule

data class DetailScheduleScreenUIState(
    val scheduleName: String = "",
    val scheduleYear: Int = 0,
    val scheduleMonth: Int = 0,
    val scheduleDate: Int = 0,
    val scheduleDayOfWeek: String = "",
    val scheduleHour: Int = 0,
    val scheduleMinute: Int = 0,
    val destinationName: String = "",
    val destinationRoadAddress: String = "",
    val memo: String = "",
    val memberInfoList: List<MemberInfo> = emptyList()
)