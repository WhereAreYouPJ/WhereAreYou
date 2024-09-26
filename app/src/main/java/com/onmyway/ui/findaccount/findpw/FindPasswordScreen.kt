package com.onmyway.ui.findaccount.findpw

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
import androidx.hilt.navigation.compose.hiltViewModel
import com.onmyway.data.findpw.EmailState
import com.onmyway.data.findpw.FindPasswordScreenSideEffect
import com.onmyway.data.findpw.FindPasswordScreenUIState
import com.onmyway.data.findpw.ResultState
import com.onmyway.data.findpw.VerificationCodeState
import com.onmyway.ui.component.CustomSurface
import com.onmyway.ui.component.CustomTextField
import com.onmyway.ui.component.CustomTextFieldState
import com.onmyway.ui.component.CustomTextFieldWithTimer
import com.onmyway.ui.component.CustomTopBar
import com.onmyway.ui.component.HorizontalDivider
import com.onmyway.ui.component.RoundedCornerButton
import com.onmyway.ui.signup.InstructionContent
import com.onmyway.ui.theme.getColor
import com.onmyway.ui.theme.OnMyWayTheme
import com.onmyway.ui.theme.medium14pt
import com.onmyway.util.CustomPreview
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.withContext


@Composable
fun FindPasswordScreen(
    moveToSignInScreen: () -> Unit,
    moveToPasswordResettingScreen: (String, ResultState) -> Unit,
    viewModel: FindPasswordViewModel = hiltViewModel()
) {
    val findPasswordScreenUIState = viewModel.findPasswordScreenUIState.collectAsState().value
    val findPasswordScreenSideEffectFlow = viewModel.findPasswordScreenSideEffectFlow
    FindPasswordScreen (
        findPasswordScreenUIState = findPasswordScreenUIState,
        findPasswordScreenSideEffectFlow = findPasswordScreenSideEffectFlow,
        updateInputUserId = viewModel::updateInputUserId,
        updateInputEmail = viewModel::updateInputEmail,
        updateInputVerificationCode = viewModel::updateInputVerificationCode,
        authenticateEmail = viewModel::sendEmailVerificationCode,
        moveToSignInScreen = moveToSignInScreen,
        verifyPasswordResetCode = viewModel::verifyPasswordResetCode,
        moveToPasswordResettingScreen = moveToPasswordResettingScreen
    )
}

@Composable
private fun FindPasswordScreen(
    findPasswordScreenUIState: FindPasswordScreenUIState,
    findPasswordScreenSideEffectFlow: SharedFlow<FindPasswordScreenSideEffect>,
    updateInputUserId: (String) -> Unit,
    updateInputEmail: (String) -> Unit,
    updateInputVerificationCode: (String) -> Unit,
    authenticateEmail: () -> Unit,
    moveToSignInScreen: () -> Unit,
    verifyPasswordResetCode: ((String, ResultState) -> Unit) -> Unit,
    moveToPasswordResettingScreen: (String, ResultState) -> Unit
) {
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        findPasswordScreenSideEffectFlow.collect { sideEffect ->
            when (sideEffect) {
                is FindPasswordScreenSideEffect.Toast -> {
                    withContext(Dispatchers.Main) { Toast.makeText(context, sideEffect.text, Toast.LENGTH_SHORT).show() }
                }
            }
        }
    }
    CustomSurface {
        Column {
            FindIdScreenTopBar(moveToSignInScreen)

            HorizontalDivider()

            Spacer(Modifier.height(40.dp))

            InstructionContent(text = "아이디와 이메일을 확인 후\n인증코드를 입력해주세요")

            Spacer(Modifier.height(40.dp))

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 15.dp, end = 15.dp)
            ) {
                UserIdInputBox(
                    inputText = findPasswordScreenUIState.inputUserId,
                    onValueChange = updateInputUserId
                )

                Spacer(Modifier.height(20.dp))

                Row(
                    modifier = Modifier
                        .animateContentSize { _, _ -> }
                        .height(IntrinsicSize.Min)
                        .fillMaxWidth()
                ) {
                    Box(modifier = Modifier.weight(1f)) {
                        EmailInputBox(
                            inputEmail = findPasswordScreenUIState.inputEmail,
                            updateInputEmail = updateInputEmail,
                            inputEmailState = findPasswordScreenUIState.inputEmailState,
                            guideLine = when (findPasswordScreenUIState.inputEmailState) {
                                EmailState.Unsatisfied -> "올바른 이메일 형식으로 입력해주세요."
                                else -> ""
                            }
                        )
                    }
                    Spacer(Modifier.width(10.dp))
                    AuthenticationButton(
                        onClick = authenticateEmail
                    )
                }

                Spacer(Modifier.height(20.dp))

                if (findPasswordScreenUIState.isVerificationCodeSent) {
                    VerificationCodeTextField(
                        inputText = findPasswordScreenUIState.inputVerificationCode,
                        onValueChange = updateInputVerificationCode,
                        warningText = "인증코드가 일치하지 않습니다.",
                        inputVerificationCodeState = findPasswordScreenUIState.inputVerificationCodeState,
                        leftTime = findPasswordScreenUIState.emailVerificationLeftTime
                    )
                }

                Spacer(Modifier.weight(1f))

                RoundedCornerButton(
                    onClick = { verifyPasswordResetCode(moveToPasswordResettingScreen) }
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
        warningText = "",
        textFieldState = CustomTextFieldState.Idle
    )
}

@Composable
fun EmailInputBox(
    inputEmail: String,
    updateInputEmail: (String) -> Unit,
    inputEmailState: EmailState,
    guideLine: String
) {
    CustomTextField(
        hint = "이메일",
        inputText = inputEmail,
        onValueChange = updateInputEmail,
        warningText = guideLine,
        textFieldState = when (inputEmailState) {
            EmailState.Empty -> CustomTextFieldState.Idle
            EmailState.Satisfied -> CustomTextFieldState.Satisfied
            EmailState.Unsatisfied -> CustomTextFieldState.Unsatisfied
        }
    )
}

@Composable
private fun VerificationCodeTextField(
    inputText: String,
    onValueChange: (String) -> Unit,
    warningText: String,
    inputVerificationCodeState: VerificationCodeState,
    leftTime: Int
) {
    CustomTextFieldWithTimer(
        hint = "이메일 인증코드",
        inputText = inputText,
        onValueChange = onValueChange,
        warningText = warningText,
        textFieldState = when (inputVerificationCodeState) {
            VerificationCodeState.Unsatisfied -> CustomTextFieldState.Unsatisfied
            else -> CustomTextFieldState.Idle
        },
        leftTime = leftTime
    )
}

@Composable
private fun AuthenticationButton(
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .width(100.dp)
            .height(44.dp)
            .clip(RoundedCornerShape(6.dp))
            .background(
                color = getColor().brandColor,
                shape = RoundedCornerShape(6.dp)
            )
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "인증요청",
            color = Color(0xFFFFFFFF),
            style = medium14pt
        )
    }
}

@CustomPreview
@Composable
private fun FindPasswordScreenPreview() {
    OnMyWayTheme {
        FindPasswordScreen(
            findPasswordScreenUIState = FindPasswordScreenUIState(),
            findPasswordScreenSideEffectFlow = MutableSharedFlow(),
            updateInputUserId = {},
            updateInputEmail = {},
            updateInputVerificationCode = {},
            authenticateEmail = {},
            moveToSignInScreen = {},
            verifyPasswordResetCode = {},
            moveToPasswordResettingScreen = { _, _ -> }
        )
    }
}