package com.whereareyounow.ui.signin

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.whereareyounow.data.signin.SignInScreenSideEffect
import com.whereareyounow.data.signin.SignInScreenUIState
import com.whereareyounow.ui.component.CustomSurface
import com.whereareyounow.ui.component.CustomTextField
import com.whereareyounow.ui.component.CustomTextFieldState
import com.whereareyounow.ui.component.CustomTopBar
import com.whereareyounow.ui.component.HorizontalDivider
import com.whereareyounow.ui.component.RoundedCornerButton
import com.whereareyounow.ui.signup.InstructionContent
import com.whereareyounow.ui.theme.getColor
import com.whereareyounow.ui.theme.WhereAreYouTheme
import com.whereareyounow.ui.theme.bold18pt
import com.whereareyounow.ui.theme.medium12pt
import com.whereareyounow.ui.theme.medium14pt
import com.whereareyounow.util.CustomPreview
import com.whereareyounow.util.clickableNoEffect
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.withContext

@Composable
fun SignInWithAccountScreen(
    moveToSignInMethodSelectionScreen: () -> Unit,
    moveToMainHomeScreen: () -> Unit,
    moveToFindAccountScreen: () -> Unit,
    moveToResetPasswordScreen: () -> Unit,
    moveToSignUpScreen: () -> Unit,
    viewModel: SignInViewModel = hiltViewModel()
) {
    val signInScreenUIState = viewModel.signInScreenUIState.collectAsState().value
    val signUpScreenSideEffectFlow = viewModel.signInScreenSideEffectFlow
    SignInWithAccountScreen(
        signInScreenUIState = signInScreenUIState,
        signInScreenSideEffectFlow = signUpScreenSideEffectFlow,
        updateInputUserId = viewModel::updateInputUserId,
        updateInputPassword = viewModel::updateInputPassword,
        signIn = viewModel::signIn,
        updateIsSignInFailed = viewModel::updateIsSignInFailed,
        moveToSignInMethodSelectionScreen = moveToSignInMethodSelectionScreen,
        moveToMainHomeScreen = moveToMainHomeScreen,
        moveToFindAccountScreen = moveToFindAccountScreen,
        moveToResetPasswordScreen = moveToResetPasswordScreen,
        moveToSignUpScreen = moveToSignUpScreen
    )
}

@Composable
private fun SignInWithAccountScreen(
    signInScreenUIState: SignInScreenUIState,
    signInScreenSideEffectFlow: SharedFlow<SignInScreenSideEffect>,
    updateInputUserId: (String) -> Unit,
    updateInputPassword: (String) -> Unit,
    signIn: (() -> Unit) -> Unit,
    updateIsSignInFailed: (Boolean) -> Unit,
    moveToSignInMethodSelectionScreen: () -> Unit,
    moveToMainHomeScreen: () -> Unit,
    moveToFindAccountScreen: () -> Unit,
    moveToResetPasswordScreen: () -> Unit,
    moveToSignUpScreen: () -> Unit
) {
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        updateIsSignInFailed(false)
        signInScreenSideEffectFlow.collect { value ->
            when (value) {
                is SignInScreenSideEffect.Toast -> {
                    withContext(Dispatchers.Main) { Toast.makeText(context, value.text, Toast.LENGTH_SHORT).show() }
                }
            }
        }
    }
    CustomSurface {
        Column {
            SignInWithEmailScreenTopBar(onBackButtonClicked = moveToSignInMethodSelectionScreen)

            HorizontalDivider()

            Spacer(Modifier.height(40.dp))

            InstructionContent(text = "로그인하기")

            Spacer(Modifier.height(38.dp))

            Column(
                modifier = Modifier
                    .semantics { contentDescription = "Sign In Screen" }
                    .fillMaxSize()
                    .background(Color(0xFFFFFFFF))
                    .padding(start = 15.dp, end = 15.dp)
            ) {
                // 아이디 입력 창
                UserIdInputBox(
                    contentDescription = "Id TextField",
                    inputText = signInScreenUIState.inputUserId,
                    onValueChange = updateInputUserId
                )

                Spacer(Modifier.height(20.dp))

                // 비밀번호 입력 창
                PasswordInputBox(
                    contentDescription = "Password TextField",
                    inputText = signInScreenUIState.inputPassword,
                    onValueChange = updateInputPassword
                )


                if (signInScreenUIState.isSignInFailed) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 10.dp, top = 20.dp, end = 10.dp, bottom = 20.dp),
                        text = "아이디 또는 비밀번호가 맞지 않습니다.\n다시 시도해주세요.",
                        color = Color(0xFFFF0000)
                    )
                }

                Spacer(Modifier.height(24.dp))


                RoundedCornerButton(
                    contentDescription = "Login Button",
                    onClick = { signIn(moveToMainHomeScreen) },
                    isLoading = signInScreenUIState.isSignInLoading
                ) {
                    Text(
                        text = "로그인하기",
                        color = Color(0xFFF2F2F2),
                        style = bold18pt
                    )
                }

                Spacer(Modifier.height(30.dp))

                OtherButtons(
                    moveToFindAccountScreen = moveToFindAccountScreen,
                    moveToResetPasswordScreen = moveToResetPasswordScreen,
                    moveToSignUpScreen = moveToSignUpScreen
                )
            }
        }
    }
}

