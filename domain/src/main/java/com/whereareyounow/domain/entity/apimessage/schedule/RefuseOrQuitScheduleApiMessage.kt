package com.whereareyounow.domain.entity.apimessage.schedule

import com.google.gson.annotations.SerializedName

data class RefuseOrQuitScheduleRequest(
    @SerializedName("refuseMemberId")
    val refuseMemberId: String,
    @SerializedName("scheduleId")
    val scheduleId: String
)