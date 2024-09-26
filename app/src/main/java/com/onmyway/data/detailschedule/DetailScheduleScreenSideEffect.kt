package com.onmyway.data.detailschedule

sealed class DetailScheduleScreenSideEffect {
    data class Toast(val text: String): DetailScheduleScreenSideEffect()
}