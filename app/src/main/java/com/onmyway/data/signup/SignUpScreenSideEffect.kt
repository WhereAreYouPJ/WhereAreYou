package com.onmyway.data.signup

sealed class SignUpScreenSideEffect {
    data class Toast(val text: String): SignUpScreenSideEffect()
}