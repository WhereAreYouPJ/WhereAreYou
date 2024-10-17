package com.whereareyounow.ui.signup

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.whereareyounow.data.signup.SignUpScreenUIState
import com.whereareyounow.data.signup.SignUpUserNameState
import com.whereareyounow.ui.component.CustomSurface
import com.whereareyounow.ui.component.CustomTextField
import com.whereareyounow.ui.component.CustomTextFieldState
import com.whereareyounow.ui.component.RoundedCornerButton
import com.whereareyounow.ui.signup.policy.InstructionContent
import com.whereareyounow.ui.signup.policy.TopProgressContent
import com.whereareyounow.ui.theme.bold18pt
import com.whereareyounow.ui.theme.getColor
import com.whereareyounow.ui.theme.medium12pt
import com.whereareyounow.ui.theme.medium14pt

@Composable
fun KakaoSignUpScreen(
    name: String,
    email: String,
    userId: String,
    moveToBackScreen: () -> Unit,
    moveToSignUpSuccessScreen: () -> Unit,
    moveToAccountDuplicateScreen: (String, String, List<String>, String, String) -> Unit,
    viewModel: SignUpViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
    LaunchedEffect(Unit) {
        viewModel.initKakaoInfo(name, email, userId)
    }
    KakaoSignUpScreen(
        uiState = uiState,
        moveToBackScreen = moveToBackScreen,
        updateInputUserName = viewModel::updateInputUserName,
        kakaoSignUp = {
            viewModel.kakaoSignUp(
                moveToSignUpSuccessScreen = { moveToSignUpSuccessScreen() }
            )
        },
        checkEmailDuplicate = viewModel::checkEmailDuplicate,
        moveToAccountDuplicateScreen = moveToAccountDuplicateScreen,
        moveToSignUpSuccessScreen = moveToSignUpSuccessScreen,
        isContent = true
    )
}

@Composable
private fun KakaoSignUpScreen(
    uiState: SignUpScreenUIState,
    updateInputUserName: (String) -> Unit,
    moveToBackScreen: () -> Unit,
    kakaoSignUp: () -> Unit,
    checkEmailDuplicate: (String, (String, String, List<String>, String, String) -> Unit, () -> Unit) -> Unit,
    moveToAccountDuplicateScreen: (String, String, List<String>, String, String) -> Unit,
    moveToSignUpSuccessScreen: () -> Unit,
    isContent: Boolean
) {
    CustomSurface {
        SignUpScreenTopBar(moveToBackScreen = moveToBackScreen)

        Spacer(Modifier.height(10.dp))

        TopProgressContent(step = 2)

        Spacer(Modifier.height(40.dp))

        InstructionContent(text = "아래 내용을 작성해주세요")

        Spacer(Modifier.height(30.dp))

        Column(
            modifier = Modifier.padding(start = 15.dp, end = 15.dp)
        ) {
            // 사용자명 입력
            Text(
                modifier = Modifier.padding(start = 6.dp, top = 4.dp, end = 6.dp, bottom = 4.dp),
                text = "이름",
                color = Color(0xFF333333),
                style = medium12pt
            )

            CustomTextField(
                hint = "이름",
                inputText = uiState.inputUserName,
                onValueChange = updateInputUserName,
                warningText = "이름은 2~4자의 한글, 영문 대/소문자 조합으로 입력해주세요.",
                onSuccessText = "사용가능한 이름입니다.",
                textFieldState = when (uiState.inputUserNameState) {
                    SignUpUserNameState.Idle -> CustomTextFieldState.Idle
                    SignUpUserNameState.Valid -> CustomTextFieldState.Satisfied
                    SignUpUserNameState.Invalid -> CustomTextFieldState.Unsatisfied
                }
            )

            Spacer(Modifier.height(10.dp))

            Text(
                modifier = Modifier.padding(start = 6.dp, top = 4.dp, end = 6.dp, bottom = 4.dp),
                text = "이메일",
                color = Color(0xFF333333),
                style = medium12pt
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(42.dp)
                    .border(
                        border = BorderStroke(
                            width = 1.dp,
                            color = Color(0xFFD4D4D4)
                        ),
                        shape = RoundedCornerShape(6.dp)
                    )
                    .padding(start = 8.dp, end = 8.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    text = uiState.inputEmail,
                    color = getColor().littleDark,
                    style = medium14pt
                )
            }

            Spacer(Modifier.weight(1f))

            RoundedCornerButton(onClick = { kakaoSignUp() }) {
                Text(
                    text = "시작하기",
                    color = Color(0xFFFFFFFF),
                    style = bold18pt
                )
            }

            Spacer(Modifier.height(20.dp))
        }
    }
}