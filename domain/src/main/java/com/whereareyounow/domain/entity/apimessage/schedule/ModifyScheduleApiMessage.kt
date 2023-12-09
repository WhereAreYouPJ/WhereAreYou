package com.whereareyounow.domain.entity.apimessage.schedule

import com.google.gson.annotations.SerializedName

data class ModifyScheduleRequest(
    @SerializedName("creatorId")
    val creatorId: String,
    @SerializedName("scheduleId")
    val scheduleId: String,
    @SerializedName("appointmentTime")
    val appointmentTime: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("place")
    val place: String,
    @SerializedName("memo")
    val memo: String
)