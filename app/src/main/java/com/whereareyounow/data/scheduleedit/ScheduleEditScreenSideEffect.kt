package com.whereareyounow.data.scheduleedit

sealed class ScheduleEditScreenSideEffect {
    data class Toast(val text: String): ScheduleEditScreenSideEffect()
}