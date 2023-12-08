package com.whereareyounow.domain.entity.apimessage.schedule

import com.google.gson.annotations.SerializedName

data class ModifyScheduleMemberRequest(
    @SerializedName("creatorId")
    val creatorId: String,
    @SerializedName("scheduleId")
    val scheduleId: String,
    @SerializedName("friendId")
    val friendId: List<String>
)