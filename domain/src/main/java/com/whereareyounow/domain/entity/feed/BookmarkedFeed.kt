package com.whereareyounow.domain.entity.feed

import com.google.gson.annotations.SerializedName
import java.io.Serial

data class BookmarkedFeed(
    @SerializedName("memberSeq")
    val memberSeq: Int,
    @SerializedName("profileImage")
    val profileImage: String,
    @SerializedName("startTime")
    val startTime: String,
    @SerializedName("location")
    val location: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("bookMarkImageInfos")
    val bookMarkImageInfos: List<BookmarkImageInfo>,
    @SerializedName("bookMarkFriendInfos")
    val bookMarkFriendInfos: List<FeedMemberInfo>,
    @SerializedName("content")
    val content: String,
    @SerializedName("bookMark")
    val bookMark: Boolean
)
