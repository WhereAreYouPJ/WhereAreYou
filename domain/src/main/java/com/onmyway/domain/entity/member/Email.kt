package com.onmyway.domain.entity.member

import com.google.gson.annotations.SerializedName

data class Email(
    @SerializedName("email")
    val email: String
)
