package com.whereareyou.domain.entity.location

import com.google.gson.annotations.SerializedName

data class UserLocation(
    @SerializedName("latitude")
    val latitude: Double,
    @SerializedName("longitude")
    val longitude: Double
)
