package com.whereareyounow.domain.entity.apimessage.schedule

import com.google.gson.annotations.SerializedName
import com.whereareyounow.domain.entity.schedule.BriefSchedule


data class GetDailyBriefScheduleResponse(
    @SerializedName("briefDateScheduleDTOList")
    val schedules: List<BriefSchedule>
)
