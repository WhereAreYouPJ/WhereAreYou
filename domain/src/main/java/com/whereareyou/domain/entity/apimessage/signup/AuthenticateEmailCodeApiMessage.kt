package com.whereareyou.domain.entity.apimessage.signup

import com.google.gson.annotations.SerializedName

data class AuthenticateEmailCodeRequest(
    @SerializedName("email")
    val email: String,
    @SerializedName("code")
    val code: Int
)

data class AuthenticateEmailCodeResponse(
    @SerializedName("message")
    val message: String
)