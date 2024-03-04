package com.whereareyounow.ui.signup

import android.widget.Toast
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.whereareyounow.data.signup.EmailState
import com.whereareyounow.data.signup.EmailVerificationProgressState
import com.whereareyounow.data.signup.PasswordCheckingState
import com.whereareyounow.data.signup.PasswordState
import com.whereareyounow.data.signup.SignUpScreenSideEffect
import com.whereareyounow.data.signup.SignUpScreenUIState
import com.whereareyounow.data.signup.UserIdState
import com.whereareyounow.data.signup.UserNameState
import com.whereareyounow.data.signup.VerificationCodeState
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
fun SignUpScreen(
    moveToBackScreen: () -> Unit,
    moveToSignUpSuccessScreen: () -> Unit,
    viewModel: SignUpViewModel = hiltViewModel()
) {
    val signUpScreenUIState = viewModel.signUpScreenUIState.collectAsState().value
    val signUpScreenSideEffectFlow = viewModel.signUpScreenSideEffectFlow
    SignUpScreen(
        signUpScreenUIState = signUpScreenUIState,
        signUpScreenSideEffectFlow = signUpScreenSideEffectFlow,
        updateInputUserName = viewModel::updateInputUserName,
        updateInputUserId = viewModel::updateInputUserId,
        checkIdDuplicate = viewModel::checkIdDuplicate,
        updateInputPassword = viewModel::updateInputPassword,
        updateInputPasswordForChecking = viewModel::updateInputPasswordForChecking,
        updateInputEmail = viewModel::updateInputEmail,
        verifyEmail = viewModel::verifyEmail,
        checkEmailDuplicate = viewModel::checkEmailDuplicate,
        updateInputVerificationCode = viewModel::updateInputVerificationCode,
        verifyEmailCode = viewModel::verifyEmailCode,
        signUp = viewModel::signUp,
        moveToBackScreen = moveToBackScreen,
        moveToSignUpSuccessScreen = moveToSignUpSuccessScreen,
    )
}

