package com.whereareyounow.domain.request.member

import com.google.gson.annotations.SerializedName

data class SnsSignUpRequest(
    @SerializedName("userName")
    val userName: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("loginType")
    val loginType: String,
    @SerializedName("fcmToken")
    val fcmToken: String,
)
