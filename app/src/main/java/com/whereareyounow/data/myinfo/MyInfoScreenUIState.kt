package com.whereareyounow.data.myinfo

import com.whereareyounow.data.infomodification.UserNameState
import com.whereareyounow.data.signup.SignUpUserNameState

data class MyInfoScreenUIState(
    val name: String = "",
    val email: String = "",
    val nameState: SignUpUserNameState = SignUpUserNameState.Idle
)
