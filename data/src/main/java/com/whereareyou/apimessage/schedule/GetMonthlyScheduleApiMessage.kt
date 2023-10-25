package com.whereareyou.apimessage.schedule

import com.google.gson.annotations.SerializedName
import com.whereareyou.domain.entity.schedule.ScheduleCountByDay

data class GetMonthlyScheduleResponse(
    @SerializedName("year")
    val year: Int,
    @SerializedName("month")
    val month: Int,
    @SerializedName("schedules")
    val schedules: List<ScheduleCountByDay>
)