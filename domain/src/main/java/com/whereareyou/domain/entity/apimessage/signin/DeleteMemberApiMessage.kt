package com.whereareyou.domain.entity.apimessage.signin

import com.google.gson.annotations.SerializedName

data class DeleteMemberRequest(
    @SerializedName("memberId")
    val memberId: String
)