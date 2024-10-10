package com.whereareyounow.domain.entity.schedule

import com.google.gson.annotations.SerializedName

data class Friend(
    var number: Int = 0,
    @SerializedName("memberSeq")
    var memberSeq: Int,
    @SerializedName("userName")
    var name: String,
    @SerializedName("userId")
    var userId: String = "",
    @SerializedName("profileImage")
    var profileImgUrl: String? = null,
    var isFavorite: Boolean = false
)