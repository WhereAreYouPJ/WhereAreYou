package com.whereareyounow.data

import com.whereareyounow.ui.signup.EmailState
import com.whereareyounow.ui.signup.EmailVerificationProgressState
import com.whereareyounow.ui.signup.PasswordCheckingState
import com.whereareyounow.ui.signup.PasswordState
import com.whereareyounow.ui.signup.UserIdState
import com.whereareyounow.ui.signup.UserNameState
import com.whereareyounow.ui.signup.VerificationCodeState

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
