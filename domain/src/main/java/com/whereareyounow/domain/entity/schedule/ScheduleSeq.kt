package com.whereareyounow.domain.entity.schedule

import com.google.gson.annotations.SerializedName

data class ScheduleSeq(
    @SerializedName("scheduleSeq")
    val scheduleSeq: Int,
    @SerializedName("chatRoomSeq")
    val chatRoomSeq: String
)
