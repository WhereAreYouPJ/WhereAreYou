package com.whereareyounow.domain.request.feed

import com.google.gson.annotations.SerializedName

data class BookmarkFeedRequest(
    @SerializedName("feedSeq")
    val feedSeq: Int,
    @SerializedName("memberSeq")
    val memberSeq: Int,
)
