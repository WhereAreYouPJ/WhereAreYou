package com.onmyway.domain.entity.friend

import com.google.gson.annotations.SerializedName

data class FriendInfo(
    @SerializedName("memberSeq")
    val memberSeq: Int,
    @SerializedName("userName")
    val userName: String,
    @SerializedName("profileImage")
    val profileImage: String,
)
