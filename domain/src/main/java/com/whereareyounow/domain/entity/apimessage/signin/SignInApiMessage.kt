package com.whereareyounow.domain.entity.apimessage.signin

import com.google.gson.annotations.SerializedName

data class SignInRequest(
    @SerializedName("userId")
    val userId: String,
    @SerializedName("password")
    val password: String
)

data class SignInResponse(
    @SerializedName("message")
    val message: String,
    @SerializedName("accessToken")
    val accessToken: String,
    @SerializedName("refreshToken")
    val refreshToken: String,
    @SerializedName("memberId")
    val memberId: String
)