package com.whereareyounow.domain.entity.apimessage.schedule

import com.google.gson.annotations.SerializedName

data class AddNewScheduleRequest(
    @SerializedName("memberId")
    val memberId: String,
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
    val destinationLongitude: Double,
    @SerializedName("memberIdList")
    val selectedMemberIdsList: List<String>
)

data class AddNewScheduleResponse(
    @SerializedName("scheduleId")
    val scheduleId: String
)