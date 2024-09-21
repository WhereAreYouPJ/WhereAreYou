package com.whereareyounow.ui.signup

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.animation.animateContentSize
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.whereareyounow.data.signup.SignUpPasswordCheckState
import com.whereareyounow.data.signup.SignUpPasswordState
import com.whereareyounow.data.signup.SignUpScreenSideEffect
import com.whereareyounow.data.signup.SignUpScreenUIState
import com.whereareyounow.data.signup.SignUpUserNameState
import com.whereareyounow.globalvalue.type.EmailButtonState
import com.whereareyounow.globalvalue.type.EmailCodeButtonState
import com.whereareyounow.globalvalue.type.EmailState
import com.whereareyounow.ui.component.CustomSurface
import com.whereareyounow.ui.component.CustomTextField
import com.whereareyounow.ui.component.CustomTextFieldState
import com.whereareyounow.ui.component.CustomTopBar
import com.whereareyounow.ui.component.EmailCodeTextField
import com.whereareyounow.ui.component.EmailTextField
import com.whereareyounow.ui.component.RoundedCornerButton
import com.whereareyounow.ui.signup.component.CheckingButton
import com.whereareyounow.ui.theme.WhereAreYouTheme
import com.whereareyounow.ui.theme.bold18pt
import com.whereareyounow.ui.theme.medium12pt
import com.whereareyounow.util.CustomPreview
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.withContext

@Composable
fun SignUpScreen(
    moveToBackScreen: () -> Unit,
    moveToSignUpSuccessScreen: () -> Unit,
    viewModel: SignUpViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
    val sideEffectFlow = viewModel.sideEffectFlow
    SignUpScreen(
        uiState = uiState,
        sideEffectFlow = sideEffectFlow,
        sendEmailCode = viewModel::sendEmailCode,
        updateInputUserName = viewModel::updateInputUserName,
        updateInputPassword = viewModel::updateInputPassword,
        updateInputPasswordForChecking = viewModel::updateInputPasswordForChecking,
        updateInputEmail = viewModel::updateInputEmail,
        updateInputVerificationCode = viewModel::updateInputVerificationCode,
        verifyEmailCode = viewModel::verifyEmailCode,
        signUp = viewModel::signUp,
        moveToBackScreen = moveToBackScreen,
        moveToSignUpSuccessScreen = moveToSignUpSuccessScreen,
    )
}

@Composable
private fun SignUpScreen(
    uiState: SignUpScreenUIState,
    sideEffectFlow: SharedFlow<SignUpScreenSideEffect>,
    sendEmailCode: () -> Unit,
    updateInputUserName: (String) -> Unit,
    updateInputPassword: (String) -> Unit,
    updateInputPasswordForChecking: (String) -> Unit,
    updateInputEmail: (String) -> Unit,
    updateInputVerificationCode: (String) -> Unit,
    verifyEmailCode: () -> Unit,
    signUp: (() -> Unit) -> Unit,
    moveToBackScreen: () -> Unit,
    moveToSignUpSuccessScreen: () -> Unit
) {
    BackHandler {
        moveToBackScreen()
    }
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        sideEffectFlow.collect { sideEffect ->
            when (sideEffect) {
                is SignUpScreenSideEffect.Toast -> {
                    withContext(Dispatchers.Main) { Toast.makeText(context, sideEffect.text, Toast.LENGTH_SHORT).show() }
                }
            }
        }
    }
    CustomSurface {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .imePadding(),
            // 스크롤 상태가 아니면 마지막 item을 맨 아래에 붙인다.
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
                            // 마지막 아이템이 아니면
                                outPositions[index] = currentOffset
                                currentOffset += size
                            }
                        }
                    }
                }
            }
        ) {
            item {
                Column {
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
                        Title(text = "이름")

                        UserNameTextField(
                            inputUserName = uiState.inputUserName,
                            updateInputUserName = updateInputUserName,
                            inputUserNameState = uiState.inputUserNameState,
                            guideLine = when (uiState.inputUserNameState) {
                                SignUpUserNameState.Invalid -> "이름은 2~4자의 한글, 영문 대/소문자 조합으로 입력해주세요."
                                else -> ""
                            }
                        )

                        Spacer(Modifier.height(10.dp))

                        Title(text = "이메일 주소")

                        Row(
                            modifier = Modifier
                                .animateContentSize()
                                .height(IntrinsicSize.Min)
                                .fillMaxWidth()
                        ) {
                            // 이메일 입력창
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

                        // 이메일 인증코드 확인
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
                                        updateInputEmailCode = updateInputVerificationCode,
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

                        Title(text = "비밀번호")

                        // 비밀번호 입력창
                        PasswordTextField(
                            inputPassword = uiState.inputPassword,
                            updateInputPassword = updateInputPassword,
                            inputPasswordState = uiState.inputPasswordState,
                            guideLine = when (uiState.inputPasswordState) {
                                SignUpPasswordState.Invalid -> "영문 대문자와 소문자, 숫자를 적어도 하나씩 사용하여, 영문으로 시작하는 6~20자의 비밀번호를 입력해주세요."
                                else -> ""
                            }
                        )

                        Spacer(Modifier.height(10.dp))

                        // 비밀번호 확인
                        PasswordForCheckingTextField(
                            inputPassword = uiState.inputPasswordCheck,
                            updateInputPassword = updateInputPasswordForChecking,
                            inputPasswordState = uiState.inputPasswordCheckState,
                            guideLine = when (uiState.inputPasswordCheckState) {
                                SignUpPasswordCheckState.Invalid -> "비밀번호가 일치하지 않습니다."
                                else -> ""
                            }
                        )
                    }

                    Spacer(Modifier.height(20.dp))
                }
            }

            item {
                Box(modifier = Modifier.padding(start = 15.dp, end = 15.dp)) {
                    // 회원가입 버튼
                    RoundedCornerButton(
                        onClick = { signUp(moveToSignUpSuccessScreen) }
                    ) {
                        Text(
                            text = "시작하기",
                            color = Color(0xFFF2F2F2),
                            style = bold18pt
                        )
                    }
                }
                Spacer(Modifier.height(20.dp))
            }
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
        modifier = Modifier.padding(start = 6.dp, top = 4.dp, end = 6.dp, bottom = 4.dp),
        text = text,
        color = Color(0xFF333333),
        style = medium12pt
    )
}

