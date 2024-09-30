package com.whereareyounow.data.findpw

sealed class FindPasswordScreenSideEffect {
    data class Toast(val text: String): FindPasswordScreenSideEffect()
}