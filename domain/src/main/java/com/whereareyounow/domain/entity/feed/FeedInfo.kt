package com.whereareyounow.domain.entity.feed

import com.google.gson.annotations.SerializedName

data class FeedInfo(
    @SerializedName("feedInfo")
    val feedInfo: String,
)
