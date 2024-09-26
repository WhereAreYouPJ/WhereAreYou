package com.onmyway.data.signin

sealed class SignInScreenSideEffect {
    data class Toast(val text: String): SignInScreenSideEffect()
}