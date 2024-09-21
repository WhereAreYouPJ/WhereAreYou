package com.whereareyounow.domain.request.member

import com.google.gson.annotations.SerializedName

data class UpdateUserNameRequest(
    @SerializedName("memberSeq")
    val memberSeq: Int,
    @SerializedName("userName")
    val userName: String
)
