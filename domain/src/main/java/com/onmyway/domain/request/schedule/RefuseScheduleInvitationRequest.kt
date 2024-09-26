package com.onmyway.domain.request.schedule

import com.google.gson.annotations.SerializedName

data class RefuseScheduleInvitationRequest(
    @SerializedName("memberSeq")
    val memberSeq: Int,
    @SerializedName("scheduleSeq")
    val scheduleSeq: Int
)
