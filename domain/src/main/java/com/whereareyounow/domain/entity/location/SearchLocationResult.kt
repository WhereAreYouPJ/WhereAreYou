package com.whereareyounow.domain.entity.location

import com.google.gson.annotations.SerializedName
import com.whereareyounow.domain.entity.schedule.LocationInformation

data class SearchLocationResult(
    @SerializedName("lastBuildData")
    val lastBuildData: String,
    @SerializedName("total")
    val total: Int,
    @SerializedName("start")
    val start: Int,
    @SerializedName("display")
    val display: Int,
    @SerializedName("items")
    val items: List<LocationInformation>
)
