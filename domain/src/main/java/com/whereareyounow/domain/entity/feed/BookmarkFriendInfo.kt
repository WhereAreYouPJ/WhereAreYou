package com.whereareyounow.domain.entity.feed

import com.google.gson.annotations.SerializedName

data class BookmarkFriendInfo(
    @SerializedName("memberSeq")
    val memberSeq: Int,
    @SerializedName("profileImageURL")
    val profileImageURL: String
)
