package com.whereareyou.domain.entity.apimessage.schedule

import com.google.gson.annotations.SerializedName

data class EndScheduleRequest(
    @SerializedName("creatorId")
    val creatorId: String,
    @SerializedName("scheduleId")
    val scheduleId: String
)