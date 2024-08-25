package com.whereareyounow.domain.request.schedule

import com.google.gson.annotations.SerializedName

data class CreateNewScheduleRequest(
    @SerializedName("title")
    val title: String,
    @SerializedName("startTime")
    val startTime: String,
    @SerializedName("endTime")
    val endTime: String,
    @SerializedName("location")
    val location: String,
    @SerializedName("streetName")
    val streetName: String,
    @SerializedName("x")
    val x: Double,
    @SerializedName("y")
    val y: Double,
    @SerializedName("color")
    val color: String,
    @SerializedName("memo")
    val memo: String,
    @SerializedName("invitedMemberSeqs")
    val invitedMemberSeqs: List<Int>,
    @SerializedName("createMemberSeq")
    val createMemberSeq: Int
)
