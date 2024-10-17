package com.whereareyounow.domain.request.location

import com.google.gson.annotations.SerializedName

data class SendUserLocationRequest(
    @SerializedName("memberSeq")
    val memberSeq: Int,
    @SerializedName("x")
    val x: Double,
    @SerializedName("y")
    val y: Double
)