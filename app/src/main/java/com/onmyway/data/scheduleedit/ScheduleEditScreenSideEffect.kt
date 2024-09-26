package com.onmyway.data.scheduleedit

sealed class ScheduleEditScreenSideEffect {
    data class Toast(val text: String): ScheduleEditScreenSideEffect()
}