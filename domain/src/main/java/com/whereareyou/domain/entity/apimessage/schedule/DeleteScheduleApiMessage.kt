package com.whereareyou.domain.entity.apimessage.schedule

import com.google.gson.annotations.SerializedName

data class DeleteScheduleRequest(
    @SerializedName("scheduleId")
    val scheduleId: String
)