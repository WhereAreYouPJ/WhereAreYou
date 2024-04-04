package com.whereareyounow.data.findpw

data class PasswordResettingScreenUIState(
    val inputPassword: String = "",
    val inputPasswordState: PasswordState = PasswordState.Empty,
    val inputPasswordForChecking: String = "",
    val passwordCheckingState: PasswordCheckingState = PasswordCheckingState.Empty
)

enum class PasswordState {
    Empty, Satisfied, Unsatisfied
}

enum class PasswordCheckingState {
    Empty, Satisfied, Unsatisfied
}