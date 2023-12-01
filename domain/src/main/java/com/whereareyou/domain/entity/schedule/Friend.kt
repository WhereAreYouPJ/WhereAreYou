package com.whereareyou.domain.entity.schedule

import com.google.gson.annotations.SerializedName

data class Friend(
    var number: Int = 0,
    @SerializedName("memberId")
    var memberId: String,
    @SerializedName("userName")
    var name: String,
    @SerializedName("profileImage")
    var profileImgUrl: String? = null,
    var isPinned: Boolean = false
)