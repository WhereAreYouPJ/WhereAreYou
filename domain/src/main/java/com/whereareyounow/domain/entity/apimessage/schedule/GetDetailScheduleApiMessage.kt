package com.whereareyounow.domain.entity.apimessage.schedule

import com.google.gson.annotations.SerializedName

data class GetDetailScheduleResponse(
    @SerializedName("creatorId")
    val creatorId: String,
    @SerializedName("appointmentTime")
    val appointmentTime: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("place")
    val place: String,
    @SerializedName("roadName")
    val roadName: String,
    @SerializedName("memo")
    val memo: String,
    @SerializedName("destinationLatitude")
    val destinationLatitude: Double,
    @SerializedName("destinationLongitude")
    val destinationLongitude: Double,
    @SerializedName("friendsIdListDTO")
    val friendsIdList: List<String>,
    @SerializedName("arrivedFriendsIdList")
    val arrivedFriendsIdList: List<String>
)