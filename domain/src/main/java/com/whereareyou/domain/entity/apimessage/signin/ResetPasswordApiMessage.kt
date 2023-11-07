package com.whereareyou.domain.entity.apimessage.signin

import com.google.gson.annotations.SerializedName

data class ResetPasswordRequest(
    @SerializedName("userId")
    val userId: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("checkPassword")
    val checkPassword: String
)