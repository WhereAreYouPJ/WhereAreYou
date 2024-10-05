package com.whereareyounow.domain.request.feed

data class GetHidedFeedRequest(
    val memberSeq: Int,
    val page: Int,
    val size: Int
)
