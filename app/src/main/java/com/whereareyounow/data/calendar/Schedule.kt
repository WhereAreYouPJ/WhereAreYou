package com.whereareyounow.data.calendar

data class Schedule(
    val year: Int,
    val month: Int,
    val date: Int,
    val scheduleCount: Int = 0
)
