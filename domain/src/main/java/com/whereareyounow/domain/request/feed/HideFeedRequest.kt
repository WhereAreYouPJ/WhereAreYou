package com.whereareyounow.domain.request.feed

import com.google.gson.annotations.SerializedName
import java.io.Serial

data class HideFeedRequest(
    @SerializedName("feedSeq")
    val feedSeq: Int,
    @SerializedName("memberSeq")
    val memberSeq: Int,
)
