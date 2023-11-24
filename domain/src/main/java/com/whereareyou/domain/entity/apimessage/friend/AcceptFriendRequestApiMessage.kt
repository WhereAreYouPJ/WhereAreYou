package com.whereareyou.domain.entity.apimessage.friend

import com.google.gson.annotations.SerializedName

data class AcceptFriendRequestRequest(
    @SerializedName("friendRequestId")
    val friendRequestId: String,
    @SerializedName("memberId")
    val memberId: String,
    @SerializedName("senderId")
    val senderId: String
)