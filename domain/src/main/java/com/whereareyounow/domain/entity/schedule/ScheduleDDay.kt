package com.whereareyounow.domain.entity.schedule

import com.google.gson.annotations.SerializedName

data class ScheduleDDay(
    @SerializedName("scheduleSeq")
    val scheduleSeq: Int = 0,
    @SerializedName("title")
    val title: String = "",
    @SerializedName("dDay")
    val dDay: String = ""
)
