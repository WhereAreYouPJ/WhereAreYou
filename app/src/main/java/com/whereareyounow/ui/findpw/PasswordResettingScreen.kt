package com.whereareyounow.ui.findpw

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.whereareyounow.ui.component.BottomOKButton
import com.whereareyounow.ui.component.CustomTextField
import com.whereareyounow.ui.component.CustomTextFieldState
import com.whereareyounow.ui.component.CustomTopBar

@Composable
fun PasswordResettingScreen(
    moveToSignInScreen: () -> Unit,
    viewModel: FindPasswordViewModel = hiltViewModel()
) {
    val inputPassword = viewModel.inputPassword.collectAsState().value
    val inputPasswordForChecking = viewModel.inputPasswordChecking.collectAsState().value
    PasswordResettingScreen(
        inputPassword = inputPassword,
        updateInputPassword = viewModel::updateInputPassword,
        inputPasswordForChecking = inputPasswordForChecking,
        updateInputPasswordForChecking = viewModel::updateInputPasswordForChecking,
        resetPassword = viewModel::resetPassword,
        moveToSignInScreen = moveToSignInScreen
    )
}

@Composable
private fun PasswordResettingScreen(
    inputPassword: String,
    updateInputPassword: (String) -> Unit,
    inputPasswordForChecking: String,
    updateInputPasswordForChecking: (String) -> Unit,
    resetPassword: (() -> Unit) -> Unit,
    moveToSignInScreen: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 20.dp, end = 20.dp)
    ) {
        PasswordResettingScreenTopBar(moveToSignInScreen)

        Spacer(Modifier.height(20.dp))

        Text(
            text = "비밀번호를 변경해주세요.",
            fontSize = 20.sp
        )

        Spacer(Modifier.height(20.dp))

        NewPasswordTextField(
            inputPassword = inputPassword,
            updateInputPassword = updateInputPassword
        )

        Spacer(Modifier.height(20.dp))

        NewPasswordCheckingTextField(
            inputPasswordForChecking = inputPasswordForChecking,
            updateInputPasswordForChecking = updateInputPasswordForChecking
        )

        Spacer(Modifier.weight(1f))

        BottomOKButton(
            text = "로그인하러 가기",
            onClick = { resetPassword(moveToSignInScreen) }
        )

        Spacer(Modifier.height(20.dp))
    }
}

@Composable
private fun PasswordResettingScreenTopBar(
    moveToSignInScreen: () -> Unit
) {
    CustomTopBar(
        title = "비밀번호 변경",
        onBackButtonClicked = moveToSignInScreen
    )
}

@Composable
private fun NewPasswordTextField(
    inputPassword: String,
    updateInputPassword: (String) -> Unit
) {
    CustomTextField(
        hint = "새 비밀번호",
        inputText = inputPassword,
        onValueChange = updateInputPassword,
        textFieldState = CustomTextFieldState.IDLE,
        isPassword = true
    )
}

@Composable
private fun NewPasswordCheckingTextField(
    inputPasswordForChecking: String,
    updateInputPasswordForChecking: (String) -> Unit
) {
    CustomTextField(
        hint = "새 비밀번호 확인",
        inputText = inputPasswordForChecking,
        onValueChange = updateInputPasswordForChecking,
        textFieldState = CustomTextFieldState.IDLE,
        isPassword = true
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun PasswordResettingScreenPreview() {
    PasswordResettingScreen(
        inputPassword = "",
        updateInputPassword = {},
        inputPasswordForChecking = "",
        updateInputPasswordForChecking = {},
        resetPassword = {},
        moveToSignInScreen = {}
    )
}