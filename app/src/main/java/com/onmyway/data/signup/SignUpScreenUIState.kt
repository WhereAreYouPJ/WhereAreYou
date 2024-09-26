package com.onmyway.data.signup

import com.onmyway.globalvalue.type.EmailButtonState
import com.onmyway.globalvalue.type.EmailCodeButtonState
import com.onmyway.globalvalue.type.EmailCodeState
import com.onmyway.globalvalue.type.EmailState

data class SignUpScreenUIState(
    val inputUserName: String = "",
    val inputUserNameState: SignUpUserNameState = SignUpUserNameState.Idle,
    val inputEmail: String = "",
    val inputEmailState: EmailState = EmailState.Idle,
    val requestButtonState: EmailButtonState = EmailButtonState.Request,
    val inputEmailCode: String = "",
    val inputEmailCodeState: EmailCodeState = EmailCodeState.Idle,
    val emailCodeButtonState: EmailCodeButtonState = EmailCodeButtonState.Active,
    val emailCodeLeftTime: Int = 0,
    val inputPassword: String = "",
    val inputPasswordState: SignUpPasswordState = SignUpPasswordState.Idle,
    val inputPasswordCheck: String = "",
    val inputPasswordCheckState: SignUpPasswordCheckState = SignUpPasswordCheckState.Idle
)

enum class SignUpUserNameState {
    Idle, Valid, Invalid
}



enum class SignUpPasswordState {
    Idle, Valid, Invalid
}

enum class SignUpPasswordCheckState {
    Idle, Valid, Invalid
}