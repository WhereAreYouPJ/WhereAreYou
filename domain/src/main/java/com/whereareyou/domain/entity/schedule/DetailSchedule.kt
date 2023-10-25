package com.whereareyou.domain.entity.schedule

import com.google.gson.annotations.SerializedName

data class DetailSchedule(
    @SerializedName("start")
    var start: String,
    @SerializedName("end")
    var end: String,
    @SerializedName("title")
    var title: String,
    @SerializedName("place")
    var place: String,
    @SerializedName("memo")
    var memo: String,
    @SerializedName("friendList")
    var friendsIdList: List<String>,
)