@Composable
private fun SignUpScreen(
    signUpScreenUIState: SignUpScreenUIState,
    signUpScreenSideEffectFlow: SharedFlow<SignUpScreenSideEffect>,
    updateInputUserName: (String) -> Unit,
    updateInputUserId: (String) -> Unit,
    checkIdDuplicate: () -> Unit,
    updateInputPassword: (String) -> Unit,
    updateInputPasswordForChecking: (String) -> Unit,
    updateInputEmail: (String) -> Unit,
    verifyEmail: () -> Unit,
    checkEmailDuplicate: () -> Unit,
    updateInputVerificationCode: (String) -> Unit,
    verifyEmailCode: () -> Unit,
    signUp: (() -> Unit) -> Unit,
    moveToBackScreen: () -> Unit,
    moveToSignUpSuccessScreen: () -> Unit
) {
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        signUpScreenSideEffectFlow.collect { sideEffect ->
            when (sideEffect) {
                is SignUpScreenSideEffect.Toast -> {
                    withContext(Dispatchers.Main) { Toast.makeText(context, sideEffect.text, Toast.LENGTH_SHORT).show() }
                }
            }
        }
    }
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 20.dp, end = 20.dp)
            .imePadding(),
        // LazyColumn이지만 화면 세로 길이와 상관없이 마지막 item을 최하단에 고정시키기 위함
        verticalArrangement = remember {
            object : Arrangement.Vertical {
                override fun Density.arrange(
                    totalSize: Int,
                    sizes: IntArray,
                    outPositions: IntArray
                ) {
                    var currentOffset = 0
                    sizes.forEachIndexed { index, size ->
                        if (index == sizes.lastIndex) {
                            outPositions[index] = totalSize - size
                        } else {
                            outPositions[index] = currentOffset
                            currentOffset += size
                        }
                    }
                }
            }
        }
    ) {
        item {
            Column(modifier = Modifier.fillMaxSize()) {
                SignUpScreenTopBar(moveToBackScreen = moveToBackScreen)
                Spacer(Modifier.height(20.dp))

                // 사용자명 입력
                Title(text = "이름")
                UserNameTextField(
                    inputUserName = signUpScreenUIState.inputUserName,
                    updateInputUserName = updateInputUserName,
                    inputUserNameState = signUpScreenUIState.inputUserNameState,
                    guideLine = when (signUpScreenUIState.inputUserNameState) {
                        UserNameState.UNSATISFIED -> "사용자명은 2~4자의 한글, 영문 대/소문자 조합으로 입력해주세요."
                        else -> ""
                    }
                )

                Spacer(Modifier.height(20.dp))

                // 아이디 입력
                Title(text = "아이디")
                Row(
                    modifier = Modifier
                        .animateContentSize { _, _ -> }
                        .height(IntrinsicSize.Min)
                        .fillMaxWidth()
                ) {
                    // 아이디 입력창
                    Box(
                        modifier = Modifier.weight(1f)
                    ) {
                        UserIdTextField(
                            inputUserId = signUpScreenUIState.inputUserId,
                            updateInputUserId = updateInputUserId,
                            inputUserIdState = signUpScreenUIState.inputUserIdState,
                            guideLine = when (signUpScreenUIState.inputUserIdState) {
                                UserIdState.UNSATISFIED -> "아이디는 영문 소문자로 시작하는 4~10자의 영문 소문자, 숫자 조합으로 입력해주세요."
                                UserIdState.DUPLICATED -> "이미 존재하는 아이디입니다."
                                else -> ""
                            }
                        )
                    }
                    Spacer(Modifier.width(10.dp))
                    // 중복확인 버튼
                    CheckingButton(
                        text = "중복확인",
                        onClick = checkIdDuplicate
                    )
                }

                Spacer(Modifier.height(20.dp))

                // 비밀번호 입력
                Title(text = "비밀번호")
                // 비밀번호 입력창
                PasswordTextField(
                    inputPassword = signUpScreenUIState.inputPassword,
                    updateInputPassword = updateInputPassword,
                    inputPasswordState = signUpScreenUIState.inputPasswordState,
                    guideLine = when (signUpScreenUIState.inputPasswordState) {
                        PasswordState.UNSATISFIED -> "비밀번호는 영문 대/소문자로 시작하는 4~10자의 영문 대/소문자, 숫자 조합으로 입력해주세요." +
                                "\n* 영문 대문자, 소문자, 숫자를 최소 하나 이상씩 포함해야합니다."
                        else -> ""
                    }
                )

                Spacer(Modifier.height(10.dp))

                // 비밀번호 확인
                PasswordForCheckingTextField(
                    inputPassword = signUpScreenUIState.inputPasswordForChecking,
                    updateInputPassword = updateInputPasswordForChecking,
                    inputPasswordState = signUpScreenUIState.inputPasswordForCheckingState,
                    guideLine = when (signUpScreenUIState.inputPasswordForCheckingState) {
                        PasswordCheckingState.UNSATISFIED -> "비밀번호가 일치하지 않습니다."
                        else -> ""
                    }
                )

                Spacer(Modifier.height(20.dp))

                // 이메일 입력
                Title(text = "이메일")
                Row(
                    modifier = Modifier
                        .animateContentSize { _, _ -> }
                        .height(IntrinsicSize.Min)
                        .fillMaxWidth()
                ) {
                    // 이메일 입력창
                    Box(modifier = Modifier.weight(1f)) {
                        EmailTextField(
                            inputEmail = signUpScreenUIState.inputEmail,
                            updateInputEmail = updateInputEmail,
                            inputEmailState = signUpScreenUIState.inputEmailState,
                            guideLine = when (signUpScreenUIState.inputEmailState) {
                                EmailState.UNSATISFIED -> "올바른 이메일 형식으로 입력해주세요."
                                EmailState.DUPLICATED -> "이미 존재하는 이메일입니다."
                                else -> ""
                            }
                        )
                    }
                    Spacer(Modifier.width(10.dp))
                    // 중복확인, 인증요청, 재전송 버튼
                    CheckingButton(
                        text = when (signUpScreenUIState.emailVerificationProgressState) {
                            EmailVerificationProgressState.DUPLICATE_UNCHECKED -> "중복확인"
                            EmailVerificationProgressState.DUPLICATE_CHECKED -> "인증요청"
                            EmailVerificationProgressState.VERIFICATION_REQUESTED -> "재전송"
                        }
                    ) {
                        when (signUpScreenUIState.emailVerificationProgressState) {
                            EmailVerificationProgressState.DUPLICATE_UNCHECKED -> checkEmailDuplicate()
                            else -> verifyEmail()
                        }
                    }
                }

                Spacer(Modifier.height(10.dp))

                // 이메일 인증코드 확인
                if (signUpScreenUIState.emailVerificationProgressState == EmailVerificationProgressState.VERIFICATION_REQUESTED) {
                    Row(
                        modifier = Modifier
                            .height(IntrinsicSize.Min)
                            .fillMaxWidth()
                    ) {
                        // 이메일 입력창
                        Box(modifier = Modifier.weight(1f)) {
                            EmailVerificationCodeTextField(
                                inputVerificationCode = signUpScreenUIState.inputVerificationCode,
                                updateInputVerificationCode = updateInputVerificationCode,
                                inputVerificationCodeState = signUpScreenUIState.inputVerificationCodeState,
                                leftTime = signUpScreenUIState.emailVerificationCodeLeftTime
                            )
                        }
                        Spacer(Modifier.width(10.dp))
                        // 확인 버튼
                        CheckingButton(text = "확인") {
                            verifyEmailCode()
                        }
                    }
                }

                Spacer(Modifier.height(40.dp))
            }
        }

        item {
            // 회원가입 버튼
            BottomOKButton(
                text = "회원가입",
                onClick = { signUp(moveToSignUpSuccessScreen) }
            )

            Spacer(Modifier.height(20.dp))
        }
    }
}

