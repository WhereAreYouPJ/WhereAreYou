package com.whereareyounow.ui.findaccount.findpw

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.whereareyounow.data.findpw.PasswordCheckingState
import com.whereareyounow.data.findpw.PasswordResettingScreenSideEffect
import com.whereareyounow.data.findpw.PasswordResettingScreenUIState
import com.whereareyounow.data.findpw.PasswordState
import com.whereareyounow.data.findpw.ResultState
import com.whereareyounow.ui.component.CustomSurface
import com.whereareyounow.ui.component.CustomTextField
import com.whereareyounow.ui.component.CustomTextFieldState
import com.whereareyounow.ui.component.CustomTopBar
import com.whereareyounow.ui.component.HorizontalDivider
import com.whereareyounow.ui.component.RoundedCornerButton
import com.whereareyounow.ui.signup.InstructionContent
import com.whereareyounow.util.CustomPreview
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.withContext

@Composable
fun PasswordResettingScreen(
    userId: String,
    resultState: ResultState,
    moveToSignInScreen: () -> Unit,
    moveToPasswordResetSuccessScreen: () -> Unit,
    viewModel: FindPasswordViewModel = hiltViewModel()
) {
    val passwordResettingScreenUIState = viewModel.passwordResettingScreenUIState.collectAsState().value
    val passwordResettingScreenSideEffectFlow = viewModel.passwordResettingScreenSideEffectFlow
    PasswordResettingScreen(
        userId = userId,
        resultState = resultState,
        passwordResettingScreenUIState = passwordResettingScreenUIState,
        passwordResettingScreenSideEffectFlow = passwordResettingScreenSideEffectFlow,
        updateInputPassword = viewModel::updateInputPassword,
        updateInputPasswordForChecking = viewModel::updateInputPasswordForChecking,
        resetPassword = viewModel::resetPassword,
        moveToSignInScreen = moveToSignInScreen,
        moveToPasswordResetSuccessScreen = moveToPasswordResetSuccessScreen
    )
}

