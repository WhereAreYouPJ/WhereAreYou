package com.onmyway.domain.request.fcm

import com.google.gson.annotations.SerializedName

data class DeleteFCMTokenRequest(
    @SerializedName("memberId")
    val memberId: String
)