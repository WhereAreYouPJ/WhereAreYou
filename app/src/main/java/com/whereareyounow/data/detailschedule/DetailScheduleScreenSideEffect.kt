package com.whereareyounow.data.detailschedule

sealed class DetailScheduleScreenSideEffect {
    data class Toast(val text: String): DetailScheduleScreenSideEffect()
}