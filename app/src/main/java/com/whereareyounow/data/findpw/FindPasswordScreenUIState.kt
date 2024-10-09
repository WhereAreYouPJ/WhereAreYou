package com.whereareyounow.data.findpw

import com.whereareyounow.globalvalue.type.EmailButtonState
import com.whereareyounow.globalvalue.type.EmailCodeButtonState
import com.whereareyounow.globalvalue.type.EmailCodeState
import com.whereareyounow.globalvalue.type.FindAccountEmailState

data class FindPasswordScreenUIState(
    val inputUserId: String = "",
    val inputEmail: String = "",
    val inputEmailState: FindAccountEmailState = FindAccountEmailState.Idle,
    val requestButtonState: EmailButtonState = EmailButtonState.Request,
    val inputEmailCode: String = "",
    val inputEmailCodeState: EmailCodeState = EmailCodeState.Idle,
    val emailCodeButtonState: EmailCodeButtonState = EmailCodeButtonState.Active,
    val emailCodeLeftTime: Int = 0,
    val email: String = ""
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