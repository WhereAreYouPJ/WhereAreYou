package com.onmyway.ui.findaccount.findid

import android.widget.Toast
import androidx.compose.animation.animateContentSize
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.onmyway.data.findaccount.FindAccountEmailVerificationScreenSideEffect
import com.onmyway.data.findaccount.FindAccountEmailVerificationScreenUIState
import com.onmyway.globalvalue.type.EmailButtonState
import com.onmyway.globalvalue.type.EmailCodeButtonState
import com.onmyway.globalvalue.type.EmailCodeState
import com.onmyway.globalvalue.type.EmailState
import com.onmyway.ui.component.CustomSurface
import com.onmyway.ui.component.CustomTopBar
import com.onmyway.ui.component.EmailCodeTextField
import com.onmyway.ui.component.EmailTextField
import com.onmyway.ui.component.HorizontalDivider
import com.onmyway.ui.component.RoundedCornerButton
import com.onmyway.ui.signup.InstructionContent
import com.onmyway.ui.signup.component.CheckingButton
import com.onmyway.ui.theme.OnMyWayTheme
import com.onmyway.util.CustomPreview
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.withContext

@Composable
fun FindAccountEmailVerificationScreen(
    moveToBackScreen: () -> Unit,
    viewModel: FindAccountViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
    val sideEffectFlow = viewModel.sideEffectFlow
    FindAccountEmailVerificationScreen(
        uiState = uiState,
        sideEffectFlow = sideEffectFlow,
        updateInputEmail = viewModel::updateInputEmail,
        updateInputEmailCode = viewModel::updateInputEmailCode,
        sendEmailCode = viewModel::sendEmailCode,
        verifyEmailCode = viewModel::verifyEmailCode,
    )
}

@Composable
private fun FindAccountEmailVerificationScreen(
    uiState: FindAccountEmailVerificationScreenUIState,
    sideEffectFlow: SharedFlow<FindAccountEmailVerificationScreenSideEffect>,
    updateInputEmail: (String) -> Unit,
    updateInputEmailCode: (String) -> Unit,
    sendEmailCode: () -> Unit,
    verifyEmailCode: () -> Unit,
) {
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        sideEffectFlow.collect { sideEffect ->
            when (sideEffect) {
                is FindAccountEmailVerificationScreenSideEffect.Toast -> {
                    withContext(Dispatchers.Main) { Toast.makeText(context, sideEffect.text, Toast.LENGTH_SHORT).show() }
                }
            }
        }
    }
    CustomSurface {
        Column {
            FindIdScreenTopBar({})

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
                        .animateContentSize()
                        .height(IntrinsicSize.Min)
                        .fillMaxWidth()
                ) {
                    Box(modifier = Modifier.weight(1f)) {
                        EmailTextField(
                            inputEmail = uiState.inputEmail,
                            updateInputEmail = updateInputEmail,
                            inputEmailState = uiState.inputEmailState,
                            guideLine = when (uiState.inputEmailState) {
                                EmailState.Invalid -> "이메일 형식에 알맞지 않습니다."
                                else -> ""
                            },
                            onSuccessText = when (uiState.inputEmailState) {
                                EmailState.Valid -> {
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
//                            moveToFindIdResultScreen("")
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

@CustomPreview
@Composable
private fun FindIdScreenPreview() {
    OnMyWayTheme {
//        FindAccountEmailVerificationScreen(
//            uiState = FindIdScreenUIState(),
//            sideEffectFlow = MutableSharedFlow(),
//            updateInputEmail = {},
//            updateInputVerificationCode = {},
//            sendEmailVerificationCode = {},
//            findId = {},
//            moveToFindAccountScreen = {},
//            moveToFindIdResultScreen = {}
//        )
    }
}