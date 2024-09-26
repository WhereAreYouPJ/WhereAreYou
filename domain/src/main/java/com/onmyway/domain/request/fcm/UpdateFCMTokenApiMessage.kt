package com.onmyway.domain.request.fcm

import com.google.gson.annotations.SerializedName

data class UpdateFCMTokenRequest(
    @SerializedName("memberId")
    val memberId: String,
    @SerializedName("targetToken")
    val targetToken: String
)
