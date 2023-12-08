package com.whereareyounow.domain.entity.apimessage.schedule

import com.google.gson.annotations.SerializedName

data class AcceptScheduleRequest(
    @SerializedName("acceptMemberId")
    val acceptMemberId: String,
    @SerializedName("scheduleId")
    val scheduleId: String
)