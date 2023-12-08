package com.whereareyounow.domain.entity.apimessage.schedule

import com.google.gson.annotations.SerializedName

data class AddNewScheduleRequest(
    @SerializedName("memberId")
    val memberId: String,
    @SerializedName("start")
    val start: String,
    @SerializedName("end")
    val end: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("place")
    val place: String,
    @SerializedName("memo")
    val memo: String,
    @SerializedName("destinationLatitude")
    val destinationLatitude: Double,
    @SerializedName("destinationLongitude")
    val destinationLongitude: Double,
    @SerializedName("memberIdList")
    val memberIdList: List<String>
)

data class AddNewScheduleResponse(
    @SerializedName("scheduleId")
    val scheduleId: String
)