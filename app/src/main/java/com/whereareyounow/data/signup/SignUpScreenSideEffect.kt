package com.whereareyounow.data.signup

sealed class SignUpScreenSideEffect {
    data class Toast(val text: String): SignUpScreenSideEffect()
}