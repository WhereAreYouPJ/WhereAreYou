package com.onmyway.domain.request.schedule

data class GetScheduleListRequest(
    val memberSeq: Int,
    val page: Int,
    val size: Int
)
