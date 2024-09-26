package com.onmyway.domain.request.schedule

import com.google.gson.annotations.SerializedName

data class GetDailyScheduleRequest(
    @SerializedName("date")
    val date: String,
    @SerializedName("memberSeq")
    val memberSeq: Int
)
