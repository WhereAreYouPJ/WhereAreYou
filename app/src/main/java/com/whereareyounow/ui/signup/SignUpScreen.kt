package com.whereareyounow.ui.signup

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.whereareyounow.R
import com.whereareyounow.ui.component.BottomOKButton
import com.whereareyounow.ui.component.CustomTextField
import com.whereareyounow.ui.component.CustomTextFieldState
import com.whereareyounow.ui.component.CustomTopBar
import com.whereareyounow.ui.theme.WhereAreYouTheme

@Composable
fun SignUpScreen(
    moveToBackScreen: () -> Unit,
    moveToSignUpSuccessScreen: () -> Unit,
    viewModel: SignUpViewModel = hiltViewModel()
) {
    val inputUserName = viewModel.inputUserName.collectAsState().value
    val inputUserNameState = viewModel.inputUserNameState.collectAsState().value
    val inputUserId = viewModel.inputUserId.collectAsState().value
    val inputUserIdState = viewModel.inputUserIdState.collectAsState().value
    val inputPassword = viewModel.inputPassword.collectAsState().value
    val inputPasswordState = viewModel.inputPasswordState.collectAsState().value
    val inputPasswordForChecking = viewModel.inputPasswordForChecking.collectAsState().value
    val inputPasswordForCheckingState = viewModel.inputPasswordForCheckingState.collectAsState().value
    val inputEmail = viewModel.inputEmail.collectAsState().value
    val inputEmailState = viewModel.inputEmailState.collectAsState().value
    val emailVerificationProgressState = viewModel.emailVerificationProgressState.collectAsState().value
    val inputVerificationCode = viewModel.inputVerificationCode.collectAsState().value
    val inputVerificationCodeState = viewModel.inputVerificationCodeState.collectAsState().value
    val emailVerificationLeftTime = viewModel.emailVerificationLeftTime.collectAsState().value
    SignUpScreen(
        inputUserName = inputUserName,
        updateInputUserName = viewModel::updateInputUserName,
        inputUserNameState = inputUserNameState,
        inputUserId = inputUserId,
        updateInputUserId = viewModel::updateInputUserId,
        inputUserIdState = inputUserIdState,
        checkIdDuplicate = viewModel::checkIdDuplicate,
        inputPassword = inputPassword,
        updateInputPassword = viewModel::updateInputPassword,
        inputPasswordState = inputPasswordState,
        inputPasswordForChecking = inputPasswordForChecking,
        updateInputPasswordForChecking = viewModel::updateInputPasswordForChecking,
        inputPasswordForCheckingState = inputPasswordForCheckingState,
        inputEmail = inputEmail,
        updateInputEmail = viewModel::updateInputEmail,
        inputEmailState = inputEmailState,
        emailVerificationProgressState = emailVerificationProgressState,
        verifyEmail = viewModel::verifyEmail,
        checkEmailDuplicate = viewModel::checkEmailDuplicate,
        inputVerificationCode = inputVerificationCode,
        inputVerificationCodeState = inputVerificationCodeState,
        updateInputVerificationCode = viewModel::updateInputVerificationCode,
        emailVerificationLeftTime = emailVerificationLeftTime,
        verifyEmailCode = viewModel::verifyEmailCode,
        signUp = viewModel::signUp,
        moveToBackScreen = moveToBackScreen,
        moveToSignUpSuccessScreen = moveToSignUpSuccessScreen,
    )
}

