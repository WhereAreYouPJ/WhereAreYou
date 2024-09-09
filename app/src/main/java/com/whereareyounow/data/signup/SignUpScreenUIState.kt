package com.whereareyounow.data.signup

data class SignUpScreenUIState(
    val inputUserName: String = "",
    val inputUserNameState: SignUpUserNameState = SignUpUserNameState.Idle,
    val inputEmail: String = "",
    val inputEmailState: SignUpEmailState = SignUpEmailState.Idle,
    val requestButtonState: SignUpEmailButtonState = SignUpEmailButtonState.Request,
    val inputVerificationCode: String = "",
    val inputVerificationCodeState: SignUpVerificationCodeState = SignUpVerificationCodeState.Idle,
    val verificationCodeButtonState: SignUpCodeOKButtonState = SignUpCodeOKButtonState.Inactive,
    val emailCodeLeftTime: Int = 0,
    val inputPassword: String = "",
    val inputPasswordState: SignUpPasswordState = SignUpPasswordState.Idle,
    val inputPasswordCheck: String = "",
    val inputPasswordCheckState: SignUpPasswordCheckState = SignUpPasswordCheckState.Idle
)

enum class SignUpUserNameState {
    Idle, Valid, Invalid
}

enum class SignUpEmailState {
    Idle, Valid, Invalid
}

enum class SignUpEmailButtonState {
    Request, RequestDone
}

enum class SignUpVerificationCodeState {
    Idle, Valid, Invalid
}

enum class SignUpCodeOKButtonState {
    Active, Inactive
}

enum class SignUpPasswordState {
    Idle, Valid, Invalid
}

enum class SignUpPasswordCheckState {
    Idle, Valid, Invalid
}