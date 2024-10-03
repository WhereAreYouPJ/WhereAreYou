package com.whereareyounow.domain.request.feedbookmark

import com.google.gson.annotations.SerializedName

data class AddFeedBookMarkRequest(
    @SerializedName("feedSeq")
    val feedSeq : Int,
    @SerializedName("memberSeq")
    val memberSeq : Int,
)
