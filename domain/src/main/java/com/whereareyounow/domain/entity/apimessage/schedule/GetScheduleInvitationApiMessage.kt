package com.whereareyounow.domain.entity.apimessage.schedule

import com.google.gson.annotations.SerializedName

data class GetScheduleInvitationResponse(
    @SerializedName("inviteList")
    val inviteList: List<ScheduleInvitation>
)

data class ScheduleInvitation(
    @SerializedName("scheduleId")
    val scheduleId: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("userName")
    val userName: String,
    @SerializedName("start")
    val start: String,
    @SerializedName("createTime")
    val createTime: String
)