package com.whereareyounow.domain.request.feed

import com.google.gson.annotations.SerializedName
import java.io.Serial

data class RestoreHidedFeedRequest(
    @SerializedName("hideFeedSeq")
    val hideFeedSeq: Int,
    @SerializedName("memberSeq")
    val memberSeq: Int,
)
