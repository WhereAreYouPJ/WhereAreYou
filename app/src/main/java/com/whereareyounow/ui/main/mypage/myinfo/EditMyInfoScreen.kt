package com.whereareyounow.ui.main.mypage.myinfo

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.whereareyounow.R
import com.whereareyounow.data.globalvalue.SYSTEM_STATUS_BAR_HEIGHT
import com.whereareyounow.data.globalvalue.TOP_BAR_HEIGHT
import com.whereareyounow.ui.component.tobbar.OneTextOneIconTobBar
import com.whereareyounow.ui.main.mypage.MyPageViewModel
import com.whereareyounow.ui.main.mypage.byebye.Gap
import com.whereareyounow.ui.main.mypage.byebye.WithdrawlButton
import com.whereareyounow.ui.theme.getColor
import com.whereareyounow.ui.theme.medium12pt
import com.whereareyounow.ui.theme.medium14pt
import com.whereareyounow.ui.theme.medium18pt
import com.whereareyounow.util.CustomPreview
import com.whereareyounow.util.clickableNoEffect

@Composable
fun EditMyInfoScreen(
    moveToMyInfoScreen: () -> Unit,
    moveToBackScreen: () -> Unit
) {
    EditMyInfoScreen(
        isContent = true,
        moveToMyInfoScreen = moveToMyInfoScreen,
        moveToBackScreen = moveToBackScreen
    )
}

@Composable
private fun EditMyInfoScreen(
    isContent: Boolean,
    moveToMyInfoScreen: () -> Unit,
    moveToBackScreen: () -> Unit

) {
    val myPageViewModel: MyPageViewModel = hiltViewModel()
    val myName = myPageViewModel.name.collectAsState().value
    val myEmail = myPageViewModel.email.collectAsState().value
    val myEmailCodeIsVerifyed = myPageViewModel.isVerifyed.collectAsState().value


    val defaultFalse = remember {
        mutableStateOf(false)
    }
    val defaultTrue = remember {
        mutableStateOf(false)
    }
    val myNameState = remember {
        mutableStateOf(myName)
    }
    val myEmailState = remember {
        mutableStateOf(myEmail)
    }
    val emailVerifyed = remember {
        mutableStateOf(true)
    }
    val emailAuthCall = remember {
        mutableStateOf(false)
    }
    val authCode = remember {
        mutableStateOf("")
    }
    val callEmailAuth = remember {
        mutableStateOf(false)
    }
    val emailVerifyeString = remember {
        mutableStateOf(myEmailCodeIsVerifyed)
    }


    if(myEmailCodeIsVerifyed == "SUCCESS") {
        defaultTrue.value = true
    }


    val borderColor = if (emailVerifyed.value) getColor().brandColor else Color(0xFFE13131)

    fun emailVerify(
//        authCode : MutableState<String>
    ) {
        if(authCode.value != "123456") {
            emailVerifyed.value = false
        } else {
            emailVerifyed.value = true
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = SYSTEM_STATUS_BAR_HEIGHT.dp),
    ) {

        OneTextOneIconTobBar(
            title = "내 정보 관리",
            firstIcon = R.drawable.ic_back,
            firstIconClicked = { moveToBackScreen() }
        )

        Gap(34)

        InputMyInfoBox(
            title = "이름",
            content = myName,
            isReadOnly = defaultFalse,
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent,
            borderDp = 1f,
//            borderColor = getColor().brandColor
            borderColor = Color(0xFFD4D4D4)
        )

        Gap(10)

        InputEditMyInfoBox(
            title = "이메일",
            content = myEmailState,
            isReadOnly = defaultFalse,
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent,
            borderDp = 1.5f,
            borderColor = borderColor,
            emailAuthCall = emailAuthCall,
            authCode = authCode,
            requestEmail = { myPageViewModel.emailRequest(myEmailState.value!!) },
            callEmailAuth = callEmailAuth,
            verifyEmailCode = { myPageViewModel.emailVerify(email = myEmailState.value!! , code = authCode.value) },
            emailVerifyeString = emailVerifyeString
        )

        Spacer(Modifier.weight(1f))
        WithdrawlButton(
            "수정하기",
            canMove = defaultTrue,
            onClicked = {
                moveToBackScreen()
            }
        )
    }


}

//@Composable
//fun OneTextOneIconTobBar(
//    title: String,
//    firstIcon: Int,
//    firstIconClicked: () -> Unit
//) {
//    Box(
//        modifier = Modifier
//            .fillMaxWidth()
//            .height(TOP_BAR_HEIGHT.dp)
//            .drawBehind {
//                val strokeWidth = 1.5.dp.toPx()
//                val y = size.height - strokeWidth / 2
//                drawLine(
//                    color = Color(0xFFC9C9C9),
//                    start = Offset(0f, y),
//                    end = Offset(size.width, y),
//                    strokeWidth = strokeWidth
//                )
//            },
//        contentAlignment = Alignment.Center
//    ) {
//        Image(
//            modifier = Modifier
//                .align(Alignment.CenterStart)
//                .padding(start = 15.dp, bottom = 10.dp)
//                .clickableNoEffect {
//                    firstIconClicked()
//                }
//                .offset(y = 4.dp),
//            painter = painterResource(id = firstIcon),
//            contentDescription = "",
//            colorFilter = ColorFilter.tint(Color(0xFFACACAC))
//        )
//        Text(
//            text = title,
//            style = medium18pt,
//            color = Color(0xFF000000),
//            modifier = Modifier.offset(y = -2.dp)
//        )
//    }
//}


