package com.whereareyounow.data.findpw

data class PasswordResettingScreenUIState(
    val inputPassword: String = "",
    val inputPasswordForChecking: String = "",
    val passwordCheckingState: PasswordCheckingState = PasswordCheckingState.EMPTY
)

enum class PasswordCheckingState {
    EMPTY, SATISFIED, UNSATISFIED
}