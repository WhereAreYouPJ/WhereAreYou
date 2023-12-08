package com.whereareyounow.domain.entity.apimessage.friend

import com.google.gson.annotations.SerializedName
import com.whereareyounow.domain.entity.schedule.Friend

data class GetFriendListRequest(
    @SerializedName("friendId")
    val friendId: List<String>
)

data class GetFriendListResponse(
    @SerializedName("friendsList")
    val friendsList: List<Friend>
)