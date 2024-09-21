package com.whereareyounow.ui.component

import androidx.compose.runtime.Composable
import com.whereareyounow.globalvalue.type.EmailCodeState

@Composable
fun EmailCodeTextField(
    inputEmailCode: String,
    updateInputEmailCode: (String) -> Unit,
    inputEmailCodeState: EmailCodeState,
    leftTime: Int
) {
    CustomTextFieldWithTimer(
        hint = "이메일 인증코드",
        inputText = inputEmailCode,
        onValueChange = updateInputEmailCode,
        warningText = "인증코드가 일치하지 않습니다.",
        onSuccessText = "인증코드가 확인되었습니다.",
        textFieldState = when (inputEmailCodeState) {
            EmailCodeState.Idle -> CustomTextFieldState.Idle
            EmailCodeState.Invalid -> CustomTextFieldState.Unsatisfied
            EmailCodeState.Valid -> CustomTextFieldState.Satisfied
        },
        leftTime = leftTime
    )
}