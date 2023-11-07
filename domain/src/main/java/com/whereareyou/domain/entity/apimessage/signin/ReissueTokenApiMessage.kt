package com.whereareyou.domain.entity.apimessage.signin

import com.google.gson.annotations.SerializedName

data class ReissueTokenRequest(
    @SerializedName("refreshToken")
    val refreshToken: String
)

data class ReissueTokenResponse(
    @SerializedName("accessToken")
    val accessToken: String,
    @SerializedName("refreshToken")
    val refreshToken: String
)