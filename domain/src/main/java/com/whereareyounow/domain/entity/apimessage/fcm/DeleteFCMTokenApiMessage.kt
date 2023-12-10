package com.whereareyounow.domain.entity.apimessage.fcm

import com.google.gson.annotations.SerializedName

data class DeleteFCMTokenRequest(
    @SerializedName("memberId")
    val memberId: String
)