package com.whereareyounow.domain.request.member

import com.google.gson.annotations.SerializedName
import java.io.File

data class UpdateProfileImageRequest(
    @SerializedName("memberSeq")
    val memberSeq: Long,
    @SerializedName("images")
    val images: File?,
    @SerializedName("newUserId")
    val newUserId: String,
    @SerializedName("newUserName")
    val newUserName: String
)
