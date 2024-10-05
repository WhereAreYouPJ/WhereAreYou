package com.whereareyounow.domain.entity.feed

import com.google.gson.annotations.SerializedName
import java.io.Serial

data class BookmarkImageInfo(
    @SerializedName("feedImageSeq")
    val feedImageSeq: Int,
    @SerializedName("feedImageURL")
    val feedImageURL: String,
    @SerializedName("feedImageOrder")
    val feedImageOrder: Int
)
