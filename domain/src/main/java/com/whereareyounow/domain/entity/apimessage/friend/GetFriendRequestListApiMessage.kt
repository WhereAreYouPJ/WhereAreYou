package com.whereareyounow.domain.entity.apimessage.friend

import com.google.gson.annotations.SerializedName
import com.whereareyounow.domain.entity.friend.FriendRequest

data class GetFriendRequestListResponse(
    @SerializedName("friendsRequestList")
    val friendsRequestList: List<FriendRequest>
)