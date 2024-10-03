package com.whereareyounow.domain.entity.feed

import com.google.gson.annotations.SerializedName
import java.io.Serial

data class FeedImageInfo2(
    @SerializedName("feedImageSeq")
    val feedImageSeq: Int,
    @SerializedName("feedImageURL")
    val feedImageUrl: String,
    @SerializedName("feedImageOrder")
    val feedImageOrder: Int
)
