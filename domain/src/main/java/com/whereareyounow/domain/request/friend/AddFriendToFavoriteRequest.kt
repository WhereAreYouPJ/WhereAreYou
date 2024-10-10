package com.whereareyounow.domain.request.friend

import com.google.gson.annotations.SerializedName

data class AddFriendToFavoriteRequest(
    @SerializedName("friendSeq")
    val friendSeq: Int,
    @SerializedName("memberSeq")
    val memberSeq: Int
)