@Composable
private fun UserNameTextField(
    inputUserName: String,
    updateInputUserName: (String) -> Unit,
    inputUserNameState: SignUpUserNameState,
    guideLine: String
) {
    CustomTextField(
        hint = "이름",
        inputText = inputUserName,
        onValueChange = updateInputUserName,
        warningText = guideLine,
        onSuccessText = "사용가능한 이름입니다.",
        textFieldState = when (inputUserNameState) {
            SignUpUserNameState.Idle -> CustomTextFieldState.Idle
            SignUpUserNameState.Valid -> CustomTextFieldState.Satisfied
            SignUpUserNameState.Invalid -> CustomTextFieldState.Unsatisfied
        }
    )
}

@Composable
private fun PasswordTextField(
    inputPassword: String,
    updateInputPassword: (String) -> Unit,
    inputPasswordState: SignUpPasswordState,
    guideLine: String
) {
    CustomTextField(
        hint = "비밀번호",
        inputText = inputPassword,
        onValueChange = updateInputPassword,
        warningText = guideLine,
        onSuccessText = "사용 가능한 비밀번호입니다.",
        textFieldState = when (inputPasswordState) {
            SignUpPasswordState.Idle -> CustomTextFieldState.Idle
            SignUpPasswordState.Valid -> CustomTextFieldState.Satisfied
            SignUpPasswordState.Invalid -> CustomTextFieldState.Unsatisfied
        },
        isPassword = true
    )
}

