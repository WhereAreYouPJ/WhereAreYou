package com.whereareyounow.domain.entity.feed

import com.google.gson.annotations.SerializedName

data class FeedListData(
    @SerializedName("totalElements")
    val totalElements: Int,
    @SerializedName("totalPages")
    val totalPages: Int,
    @SerializedName("size")
    val size: Int,
    @SerializedName("content")
    val content: List<FeedInfo>
)
