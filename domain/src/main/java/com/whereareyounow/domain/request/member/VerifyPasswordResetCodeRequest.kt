package com.whereareyounow.domain.request.member

import com.google.gson.annotations.SerializedName

data class VerifyPasswordResetCodeRequest(
    @SerializedName("email")
    val email: String,
    @SerializedName("code")
    val code: String
)