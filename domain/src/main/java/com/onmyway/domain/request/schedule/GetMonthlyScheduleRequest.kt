package com.onmyway.domain.request.schedule

import com.google.gson.annotations.SerializedName

data class GetMonthlyScheduleRequest(
    @SerializedName("yearMonth")
    val yearMonth: String,
    @SerializedName("memberSeq")
    val memberSeq: Int
)
