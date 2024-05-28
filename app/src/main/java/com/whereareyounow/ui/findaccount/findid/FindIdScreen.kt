package com.whereareyounow.ui.findaccount.findid

import android.widget.Toast
import androidx.activity.compose.BackHandler
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.whereareyounow.data.findid.EmailState
import com.whereareyounow.data.findid.FindIdScreenSideEffect
import com.whereareyounow.data.findid.FindIdScreenUIState
import com.whereareyounow.data.findid.VerificationCodeState
import com.whereareyounow.ui.component.CustomSurface
import com.whereareyounow.ui.component.CustomTextField
import com.whereareyounow.ui.component.CustomTextFieldState
import com.whereareyounow.ui.component.CustomTextFieldWithTimer
import com.whereareyounow.ui.component.CustomTopBar
import com.whereareyounow.ui.component.HorizontalDivider
import com.whereareyounow.ui.component.RoundedCornerButton
import com.whereareyounow.ui.signup.InstructionContent
import com.whereareyounow.ui.theme.WhereAreYouTheme
import com.whereareyounow.util.CustomPreview
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.withContext

@Composable
fun FindIdScreen(
    moveToFindAccountScreen: () -> Unit,
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
        moveToFindAccountScreen = moveToFindAccountScreen,
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
    findId: () -> Unit,
    moveToFindAccountScreen: () -> Unit,
    moveToFindIdResultScreen: (String) -> Unit,
) {
    BackHandler {
        moveToFindAccountScreen()
    }
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
    CustomSurface {
        Column {
            FindIdScreenTopBar(moveToFindAccountScreen)

            HorizontalDivider()

            Spacer(Modifier.height(40.dp))

            InstructionContent(text = "지금어디 가입 정보로\n아이디를 확인하세요")

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 15.dp, end = 15.dp)
                    .imePadding()
            ) {

                Spacer(Modifier.height(30.dp))

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
                            inputEmailState = findIdScreenUIState.inputEmailState,
                            isVerificationCodeSent = findIdScreenUIState.isVerificationCodeSent
                        )
                    }

                    Spacer(Modifier.width(4.dp))

                    VerificationButton(
                        text = "인증요청",
                        onClick = sendEmailVerificationCode
                    )
                }

                Spacer(Modifier.height(20.dp))

                if (findIdScreenUIState.isVerificationCodeSent) {
                    Row(
                        modifier = Modifier
                            .animateContentSize { _, _ -> }
                            .height(IntrinsicSize.Min)
                            .fillMaxWidth()
                    ) {
                        Box(modifier = Modifier.weight(1f)) {
                            VerificationCodeTextField(
                                inputText = findIdScreenUIState.inputVerificationCode,
                                onValueChange = updateInputVerificationCode,
                                inputVerificationCodeState = findIdScreenUIState.inputVerificationCodeState,
                                leftTime = findIdScreenUIState.emailVerificationLeftTime
                            )
                        }

                        Spacer(Modifier.width(4.dp))

                        VerificationButton(
                            text = "확인",
                            onClick = findId
                        )
                    }
                }

                Spacer(Modifier.weight(1f))

                RoundedCornerButton(
                    onClick = {
                        if (findIdScreenUIState.inputVerificationCodeState == VerificationCodeState.Satisfied) {
                            moveToFindIdResultScreen(findIdScreenUIState.userIdReceived)
                        } else {
                            Toast.makeText(context, "이메일 인증을 진행해주세요.", Toast.LENGTH_SHORT).show()
                        }
                    }
                ) {
                    Text(
                        text = "확인",
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFF2F2F2)
                    )
                }

                Spacer(Modifier.height(20.dp))
            }
        }
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
    inputEmailState: EmailState,
    isVerificationCodeSent: Boolean
) {
    CustomTextField(
        hint = "이메일",
        inputText = inputEmail,
        onValueChange = onValueChange,
        warningText = "이메일 형식에 알맞지 않습니다.",
        onSuccessText = "코드가 발송되었습니다.\n인증 코드를 하단에 입력해주세요.",
        textFieldState = when (inputEmailState) {
            EmailState.Unsatisfied -> CustomTextFieldState.Unsatisfied
            else -> if (isVerificationCodeSent) CustomTextFieldState.Satisfied else CustomTextFieldState.Idle
        }
    )
}


@Composable
private fun VerificationCodeTextField(
    inputText: String,
    onValueChange: (String) -> Unit,
    inputVerificationCodeState: VerificationCodeState,
    leftTime: Int
) {
    CustomTextFieldWithTimer(
        hint = "이메일 인증코드",
        inputText = inputText,
        onValueChange = onValueChange,
        warningText = "인증코드가 일치하지 않습니다.",
        onSuccessText = "인증코드가 일치합니다.",
        textFieldState = when (inputVerificationCodeState) {
            VerificationCodeState.Empty -> CustomTextFieldState.Idle
            VerificationCodeState.Unsatisfied -> CustomTextFieldState.Unsatisfied
            VerificationCodeState.Satisfied -> CustomTextFieldState.Satisfied
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
            .width(100.dp)
            .height(44.dp)
            .clip(RoundedCornerShape(6.dp))
            .background(Color(0xFF7B50FF))
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = Color(0xFFFFFFFF),
            fontSize = 16.sp
        )
    }
}

@CustomPreview
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
            moveToFindAccountScreen = {},
            moveToFindIdResultScreen = {}
        )
    }
}