package com.whereareyounow.domain.entity.apimessage.signin

import com.google.gson.annotations.SerializedName

data class ModifyMyInfoRequest(
    @SerializedName("memberId")
    val memberId: String,
    @SerializedName("images")
    val images: String,
    @SerializedName("newUserName")
    val newUserName: String,
    @SerializedName("newId")
    val newId: String
)