package com.whereareyounow.domain.entity.schedule

import com.google.gson.annotations.SerializedName

data class BriefSchedule(
    @SerializedName("scheduleId")
    var scheduleId: String,
    @SerializedName("title")
    var title: String,
    @SerializedName("start")
    var start: String,
    @SerializedName("end")
    var end: String
)
