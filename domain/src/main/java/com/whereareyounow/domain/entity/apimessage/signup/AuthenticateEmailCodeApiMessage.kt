package com.whereareyounow.domain.entity.apimessage.signup

import com.google.gson.annotations.SerializedName

data class AuthenticateEmailCodeRequest(
    @SerializedName("email")
    val email: String,
    @SerializedName("code")
    val code: String
)

data class AuthenticateEmailCodeResponse(
    @SerializedName("message")
    val message: String
)