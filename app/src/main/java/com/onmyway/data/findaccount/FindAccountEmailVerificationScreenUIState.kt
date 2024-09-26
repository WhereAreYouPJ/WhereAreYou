package com.onmyway.data.findaccount

import com.onmyway.globalvalue.type.EmailButtonState
import com.onmyway.globalvalue.type.EmailCodeButtonState
import com.onmyway.globalvalue.type.EmailCodeState
import com.onmyway.globalvalue.type.EmailState


data class FindAccountEmailVerificationScreenUIState(
    val inputEmail: String = "",
    val inputEmailState: EmailState = EmailState.Idle,
    val requestButtonState: EmailButtonState = EmailButtonState.Request,
    val inputEmailCode: String = "",
    val inputEmailCodeState: EmailCodeState = EmailCodeState.Idle,
    val emailCodeLeftTime: Int = 0,
    val emailCodeButtonState: EmailCodeButtonState = EmailCodeButtonState.Active
)