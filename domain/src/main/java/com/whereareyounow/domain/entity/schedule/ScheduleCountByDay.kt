package com.whereareyounow.domain.entity.schedule

import com.google.gson.annotations.SerializedName

data class ScheduleCountByDay(
    @SerializedName("date")
    var date: Int,
    @SerializedName("hasSchedule")
    var hasSchedule: Boolean,
    @SerializedName("amountSchedule")
    var scheduleCount: Int
)
