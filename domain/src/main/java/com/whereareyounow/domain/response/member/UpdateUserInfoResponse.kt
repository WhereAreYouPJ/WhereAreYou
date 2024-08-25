package com.whereareyounow.domain.response.member

import com.google.gson.annotations.SerializedName

data class UpdateUserInfoResponse(
    @SerializedName("status")
    val status: Long?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("data")
    val data: String?,
)
