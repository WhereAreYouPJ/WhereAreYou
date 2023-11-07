package com.whereareyou.domain.entity.apimessage.friend

import com.google.gson.annotations.SerializedName
import com.whereareyou.domain.entity.friend.FriendRequest

data class GetFriendRequestListResponse(
    @SerializedName("friendsRequestList")
    val friendsRequestList: List<FriendRequest>,
    @SerializedName("todaySchedule")
    val todaySchedule: Int
)