package com.whereareyounow.domain.request.feed

import com.whereareyounow.domain.entity.feed.FeedImageInfo

data class ModifyFeedRequest(
    val feedSeq: Int,
    val memberSeq: Int,
    val title: String,
    val content: String,
    val feedImageInfos: List<FeedImageInfo>
)
