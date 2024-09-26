package com.onmyway.domain.request.schedule

import com.google.gson.annotations.SerializedName

data class AcceptScheduleRequestRequest(
    @SerializedName("scheduleSeq")
    val scheduleSeq: Int,
    @SerializedName("memberSeq")
    val memberSeq: Int,
)
