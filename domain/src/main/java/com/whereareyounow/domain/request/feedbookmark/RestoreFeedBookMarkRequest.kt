package com.whereareyounow.domain.request.feedbookmark

import com.google.gson.annotations.SerializedName

data class RestoreFeedBookMarkRequest(
    @SerializedName("bookMarkFeedSeq")
    val bookMarkFeedSeq : Int,
    @SerializedName("memberSeq")
    val memberSeq : Int,
)
