package com.whereareyou.domain.entity.apimessage.schedule

import com.google.gson.annotations.SerializedName

data class GetDetailScheduleResponse(
    @SerializedName("creatorId")
    val creatorId: String,
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
    @SerializedName("friendsIdListDTO")
    val friendsIdList: List<String>,
)