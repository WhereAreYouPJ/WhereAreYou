package com.whereareyounow.ui.findaccount.findpw

import android.widget.Toast
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
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
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.whereareyounow.data.findpw.EmailState
import com.whereareyounow.data.findpw.FindPasswordScreenSideEffect
import com.whereareyounow.data.findpw.FindPasswordScreenUIState
import com.whereareyounow.data.findpw.VerificationCodeState
import com.whereareyounow.globalvalue.type.EmailButtonState
import com.whereareyounow.globalvalue.type.EmailCodeButtonState
import com.whereareyounow.globalvalue.type.EmailCodeState
import com.whereareyounow.globalvalue.type.FindAccountEmailState
import com.whereareyounow.ui.component.CustomSurface
import com.whereareyounow.ui.component.CustomTextField
import com.whereareyounow.ui.component.CustomTextFieldState
import com.whereareyounow.ui.component.CustomTextFieldWithTimer
import com.whereareyounow.ui.component.CustomTopBar
import com.whereareyounow.ui.component.EmailCodeTextField
import com.whereareyounow.ui.component.FindAccountEmailTextField
import com.whereareyounow.ui.component.HorizontalDivider
import com.whereareyounow.ui.component.RoundedCornerButton
import com.whereareyounow.ui.signup.component.CheckingButton
import com.whereareyounow.ui.signup.policy.InstructionContent
import com.whereareyounow.ui.theme.getColor
import com.whereareyounow.ui.theme.medium14pt
import com.whereareyounow.ui.theme.notoSanskr
import com.whereareyounow.util.CustomPreview
import com.whereareyounow.util.clickableNoEffect
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.withContext


@Composable
fun FindPasswordScreen(
    moveToSignInMethodSelectionScreen: () -> Unit,
    moveToPasswordResettingScreen: (String) -> Unit,
    viewModel: FindPasswordViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsState().value
    val sideEffectFlow = viewModel.findPasswordScreenSideEffectFlow
    FindPasswordScreen (
        uiState = uiState,
        findPasswordScreenSideEffectFlow = sideEffectFlow,
        updateInputEmail = viewModel::updateInputEmail,
        updateInputEmailCode = viewModel::updateInputEmailCode,
        sendEmailCode = viewModel::checkEmailDuplicate,
        moveToSignInScreen = moveToSignInMethodSelectionScreen,
        verifyEmailCode = viewModel::verifyEmailCode,
        moveToPasswordResettingScreen = moveToPasswordResettingScreen
    )
}

@Composable
private fun FindPasswordScreen(
    uiState: FindPasswordScreenUIState,
    findPasswordScreenSideEffectFlow: SharedFlow<FindPasswordScreenSideEffect>,
    updateInputEmail: (String) -> Unit,
    updateInputEmailCode: (String) -> Unit,
    sendEmailCode: () -> Unit,
    moveToSignInScreen: () -> Unit,
    verifyEmailCode: () -> Unit,
    moveToPasswordResettingScreen: (String) -> Unit
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

            InstructionContent(text = "온마이웨이에 가입한\n이메일을 적어주세요")

            Spacer(Modifier.height(40.dp))

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 15.dp, end = 15.dp)
            ) {
                Row(
                    modifier = Modifier
                        .animateContentSize { _, _ -> }
                        .height(IntrinsicSize.Min)
                        .fillMaxWidth()
                ) {
                    Box(modifier = Modifier.weight(1f)) {
                        FindAccountEmailTextField(
                            inputEmail = uiState.inputEmail,
                            updateInputEmail = updateInputEmail,
                            inputEmailState = uiState.inputEmailState,
                            guideLine = when (uiState.inputEmailState) {
                                FindAccountEmailState.Invalid -> "이메일 형식에 알맞지 않습니다."
                                FindAccountEmailState.NonExist -> "해당 이메일이 존재하지 않습니다."
                                else -> ""
                            },
                            onSuccessText = when (uiState.inputEmailState) {
                                FindAccountEmailState.Valid -> {
                                    "인증코드가 전송되었습니다."
                                }
                                else -> ""
                            }
                        )
                    }

                    Spacer(Modifier.width(4.dp))

                    // 중복확인, 인증요청, 재전송 버튼
                    CheckingButton(
                        text = when (uiState.requestButtonState) {
                            EmailButtonState.Request -> "인증요청"
                            EmailButtonState.RequestDone -> "인증요청 완료"
                        },
                        isActive = uiState.requestButtonState == EmailButtonState.Request
                    ) {
                        sendEmailCode()
                    }
                }

                Spacer(Modifier.height(10.dp))

                if (uiState.requestButtonState == EmailButtonState.RequestDone) {
                    Row(
                        modifier = Modifier
                            .height(IntrinsicSize.Min)
                            .fillMaxWidth()
                    ) {
                        // 이메일 입력창
                        Box(modifier = Modifier.weight(1f)) {
                            EmailCodeTextField(
                                inputEmailCode = uiState.inputEmailCode,
                                updateInputEmailCode = updateInputEmailCode,
                                inputEmailCodeState = uiState.inputEmailCodeState,
                                leftTime = uiState.emailCodeLeftTime
                            )
                        }

                        Spacer(Modifier.width(4.dp))

                        // 확인 버튼
                        CheckingButton(
                            text = when (uiState.emailCodeButtonState) {
                                EmailCodeButtonState.Inactive -> "인증 완료"
                                EmailCodeButtonState.Active -> "확인"
                            },
                            isActive = uiState.emailCodeButtonState == EmailCodeButtonState.Active
                        ) {
                            verifyEmailCode()
                        }
                    }

                    Spacer(Modifier.height(10.dp))
                }

                Spacer(Modifier.weight(1f))

                RoundedCornerButton(
                    onClick = {
                        if (uiState.inputEmailCodeState == EmailCodeState.Valid) {
                            moveToPasswordResettingScreen(uiState.email)
                        } else {
                            Toast.makeText(context, "이메일 인증을 진행해주세요.", Toast.LENGTH_SHORT).show()
                        }
                    }
                ) {
                    Text(
                        text = "확인",
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFF2F2F2),
                        fontFamily = notoSanskr,
                        fontSize = 18.sp
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

@CustomPreview
@Composable
private fun FindPasswordScreenPreview() {
//    OnMyWayTheme {
//        FindPasswordScreen(
//            findPasswordScreenUIState = FindPasswordScreenUIState(),
//            findPasswordScreenSideEffectFlow = MutableSharedFlow(),
//            updateInputUserId = {},
//            updateInputEmail = {},
//            updateInputVerificationCode = {},
//            authenticateEmail = {},
//            moveToSignInScreen = {},
//            verifyPasswordResetCode = {},
//            moveToPasswordResettingScreen = { _, _ -> }
//        )
//    }
}