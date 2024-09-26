package com.onmyway.data.calendar

sealed class CalendarScreenSideEffect {
    data class Toast(val text: String): CalendarScreenSideEffect()
}