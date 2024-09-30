package com.whereareyounow.domain.request.friend

import com.google.gson.annotations.SerializedName

data class DeleteFriendRequest(
    @SerializedName("memberSeq")
    val memberSeq: Int,
    @SerializedName("friendSeq")
    val friendSeq: Int
)