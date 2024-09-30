package com.whereareyounow.data.findaccount

import com.whereareyounow.globalvalue.type.EmailButtonState
import com.whereareyounow.globalvalue.type.EmailCodeButtonState
import com.whereareyounow.globalvalue.type.EmailCodeState
import com.whereareyounow.globalvalue.type.EmailState


data class FindAccountEmailVerificationScreenUIState(
    val inputEmail: String = "",
    val inputEmailState: EmailState = EmailState.Idle,
    val requestButtonState: EmailButtonState = EmailButtonState.Request,
    val inputEmailCode: String = "",
    val inputEmailCodeState: EmailCodeState = EmailCodeState.Idle,
    val emailCodeLeftTime: Int = 0,
    val emailCodeButtonState: EmailCodeButtonState = EmailCodeButtonState.Active
)