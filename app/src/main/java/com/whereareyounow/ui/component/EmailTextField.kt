package com.whereareyounow.ui.component

import androidx.compose.runtime.Composable
import com.whereareyounow.globalvalue.type.EmailState
import com.whereareyounow.globalvalue.type.FindAccountEmailState

@Composable
fun FindAccountEmailTextField(
    inputEmail: String,
    updateInputEmail: (String) -> Unit,
    inputEmailState: FindAccountEmailState,
    guideLine: String,
    onSuccessText: String,
) {
    CustomTextField(
        hint = "이메일",
        inputText = inputEmail,
        onValueChange = updateInputEmail,
        warningText = guideLine,
        onSuccessText = onSuccessText,
        textFieldState = when (inputEmailState) {
            FindAccountEmailState.Idle -> CustomTextFieldState.Idle
            FindAccountEmailState.Valid -> CustomTextFieldState.Satisfied
            FindAccountEmailState.Invalid,
            FindAccountEmailState.NonExist-> CustomTextFieldState.Unsatisfied
        }
    )
}

@Composable
fun EmailTextField(
    inputEmail: String,
    updateInputEmail: (String) -> Unit,
    inputEmailState: EmailState,
    guideLine: String,
    onSuccessText: String,
) {
    CustomTextField(
        hint = "이메일",
        inputText = inputEmail,
        onValueChange = updateInputEmail,
        warningText = guideLine,
        onSuccessText = onSuccessText,
        textFieldState = when (inputEmailState) {
            EmailState.Idle -> CustomTextFieldState.Idle
            EmailState.Valid -> CustomTextFieldState.Satisfied
            EmailState.Invalid -> CustomTextFieldState.Unsatisfied
        }
    )
}