package com.whereareyounow.domain.entity.schedule

import com.google.gson.annotations.SerializedName

data class DetailScheduleInfo(
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
    @SerializedName("userName")
    val userName: List<String>
)
