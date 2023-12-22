package com.whereareyounow.ui.signup

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.whereareyounow.R

@Composable
fun SignUpScreen(
    moveToBackScreen: () -> Unit,
    moveToSignUpSuccessScreen: () -> Unit,
    viewModel: SignUpViewModel = hiltViewModel()
) {
    LazyColumn(
        modifier = Modifier
            .padding(start = 20.dp, end = 20.dp)
            .fillMaxSize()
            .imePadding()
    ) {
        item {
            SignUpScreenTopBar(moveToBackScreen = moveToBackScreen)
            Spacer(Modifier.height(20.dp))

            // 사용자명 입력
            val inputUserName = viewModel.inputUserName.collectAsState().value
            val inputUserNameState = viewModel.inputUserNameState.collectAsState().value
            Title(text = "사용자명")
            InputBox(
                hint = "사용자명",
                inputText = inputUserName,
                onValueChange = viewModel::updateInputUserName,
                inputBoxState = when (inputUserNameState) {
                    UserNameState.EMPTY -> InputBoxState.IDLE
                    UserNameState.SATISFIED -> InputBoxState.SATISFIED
                    UserNameState.UNSATISFIED -> InputBoxState.UNSATISFIED
                },
                isPassword = false,
                guide = when (inputUserNameState) {
                    UserNameState.EMPTY -> "사용자명은 4~10자의 한글, 영문 대/소문자 조합으로 입력해주세요."
                    UserNameState.SATISFIED -> "사용 가능한 사용자명입니다."
                    UserNameState.UNSATISFIED -> "사용자명은 4~10자의 한글, 영문 대/소문자 조합으로 입력해주세요."
                },
            )

            Spacer(Modifier.height(20.dp))

            // 아이디 입력
            val inputUserId = viewModel.inputUserId.collectAsState().value
            val inputUserIdState = viewModel.inputUserIdState.collectAsState().value
            Title(text = "아이디")
            Row(
                modifier = Modifier
                    .height(IntrinsicSize.Min)
                    .fillMaxWidth()
            ) {
                // 아이디 입력창
                Box(modifier = Modifier.weight(1f)) {
                    InputBox(
                        hint = "아이디",
                        inputText = inputUserId,
                        onValueChange = viewModel::updateInputUserId,
                        inputBoxState = when (inputUserIdState) {
                            UserIdState.EMPTY -> InputBoxState.IDLE
                            UserIdState.SATISFIED -> InputBoxState.IDLE
                            UserIdState.UNSATISFIED -> InputBoxState.UNSATISFIED
                            UserIdState.DUPLICATED -> InputBoxState.UNSATISFIED
                            UserIdState.UNIQUE -> InputBoxState.SATISFIED
                        },
                        isPassword = false,
                        guide = when (inputUserIdState) {
                            UserIdState.EMPTY -> "아이디는 영문 소문자로 시작하는 4~10자의 영문 소문자, 숫자 조합으로 입력해주세요."
                            UserIdState.SATISFIED -> "중복 확인을 해주세요."
                            UserIdState.UNSATISFIED -> "아이디는 영문 소문자로 시작하는 4~10자의 영문 소문자, 숫자 조합으로 입력해주세요."
                            UserIdState.DUPLICATED -> "이미 존재하는 아이디입니다."
                            UserIdState.UNIQUE -> "사용 가능한 아이디입니다."
                        }
                    )
                }
                Spacer(Modifier.width(10.dp))
                // 중복확인 버튼
                CheckingButton(text = "중복확인") { viewModel.checkIdDuplicate() }
            }


            Spacer(Modifier.height(20.dp))

            // 비밀번호 입력
            val inputPassword = viewModel.inputPassword.collectAsState().value
            val inputPasswordState = viewModel.inputPasswordState.collectAsState().value
            Title(text = "비밀번호")
            // 비밀번호 입력창
            InputBox(
                hint = "비밀번호",
                inputText = inputPassword,
                onValueChange = viewModel::updateInputPassword,
                inputBoxState = when (inputPasswordState) {
                    PasswordState.EMPTY -> InputBoxState.IDLE
                    PasswordState.SATISFIED -> InputBoxState.SATISFIED
                    PasswordState.UNSATISFIED -> InputBoxState.UNSATISFIED
                },
                isPassword = false,
                guide = when (inputPasswordState) {
                    PasswordState.EMPTY -> "비밀번호는 영문 대/소문자로 시작하는 4~10자의 영문 대/소문자, 숫자 조합으로 입력해주세요." +
                            "\n* 영문 대문자, 소문자, 숫자를 최소 하나 이상씩 포함해야합니다."
                    PasswordState.SATISFIED -> "사용 가능한 비밀번호입니다."
                    PasswordState.UNSATISFIED -> "비밀번호는 영문 대/소문자로 시작하는 4~10자의 영문 대/소문자, 숫자 조합으로 입력해주세요." +
                            "\n* 영문 대문자, 소문자, 숫자를 최소 하나 이상씩 포함해야합니다."
                },
            )

            Spacer(Modifier.height(20.dp))

            // 비밀번호 확인
            val inputPasswordForChecking = viewModel.inputPasswordForChecking.collectAsState().value
            val inputPasswordForCheckingState =
                viewModel.inputPasswordForCheckingState.collectAsState().value
            Title(text = "비밀번호 확인")
            Spacer(Modifier.height(10.dp))
            InputBox(
                hint = "비밀번호 확인",
                inputText = inputPasswordForChecking,
                onValueChange = viewModel::updateInputPasswordForChecking,
                inputBoxState = when (inputPasswordForCheckingState) {
                    PasswordCheckingState.EMPTY -> InputBoxState.IDLE
                    PasswordCheckingState.SATISFIED -> InputBoxState.SATISFIED
                    PasswordCheckingState.UNSATISFIED -> InputBoxState.UNSATISFIED
                },
                isPassword = false,
                guide = when (inputPasswordForCheckingState) {
                    PasswordCheckingState.EMPTY -> "비밀번호를 다시 한번 입력해주세요."
                    PasswordCheckingState.SATISFIED -> "일치합니다."
                    PasswordCheckingState.UNSATISFIED -> "일치하지 않습니다."
                },
            )

            Spacer(Modifier.height(20.dp))

            // 이메일 입력
            val inputEmail = viewModel.inputEmail.collectAsState().value
            val inputEmailState = viewModel.inputEmailState.collectAsState().value
            val isEmailDuplicationChecked = viewModel.isEmailDuplicationChecked.collectAsState().value
            Title(text = "이메일")
            Row(
                modifier = Modifier
                    .height(IntrinsicSize.Min)
                    .fillMaxWidth()
                ) {
                // 이메일 입력창
                Box(modifier = Modifier.weight(1f)) {
                    InputBox(
                        hint = "이메일",
                        inputText = inputEmail,
                        onValueChange = viewModel::updateInputEmail,
                        inputBoxState = when (inputEmailState) {
                            EmailState.EMPTY -> InputBoxState.IDLE
                            EmailState.SATISFIED -> InputBoxState.IDLE
                            EmailState.UNSATISFIED -> InputBoxState.UNSATISFIED
                            EmailState.DUPLICATED -> InputBoxState.UNSATISFIED
                            EmailState.UNIQUE -> InputBoxState.SATISFIED
                        },
                        isPassword = false,
                        guide = when (inputEmailState) {
                            EmailState.EMPTY -> "이메일을 입력해주세요."
                            EmailState.SATISFIED -> "중복 확인을 해주세요."
                            EmailState.UNSATISFIED -> "올바른 이메일 형식으로 입력해주세요."
                            EmailState.DUPLICATED -> "이미 존재하는 이메일입니다."
                            EmailState.UNIQUE -> "사용 가능한 이메일입니다. 인증을 해주세요."
                        }
                    )
                }
                Spacer(Modifier.width(10.dp))
                // 중복확인, 인증 요청 버튼
                CheckingButton(
                    text = when (isEmailDuplicationChecked) {
                        true -> "인증요청"
                        false -> "중복확인"
                    }
                ) {
                    when (isEmailDuplicationChecked) {
                        true -> viewModel.verifyEmail()
                        false -> viewModel.checkEmailDuplicate()
                    }
                }
            }

            // 이메일 인증코드 확인
            val inputVerificationCode = viewModel.inputVerificationCode.collectAsState().value
            val inputVerificationCodeState = viewModel.inputVerificationCodeState.collectAsState().value
            val isEmailVerificationInProgress = viewModel.isEmailVerificationInProgress.collectAsState().value
            if (isEmailVerificationInProgress) {
                Row(
                    modifier = Modifier
                        .height(IntrinsicSize.Min)
                        .fillMaxWidth()
                ) {
                    // 이메일 입력창
                    Box(modifier = Modifier.weight(1f)) {
                        InputBox(
                            hint = "이메일 인증코드",
                            inputText = inputVerificationCode,
                            onValueChange = viewModel::updateInputVerificationCode,
                            inputBoxState = when (inputVerificationCodeState) {
                                VerificationCodeState.EMPTY -> InputBoxState.IDLE
                                VerificationCodeState.SATISFIED -> InputBoxState.SATISFIED
                                VerificationCodeState.UNSATISFIED -> InputBoxState.UNSATISFIED
                            },
                            isPassword = false,
                            guide = when (inputVerificationCodeState) {
                                VerificationCodeState.EMPTY -> "인증 코드를 입력해주세요."
                                VerificationCodeState.SATISFIED -> "일치합니다."
                                VerificationCodeState.UNSATISFIED -> "일치하지 않습니다."
                            }
                        )
                    }
                    Spacer(Modifier.width(10.dp))
                    // 확인 버튼
                    CheckingButton(text = "확인") {
                        viewModel.verifyEmailCode()
                    }
                }
            }

            Spacer(Modifier.height(20.dp))

            // 회원가입 버튼
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(
                        color = Color(0xFF2A2550),
                        shape = RoundedCornerShape(10.dp)
                    )
                    .clickable {
                        viewModel.signUp(moveToSignUpSuccessScreen)
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "시작하기",
                    fontSize = 30.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(Modifier.height(20.dp))
        }
    }
}

@Composable
fun Title(
    text: String
) {
    Text(
        text = text,
        fontSize = 20.sp
    )
    Spacer(Modifier.height(10.dp))
}

@Composable
fun InputBox(
    hint: String,
    inputText: String,
    onValueChange: (String) -> Unit,
    inputBoxState: InputBoxState,
    isPassword: Boolean,
    guide: String
) {
    BasicTextField(
        modifier = Modifier
            .fillMaxWidth(),
        value = inputText,
        onValueChange = { onValueChange(it) },
        textStyle = TextStyle(fontSize = 20.sp),
        singleLine = true,
        visualTransformation = when (isPassword) {
            true -> PasswordVisualTransformation()
            false -> VisualTransformation.None
        },
        decorationBox = {
            Column {
                Box(
                    modifier = Modifier
                        .height(50.dp)
                        .border(
                            border = BorderStroke(
                                width = 1.dp,
                                color = when (inputBoxState) {
                                    InputBoxState.IDLE -> Color(0xFFBCBCBC)
                                    InputBoxState.SATISFIED -> Color.Green
                                    InputBoxState.UNSATISFIED -> Color.Red
                                }
                            ),
                            shape = RoundedCornerShape(10.dp)
                        )
                        .background(
                            color = Color(0xFFEEEEEE),
                            shape = RoundedCornerShape(10.dp)
                        )
                        .padding(start = 20.dp, end = 20.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    it()
                    if (inputText == "") {
                        Text(
                            text = hint,
                            fontSize = 20.sp,
                            color = Color(0xFFBCBCBC)
                        )
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(),
                        contentAlignment = Alignment.CenterEnd
                    ) {
                        when (inputBoxState) {
                            InputBoxState.IDLE -> {  }
                            InputBoxState.SATISFIED -> {
                                Image(
                                    modifier = Modifier.size(20.dp),
                                    painter = painterResource(id = R.drawable.check_circle_fill0_wght300_grad0_opsz24),
                                    contentDescription = null,
                                    colorFilter = ColorFilter.tint(color = Color(0xFF67C370))
                                )
                            }
                            InputBoxState.UNSATISFIED -> {
                                Image(
                                    modifier = Modifier.size(20.dp),
                                    painter = painterResource(id = R.drawable.cancel_fill0_wght300_grad0_opsz24),
                                    contentDescription = null,
                                    colorFilter = ColorFilter.tint(color = Color(0xFFE38C8C))
                                )
                            }
                        }
                    }
                }
                Guideline(
                    text = guide,
                    inputBoxState = inputBoxState
                )
            }
        }
    )
}

@Composable
fun Guideline(
    text: String,
    inputBoxState: InputBoxState
) {
    if (text != "") {
        Spacer(Modifier.height(10.dp))
        Text(
            text = text,
            fontSize = 16.sp,
            color = when (inputBoxState) {
                InputBoxState.IDLE,
                InputBoxState.SATISFIED -> Color.Black
                InputBoxState.UNSATISFIED -> Color.Red
            }
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
            fontSize = 20.sp
        )
    }
}

enum class InputBoxState {
    IDLE, SATISFIED, UNSATISFIED
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

enum class VerificationCodeState {
    EMPTY, SATISFIED, UNSATISFIED
}