@Composable
fun SignUpScreenTopBar(
    moveToBackScreen: () -> Unit,
) {
    CustomTopBar(
        title = "회원가입",
        onBackButtonClicked = moveToBackScreen
    )
}

@Composable
private fun Title(
    text: String
) {
    Text(
        text = text,
        fontSize = 16.sp,
        fontWeight = FontWeight.Medium,
        color = Color(0xFF8D8D8D)
    )
    Spacer(Modifier.height(10.dp))
}

@Composable
private fun UserNameTextField(
    inputUserName: String,
    updateInputUserName: (String) -> Unit,
    inputUserNameState: UserNameState,
    guideLine: String
) {
    CustomTextField(
        hint = "이름",
        inputText = inputUserName,
        onValueChange = updateInputUserName,
        guideLine = guideLine,
        textFieldState = when (inputUserNameState) {
            UserNameState.EMPTY -> CustomTextFieldState.IDLE
            UserNameState.SATISFIED -> CustomTextFieldState.SATISFIED
            UserNameState.UNSATISFIED -> CustomTextFieldState.UNSATISFIED
        }
    )
}

@Composable
private fun UserIdTextField(
    inputUserId: String,
    updateInputUserId: (String) -> Unit,
    inputUserIdState: UserIdState,
    guideLine: String
) {
    CustomTextField(
        hint = "아이디",
        inputText = inputUserId,
        onValueChange = updateInputUserId,
        guideLine = guideLine,
        textFieldState = when (inputUserIdState) {
            UserIdState.EMPTY -> CustomTextFieldState.IDLE
            UserIdState.SATISFIED -> CustomTextFieldState.IDLE
            UserIdState.UNSATISFIED -> CustomTextFieldState.UNSATISFIED
            UserIdState.DUPLICATED -> CustomTextFieldState.UNSATISFIED
            UserIdState.UNIQUE -> CustomTextFieldState.SATISFIED
        }
    )
}

@Composable
private fun PasswordTextField(
    inputPassword: String,
    updateInputPassword: (String) -> Unit,
    inputPasswordState: PasswordState,
    guideLine: String
) {
    CustomTextField(
        hint = "비밀번호",
        inputText = inputPassword,
        onValueChange = updateInputPassword,
        guideLine = guideLine,
        textFieldState = when (inputPasswordState) {
            PasswordState.EMPTY -> CustomTextFieldState.IDLE
            PasswordState.SATISFIED -> CustomTextFieldState.SATISFIED
            PasswordState.UNSATISFIED -> CustomTextFieldState.UNSATISFIED
        },
        isPassword = true
    )
}

