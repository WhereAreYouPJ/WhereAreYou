package com.whereareyounow.domain.entity.apimessage.signup

import com.google.gson.annotations.SerializedName

data class AuthenticateEmailRequest(
    @SerializedName("email")
    val email: String
)

data class AuthenticateEmailResponse(
    @SerializedName("message")
    val message: String
)