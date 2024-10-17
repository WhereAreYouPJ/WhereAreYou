package com.whereareyounow.domain.entity

import android.graphics.Bitmap
import com.google.gson.annotations.SerializedName

data class UserLocation(
    @SerializedName("memberSeq")
    val memberSeq: Int,
    @SerializedName("userName")
    val userName: String = "",
    @SerializedName("profileImage")
    val profileImage: Bitmap? = null,
    @SerializedName("x")
    val x: Double? = null,
    @SerializedName("y")
    val y: Double? = null
)