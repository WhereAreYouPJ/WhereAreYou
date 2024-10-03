package com.whereareyounow.domain.entity.feed

import com.google.gson.annotations.SerializedName
import java.io.File

data class FeedImageInfo(
    @SerializedName("images")
    val image: File,
    @SerializedName("feedImageOrder")
    val feedImageOrder: Int
)
