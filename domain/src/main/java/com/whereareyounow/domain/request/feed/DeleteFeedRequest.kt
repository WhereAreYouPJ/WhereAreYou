package com.whereareyounow.domain.request.feed

import com.google.gson.annotations.SerializedName

data class DeleteFeedRequest(
    @SerializedName("memberSeq")
    val memberSeq: Int,
    @SerializedName("feedSeq")
    val feedSeq: Int
)
