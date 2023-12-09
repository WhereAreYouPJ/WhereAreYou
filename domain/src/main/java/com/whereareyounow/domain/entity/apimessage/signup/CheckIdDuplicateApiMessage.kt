package com.whereareyounow.domain.entity.apimessage.signup

import com.google.gson.annotations.SerializedName

data class CheckIdDuplicateResponse(
    @SerializedName("userId")
    val userId: String = ""
)