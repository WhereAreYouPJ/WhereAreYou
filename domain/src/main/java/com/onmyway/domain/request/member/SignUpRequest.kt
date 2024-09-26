package com.onmyway.domain.request.member

import com.google.gson.annotations.SerializedName

data class SignUpRequest(
    @SerializedName("userName")
    val userName: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("email")
    val email: String
)