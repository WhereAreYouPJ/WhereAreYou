package com.whereareyounow.domain.request.friend

import com.google.gson.annotations.SerializedName

data class GetFriendRequestListRequest(
    @SerializedName("memberSeq")
    val memberSeq: Int
)