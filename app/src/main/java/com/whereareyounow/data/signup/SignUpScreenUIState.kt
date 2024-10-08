package com.whereareyounow.data.signup

import com.whereareyounow.globalvalue.type.EmailButtonState
import com.whereareyounow.globalvalue.type.EmailCodeButtonState
import com.whereareyounow.globalvalue.type.EmailCodeState
import com.whereareyounow.globalvalue.type.EmailState

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
    val inputPasswordCheckState: SignUpPasswordCheckState = SignUpPasswordCheckState.Idle,
    val checkedEmail: String = "",
    val duplicateType: List<String> = emptyList()
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