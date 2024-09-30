package com.whereareyounow.domain.entity.schedule

import com.google.gson.annotations.SerializedName

data class ScheduleListItem(
    @SerializedName("scheduleSeq")
    val scheduleSeq: Int,
    @SerializedName("startTime")
    val startTime: String,
    @SerializedName("title")
    val title: String
)
