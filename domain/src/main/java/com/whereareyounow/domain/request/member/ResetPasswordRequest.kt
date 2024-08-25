package com.whereareyounow.domain.request.member

import com.google.gson.annotations.SerializedName

data class ResetPasswordRequest(
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("checkPassword")
    val checkPassword: String
)