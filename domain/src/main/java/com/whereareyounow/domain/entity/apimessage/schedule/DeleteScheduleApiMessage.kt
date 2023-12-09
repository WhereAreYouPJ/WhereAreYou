package com.whereareyounow.domain.entity.apimessage.schedule

import com.google.gson.annotations.SerializedName

data class DeleteScheduleRequest(
    @SerializedName("creatorId")
    val creatorId: String,
    @SerializedName("scheduleId")
    val scheduleId: String
)