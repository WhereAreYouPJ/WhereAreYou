package com.whereareyounow.data.findpw

sealed class PasswordResettingScreenSideEffect {
    data class Toast(val text: String): PasswordResettingScreenSideEffect()
}