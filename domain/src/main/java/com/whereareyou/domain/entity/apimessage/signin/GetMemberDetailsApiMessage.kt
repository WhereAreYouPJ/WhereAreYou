package com.whereareyou.domain.entity.apimessage.signin

import com.google.gson.annotations.SerializedName

data class GetMemberDetailsResponse(
    @SerializedName("userName")
    val userName: String,
    @SerializedName("userId")
    val userId: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("profileImage")
    val profileImage: String
)