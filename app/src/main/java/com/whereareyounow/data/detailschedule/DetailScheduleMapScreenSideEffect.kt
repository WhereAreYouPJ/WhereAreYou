package com.whereareyounow.data.detailschedule

sealed class DetailScheduleMapScreenSideEffect {
    data class Toast(val text: String): DetailScheduleMapScreenSideEffect()
}