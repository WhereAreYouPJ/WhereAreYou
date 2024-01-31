package com.whereareyounow.domain.entity.apimessage.signin

import com.google.gson.annotations.SerializedName

data class FindIdRequest(
    @SerializedName("email")
    val email: String,
    @SerializedName("code")
    val code: String
)

data class FindIdResponse(
    @SerializedName("userId")
    val userId: String
)