package com.whereareyounow.data.signin

data class SignInScreenUIState(
    val inputEmail: String = "",
    val inputPassword: String = "",
    val isSignInLoading: Boolean = false,
    val isSignInFailed: Boolean = false
)