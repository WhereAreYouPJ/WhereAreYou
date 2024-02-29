package com.whereareyounow.data.signin

data class SignInScreenUIState(
    val inputUserId: String = "",
    val inputPassword: String = "",
    val isSignInLoading: Boolean = false,
    val isSignInFailed: Boolean = false
)