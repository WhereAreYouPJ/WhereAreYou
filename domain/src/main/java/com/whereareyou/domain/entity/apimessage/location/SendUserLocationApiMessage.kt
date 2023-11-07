package com.whereareyou.domain.entity.apimessage.location

import com.google.gson.annotations.SerializedName

data class SendUserLocationRequest(
    @SerializedName("memberId")
    val memberId: String,
    @SerializedName("latitude")
    val latitude: String,
    @SerializedName("longitude")
    val longitude: String
)