package com.whereareyounow.domain.entity.apimessage.signin

import com.google.gson.annotations.SerializedName

data class GetMemberIdByUserIdResponse(
    @SerializedName("userName")
    val userName: String,
    @SerializedName("memberId")
    val memberId: String
)
