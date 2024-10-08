package com.whereareyounow.domain.request.feedbookmark

import com.google.gson.annotations.SerializedName

data class GetFeedBookMarkRequest(
    @SerializedName("memberSeq")
    val memberSeq : Int,
    @SerializedName("page")
    val page : Int,
    @SerializedName("size")
    val size : Int
)