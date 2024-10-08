package com.whereareyounow.domain.entity.schedule

import com.google.gson.annotations.SerializedName

data class InvitedSchedule(
    @SerializedName("scheduleSeq")
    val scheduleSeq: Int,
    @SerializedName("startTime")
    val startTime: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("location")
    val location: String,
    @SerializedName("dDay")
    val dDay: Int
)
