package com.whereareyounow.domain.request.feed

import com.google.gson.annotations.SerializedName
import com.whereareyounow.domain.entity.feed.FeedImageInfo
import com.whereareyounow.domain.entity.feed.FeedImageInfo2

data class CreateFeedRequest(
    @SerializedName("scheduleSeq")
    val scheduleSeq: Int,
    @SerializedName("memberSeq")
    val memberSeq: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("content")
    val content: String,
    @SerializedName("feedImageInfos")
    val feedImageInfos: List<FeedImageInfo>
)
