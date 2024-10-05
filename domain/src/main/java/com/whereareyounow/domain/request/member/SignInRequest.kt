package com.whereareyounow.domain.request.member

import com.google.gson.annotations.SerializedName

data class SignInRequest(
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("fcmToken")
    val fcmToken: String = "a",
    @SerializedName("loginType")
    val loginType: String
)