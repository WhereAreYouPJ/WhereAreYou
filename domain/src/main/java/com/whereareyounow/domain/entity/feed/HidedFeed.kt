package com.whereareyounow.domain.entity.feed

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Since
import java.io.Serial

data class HidedFeed(
    @SerializedName("profileImage")
    val profileImage: String,
    @SerializedName("startTime")
    val startTime: String,
    @SerializedName("location")
    val location: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("hideFeedImageInfos")
    val hideFeedImageInfos: List<HidedFeedImageInfo>,
    @SerializedName("content")
    val content: String,
    @SerializedName("bookMark")
    val bookMark: Boolean
)
