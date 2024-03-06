package com.whereareyounow.ui.findid

import android.widget.Toast
import androidx.compose.animation.animateContentSize
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.whereareyounow.data.findid.EmailState
import com.whereareyounow.data.findid.FindIdScreenSideEffect
import com.whereareyounow.data.findid.FindIdScreenUIState
import com.whereareyounow.data.findid.VerificationCodeState
import com.whereareyounow.ui.component.BottomOKButton
import com.whereareyounow.ui.component.CustomTextField
import com.whereareyounow.ui.component.CustomTextFieldState
import com.whereareyounow.ui.component.CustomTextFieldWithTimer
import com.whereareyounow.ui.component.CustomTopBar
import com.whereareyounow.ui.theme.WhereAreYouTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.withContext

@Composable
fun FindIdScreen(
    moveToSignInScreen: () -> Unit,
    moveToFindIdResultScreen: (String) -> Unit,
    viewModel: FindIdViewModel = hiltViewModel()
) {
    val findIdScreenUIState = viewModel.findIdScreenUIState.collectAsState().value
    val findIdScreenSideEffectFlow = viewModel.findIdScreenSideEffectFlow
    FindIdScreen (
        findIdScreenUIState = findIdScreenUIState,
        findIdScreenSideEffectFlow = findIdScreenSideEffectFlow,
        updateInputEmail = viewModel::updateInputEmail,
        updateInputVerificationCode = viewModel::updateInputVerificationCode,
        sendEmailVerificationCode = viewModel::sendEmailVerificationCode,
        findId = viewModel::findId,
        moveToSignInScreen = moveToSignInScreen,
        moveToFindIdResultScreen = moveToFindIdResultScreen
    )
}

@Composable
private fun FindIdScreen(
    findIdScreenUIState: FindIdScreenUIState,
    findIdScreenSideEffectFlow: SharedFlow<FindIdScreenSideEffect>,
    updateInputEmail: (String) -> Unit,
    updateInputVerificationCode: (String) -> Unit,
    sendEmailVerificationCode: () -> Unit,
    findId: ((String) -> Unit) -> Unit,
    moveToSignInScreen: () -> Unit,
    moveToFindIdResultScreen: (String) -> Unit,
) {
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        findIdScreenSideEffectFlow.collect { sideEffect ->
            when (sideEffect) {
                is FindIdScreenSideEffect.Toast -> {
                    withContext(Dispatchers.Main) { Toast.makeText(context, sideEffect.text, Toast.LENGTH_SHORT).show() }
                }
            }
        }
    }
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
                EmailTextField(
                    inputEmail = findIdScreenUIState.inputEmail,
                    onValueChange = updateInputEmail,
                    inputEmailState = findIdScreenUIState.inputEmailState
                )
            }
            Spacer(Modifier.width(10.dp))
            VerificationButton(
                text = "인증요청",
                onClick = sendEmailVerificationCode
            )
        }

        Spacer(Modifier.height(20.dp))

        if (findIdScreenUIState.isVerificationCodeSent) {
            VerificationCodeTextField(
                inputText = findIdScreenUIState.inputVerificationCode,
                onValueChange = updateInputVerificationCode,
                guideLine = "인증코드가 일치하지 않습니다.",
                inputVerificationCodeState = findIdScreenUIState.inputVerificationCodeState,
                leftTime = findIdScreenUIState.emailVerificationLeftTime
            )
        }

        Spacer(Modifier.weight(1f))

        BottomOKButton(
            text = "확인",
            onClick = { findId(moveToFindIdResultScreen) }
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
fun EmailTextField(
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
private fun VerificationCodeTextField(
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

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun FindIdScreenPreview() {
    WhereAreYouTheme {
        FindIdScreen(
            findIdScreenUIState = FindIdScreenUIState(),
            findIdScreenSideEffectFlow = MutableSharedFlow(),
            updateInputEmail = {},
            updateInputVerificationCode = {},
            sendEmailVerificationCode = {},
            findId = {},
            moveToSignInScreen = {},
            moveToFindIdResultScreen = {}
        )
    }
}