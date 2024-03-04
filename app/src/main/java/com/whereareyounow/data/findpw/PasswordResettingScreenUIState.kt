package com.whereareyounow.data.findpw

data class PasswordResettingScreenUIState(
    val inputPassword: String = "",
    val inputPasswordState: PasswordState = PasswordState.EMPTY,
    val inputPasswordForChecking: String = "",
    val passwordCheckingState: PasswordCheckingState = PasswordCheckingState.EMPTY
)

enum class PasswordState {
    EMPTY, SATISFIED, UNSATISFIED
}

enum class PasswordCheckingState {
    EMPTY, SATISFIED, UNSATISFIED
}