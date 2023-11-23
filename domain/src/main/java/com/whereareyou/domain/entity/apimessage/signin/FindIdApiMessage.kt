package com.whereareyou.domain.entity.apimessage.signin

import com.google.gson.annotations.SerializedName

data class FindIdRequest(
    @SerializedName("email")
    val email: String,
    @SerializedName("code")
    val code: Int
)

data class FindIdResponse(
    @SerializedName("userId")
    val userId: String
)