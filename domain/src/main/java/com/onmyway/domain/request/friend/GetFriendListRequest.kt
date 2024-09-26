package com.onmyway.domain.request.friend

import com.google.gson.annotations.SerializedName

data class GetFriendListRequest(
    @SerializedName("memberSeq")
    val memberSeq: Int
)