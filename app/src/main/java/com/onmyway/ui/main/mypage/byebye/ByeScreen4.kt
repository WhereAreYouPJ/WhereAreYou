package com.onmyway.ui.main.mypage.byebye

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.onmyway.R
import com.onmyway.data.globalvalue.TOP_BAR_HEIGHT
import com.onmyway.ui.component.tobbar.DefaultTopBar
import com.onmyway.ui.theme.medium12pt
import com.onmyway.ui.theme.medium14pt
import com.onmyway.ui.theme.medium20pt

@Composable
fun ByeScreen4(
    moveToBackScreen: () -> Unit,
    moveToByeScreen5: () -> Unit,
) {
    ByeScreen4(
        isContent = true,
        moveToBackScreen = moveToBackScreen,
        moveToByeScreen5 = moveToByeScreen5
    )
}


@Composable
private fun ByeScreen4(
    isContent: Boolean,
    moveToBackScreen: () -> Unit,
    moveToByeScreen5: () -> Unit,

    ) {
    val password = remember {
        mutableStateOf("")
    }
    val answer = remember {
        mutableStateOf("gaga")
    }
    val isSamed = remember {
        mutableStateOf(false)
    }
    val canMove = remember {
        mutableStateOf(false)
    }
    if (checkPassword(
            input = password.value,
            answer = answer.value,
            isSamed = isSamed.value
        )
    ) {
        canMove.value = true
    } else {
        canMove.value = false
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = TOP_BAR_HEIGHT.dp)
    ) {
        DefaultTopBar(
            title = "회원탈퇴"
        ) {
            moveToBackScreen()
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = TOP_BAR_HEIGHT.dp, start = 15.dp, end = 15.dp)
        ) {

            Spacer(Modifier.height(30.dp))

            val isPasswordSamed = remember {
                mutableStateOf(false)
            }
            val passwordOutlinedTextFieldContainerColor = if (!isPasswordSamed.value) Color(0xFFD4D4D4) else Color(0xFFE13131)

            Text(
                text = "계정 삭제를 위해",
                color = Color.Black,
                style = medium20pt
            )
            Spacer(modifier = Modifier.height(1.dp))
            Text(
                text = "비밀번호를 입력해주세요.",
                color = Color.Black,
                style = medium20pt
            )


            if (isPasswordSamed.value) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "아래 내용을 다시 한 번 확인해 주세요.",
                    color = Color(0xFF767676),
                    style = medium14pt,
                )
            }

            Gap(30)

            Image(
                painter = painterResource(id = R.drawable.ic_password),
                contentDescription = ""
            )

            Gap(6)

            OutlinedTextField(
                value = password.value,
                onValueChange = {
                    password.value = it
                },
                modifier = Modifier
                    .height(44.dp)
                    .fillMaxWidth()
                    .clip(
                        RoundedCornerShape(4.dp)
                    ),
                visualTransformation = PasswordVisualTransformation(),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = passwordOutlinedTextFieldContainerColor,
                    focusedBorderColor = passwordOutlinedTextFieldContainerColor
                )
            )

            if (isPasswordSamed.value) {
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "비밀번호가 맞지 않습니다.",
                    color = Color(0xFFDF4343),
                    style = medium12pt,
                    modifier = Modifier.padding(start = 6.dp)
                )
            }


            Spacer(Modifier.weight(1f))


            WithdrawlButton(
                text = "회원 탈퇴하기",
                canMove = canMove,
                onClicked = {
                    if (canMove.value) {

                        moveToByeScreen5()

//                        checkPassword(
//                            input = password.value,
//                            answer = answer.value,
//                            isSamed = isSamed.value
//                        ).apply {
//                            if (this) {
//                            } else {
//                            }
//                        }
                    } else {
                        isPasswordSamed.value = true

                    }

                }
            )

//            Image(
//                painter = painterResource(id = R.drawable.ic_byebutton),
//                contentDescription = "",
//                modifier = Modifier
//                    .padding(bottom = 68.dp)
//                    .clickableNoEffect {
//
//                        checkPassword(
//                            input = password.value,
//                            answer = answer.value,
//                            isSamed = isSamed.value
//                        ).apply {
//                            if (this) {
//                                moveToByeScreen5()
//                            } else {
//                                sflshf.value = true
//                            }
//                        }
//
//                    }
//            )

        }
    }

}

private fun checkPassword(
    input: String,
    answer: String,
    isSamed: Boolean
): Boolean {
    return if (input == answer) {
        !isSamed
    } else {
        isSamed
    }
}

@Preview
@Composable
fun PreviewByeScreen4() {
    ByeScreen4(
        isContent = true,
        moveToBackScreen = {},
        moveToByeScreen5 = {}
    )
}

