package com.whereareyounow.domain.request.member

import com.google.gson.annotations.SerializedName

data class SignOutRequest(
    @SerializedName("memberSeq")
    val memberSeq: Int,
)
