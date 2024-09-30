package com.whereareyounow.domain.entity.schedule

import com.google.gson.annotations.SerializedName

data class BriefSchedule(
    @SerializedName("scheduleId")
    var scheduleId: String,
    @SerializedName("title")
    var title: String,
    @SerializedName("appointmentTime")
    val appointmentTime: String
)
