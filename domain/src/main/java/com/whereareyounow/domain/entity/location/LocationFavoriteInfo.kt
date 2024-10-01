package com.whereareyounow.domain.entity.location

import com.google.gson.annotations.SerializedName

data class LocationFavoriteInfo (
    @SerializedName("locationSeq")
    val locationSeq: Int,
    @SerializedName("location")
    val location: String,
    @SerializedName("streetName")
    val streetName: String,
)