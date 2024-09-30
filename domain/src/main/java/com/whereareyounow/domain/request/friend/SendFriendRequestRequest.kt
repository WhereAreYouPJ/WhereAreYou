package com.whereareyounow.domain.request.friend

import com.google.gson.annotations.SerializedName

data class SendFriendRequestRequest(
    @SerializedName("memberSeq")
    val memberSeq: String,
    @SerializedName("friendSeq")
    val friendSeq: String
)