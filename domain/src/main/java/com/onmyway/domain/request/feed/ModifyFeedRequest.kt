package com.onmyway.domain.request.feed

import com.google.gson.annotations.SerializedName

data class ModifyFeedRequest(
    val feedSeq: Int,
    val memberSeq: Int,
    val title: String,
    val content: String,
    val feed
)
