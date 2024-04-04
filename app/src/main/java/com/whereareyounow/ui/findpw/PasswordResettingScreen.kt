package com.whereareyounow.ui.findpw

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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.whereareyounow.data.findpw.PasswordCheckingState
import com.whereareyounow.data.findpw.PasswordResettingScreenSideEffect
import com.whereareyounow.data.findpw.PasswordResettingScreenUIState
import com.whereareyounow.data.findpw.PasswordState
import com.whereareyounow.data.findpw.ResultState
import com.whereareyounow.ui.component.RoundedCornerButton
import com.whereareyounow.ui.component.CustomTextField
import com.whereareyounow.ui.component.CustomTextFieldState
import com.whereareyounow.ui.component.CustomTopBar
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
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 20.dp, end = 20.dp)
    ) {
        PasswordResettingScreenTopBar(moveToSignInScreen)

        Spacer(Modifier.height(20.dp))
        when (resultState) {
            ResultState.EmailNotFound -> {
                Text(
                    text = "이메일이 존재하지 않습니다.",
                    fontSize = 20.sp
                )

                Spacer(Modifier.weight(1f))

                RoundedCornerButton(
                    text = "로그인하러 가기",
                    onClick = { moveToSignInScreen() }
                )

                Spacer(Modifier.height(20.dp))
            }
            ResultState.MemberMismatch -> {
                Text(
                    text = "아이디에 연동된 이메일이 아닙니다.",
                    fontSize = 20.sp
                )

                Spacer(Modifier.weight(1f))

                RoundedCornerButton(
                    text = "로그인하러 가기",
                    onClick = { moveToSignInScreen() }
                )

                Spacer(Modifier.height(20.dp))
            }
            ResultState.OK -> {
                Text(
                    text = "비밀번호를 변경해주세요.",
                    fontSize = 20.sp
                )

                Spacer(Modifier.height(20.dp))

                NewPasswordTextField(
                    inputPassword = passwordResettingScreenUIState.inputPassword,
                    updateInputPassword = updateInputPassword,
                    inputPasswordState = passwordResettingScreenUIState.inputPasswordState,
                    guideLine = when (passwordResettingScreenUIState.inputPasswordState) {
                        PasswordState.Unsatisfied -> "비밀번호는 영문 대/소문자로 시작하는 4~10자의 영문 대/소문자, 숫자 조합으로 입력해주세요." +
                                "\n* 영문 대문자, 소문자, 숫자를 최소 하나 이상씩 포함해야합니다."
                        else -> ""
                    }
                )

                Spacer(Modifier.height(20.dp))

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
                    text = "비밀번호 변경",
                    onClick = { resetPassword(userId, moveToPasswordResetSuccessScreen) }
                )

                Spacer(Modifier.height(20.dp))
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
        guideLine = guideLine,
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
        guideLine = guideLine,
        textFieldState = when (passwordCheckingState) {
            PasswordCheckingState.Empty -> CustomTextFieldState.Idle
            PasswordCheckingState.Satisfied -> CustomTextFieldState.Satisfied
            PasswordCheckingState.Unsatisfied -> CustomTextFieldState.Unsatisfied
        },
        isPassword = true
    )
}

@Preview(showBackground = true, showSystemUi = true)
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

@Preview(showBackground = true, showSystemUi = true)
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

@Preview(showBackground = true, showSystemUi = true)
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