package com.whereareyou.domain.entity.apimessage.signup

import com.google.gson.annotations.SerializedName

data class CheckIdDuplicateResponse(
    @SerializedName("userId")
    val userId: String = "",
    @SerializedName("message")
    val message: String = ""
)