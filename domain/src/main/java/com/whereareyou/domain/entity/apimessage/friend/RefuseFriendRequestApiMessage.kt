package com.whereareyou.domain.entity.apimessage.friend

import com.google.gson.annotations.SerializedName

data class RefuseFriendRequestRequest(
    @SerializedName("friendRequestId")
    val friendRequestId: String
)