@Composable
private fun PasswordForCheckingTextField(
    inputPassword: String,
    updateInputPassword: (String) -> Unit,
    inputPasswordState: SignUpPasswordCheckState,
    guideLine: String
) {
    CustomTextField(
        hint = "비밀번호 확인",
        inputText = inputPassword,
        onValueChange = updateInputPassword,
        warningText = guideLine,
        onSuccessText = "비밀번호가 일치합니다.",
        textFieldState = when (inputPasswordState) {
            SignUpPasswordCheckState.Idle -> CustomTextFieldState.Idle
            SignUpPasswordCheckState.Valid -> CustomTextFieldState.Satisfied
            SignUpPasswordCheckState.Invalid -> CustomTextFieldState.Unsatisfied
        },
        isPassword = true
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



@CustomPreview
@Composable
private fun SignUpScreenPreview1() {
    WhereAreYouTheme {
//        SignUpScreen(
//            uiState = uiState(),
//            signUpScreenSideEffectFlow = MutableSharedFlow(),
//            updateInputUserName = {  },
//            updateInputUserId = {},
//            checkIdDuplicate = {},
//            updateInputPassword = {},
//            updateInputPasswordForChecking = {},
//            updateInputEmail = {},
//            verifyEmail = {},
//            checkEmailDuplicate = {},
//            updateInputVerificationCode = {},
//            verifyEmailCode = {},
//            signUp = {},
//            moveToBackScreen = {},
//            moveToSignUpSuccessScreen = {},
//        )
    }
}

@CustomPreview
@Composable
private fun SignUpScreenPreview2() {
    WhereAreYouTheme {
//        SignUpScreen(
//            uiState = uiState(),
//            signUpScreenSideEffectFlow = MutableSharedFlow(),
//            updateInputUserName = {},
//            updateInputUserId = {},
//            checkIdDuplicate = {},
//            updateInputPassword = {},
//            updateInputPasswordForChecking = {},
//            updateInputEmail = {},
//            verifyEmail = {},
//            checkEmailDuplicate = {},
//            updateInputVerificationCode = {},
//            verifyEmailCode = {},
//            signUp = {},
//            moveToBackScreen = {},
//            moveToSignUpSuccessScreen = {},
//        )
    }
}

//private const val TOP_PROGRESS_BAR = "TOP_PROGRESS_BAR"
//private const val TEXT_1 = "TEXT_1"
//private const val TEXT_2 = "TEXT_2"
//private const val TEXT_3 = "TEXT_3"
//
//@Composable
//private fun TopProgressContent() {
//    Layout(
//        content = {
//            TopProgressBar(Modifier.layoutId(TOP_PROGRESS_BAR))
//            Text(
//                modifier = Modifier.layoutId(TEXT_1),
//                text = "약관동의",
//                fontSize = 16.sp,
//                color = Color(0xFF5F5F5F)
//            )
//            Text(
//                modifier = Modifier.layoutId(TEXT_2),
//                text = "정보입력",
//                fontSize = 16.sp,
//                color = Color(0xFF5F5F5F)
//            )
//            Text(
//                modifier = Modifier.layoutId(TEXT_3),
//                text = "가입완료",
//                fontSize = 16.sp,
//                color = Color(0xFF959595)
//            )
//        },
//        modifier = Modifier.padding(start = 10.dp, end = 10.dp)
//    ) { measurables, constraint ->
//        val text1 = measurables.first { it.layoutId == TEXT_1 }.measure(constraint)
//        val text2 = measurables.first { it.layoutId == TEXT_2 }.measure(constraint)
//        val text3 = measurables.first { it.layoutId == TEXT_3 }.measure(constraint)
//        val topProgressBar = measurables.first { it.layoutId == TOP_PROGRESS_BAR }.measure(
//            Constraints(
//                minWidth = 0,
//                minHeight = 0,
//                maxWidth = constraint.maxWidth - text1.width + 20.dp.toPx().toInt(),
//                maxHeight = constraint.maxHeight
//            )
//        )
//        val space = 10
//        val height = topProgressBar.height + space.dp.toPx() + text1.height
//
//        layout(constraint.maxWidth, height.toInt()) {
//            topProgressBar.place((constraint.maxWidth - topProgressBar.width) / 2, 0)
//            text1.place(0, topProgressBar.height + space.dp.toPx().toInt())
//            text2.place((constraint.maxWidth - text2.width) / 2, topProgressBar.height + space.dp.toPx().toInt())
//            text3.place(constraint.maxWidth - text3.width, topProgressBar.height + space.dp.toPx().toInt())
//        }
//    }
//}
//
//private const val CHECK_CIRCLE = "CHECK_CIRCLE"
//private const val DOUBLE_CIRCLE = "DOUBLE_CIRCLE"
//private const val GRAY_CIRCLE = "GRAY_CIRCLE"
//
//@Composable
//private fun TopProgressBar(modifier: Modifier) {
//    val progressBarHeight = 20
//    Layout(
//        content = {
//            CheckCircle(
//                Modifier
//                    .layoutId(CHECK_CIRCLE)
//                    .size(progressBarHeight.dp)
//            )
//            DoubleCircle(
//                Modifier
//                    .layoutId(DOUBLE_CIRCLE)
//                    .size(progressBarHeight.dp)
//            )
//            GrayCircle(
//                Modifier
//                    .layoutId(GRAY_CIRCLE)
//                    .size(progressBarHeight.dp)
//            )
//        },
//        modifier = modifier
//            .drawBehind {
//                drawRect(
//                    color = Color(0xFF726CA5),
//                    topLeft = Offset((progressBarHeight / 2).dp.toPx(), ((progressBarHeight - 2) / 2).dp.toPx()),
//                    size = Size((size.width - progressBarHeight.dp.toPx()) / 2, 2.dp.toPx())
//                )
//                drawRect(
//                    color = Color(0xFFBBBAB8),
//                    topLeft = Offset(size.width / 2, ((progressBarHeight - 2) / 2).dp.toPx()),
//                    size = Size((size.width - progressBarHeight.dp.toPx()) / 2, 2.dp.toPx())
//                )
//            }
//    ) { measurables, constraint ->
//        val yellowCheckCircle = measurables.first { it.layoutId == CHECK_CIRCLE }.measure(constraint)
//        val yellowDoubleCircle = measurables.first { it.layoutId == DOUBLE_CIRCLE }.measure(constraint)
//        val grayCircle = measurables.first { it.layoutId == GRAY_CIRCLE }.measure(constraint)
//        val height = listOf(yellowCheckCircle.height, yellowDoubleCircle.height, grayCircle.height).min()
//
//        layout(constraint.maxWidth, height) {
//            yellowCheckCircle.place(0, 0)
//            yellowDoubleCircle.place((constraint.maxWidth - yellowDoubleCircle.width) / 2, 0)
//            grayCircle.place(constraint.maxWidth - grayCircle.width, 0)
//        }
//    }
//}