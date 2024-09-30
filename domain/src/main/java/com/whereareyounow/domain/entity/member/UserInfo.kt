package com.whereareyounow.domain.entity.member

import com.google.gson.annotations.SerializedName

data class UserInfo(
    @SerializedName("userName")
    val userName: String,
    @SerializedName("memberSeq")
    val memberSeq: Int,
    @SerializedName("profileImage")
    val profileImage: String,
)
