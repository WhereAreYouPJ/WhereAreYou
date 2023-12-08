package com.whereareyounow.domain.entity.apimessage.schedule

import com.google.gson.annotations.SerializedName

data class CheckArrivalRequest(
    @SerializedName("arrivedMemberId")
    val arrivedMemberId: String,
    @SerializedName("scheduleId")
    val scheduleId: String
)