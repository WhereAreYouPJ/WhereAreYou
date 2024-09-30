package com.whereareyounow.domain.request.member

import com.google.gson.annotations.SerializedName

data class CheckEmailDuplicateRequest(
    @SerializedName("email")
    val email: String
)
