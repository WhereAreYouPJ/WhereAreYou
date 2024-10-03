package com.whereareyounow.domain.entity.feed

import com.google.gson.annotations.SerializedName

data class FeedMemberInfo(
    @SerializedName("memberSeq")
    val memberSeq: Int,
    @SerializedName("userName")
    val userName: String,
    @SerializedName("profileImage")
    val profileImage: String
)
