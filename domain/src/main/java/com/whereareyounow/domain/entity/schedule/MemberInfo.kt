package com.whereareyounow.domain.entity.schedule

import com.google.gson.annotations.SerializedName

data class MemberInfo(
    @SerializedName("memberSeq")
    val memberSeq: Int = 0,
    @SerializedName("userName")
    val userName: String = ""
)
