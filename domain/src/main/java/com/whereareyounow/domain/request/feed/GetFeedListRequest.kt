package com.whereareyounow.domain.request.feed

data class GetFeedListRequest(
    val memberSeq: Int,
    val page: Int,
    val size: Int
)
