package com.whereareyounow.domain.entity.feed

import com.google.gson.annotations.SerializedName

data class FeedContentInfo(
    @SerializedName("feedSeq")
    val feedSeq: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("content")
    val content: String
)
