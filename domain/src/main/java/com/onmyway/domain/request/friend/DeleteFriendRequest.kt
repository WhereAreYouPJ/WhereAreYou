package com.onmyway.domain.request.friend

import com.google.gson.annotations.SerializedName

data class DeleteFriendRequest(
    @SerializedName("memberSeq")
    val memberSeq: Int,
    @SerializedName("friendSeq")
    val friendSeq: Int
)