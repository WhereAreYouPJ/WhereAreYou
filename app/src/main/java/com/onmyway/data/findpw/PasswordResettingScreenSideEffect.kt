package com.onmyway.data.findpw

sealed class PasswordResettingScreenSideEffect {
    data class Toast(val text: String): PasswordResettingScreenSideEffect()
}