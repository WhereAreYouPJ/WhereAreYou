package com.whereareyounow.domain.entity.friend

import com.google.gson.annotations.SerializedName

data class FriendRequest(
    @SerializedName("friendRequestSeq")
    val friendRequestSeq: Int,
    @SerializedName("senderSeq")
    val senderSeq: Int,
    @SerializedName("createTime")
    val createTime: String,
    @SerializedName("userName")
    val userName: String,
)
