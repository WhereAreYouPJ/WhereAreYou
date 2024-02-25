package com.whereareyounow.data.detailschedule

data class DetailScheduleMapScreenUIState(
    val destinationLatitude: Double = 0.0,
    val destinationLongitude: Double = 0.0,
    val memberLocationsList: List<MemberLocation> = emptyList()
)
