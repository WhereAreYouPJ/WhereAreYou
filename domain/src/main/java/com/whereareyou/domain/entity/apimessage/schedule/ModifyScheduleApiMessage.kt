package com.whereareyou.domain.entity.apimessage.schedule

import com.google.gson.annotations.SerializedName

data class ModifyScheduleRequest(
    @SerializedName("memberId")
    val memberId: String,
    @SerializedName("scheduleId")
    val scheduleId: String,
    @SerializedName("start")
    val start: String,
    @SerializedName("end")
    val end: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("place")
    val place: String,
    @SerializedName("memo")
    val memo: String
)