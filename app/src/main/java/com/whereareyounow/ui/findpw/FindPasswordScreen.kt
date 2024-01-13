package com.whereareyounow.ui.findpw

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.whereareyounow.ui.component.BottomOKButton
import com.whereareyounow.ui.component.CustomTextField
import com.whereareyounow.ui.component.CustomTopBar
import com.whereareyounow.ui.component.CustomTextFieldState
import com.whereareyounow.ui.theme.WhereAreYouTheme


@Composable
fun FindPasswordScreen(
    moveToSignInScreen: () -> Unit,
    viewModel: FindPasswordViewModel = hiltViewModel()
) {
    val inputId = viewModel.inputUserId.collectAsState().value
    val inputEmail = viewModel.inputEmail.collectAsState().value
    val inputAuthCode = viewModel.authCode.collectAsState().value
    FindPasswordScreen (
        inputUserId = inputId,
        updateInputUserId = viewModel::updateInputUserId,
        inputEmail = inputEmail,
        updateInputEmail = viewModel::updateInputEmail,
        inputAuthCode = inputAuthCode,
        updateInputAuthCode = viewModel::updateAuthCode,
        authenticateEmail = viewModel::authenticateEmail,
        moveToSignInScreen = moveToSignInScreen,
        verifyPasswordResetCode = viewModel::verifyPasswordResetCode,
        moveToPasswordResettingScreen = { viewModel.updateScreenState(FindPasswordViewModel.ScreenState.ResettingPassword) }
    )
}

@Composable
private fun FindPasswordScreen(
    inputUserId: String,
    updateInputUserId: (String) -> Unit,
    inputEmail: String,
    updateInputEmail: (String) -> Unit,
    inputAuthCode: String,
    updateInputAuthCode: (String) -> Unit,
    authenticateEmail: () -> Unit,
    moveToSignInScreen: () -> Unit,
    verifyPasswordResetCode: (() -> Unit) -> Unit,
    moveToPasswordResettingScreen: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 20.dp, end = 20.dp)
    ) {
        FindIdScreenTopBar(moveToSignInScreen)

        Spacer(Modifier.height(20.dp))

        UserIdInputBox(
            inputText = inputUserId,
            onValueChange = updateInputUserId
        )

        Spacer(Modifier.height(20.dp))

        Row(
            modifier = Modifier
                .height(IntrinsicSize.Min)
                .fillMaxWidth()
        ) {
            Box(modifier = Modifier.weight(1f)) {
                EmailInputBox(
                    inputText = inputEmail,
                    onValueChange = updateInputEmail
                )
            }
            Spacer(Modifier.width(10.dp))
            AuthenticationButton(
                text = "인증",
                onClick = authenticateEmail
            )
        }

        Spacer(Modifier.height(20.dp))

        AuthCodeInputBox(
            inputText = inputAuthCode,
            onValueChange = updateInputAuthCode
        )

        Spacer(Modifier.weight(1f))

        BottomOKButton(
            text = "확인",
            onClick = { verifyPasswordResetCode(moveToPasswordResettingScreen) }
        )

        Spacer(Modifier.height(20.dp))
    }
}

@Composable
private fun FindIdScreenTopBar(
    moveToSignInScreen: () -> Unit
) {
    CustomTopBar(
        title = "비밀번호 변경",
        onBackButtonClicked = moveToSignInScreen
    )
}

@Composable
private fun UserIdInputBox(
    inputText: String,
    onValueChange: (String) -> Unit
) {
    CustomTextField(
        hint = "아이디",
        inputText = inputText,
        onValueChange = onValueChange,
        textFieldState = CustomTextFieldState.IDLE
    )
}

@Composable
fun EmailInputBox(
    inputText: String,
    onValueChange: (String) -> Unit
) {
    CustomTextField(
        hint = "이메일",
        inputText = inputText,
        onValueChange = onValueChange,
        textFieldState = CustomTextFieldState.IDLE
    )
}

@Composable
private fun AuthCodeInputBox(
    inputText: String,
    onValueChange: (String) -> Unit
) {
    CustomTextField(
        hint = "인증번호 6자리",
        inputText = inputText,
        onValueChange = onValueChange,
        textFieldState = CustomTextFieldState.IDLE
    )
}

@Composable
private fun AuthenticationButton(
    text: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .height(50.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(
                color = Color(0xFF2D2573),
                shape = RoundedCornerShape(10.dp)
            )
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = Modifier
                .padding(start = 20.dp, end = 20.dp),
            text = text,
            color = Color(0xFFFFFFFF),
            fontSize = 20.sp
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun FindPasswordScreenPreview() {
    WhereAreYouTheme {
        FindPasswordScreen(
            inputUserId = "",
            updateInputUserId = {},
            inputEmail = "",
            updateInputEmail = {},
            inputAuthCode = "",
            updateInputAuthCode = {},
            authenticateEmail = {},
            moveToSignInScreen = {},
            verifyPasswordResetCode = {},
            moveToPasswordResettingScreen = {}
        )
    }
}