package com.whereareyou.domain.entity.apimessage.signin

import com.google.gson.annotations.SerializedName

data class ModifyMyInfoRequest(
    @SerializedName("memberId")
    val memberId: String,
    @SerializedName("images")
    val images: String,
    @SerializedName("newId")
    val newId: String
)