@Composable
private fun SignInWithEmailScreenTopBar(
    onBackButtonClicked: () -> Unit
) {
    CustomTopBar(
        title = "로그인",
        onBackButtonClicked = onBackButtonClicked
    )
}

@Composable
private fun UserIdInputBox(
    contentDescription: String,
    inputText: String,
    onValueChange: (String) -> Unit
) {
    Text(
        modifier = Modifier.padding(start = 6.dp),
        text = "아이디",
        color = Color(0xFF333333),
        style = medium12pt
    )

    Spacer(Modifier.height(6.dp))

    CustomTextField(
        contentDescription = contentDescription,
        hint = "아이디",
        inputText = inputText,
        onValueChange = onValueChange,
        warningText = "",
        textFieldState = CustomTextFieldState.Idle
    )
}

@Composable
private fun PasswordInputBox(
    contentDescription: String,
    inputText: String,
    onValueChange: (String) -> Unit
) {
    Text(
        modifier = Modifier.padding(start = 6.dp),
        text = "비밀번호",
        color = Color(0xFF333333),
        style = medium12pt
    )

    Spacer(Modifier.height(6.dp))

    CustomTextField(
        contentDescription = contentDescription,
        hint = "비밀번호",
        inputText = inputText,
        onValueChange = onValueChange,
        warningText = "",
        textFieldState = CustomTextFieldState.Idle,
        isPassword = true
    )
}

@Composable
private fun OtherButtons(
    moveToFindAccountScreen: () -> Unit,
    moveToResetPasswordScreen: () -> Unit,
    moveToSignUpScreen: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.height(IntrinsicSize.Min)
        ) {
            FindAccountButton(moveToFindAccountScreen = moveToFindAccountScreen)

            Spacer(Modifier.width(10.dp))

            VerticalDivider()

            Spacer(Modifier.width(10.dp))

            ResetPasswordButton(moveToResetPasswordScreen = moveToResetPasswordScreen)
        }

        Spacer(Modifier.height(18.dp))

        Row(
            modifier = Modifier.height(IntrinsicSize.Min)
        ) {
            Text(
                text = "계정이 없으신가요?",
                color = getColor().brightest,
                style = medium14pt
            )

            Spacer(Modifier.width(10.dp))

            SignUpButton(
                moveToSignUpScreen = moveToSignUpScreen,
                text = "가입하기"
            )
        }
    }
}

@Composable
private fun ResetPasswordButton(
    moveToResetPasswordScreen: () -> Unit
) {
    Text(
        modifier = Modifier.clickableNoEffect { moveToResetPasswordScreen() },
        text = "비밀번호 재설정",
        color = Color(0xFF666666),
        style = medium14pt
    )
}

@CustomPreview
@Composable
private fun SignInScreenPreview() {
    WhereAreYouTheme {
        SignInWithAccountScreen(
            signInScreenUIState = SignInScreenUIState(),
            signInScreenSideEffectFlow = MutableSharedFlow(),
            updateInputUserId = {},
            updateInputPassword = {},
            signIn = {},
            updateIsSignInFailed = {},
            moveToSignInMethodSelectionScreen = {},
            moveToMainHomeScreen = {},
            moveToFindAccountScreen = {},
            moveToResetPasswordScreen = {},
            moveToSignUpScreen = {}
        )
    }
}

@CustomPreview
@Composable
private fun LoadingSignInScreenPreview() {
    WhereAreYouTheme {
        SignInWithAccountScreen(
            signInScreenUIState = SignInScreenUIState(),
            signInScreenSideEffectFlow = MutableSharedFlow(),
            updateInputUserId = {},
            updateInputPassword = {},
            signIn = {},
            updateIsSignInFailed = {},
            moveToSignInMethodSelectionScreen = {},
            moveToMainHomeScreen = {},
            moveToFindAccountScreen = {},
            moveToResetPasswordScreen = {},
            moveToSignUpScreen = {}
        )
    }
}