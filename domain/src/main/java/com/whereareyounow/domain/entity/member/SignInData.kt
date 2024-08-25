package com.whereareyounow.domain.entity.member

import com.google.gson.annotations.SerializedName

data class SignInData(
    @SerializedName("accessToken")
    val accessToken: String,
    @SerializedName("refreshToken")
    val refreshToken: String,
    @SerializedName("memberSeq")
    val memberSeq: Int,
    @SerializedName("memberCode")
    val memberCode: String
)
