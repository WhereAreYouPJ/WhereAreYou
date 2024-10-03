package com.whereareyounow.domain.entity.feed

import com.google.gson.annotations.SerializedName

data class FeedDetailInfo(
    @SerializedName("memberInfo")
    val memberInfo: FeedMemberInfo,
    @SerializedName("feedInfo")
    val feedInfo: FeedContentInfo,
    @SerializedName("feedImageInfos")
    val feedImageInfos: List<FeedImageInfo2>,
    @SerializedName("bookMarkInfo")
    val bookmarkInfo: Boolean
)
