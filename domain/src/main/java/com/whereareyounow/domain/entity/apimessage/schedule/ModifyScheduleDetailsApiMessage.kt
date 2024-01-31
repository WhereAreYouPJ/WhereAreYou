package com.whereareyounow.domain.entity.apimessage.schedule

import com.google.gson.annotations.SerializedName

data class ModifyScheduleDetailsRequest(
    @SerializedName("creatorId")
    val creatorId: String,
    @SerializedName("scheduleId")
    val scheduleId: String,
    @SerializedName("appointmentTime")
    val scheduleTime: String,
    @SerializedName("title")
    val scheduleName: String,
    @SerializedName("place")
    val destinationName: String,
    @SerializedName("roadName")
    val destinationAddress: String,
    @SerializedName("memo")
    val memo: String,
    @SerializedName("destinationLatitude")
    val destinationLatitude: Double,
    @SerializedName("destinationLongitude")
    val destinationLongitude: Double
)