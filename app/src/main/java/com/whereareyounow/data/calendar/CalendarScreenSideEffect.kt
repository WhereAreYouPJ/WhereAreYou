package com.whereareyounow.data.calendar

sealed class CalendarScreenSideEffect {
    data class Toast(val text: String): CalendarScreenSideEffect()
}