@Composable
fun InputEditMyInfoBox(
    title: String,
    content: MutableState<String?>,
    isReadOnly: MutableState<Boolean>,
    focusedBorderColor: Color,
    unfocusedBorderColor: Color,
    borderDp: Float,
    borderColor: Color,
    emailAuthCall : MutableState<Boolean>,
    authCode : MutableState<String>,
    requestEmail : () -> Unit,
    verifyEmailCode : () -> Unit,
    callEmailAuth : MutableState<Boolean>,
    emailVerifyeString : MutableState<String?>
) {
    val authCallColor = if(callEmailAuth.value) Color(0xFFABABAB) else getColor().brandColor
    val verifyButtonColor = if(emailVerifyeString.value == "SUCCESS") getColor().brandColor else Color(0xFFABABAB)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 15.dp, end = 15.dp)
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {

            Row(
                modifier = Modifier
                    .width(35.dp)
                    .height(24.dp)
                    .offset(x = 6.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    style = medium12pt,
                    color = Color(0xFF333333)
                )
            }

            Gap(2)

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = content.value!!,
                    onValueChange = {
                        content.value = it
                    },
                    readOnly = isReadOnly.value,
                    modifier = Modifier
                        .border(
                            border = BorderStroke(
                                width = borderDp.dp,
                                color = borderColor
                            ),
                            shape = RoundedCornerShape(6.dp)
                        )
                        .height(52.dp)
                        .width(241.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = focusedBorderColor,
                        unfocusedBorderColor = unfocusedBorderColor,
                        focusedTextColor = Color(0xFF222222),
                        unfocusedTextColor = Color(0xFF666666)
                    ),
                    textStyle = medium14pt
                )

                Gap(4)

                Row(
                    modifier = Modifier
                        .width(100.dp)
                        .height(52.dp)
                        .background(
                            color = authCallColor,
                            shape = RoundedCornerShape(6.dp)
                        )
                        .clickableNoEffect {
                            emailAuthCall.value = true
                            callEmailAuth.value = true
                            requestEmail()

                        },
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "인증요청",
                        color = Color(0xFFFFFFFF),
                        style = medium14pt
                    )
                }
            }
            if(!emailAuthCall.value) {
                Row(
                    modifier = Modifier
                        .width(331.dp)
                        .height(16.dp)
                        .offset(x = 2.dp),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "이메일을 작성해주세요.",
                        style = medium12pt,
                        color = getColor().brandColor
                    )
                }
            }


            if(emailAuthCall.value) {

                Gap(10)

                // timer
                val timer = remember { mutableIntStateOf(300) }

                LaunchedEffect(timer.value) {
                    if (timer.value > 0) {
                        kotlinx.coroutines.delay(1000L)
                        timer.value -= 1
                    }
                }
                val minutes = timer.intValue / 60
                val seconds = timer.intValue % 60
                val timerText = String.format("%02d:%02d", minutes, seconds)

                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = authCode.value,
                        onValueChange = {
                            authCode.value = it
                        },
                        placeholder = {
                            Text(
                                text = "인증코드 입력",
                                style = medium14pt,
                                color = Color(0xFF666666)
                            )
                        },
                        readOnly = false,
                        modifier = Modifier
                            .border(
                                border = BorderStroke(
                                    width = borderDp.dp,
                                    color = borderColor
                                ),
                                shape = RoundedCornerShape(6.dp)
                            )
                            .height(52.dp)
                            .width(241.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = focusedBorderColor,
                            unfocusedBorderColor = unfocusedBorderColor,
                            focusedTextColor = Color(0xFF666666),
                            unfocusedTextColor = Color(0xFF666666)
                        ),
                        textStyle = medium14pt,

                        trailingIcon = {
                            if (timer.value > 0) {
                                Text(
                                    text = timerText ,
                                    style = medium14pt,
                                    color = Color(0xFFDF4343)
                                )
                            } else {
                                Text(
                                    text = "시간 만료",
                                    style = medium14pt,
                                    color = Color(0xFFDF4343)
                                )
                            }
                        }

                    )

                    Gap(4)

                    Row(
                        modifier = Modifier
                            .width(100.dp)
                            .height(52.dp)
                            .background(
                                color = getColor().brandColor,
                                shape = RoundedCornerShape(6.dp)
                            )
                            .clickableNoEffect {
                                verifyEmailCode()
                                Log.d("sfsefsg2432424" , emailVerifyeString.value!!)

                            },
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "확인",
                            color = Color(0xFFFFFFFF),
                            style = medium14pt
                        )
                    }
                }

                Gap(4)

                Row(
                    modifier = Modifier
                        .width(331.dp)
                        .height(16.dp)
                        .offset(x = 2.dp),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "인증코드를 입력해주세요.",
                        style = medium12pt,
                        color = getColor().brandColor
                    )
                }
            }


        }
    }
}


@CustomPreview
@Composable
private fun OneTextOneIconTobBarPreview() {
    OneTextOneIconTobBar(
        title = "아이디 찾기",
        firstIcon = R.drawable.ic_back,
        firstIconClicked = { }
    )
}


@CustomPreview
@Composable
private fun PreviewEditMyInfoScreen() {
    EditMyInfoScreen(
        isContent = true,
        moveToBackScreen = {},
        moveToMyInfoScreen = {}
    )
}