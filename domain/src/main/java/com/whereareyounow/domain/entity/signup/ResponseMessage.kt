package com.whereareyounow.domain.entity.signup

import com.google.gson.annotations.SerializedName

data class ResponseMessage(
    @SerializedName("message")
    val message: String
)
