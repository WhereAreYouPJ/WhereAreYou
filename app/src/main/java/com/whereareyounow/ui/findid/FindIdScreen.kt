package com.whereareyounow.ui.findid

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.foundation.layout.imePadding
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
import com.whereareyounow.ui.component.CustomTextFieldState
import com.whereareyounow.ui.component.CustomTextFieldWithTimer
import com.whereareyounow.ui.component.CustomTopBar
import com.whereareyounow.ui.theme.WhereAreYouTheme

@Composable
fun FindIdScreen(
    moveToSignInScreen: () -> Unit,
    viewModel: FindIdViewModel = hiltViewModel()
) {
    val inputEmail = viewModel.inputEmail.collectAsState().value
    val inputEmailState = viewModel.inputEmailState.collectAsState().value
    val inputVerificationCode = viewModel.inputVerificationCode.collectAsState().value
    val isVerificationCodeSent = viewModel.isVerificationCodeSent.collectAsState().value
    val inputVerificationCodeState = viewModel.inputVerificationCodeState.collectAsState().value
    val emailVerificationLeftTime = viewModel.emailVerificationLeftTime.collectAsState().value
    FindIdScreen (
        inputEmail = inputEmail,
        updateInputEmail = viewModel::updateInputEmail,
        inputEmailState = inputEmailState,
        inputVerificationCode = inputVerificationCode,
        updateInputVerificationCode = viewModel::updateVerificationCode,
        isVerificationCodeSent = isVerificationCodeSent,
        inputVerificationCodeState = inputVerificationCodeState,
        emailVerificationLeftTime = emailVerificationLeftTime,
        verifyEmail = viewModel::verifyEmail,
        findId = viewModel::findId,
        moveToSignInScreen = moveToSignInScreen,
        moveToUserIdCheckingScreen = { viewModel.updateScreenState(FindIdViewModel.ScreenState.ShowingUserId) }
    )
}

@Composable
private fun FindIdScreen(
    inputEmail: String,
    updateInputEmail: (String) -> Unit,
    inputEmailState: EmailState,
    inputVerificationCode: String,
    updateInputVerificationCode: (String) -> Unit,
    isVerificationCodeSent: Boolean,
    inputVerificationCodeState: VerificationCodeState,
    emailVerificationLeftTime: Int,
    verifyEmail: () -> Unit,
    findId: (() -> Unit) -> Unit,
    moveToSignInScreen: () -> Unit,
    moveToUserIdCheckingScreen: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 20.dp, end = 20.dp)
            .imePadding()
    ) {
        FindIdScreenTopBar(moveToSignInScreen)

        Spacer(Modifier.height(20.dp))

        Row(
            modifier = Modifier
                .animateContentSize { _, _ -> }
                .height(IntrinsicSize.Min)
                .fillMaxWidth()
        ) {
            Box(modifier = Modifier.weight(1f)) {
                EmailInputBox(
                    inputEmail = inputEmail,
                    onValueChange = updateInputEmail,
                    inputEmailState = inputEmailState
                )
            }
            Spacer(Modifier.width(10.dp))
            VerificationButton(
                text = "인증",
                onClick = verifyEmail
            )
        }

        Spacer(Modifier.height(20.dp))

        if (isVerificationCodeSent) {
            VerificationCodeInputBox(
                inputText = inputVerificationCode,
                onValueChange = updateInputVerificationCode,
                guideLine = "인증코드가 일치하지 않습니다.",
                inputVerificationCodeState = inputVerificationCodeState,
                leftTime = emailVerificationLeftTime
            )
        }

        Spacer(Modifier.weight(1f))

        BottomOKButton(
            text = "확인",
            onClick = { findId(moveToUserIdCheckingScreen) }
        )

        Spacer(Modifier.height(20.dp))
    }
}

@Composable
fun FindIdScreenTopBar(
    moveToSignInScreen: () -> Unit
) {
    CustomTopBar(
        title = "아이디 찾기",
        onBackButtonClicked = moveToSignInScreen
    )
}

@Composable
fun EmailInputBox(
    inputEmail: String,
    onValueChange: (String) -> Unit,
    inputEmailState: EmailState
) {
    CustomTextField(
        hint = "이메일",
        inputText = inputEmail,
        onValueChange = onValueChange,
        guideLine = "올바른 이메일 형식으로 입력해주세요.",
        textFieldState = when (inputEmailState) {
            EmailState.UNSATISFIED -> CustomTextFieldState.UNSATISFIED
            else -> CustomTextFieldState.IDLE
        }
    )
}


@Composable
private fun VerificationCodeInputBox(
    inputText: String,
    onValueChange: (String) -> Unit,
    guideLine: String,
    inputVerificationCodeState: VerificationCodeState,
    leftTime: Int
) {
    CustomTextFieldWithTimer(
        hint = "이메일 인증코드",
        inputText = inputText,
        onValueChange = onValueChange,
        guideLine = guideLine,
        textFieldState = when (inputVerificationCodeState) {
            VerificationCodeState.UNSATISFIED -> CustomTextFieldState.UNSATISFIED
            else -> CustomTextFieldState.IDLE
        },
        leftTime = leftTime
    )
}

@Composable
fun VerificationButton(
    text: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .height(50.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(Color(0xFF2D2573))
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = Modifier
                .padding(start = 20.dp, end = 20.dp),
            text = text,
            color = Color(0xFFFFFFFF),
            fontSize = 16.sp
        )
    }
}

enum class EmailState {
    EMPTY, SATISFIED, UNSATISFIED
}

enum class VerificationCodeState {
    EMPTY, SATISFIED, UNSATISFIED
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun FindIdScreenPreview() {
    WhereAreYouTheme {
        FindIdScreen(
            inputEmail = "",
            updateInputEmail = {},
            inputEmailState = EmailState.EMPTY,
            inputVerificationCode = "",
            updateInputVerificationCode = {},
            isVerificationCodeSent = true,
            inputVerificationCodeState = VerificationCodeState.UNSATISFIED,
            emailVerificationLeftTime = 100,
            verifyEmail = {},
            findId = {},
            moveToSignInScreen = {},
            moveToUserIdCheckingScreen = {}
        )
    }
}