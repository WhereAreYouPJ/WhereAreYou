package com.whereareyounow.domain.entity.apimessage.friend

import com.google.gson.annotations.SerializedName

data class DeleteFriendRequest(
    @SerializedName("memberId")
    val memberId: String,
    @SerializedName("friendId")
    val friendId: String
)