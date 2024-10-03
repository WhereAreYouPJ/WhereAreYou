package com.whereareyounow.domain.entity.schedule

import com.google.gson.annotations.SerializedName

data class ScheduleListData(
    @SerializedName("content")
    val scheduleList: List<ScheduleListItem>
)
