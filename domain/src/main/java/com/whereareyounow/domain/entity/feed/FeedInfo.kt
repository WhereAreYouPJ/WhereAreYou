package com.whereareyounow.domain.entity.feed

import com.google.gson.annotations.SerializedName

data class FeedInfo(
    @SerializedName("scheduleInfo")
    val scheduleInfo: FeedScheduleInfo,
    @SerializedName("scheduleFeedInfo")
    val feedInfo: List<FeedDetailInfo>
)
