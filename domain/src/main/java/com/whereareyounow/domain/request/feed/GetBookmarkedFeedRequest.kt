package com.whereareyounow.domain.request.feed

data class GetBookmarkedFeedRequest(
    val memberSeq: Int,
    val page: Int,
    val size: Int
)
