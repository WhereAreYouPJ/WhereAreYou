package com.whereareyounow.domain.entity.apimessage.schedule

import com.google.gson.annotations.SerializedName

data class GetTodayScheduleCountResponse(
    @SerializedName("todaySchedule")
    val todaySchedule: Int
)