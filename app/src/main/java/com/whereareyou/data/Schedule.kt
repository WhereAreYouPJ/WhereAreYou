package com.whereareyou.data

data class Schedule(
    val year: Int,
    val month: Int,
    val date: Int,
    var scheduleCount: Int = 0
)
