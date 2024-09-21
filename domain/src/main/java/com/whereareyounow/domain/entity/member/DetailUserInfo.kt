package com.whereareyounow.domain.entity.member

import com.google.gson.annotations.SerializedName

data class DetailUserInfo (
    @SerializedName("userName")
    val userName: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("profileImage")
    val profileImage: String,
)