@Composable
private fun PasswordResettingScreen(
    userId: String,
    resultState: ResultState,
    passwordResettingScreenUIState: PasswordResettingScreenUIState,
    passwordResettingScreenSideEffectFlow: SharedFlow<PasswordResettingScreenSideEffect>,
    updateInputPassword: (String) -> Unit,
    updateInputPasswordForChecking: (String) -> Unit,
    resetPassword: (String, () -> Unit) -> Unit,
    moveToSignInScreen: () -> Unit,
    moveToPasswordResetSuccessScreen: () -> Unit
) {
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        passwordResettingScreenSideEffectFlow.collect { sideEffect ->
            when (sideEffect) {
                is PasswordResettingScreenSideEffect.Toast -> {
                    withContext(Dispatchers.Main) { Toast.makeText(context, sideEffect.text, Toast.LENGTH_SHORT).show() }
                }
            }
        }
    }
    CustomSurface {
        Column {
            PasswordResettingScreenTopBar(moveToSignInScreen)

            HorizontalDivider()

            Spacer(Modifier.height(40.dp))

            InstructionContent(
                text = when (resultState) {
                    ResultState.EmailNotFound -> "이메일이 존재하지 않습니다."
                    ResultState.MemberMismatch -> "아이디에 연동된 이메일이 아닙니다."
                    ResultState.OK -> "비밀번호를 변경해주세요."
                }
            )



            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 15.dp, end = 15.dp)
            ) {

                Spacer(Modifier.height(20.dp))
                when (resultState) {
                    ResultState.EmailNotFound -> {
                        Spacer(Modifier.weight(1f))

                        RoundedCornerButton(
                            onClick = { moveToSignInScreen() }
                        ) {
                            Text(
                                text = "로그인하기",
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFF2F2F2)
                            )
                        }

                        Spacer(Modifier.height(20.dp))
                    }
                    ResultState.MemberMismatch -> {
                        Spacer(Modifier.weight(1f))

                        RoundedCornerButton(
                            onClick = { moveToSignInScreen() }
                        ) {
                            Text(
                                text = "로그인하기",
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFF2F2F2)
                            )
                        }

                        Spacer(Modifier.height(20.dp))
                    }
                    ResultState.OK -> {
                        Spacer(Modifier.height(20.dp))

                        NewPasswordTextField(
                            inputPassword = passwordResettingScreenUIState.inputPassword,
                            updateInputPassword = updateInputPassword,
                            inputPasswordState = passwordResettingScreenUIState.inputPasswordState,
                            guideLine = when (passwordResettingScreenUIState.inputPasswordState) {
                                PasswordState.Unsatisfied -> "영문 대문자와 소문자, 숫자를 적어도 하나씩 사용하여, 영문으로 시작하는 6~20자의 비밀번호를 입력해주세요."
                                else -> ""
                            }
                        )

                        Spacer(Modifier.height(10.dp))

                        NewPasswordCheckingTextField(
                            inputPasswordForChecking = passwordResettingScreenUIState.inputPasswordForChecking,
                            updateInputPasswordForChecking = updateInputPasswordForChecking,
                            passwordCheckingState = passwordResettingScreenUIState.passwordCheckingState,
                            guideLine = when (passwordResettingScreenUIState.passwordCheckingState) {
                                PasswordCheckingState.Unsatisfied -> "비밀번호가 일치하지 않습니다."
                                else -> ""
                            }
                        )

                        Spacer(Modifier.weight(1f))

                        RoundedCornerButton(
                            onClick = { resetPassword(userId, moveToPasswordResetSuccessScreen) }
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
    updateInputPassword: (String) -> Unit,
    inputPasswordState: PasswordState,
    guideLine: String
) {
    CustomTextField(
        hint = "새 비밀번호",
        inputText = inputPassword,
        onValueChange = updateInputPassword,
        warningText = guideLine,
        textFieldState = when (inputPasswordState) {
            PasswordState.Empty -> CustomTextFieldState.Idle
            PasswordState.Satisfied -> CustomTextFieldState.Satisfied
            PasswordState.Unsatisfied -> CustomTextFieldState.Unsatisfied
        },
        isPassword = true
    )
}

@Composable
private fun NewPasswordCheckingTextField(
    inputPasswordForChecking: String,
    updateInputPasswordForChecking: (String) -> Unit,
    passwordCheckingState: PasswordCheckingState,
    guideLine: String
) {
    CustomTextField(
        hint = "새 비밀번호 확인",
        inputText = inputPasswordForChecking,
        onValueChange = updateInputPasswordForChecking,
        warningText = guideLine,
        textFieldState = when (passwordCheckingState) {
            PasswordCheckingState.Empty -> CustomTextFieldState.Idle
            PasswordCheckingState.Satisfied -> CustomTextFieldState.Satisfied
            PasswordCheckingState.Unsatisfied -> CustomTextFieldState.Unsatisfied
        },
        isPassword = true
    )
}

@CustomPreview
@Composable
private fun OKPasswordResettingScreenPreview() {
    PasswordResettingScreen(
        userId = "",
        resultState = ResultState.OK,
        passwordResettingScreenUIState = PasswordResettingScreenUIState(),
        passwordResettingScreenSideEffectFlow = MutableSharedFlow(),
        updateInputPassword = {},
        updateInputPasswordForChecking = {},
        resetPassword = { _, _ -> },
        moveToSignInScreen = {},
        moveToPasswordResetSuccessScreen = {}
    )
}

@CustomPreview
@Composable
private fun EmailNotFoundPasswordResettingScreenPreview() {
    PasswordResettingScreen(
        userId = "",
        resultState = ResultState.EmailNotFound,
        passwordResettingScreenUIState = PasswordResettingScreenUIState(),
        passwordResettingScreenSideEffectFlow = MutableSharedFlow(),
        updateInputPassword = {},
        updateInputPasswordForChecking = {},
        resetPassword = { _, _ -> },
        moveToSignInScreen = {},
        moveToPasswordResetSuccessScreen = {}
    )
}

@CustomPreview
@Composable
private fun MemberMismatchPasswordResettingScreenPreview() {
    PasswordResettingScreen(
        userId = "",
        resultState = ResultState.MemberMismatch,
        passwordResettingScreenUIState = PasswordResettingScreenUIState(),
        passwordResettingScreenSideEffectFlow = MutableSharedFlow(),
        updateInputPassword = {},
        updateInputPasswordForChecking = {},
        resetPassword = { _, _ -> },
        moveToSignInScreen = {},
        moveToPasswordResetSuccessScreen = {}
    )
}