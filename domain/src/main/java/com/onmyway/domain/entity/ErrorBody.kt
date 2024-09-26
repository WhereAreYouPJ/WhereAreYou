package com.onmyway.domain.entity

import com.google.gson.annotations.SerializedName

data class ErrorBody(
    @SerializedName("timestamp")
    val timestamp: String,
    @SerializedName("message")
    val message: String,
    @SerializedName("details")
    val details: String
)
