package com.whereareyounow.data.signup

data class SignUpScreenUIState(
    val inputUserName: String = "",
    val inputUserNameState: UserNameState = UserNameState.EMPTY,
    val inputUserId: String = "",
    val inputUserIdState: UserIdState = UserIdState.EMPTY,
    val inputPassword: String = "",
    val inputPasswordState: PasswordState = PasswordState.EMPTY,
    val inputPasswordForChecking: String = "",
    val inputPasswordForCheckingState: PasswordCheckingState = PasswordCheckingState.EMPTY,
    val inputEmail: String = "",
    val inputEmailState: EmailState = EmailState.EMPTY,
    val emailVerificationProgressState: EmailVerificationProgressState = EmailVerificationProgressState.DUPLICATE_UNCHECKED,
    val inputVerificationCode: String = "",
    val inputVerificationCodeState: VerificationCodeState = VerificationCodeState.EMPTY,
    val emailVerificationCodeLeftTime: Int = 0
)

enum class UserNameState {
    EMPTY, SATISFIED, UNSATISFIED
}

enum class UserIdState {
    EMPTY, SATISFIED, UNSATISFIED, DUPLICATED, UNIQUE
}

enum class PasswordState {
    EMPTY, SATISFIED, UNSATISFIED
}

enum class PasswordCheckingState {
    EMPTY, SATISFIED, UNSATISFIED
}

enum class EmailState {
    EMPTY, SATISFIED, UNSATISFIED, DUPLICATED, UNIQUE
}

enum class EmailVerificationProgressState {
    DUPLICATE_UNCHECKED, DUPLICATE_CHECKED, VERIFICATION_REQUESTED
}

enum class VerificationCodeState {
    EMPTY, SATISFIED, UNSATISFIED
}