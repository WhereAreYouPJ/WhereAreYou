package com.onmyway.domain.entity.schedule

import com.google.gson.annotations.SerializedName

data class DailyScheduleInfo(
    @SerializedName("scheduleSeq")
    val scheduleSeq: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("location")
    val location: String,
    @SerializedName("color")
    val color: String,
    @SerializedName("startTime")
    val startTime: String,
    @SerializedName("endTime")
    val endTime: String,
    @SerializedName("group")
    val group: Boolean,
    @SerializedName("allDay")
    val allDay: Boolean = false
)
