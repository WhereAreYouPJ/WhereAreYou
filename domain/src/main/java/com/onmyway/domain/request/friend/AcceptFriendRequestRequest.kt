package com.onmyway.domain.request.friend

import com.google.gson.annotations.SerializedName

data class AcceptFriendRequestRequest(
    @SerializedName("friendRequestSeq")
    val friendRequestSeq: Int,
    @SerializedName("memberSeq")
    val memberSeq: Int,
    @SerializedName("senderSeq")
    val senderSeq: Int
)