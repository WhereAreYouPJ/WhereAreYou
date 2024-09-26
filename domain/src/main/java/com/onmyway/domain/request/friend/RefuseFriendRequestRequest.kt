package com.onmyway.domain.request.friend

import com.google.gson.annotations.SerializedName

data class RefuseFriendRequestRequest(
    @SerializedName("friendRequestSeq")
    val friendRequestSeq: Int
)
