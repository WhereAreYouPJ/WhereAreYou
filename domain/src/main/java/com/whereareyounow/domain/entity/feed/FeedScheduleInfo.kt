package com.whereareyounow.domain.entity.feed

import com.google.gson.annotations.SerializedName

data class FeedScheduleInfo(
    @SerializedName("scheduleSeq")
    val scheduleSeq: Int,
    @SerializedName("startTime")
    val startTime: String,
    @SerializedName("location")
    val location: String,
)
