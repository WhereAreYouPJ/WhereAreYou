package com.whereareyounow.domain.entity.apimessage.location

import com.google.gson.annotations.SerializedName

data class GetUserLocationRequest(
    @SerializedName("memberId")
    val memberId: List<String>,
    @SerializedName("scheduleId")
    val scheduleId: String
)

data class UserLocation(
    @SerializedName("memberId")
    val memberId: String,
    @SerializedName("latitude")
    val latitude: Double,
    @SerializedName("longitude")
    val longitude: Double
)