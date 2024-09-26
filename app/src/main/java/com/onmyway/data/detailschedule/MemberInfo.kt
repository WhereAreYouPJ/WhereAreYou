package com.onmyway.data.detailschedule

import android.graphics.Bitmap
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MemberInfo(
    val memberId: String = "",
    val name: String = "",
    val userId: String = "",
    val email: String = "",
    val profileImage: String? = "",
    val imageBitmap: Bitmap? = null,
    val isArrived: Boolean = false,
    val latitude: Double? = null,
    val longitude: Double? = null
) : Parcelable
