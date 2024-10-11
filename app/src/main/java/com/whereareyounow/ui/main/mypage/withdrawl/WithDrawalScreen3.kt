package com.whereareyounow.ui.main.mypage.withdrawl

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.whereareyounow.R
import com.whereareyounow.data.globalvalue.TOP_BAR_HEIGHT
import com.whereareyounow.ui.component.button.padding_yes.VariableButtonColorTextDefaultSizeButton
import com.whereareyounow.ui.component.tobbar.OneTextOneIconTobBar
import com.whereareyounow.ui.theme.medium14pt
import com.whereareyounow.ui.theme.medium20pt

@Composable
fun WithDrawalScreen3(
    moveToBackScreen: () -> Unit,
    moveToWithDrawalScreen4: () -> Unit
    ) {
    WithDrawalScreen3(
        isContent = true,
        moveToBackScreen = moveToBackScreen,
        moveToWithDrawalScreen4 = moveToWithDrawalScreen4
    )
}


@Composable
private fun WithDrawalScreen3(
    isContent: Boolean,
    moveToBackScreen: () -> Unit,
    moveToWithDrawalScreen4: () -> Unit,
) {
    val canMove = remember { mutableStateOf(true) }
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
            Text(
                text = "정말 계정을 삭제하시겠어요?",
                style = medium20pt,
                color = Color.Black,
                modifier = Modifier.padding(start = 15.dp, end = 15.dp)
            )
            Gap(7)
            Text(
                text = "아래 내용을 다시 한 번 확인해 주세요.",
                style = medium14pt,
                color = Color(0xFF767676),
                modifier = Modifier.padding(start = 15.dp, end = 15.dp)
            )
            Gap(30)
            Image(
                painter = painterResource(id = R.drawable.ic_areyourealbyebyedetail),
                contentDescription = "",
                modifier = Modifier.padding(start = 15.dp, end = 15.dp)
            )
            Spacer(Modifier.weight(1f))
            VariableButtonColorTextDefaultSizeButton(
                text = "회원 탈퇴하기",
                isVerified = canMove,
                onClicked = {
                    moveToWithDrawalScreen4()
                }
            )
        }
    }
}

@Preview
@Composable
fun PreviewWithDrawalScreen3() {
    WithDrawalScreen3(
        isContent = true,
        moveToBackScreen = {},
        moveToWithDrawalScreen4 = {}
    )
}