@Composable
private fun SignUpScreen(
    inputUserName: String,
    updateInputUserName: (String) -> Unit,
    inputUserNameState: UserNameState,
    inputUserId: String,
    updateInputUserId: (String) -> Unit,
    inputUserIdState: UserIdState,
    checkIdDuplicate: () -> Unit,
    inputPassword: String,
    updateInputPassword: (String) -> Unit,
    inputPasswordState: PasswordState,
    inputPasswordForChecking: String,
    updateInputPasswordForChecking: (String) -> Unit,
    inputPasswordForCheckingState: PasswordCheckingState,
    inputEmail: String,
    updateInputEmail: (String) -> Unit,
    inputEmailState: EmailState,
    emailVerificationProgressState: EmailVerificationProgressState,
    verifyEmail: () -> Unit,
    checkEmailDuplicate: () -> Unit,
    inputVerificationCode: String,
    inputVerificationCodeState: VerificationCodeState,
    updateInputVerificationCode: (String) -> Unit,
    emailVerificationLeftTime: Int,
    verifyEmailCode: () -> Unit,
    signUp: (() -> Unit) -> Unit,
    moveToBackScreen: () -> Unit,
    moveToSignUpSuccessScreen: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 20.dp, end = 20.dp)
            .imePadding(),
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
                    inputUserName = inputUserName,
                    updateInputUserName = updateInputUserName,
                    inputUserNameState = inputUserNameState
                )
                if (inputUserNameState == UserNameState.UNSATISFIED) {
                    Guideline(
                        text = "사용자명은 2~4자의 한글, 영문 대/소문자 조합으로 입력해주세요."
                    )
                }

                Spacer(Modifier.height(20.dp))

                // 아이디 입력
                Title(text = "아이디")
                Row(
                    modifier = Modifier
                        .height(IntrinsicSize.Min)
                        .fillMaxWidth()
                ) {
                    // 아이디 입력창
                    Box(modifier = Modifier.weight(1f)) {
                        UserIdTextField(
                            inputUserId = inputUserId,
                            updateInputUserId = updateInputUserId,
                            inputUserIdState = inputUserIdState
                        )
                    }
                    Spacer(Modifier.width(10.dp))
                    // 중복확인 버튼
                    CheckingButton(
                        text = "중복확인",
                        onClick = checkIdDuplicate
                    )
                }
                if (inputUserIdState == UserIdState.UNSATISFIED) {
                    Guideline(
                        text = "아이디는 영문 소문자로 시작하는 4~10자의 영문 소문자, 숫자 조합으로 입력해주세요."
                    )
                }
                if (inputUserIdState == UserIdState.DUPLICATED) {
                    Guideline(
                        text = "이미 존재하는 아이디입니다."
                    )
                }

                Spacer(Modifier.height(20.dp))

                // 비밀번호 입력
                Title(text = "비밀번호")
                // 비밀번호 입력창
                PasswordTextField(
                    inputPassword = inputPassword,
                    updateInputPassword = updateInputPassword,
                    inputPasswordState = inputPasswordState
                )
                if (inputPasswordState == PasswordState.UNSATISFIED) {
                    Guideline(
                        text = "비밀번호는 영문 대/소문자로 시작하는 4~10자의 영문 대/소문자, 숫자 조합으로 입력해주세요." +
                                "\n* 영문 대문자, 소문자, 숫자를 최소 하나 이상씩 포함해야합니다."
                    )
                }

                Spacer(Modifier.height(10.dp))

                // 비밀번호 확인
                PasswordForCheckingTextField(
                    inputPassword = inputPasswordForChecking,
                    updateInputPassword = updateInputPasswordForChecking,
                    inputPasswordState = inputPasswordForCheckingState
                )
                if (inputPasswordForCheckingState == PasswordCheckingState.UNSATISFIED) {
                    Guideline(
                        text = "비밀번호가 일치하지 않습니다."
                    )
                }

                Spacer(Modifier.height(20.dp))

                // 이메일 입력
                Title(text = "이메일")
                Row(
                    modifier = Modifier
                        .height(IntrinsicSize.Min)
                        .fillMaxWidth()
                ) {
                    // 이메일 입력창
                    Box(modifier = Modifier.weight(1f)) {
                        EmailTextField(
                            inputEmail = inputEmail,
                            updateInputEmail = updateInputEmail,
                            inputEmailState = inputEmailState
                        )
                    }
                    Spacer(Modifier.width(10.dp))
                    // 중복확인, 인증요청, 재전송 버튼
                    CheckingButton(
                        text = when (emailVerificationProgressState) {
                            EmailVerificationProgressState.DUPLICATE_UNCHECKED -> "중복확인"
                            EmailVerificationProgressState.DUPLICATE_CHECKED -> "인증요청"
                            EmailVerificationProgressState.VERIFICATION_REQUESTED -> "재전송"
                        }
                    ) {
                        when (emailVerificationProgressState) {
                            EmailVerificationProgressState.DUPLICATE_UNCHECKED -> checkEmailDuplicate()
                            else -> verifyEmail()
                        }
                    }
                }
                if (inputEmailState == EmailState.UNSATISFIED) {
                    Guideline(
                        text = "올바른 이메일 형식으로 입력해주세요."
                    )
                }
                if (inputEmailState == EmailState.DUPLICATED) {
                    Guideline(
                        text = "이미 존재하는 이메일입니다."
                    )
                }

                Spacer(Modifier.height(10.dp))

                // 이메일 인증코드 확인
                if (emailVerificationProgressState == EmailVerificationProgressState.VERIFICATION_REQUESTED) {
                    Row(
                        modifier = Modifier
                            .height(IntrinsicSize.Min)
                            .fillMaxWidth()
                    ) {
                        // 이메일 입력창
                        Box(modifier = Modifier.weight(1f)) {
                            EmailVerificationCodeTextField(
                                inputVerificationCode = inputVerificationCode,
                                updateInputVerificationCode = updateInputVerificationCode,
                                inputVerificationCodeState = inputVerificationCodeState,
                                leftTime = emailVerificationLeftTime
                            )
                        }
                        Spacer(Modifier.width(10.dp))
                        // 확인 버튼
                        CheckingButton(text = "확인") {
                            verifyEmailCode()
                        }
                    }
                }
                if (inputVerificationCodeState == VerificationCodeState.UNSATISFIED) {
                    Guideline(
                        text = "인증코드가 일치하지 않습니다."
                    )
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
    inputUserNameState: UserNameState
) {
    CustomTextField(
        hint = "이름",
        inputText = inputUserName,
        onValueChange = updateInputUserName,
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
    inputUserIdState: UserIdState
) {
    CustomTextField(
        hint = "아이디",
        inputText = inputUserId,
        onValueChange = updateInputUserId,
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
    inputPasswordState: PasswordState
) {
    CustomTextField(
        hint = "비밀번호",
        inputText = inputPassword,
        onValueChange = updateInputPassword,
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
    inputPasswordState: PasswordCheckingState
) {
    CustomTextField(
        hint = "비밀번호 확인",
        inputText = inputPassword,
        onValueChange = updateInputPassword,
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
    inputEmailState: EmailState
) {
    CustomTextField(
        hint = "이메일",
        inputText = inputEmail,
        onValueChange = updateInputEmail,
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
    BasicTextField(
        value = inputVerificationCode,
        onValueChange = updateInputVerificationCode,
        textStyle = TextStyle(
            color = Color(0xFF000000),
            fontSize = 16.sp
        ),
        singleLine = true
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .background(
                    color = Color(0xFFF5F5F6),
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(start = 10.dp, end = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.CenterStart
            ) {
                it()
                if (inputVerificationCode == "") {
                    Text(
                        text = "이메일 인증코드",
                        fontSize = 16.sp,
                        color = Color(0xFFC1C1C1)
                    )
                }
            }
            Spacer(Modifier.width(20.dp))
            when (inputVerificationCodeState) {
                VerificationCodeState.SATISFIED -> {
                    Image(
                        modifier = Modifier.size(20.dp),
                        painter = painterResource(id = R.drawable.check_circle_fill0_wght300_grad0_opsz24),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(color = Color(0xFF78C480))
                    )
                }
                VerificationCodeState.UNSATISFIED -> {
                    Text(
                        text = "${leftTime / 60}:${String.format("%02d", leftTime % 60)}",
                        color = Color(0xFFE59090)
                    )
                    Spacer(Modifier.width(20.dp))
                    Image(
                        modifier = Modifier.size(20.dp),
                        painter = painterResource(id = R.drawable.cancel_fill0_wght300_grad0_opsz24),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(color = Color(0xFFE59090))
                    )
                }
                else -> {
                    Text(
                        text = "${leftTime / 60}:${String.format("%02d", leftTime % 60)}",
                        color = Color(0xFFE59090)
                    )
                }
            }
        }
    }
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
            .height(50.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(
                color = Color(0xFFE9E9E9),
                shape = RoundedCornerShape(10.dp)
            )
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = Modifier
                .padding(start = 20.dp, end = 20.dp),
            text = text,
            color = Color(0xFF737373),
            fontSize = 16.sp
        )
    }
}

enum class UserNameState {
    EMPTY, SATISFIED, UNSATISFIED
}

enum class UserIdState {
    EMPTY, SATISFIED, UNSATISFIED, DUPLICATED, UNIQUE
}

enum class PasswordState {
    EMPTY, SATISFIED, UNSATISFIED
}

enum class PasswordCheckingState {
    EMPTY, SATISFIED, UNSATISFIED
}

enum class EmailState {
    EMPTY, SATISFIED, UNSATISFIED, DUPLICATED, UNIQUE
}

enum class EmailVerificationProgressState {
    DUPLICATE_UNCHECKED, DUPLICATE_CHECKED, VERIFICATION_REQUESTED
}

enum class VerificationCodeState {
    EMPTY, SATISFIED, UNSATISFIED
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun SignUpScreenPreview1() {
    WhereAreYouTheme {
        SignUpScreen(
            inputUserName = "",
            updateInputUserName = {  },
            inputUserNameState = UserNameState.EMPTY,
            inputUserId = "",
            updateInputUserId = {},
            inputUserIdState = UserIdState.EMPTY,
            checkIdDuplicate = {},
            inputPassword = "",
            updateInputPassword = {},
            inputPasswordState = PasswordState.EMPTY,
            inputPasswordForChecking = "",
            updateInputPasswordForChecking = {},
            inputPasswordForCheckingState = PasswordCheckingState.EMPTY,
            inputEmail = "",
            updateInputEmail = {},
            inputEmailState = EmailState.EMPTY,
            emailVerificationProgressState = EmailVerificationProgressState.DUPLICATE_CHECKED,
            verifyEmail = {},
            checkEmailDuplicate = {},
            inputVerificationCode = "",
            inputVerificationCodeState = VerificationCodeState.EMPTY,
            updateInputVerificationCode = {},
            emailVerificationLeftTime = 100,
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
            inputUserName = "",
            updateInputUserName = {},
            inputUserNameState = UserNameState.UNSATISFIED,
            inputUserId = "",
            updateInputUserId = {},
            inputUserIdState = UserIdState.UNSATISFIED,
            checkIdDuplicate = {},
            inputPassword = "",
            updateInputPassword = {},
            inputPasswordState = PasswordState.UNSATISFIED,
            inputPasswordForChecking = "",
            updateInputPasswordForChecking = {},
            inputPasswordForCheckingState = PasswordCheckingState.UNSATISFIED,
            inputEmail = "",
            updateInputEmail = {},
            inputEmailState = EmailState.UNSATISFIED,
            emailVerificationProgressState = EmailVerificationProgressState.VERIFICATION_REQUESTED,
            verifyEmail = {},
            checkEmailDuplicate = {},
            inputVerificationCode = "",
            inputVerificationCodeState = VerificationCodeState.UNSATISFIED,
            updateInputVerificationCode = {},
            emailVerificationLeftTime = 100,
            verifyEmailCode = {},
            signUp = {},
            moveToBackScreen = {},
            moveToSignUpSuccessScreen = {},
        )
    }
}