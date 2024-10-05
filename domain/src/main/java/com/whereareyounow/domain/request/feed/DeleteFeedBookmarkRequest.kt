package com.whereareyounow.domain.request.feed

import com.google.gson.annotations.SerializedName

data class DeleteFeedBookmarkRequest(
    @SerializedName("bookMarkFeedSeq")
    val bookMarkFeedSeq: Int,
    @SerializedName("memberSeq")
    val memberSeq: Int
)
