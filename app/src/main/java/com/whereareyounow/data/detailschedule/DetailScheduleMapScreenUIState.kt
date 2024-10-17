package com.whereareyounow.data.detailschedule

import com.whereareyounow.domain.entity.UserLocation

data class DetailScheduleMapScreenUIState(
    val x: Double = 0.0,
    val y: Double = 0.0,
    val memberLocationList: List<UserLocation> = emptyList()
)
