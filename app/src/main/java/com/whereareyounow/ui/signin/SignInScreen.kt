package com.whereareyounow.ui.signin

import android.widget.Toast
import androidx.annotation.Keep
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.whereareyounow.R
import com.whereareyounow.data.signin.SignInScreenSideEffect
import com.whereareyounow.data.signin.SignInScreenUIState
import com.whereareyounow.ui.component.BottomOKButton
import com.whereareyounow.ui.component.CustomTextField
import com.whereareyounow.ui.component.CustomTextFieldState
import com.whereareyounow.ui.theme.WhereAreYouTheme
import com.whereareyounow.ui.theme.nanumSquareAc
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.withContext

@Composable
fun SignInScreen(
    moveToMainHomeScreen: () -> Unit,
    moveToFindIdScreen: () -> Unit,
    moveToFindPasswordScreen: () -> Unit,
    moveToSignUpScreen: () -> Unit,
    viewModel: SignInViewModel = hiltViewModel()
) {
    val signInScreenUIState = viewModel.signInScreenUIState.collectAsState().value
    val signUpScreenSideEffectFlow = viewModel.signInScreenSideEffectFlow
    SignInScreen(
        signInScreenUIState = signInScreenUIState,
        signInScreenSideEffectFlow = signUpScreenSideEffectFlow,
        updateInputUserId = viewModel::updateInputUserId,
        updateInputPassword = viewModel::updateInputPassword,
        signIn = viewModel::signIn,
        updateIsSignInFailed = viewModel::updateIsSignInFailed,
        moveToMainHomeScreen = moveToMainHomeScreen,
        moveToFindIdScreen = moveToFindIdScreen,
        moveToFindPasswordScreen = moveToFindPasswordScreen,
        moveToSignUpScreen = moveToSignUpScreen
    )
}

@Composable
private fun SignInScreen(
    signInScreenUIState: SignInScreenUIState,
    signInScreenSideEffectFlow: SharedFlow<SignInScreenSideEffect>,
    updateInputUserId: (String) -> Unit,
    updateInputPassword: (String) -> Unit,
    signIn: (() -> Unit) -> Unit,
    updateIsSignInFailed: (Boolean) -> Unit,
    moveToMainHomeScreen: () -> Unit,
    moveToFindIdScreen: () -> Unit,
    moveToFindPasswordScreen: () -> Unit,
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
    Column(
        modifier = Modifier
            .semantics { contentDescription = "Sign In Screen" }
            .fillMaxSize()
            .background(Color(0xFFFFFFFF))
            .padding(start = 20.dp, end = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(160.dp))

        Image(
            modifier = Modifier.size(80.dp),
            painter = painterResource(R.drawable.logo),
            contentDescription = null
        )
        Spacer(Modifier.height(6.dp))
        Text(
            modifier = Modifier
                .padding(start = 6.dp),
            text = "지금어디?",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = nanumSquareAc,
            color = Color(0xFF271F58)
        )

        Spacer(Modifier.height(40.dp))

        // 아이디 입력
        UserIdInputBox(
            contentDescription = "Id TextField",
            inputText = signInScreenUIState.inputUserId,
            onValueChange = updateInputUserId
        )

        Spacer(Modifier.height(10.dp))

        // 비밀번호 입력
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
        } else {
            Spacer(Modifier.height(30.dp))
        }


        BottomOKButton(
            contentDescription = "Login Button",
            text = "로그인",
            onClick = { signIn(moveToMainHomeScreen) },
            isLoading = signInScreenUIState.isSignInLoading
        )

        Spacer(Modifier.height(10.dp))

        Row(
            modifier = Modifier.height(IntrinsicSize.Min)
        ) {
            Text(
                modifier = Modifier
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) {
                        moveToFindIdScreen()
                    },
                text = "아이디 찾기",
                fontSize = 14.sp,
                color = Color(0xFF858585)
            )

            Spacer(Modifier.width(10.dp))

            Box(
                modifier = Modifier
                    .padding(top = 4.dp, bottom = 2.dp)
                    .width(1.dp)
                    .fillMaxHeight()
                    .drawBehind {
                        val borderSize = 1.dp.toPx()
                        drawLine(
                            color = Color(0xFFC3C3C3),
                            start = Offset(0f, 0f),
                            end = Offset(0f, size.height),
                            strokeWidth = borderSize,
                        )
                    }
            )

            Spacer(Modifier.width(10.dp))

            Text(
                modifier = Modifier
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) {
                        moveToFindPasswordScreen()
                    },
                text = "비밀번호 찾기",
                fontSize = 14.sp,
                color = Color(0xFF858585)
            )

            Spacer(Modifier.width(10.dp))

            Box(
                modifier = Modifier
                    .padding(top = 4.dp, bottom = 2.dp)
                    .width(1.dp)
                    .fillMaxHeight()
                    .drawBehind {
                        val borderSize = 1.dp.toPx()
                        drawLine(
                            color = Color(0xFFC3C3C3),
                            start = Offset(0f, 0f),
                            end = Offset(0f, size.height),
                            strokeWidth = borderSize,
                        )
                    }
            )

            Spacer(Modifier.width(10.dp))

            Text(
                modifier = Modifier
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) {
                        moveToSignUpScreen()
                    },
                text = "회원가입",
                fontSize = 14.sp,
                color = Color(0xFF858585)
            )
        }
    }
}

@Composable
private fun UserIdInputBox(
    contentDescription: String,
    inputText: String,
    onValueChange: (String) -> Unit
) {
    CustomTextField(
        contentDescription = contentDescription,
        hint = "아이디",
        inputText = inputText,
        onValueChange = onValueChange,
        guideLine = "",
        textFieldState = CustomTextFieldState.IDLE
    )
}

@Composable
private fun PasswordInputBox(
    contentDescription: String,
    inputText: String,
    onValueChange: (String) -> Unit
) {
    CustomTextField(
        contentDescription = contentDescription,
        hint = "비밀번호",
        inputText = inputText,
        onValueChange = onValueChange,
        guideLine = "",
        textFieldState = CustomTextFieldState.IDLE,
        isPassword = true
    )
}

@Keep
@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun SignInScreenPreview() {
    WhereAreYouTheme {
        SignInScreen(
            signInScreenUIState = SignInScreenUIState(),
            signInScreenSideEffectFlow = MutableSharedFlow(),
            updateInputUserId = {},
            updateInputPassword = {},
            signIn = {},
            updateIsSignInFailed = {},
            moveToMainHomeScreen = {},
            moveToFindIdScreen = {},
            moveToFindPasswordScreen = {},
            moveToSignUpScreen = {}
        )
    }
}

@Keep
@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun LoadingSignInScreenPreview() {
    WhereAreYouTheme {
        SignInScreen(
            signInScreenUIState = SignInScreenUIState(),
            signInScreenSideEffectFlow = MutableSharedFlow(),
            updateInputUserId = {},
            updateInputPassword = {},
            signIn = {},
            updateIsSignInFailed = {},
            moveToMainHomeScreen = {},
            moveToFindIdScreen = {},
            moveToFindPasswordScreen = {},
            moveToSignUpScreen = {}
        )
    }
}