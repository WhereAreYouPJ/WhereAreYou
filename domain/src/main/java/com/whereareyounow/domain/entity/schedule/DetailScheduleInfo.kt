package com.whereareyounow.domain.entity.schedule

import com.google.gson.annotations.SerializedName

data class DetailScheduleInfo(
    @SerializedName("title")
    val title: String = "",
    @SerializedName("startTime")
    val startTime: String = "",
    @SerializedName("endTime")
    val endTime: String = "",
    @SerializedName("location")
    val location: String = "",
    @SerializedName("streetName")
    val streetName: String = "",
    @SerializedName("x")
    val x: Double = 0.0,
    @SerializedName("y")
    val y: Double = 0.0,
    @SerializedName("color")
    val color: String = "",
    @SerializedName("memo")
    val memo: String = "",
    @SerializedName("allDay")
    val allDay: Boolean = false,
    @SerializedName("memberInfos")
    val userName: List<MemberInfo> = emptyList()
)
