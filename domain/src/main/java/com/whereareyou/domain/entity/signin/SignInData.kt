package com.whereareyou.domain.entity.signin

import com.google.gson.annotations.SerializedName

data class SignInData(
    @SerializedName("message")
    val message: String,
    @SerializedName("accessToken")
    val accessToken: String,
    @SerializedName("refreshToken")
    val refreshToken: String,
    @SerializedName("memberId")
    val memberId: String
)
