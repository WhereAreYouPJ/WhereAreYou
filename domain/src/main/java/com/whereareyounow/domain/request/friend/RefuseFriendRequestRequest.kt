package com.whereareyounow.domain.request.friend

import com.google.gson.annotations.SerializedName

data class RefuseFriendRequestRequest(
    @SerializedName("friendRequestSeq")
    val friendRequestSeq: Int
)