@Composable
private fun PasswordForCheckingTextField(
    inputPassword: String,
    updateInputPassword: (String) -> Unit,
    inputPasswordState: PasswordCheckingState,
    guideLine: String
) {
    CustomTextField(
        hint = "비밀번호 확인",
        inputText = inputPassword,
        onValueChange = updateInputPassword,
        guideLine = guideLine,
        textFieldState = when (inputPasswordState) {
            PasswordCheckingState.EMPTY -> CustomTextFieldState.IDLE
            PasswordCheckingState.SATISFIED -> CustomTextFieldState.SATISFIED
            PasswordCheckingState.UNSATISFIED -> CustomTextFieldState.UNSATISFIED
        },
        isPassword = true
    )
}

@Composable
private fun EmailTextField(
    inputEmail: String,
    updateInputEmail: (String) -> Unit,
    inputEmailState: EmailState,
    guideLine: String
) {
    CustomTextField(
        hint = "이메일",
        inputText = inputEmail,
        onValueChange = updateInputEmail,
        guideLine = guideLine,
        textFieldState = when (inputEmailState) {
            EmailState.EMPTY -> CustomTextFieldState.IDLE
            EmailState.SATISFIED -> CustomTextFieldState.IDLE
            EmailState.UNSATISFIED -> CustomTextFieldState.UNSATISFIED
            EmailState.DUPLICATED -> CustomTextFieldState.UNSATISFIED
            EmailState.UNIQUE -> CustomTextFieldState.SATISFIED
        }
    )
}

@Composable
private fun EmailVerificationCodeTextField(
    inputVerificationCode: String,
    updateInputVerificationCode: (String) -> Unit,
    inputVerificationCodeState: VerificationCodeState,
    leftTime: Int
) {
    CustomTextFieldWithTimer(
        hint = "이메일 인증코드",
        inputText = inputVerificationCode,
        onValueChange = updateInputVerificationCode,
        guideLine = "인증코드가 일치하지 않습니다.",
        textFieldState = when (inputVerificationCodeState) {
            VerificationCodeState.EMPTY -> CustomTextFieldState.IDLE
            VerificationCodeState.UNSATISFIED -> CustomTextFieldState.UNSATISFIED
            VerificationCodeState.SATISFIED -> CustomTextFieldState.SATISFIED
        },
        leftTime = leftTime
    )
}

@Composable
fun Guideline(
    text: String
) {
    if (text != "") {
        Spacer(Modifier.height(10.dp))
        Text(
            text = text,
            fontSize = 14.sp,
            color = Color(0xFFFF0000)
        )
        Spacer(Modifier.height(10.dp))
    }
}

@Composable
fun CheckingButton(
    text: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .background(
                color = Color(0xFFE9E9E9),
                shape = RoundedCornerShape(8.dp)
            )
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = Modifier
                .padding(start = 20.dp, top = 14.dp, end = 20.dp, bottom = 14.dp),
            text = text,
            color = Color(0xFF737373),
            fontSize = 16.sp
        )
    }
}



@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun SignUpScreenPreview1() {
    WhereAreYouTheme {
        SignUpScreen(
            signUpScreenUIState = SignUpScreenUIState(),
            signUpScreenSideEffectFlow = MutableSharedFlow(),
            updateInputUserName = {  },
            updateInputUserId = {},
            checkIdDuplicate = {},
            updateInputPassword = {},
            updateInputPasswordForChecking = {},
            updateInputEmail = {},
            verifyEmail = {},
            checkEmailDuplicate = {},
            updateInputVerificationCode = {},
            verifyEmailCode = {},
            signUp = {},
            moveToBackScreen = {},
            moveToSignUpSuccessScreen = {},
        )
    }
}

@Preview(showBackground = true, heightDp = 1500)
@Composable
private fun SignUpScreenPreview2() {
    WhereAreYouTheme {
        SignUpScreen(
            signUpScreenUIState = SignUpScreenUIState(),
            signUpScreenSideEffectFlow = MutableSharedFlow(),
            updateInputUserName = {},
            updateInputUserId = {},
            checkIdDuplicate = {},
            updateInputPassword = {},
            updateInputPasswordForChecking = {},
            updateInputEmail = {},
            verifyEmail = {},
            checkEmailDuplicate = {},
            updateInputVerificationCode = {},
            verifyEmailCode = {},
            signUp = {},
            moveToBackScreen = {},
            moveToSignUpSuccessScreen = {},
        )
    }
}