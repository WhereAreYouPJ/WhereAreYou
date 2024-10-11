package com.whereareyounow.ui.main.mypage.withdrawl

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
import com.whereareyounow.R
import com.whereareyounow.data.globalvalue.TOP_BAR_HEIGHT
import com.whereareyounow.ui.component.button.padding_yes.VariableButtonColorTextDefaultSizeButton
import com.whereareyounow.ui.component.tobbar.OneTextOneIconTobBar
import com.whereareyounow.ui.theme.medium12pt
import com.whereareyounow.ui.theme.medium14pt
import com.whereareyounow.ui.theme.medium20pt

@Composable
fun WithDrawalScreen4(
    moveToBackScreen: () -> Unit,
    moveToWithDrawalScreen5: () -> Unit,
) {
    WithDrawalScreen4(
        isContent = true,
        moveToBackScreen = moveToBackScreen,
        moveToWithDrawalScreen5 = moveToWithDrawalScreen5
    )
}

@Composable
private fun WithDrawalScreen4(
    isContent: Boolean,
    moveToBackScreen: () -> Unit,
    moveToWithDrawalScreen5: () -> Unit
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
        OneTextOneIconTobBar(title = "회원탈퇴", firstIcon = R.drawable.ic_backarrow) {
            moveToBackScreen()
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = TOP_BAR_HEIGHT.dp)
        ) {
            Gap(30)
            val isPasswordSamed = remember {
                mutableStateOf(false)
            }
            val passwordOutlinedTextFieldContainerColor =
                if (!isPasswordSamed.value) Color(0xFFD4D4D4) else Color(0xFFE13131)
            Text(
                text = "계정 삭제를 위해",
                color = Color.Black,
                style = medium20pt,
                modifier = Modifier.padding(start = 15.dp, end = 15.dp)
            )
            Gap(1)
            Text(
                text = "비밀번호를 입력해주세요.",
                color = Color.Black,
                style = medium20pt,
                modifier = Modifier.padding(start = 15.dp, end = 15.dp)
            )
            if (isPasswordSamed.value) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "아래 내용을 다시 한 번 확인해 주세요.",
                    color = Color(0xFF767676),
                    style = medium14pt,
                    modifier = Modifier.padding(start = 15.dp, end = 15.dp)
                )
            }
            Gap(30)
            Image(
                painter = painterResource(id = R.drawable.ic_password),
                contentDescription = "",
                modifier = Modifier.padding(start = 15.dp, end = 15.dp)
            )
            Gap(6)
            OutlinedTextField(
                value = password.value,
                onValueChange = {
                    password.value = it
                },
                modifier = Modifier
                    .height(44.dp)
                    .padding(start = 15.dp, end = 15.dp)
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
                Gap(6)
                Text(
                    text = "비밀번호가 맞지 않습니다.",
                    color = Color(0xFFDF4343),
                    style = medium12pt,
                    modifier = Modifier.padding(start = 21.dp, end = 15.dp)
                )
            }
            Spacer(Modifier.weight(1f))
            VariableButtonColorTextDefaultSizeButton(
                text = "회원 탈퇴하기",
                isVerified = canMove,
                onClicked = {
                    if (canMove.value) {
                        moveToWithDrawalScreen5()
                    } else {
                        isPasswordSamed.value = true
                    }
                }
            )
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
fun PreviewWithDrawalScreen4() {
    WithDrawalScreen4(
        isContent = true,
        moveToBackScreen = {},
        moveToWithDrawalScreen5 = {}
    )
}

