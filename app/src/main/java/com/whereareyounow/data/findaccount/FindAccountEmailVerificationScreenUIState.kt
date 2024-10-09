package com.whereareyounow.data.findaccount

import com.whereareyounow.globalvalue.type.EmailButtonState
import com.whereareyounow.globalvalue.type.EmailCodeButtonState
import com.whereareyounow.globalvalue.type.EmailCodeState
import com.whereareyounow.globalvalue.type.EmailState
import com.whereareyounow.globalvalue.type.FindAccountEmailState


data class FindAccountEmailVerificationScreenUIState(
    val inputEmail: String = "",
    val inputEmailState: FindAccountEmailState = FindAccountEmailState.Idle,
    val requestButtonState: EmailButtonState = EmailButtonState.Request,
    val inputEmailCode: String = "",
    val inputEmailCodeState: EmailCodeState = EmailCodeState.Idle,
    val emailCodeLeftTime: Int = 0,
    val emailCodeButtonState: EmailCodeButtonState = EmailCodeButtonState.Active,
    val email: String = "",
    val typeList: List<String> = emptyList()
)