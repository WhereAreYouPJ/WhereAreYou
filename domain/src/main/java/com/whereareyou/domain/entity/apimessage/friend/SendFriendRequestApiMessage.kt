package com.whereareyou.domain.entity.apimessage.friend

import com.google.gson.annotations.SerializedName

data class SendFriendRequestRequest(
    @SerializedName("friendId")
    val friendId: String,
    @SerializedName("memberId")
    val memberId: String
)