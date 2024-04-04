package com.whereareyounow.data.signup

data class SignUpScreenUIState(
    val inputUserName: String = "",
    val inputUserNameState: UserNameState = UserNameState.Empty,
    val inputUserId: String = "",
    val inputUserIdState: UserIdState = UserIdState.Empty,
    val inputPassword: String = "",
    val inputPasswordState: PasswordState = PasswordState.Empty,
    val inputPasswordForChecking: String = "",
    val inputPasswordForCheckingState: PasswordCheckingState = PasswordCheckingState.Empty,
    val inputEmail: String = "",
    val inputEmailState: EmailState = EmailState.Empty,
    val emailVerificationProgressState: EmailVerificationProgressState = EmailVerificationProgressState.DuplicateUnchecked,
    val inputVerificationCode: String = "",
    val inputVerificationCodeState: VerificationCodeState = VerificationCodeState.Empty,
    val emailVerificationCodeLeftTime: Int = 0
)

enum class UserNameState {
    Empty, Satisfied, Unsatisfied
}

enum class UserIdState {
    Empty, Satisfied, Unsatisfied, Duplicated, Unique
}

enum class PasswordState {
    Empty, Satisfied, Unsatisfied
}

enum class PasswordCheckingState {
    Empty, Satisfied, Unsatisfied
}

enum class EmailState {
    Empty, Satisfied, Unsatisfied, Duplicated, Unique
}

enum class EmailVerificationProgressState {
    DuplicateUnchecked, DuplicateChecked, VerificationRequested
}

enum class VerificationCodeState {
    Empty, Satisfied, Unsatisfied
}