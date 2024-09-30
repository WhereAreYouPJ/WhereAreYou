package com.whereareyounow.util

import com.whereareyounow.globalvalue.type.ScheduleColor

fun getScheduleColor(color: String): ScheduleColor {
    return when (color) {
        "Red" -> ScheduleColor.Red
        "Yellow" -> ScheduleColor.Yellow
        "Green" -> ScheduleColor.Green
        "Blue" -> ScheduleColor.Blue
        "Purple" -> ScheduleColor.Purple
        else -> ScheduleColor.Pink
    }
}