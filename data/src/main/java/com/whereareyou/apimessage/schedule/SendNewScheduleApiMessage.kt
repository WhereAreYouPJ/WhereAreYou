package com.whereareyou.apimessage.schedule

import com.google.gson.annotations.SerializedName

data class SendNewScheduleRequest(
    @SerializedName("memberId")
    val memberId: String,
    @SerializedName("start")
    val start: String,
    @SerializedName("end")
    val end: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("place")
    val place: String,
    @SerializedName("memo")
    val memo: String,
    @SerializedName("memberIdList")
    val memberIdList: List<String>
)

data class SendNewScheduleResponse(
    @SerializedName("scheduleId")
    val scheduleId: String
)