package com.onmyway.domain.util

import com.google.gson.annotations.SerializedName

data class ResponseWrapper<T> (
    @SerializedName("status")
    val status: Int,
    @SerializedName("message")
    val message: String?,
    @SerializedName("data")
    val data: T?,
    @SerializedName("code")
    val code: String?
)