package com.onmyway.data.findpw

data class FindPasswordScreenUIState(
    val inputUserId: String = "",
    val inputEmail: String = "",
    val inputEmailState: EmailState = EmailState.Empty,
    val inputVerificationCode: String = "",
    val inputVerificationCodeState: VerificationCodeState = VerificationCodeState.Empty,
    val isVerificationCodeSent: Boolean = false,
    val emailVerificationLeftTime: Int = 0
)

enum class EmailState {
    Empty, Satisfied, Unsatisfied
}

enum class VerificationCodeState {
    Empty, Satisfied, Unsatisfied
}

enum class ResultState {
    EmailNotFound, MemberMismatch, OK
}