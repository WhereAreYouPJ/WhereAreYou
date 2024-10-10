package com.whereareyounow.domain.request.feed

data class GetDetailFeedRequest(
    val memberSeq: Int,
    val scheduleSeq: Int,
    val feedSeq: Int,
)
