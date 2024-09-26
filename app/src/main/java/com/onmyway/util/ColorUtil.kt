package com.onmyway.util

import com.onmyway.globalvalue.type.ScheduleColor

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