package com.whereareyounow.data.signin

sealed class SignInScreenSideEffect {
    data class Toast(val text: String): SignInScreenSideEffect()
}