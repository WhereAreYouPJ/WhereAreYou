package com.whereareyou.domain.entity.friend

import com.google.gson.annotations.SerializedName

data class FriendRequest(
    @SerializedName("friendRequestId")
    val friendRequestId: String,
    @SerializedName("senderId")
    val senderId: String
)
