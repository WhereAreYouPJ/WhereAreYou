package com.whereareyou.model

import com.google.gson.annotations.SerializedName

data class GetDailyScheduleRequest(
    @SerializedName("memberId")
    val memberId: String,
    @SerializedName("year")
    val year: Int,
    @SerializedName("month")
    val month: Int,
    @SerializedName("date")
    val date: Int
)

data class GetDailyScheduleResponse(
    @SerializedName("briefDateScheduleDTOList")
    val schedules: List<DailySchedule>
)

data class DailySchedule(
    @SerializedName("scheduleId")
    val scheduleId: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("start")
    val start: String,
    @SerializedName("end")
    val end: String
)