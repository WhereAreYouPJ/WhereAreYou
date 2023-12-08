package com.whereareyounow.domain.entity.apimessage.friend

import com.google.gson.annotations.SerializedName

data class GetFriendIdsListRequest(
    @SerializedName("memberId")
    val memberId: String
)

data class GetFriendIdsListResponse(
    @SerializedName("friendsIdList")
    val friendsIdList: List<String>
)