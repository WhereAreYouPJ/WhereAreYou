package com.whereareyounow.domain.entity.apimessage.signin

import com.google.gson.annotations.SerializedName

data class VerifyPasswordResetCodeRequest(
    @SerializedName("userId")
    val userId: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("code")
    val code: String
)

data class VerifyPasswordResetCodeResponse(
    @SerializedName("userId")
    val userId: String
)