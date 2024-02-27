package com.whereareyounow.data.findid


data class FindIdScreenUIState(
    val inputEmail: String = "",
    val inputEmailState: EmailState = EmailState.EMPTY,
    val inputVerificationCode: String = "",
    val inputVerificationCodeState: VerificationCodeState = VerificationCodeState.EMPTY,
    val isVerificationCodeSent: Boolean = false,
    val emailVerificationLeftTime: Int = 0,
)

enum class EmailState {
    EMPTY, SATISFIED, UNSATISFIED
}

enum class VerificationCodeState {
    EMPTY, SATISFIED, UNSATISFIED
}