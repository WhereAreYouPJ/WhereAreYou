package com.whereareyou.model

import com.google.gson.annotations.SerializedName

data class GetMonthlyScheduleRequest(
    @SerializedName("memberId")
    val memberId: String,
    @SerializedName("year")
    val year: Int,
    @SerializedName("month")
    val month: Int
)

data class GetMonthlyScheduleResponse(
    @SerializedName("year")
    val year: Int,
    @SerializedName("month")
    val month: Int,
    @SerializedName("schedules")
    val schedules: List<MonthlySchedule>
)

data class MonthlySchedule(
    @SerializedName("date")
    val date: String,
    @SerializedName("hasSchedule")
    val hasSchedule: Boolean,
    @SerializedName("amountSchedule")
    val scheduleCount: Int
)