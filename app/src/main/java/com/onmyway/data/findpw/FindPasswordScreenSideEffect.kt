package com.onmyway.data.findpw

sealed class FindPasswordScreenSideEffect {
    data class Toast(val text: String): FindPasswordScreenSideEffect()
}