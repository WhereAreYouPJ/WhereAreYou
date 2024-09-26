package com.onmyway.ui.main.mypage.byebye

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
import com.onmyway.R
import com.onmyway.data.globalvalue.TOP_BAR_HEIGHT
import com.onmyway.ui.component.tobbar.DefaultTopBar
import com.onmyway.ui.theme.medium14pt
import com.onmyway.ui.theme.medium20pt

@Composable
fun ByeScreen3(
    moveToBackScreen: () -> Unit,
    moveToByeScreen4: () -> Unit,


    ) {
    ByeScreen3(
        isContent = true,
        moveToBackScreen = moveToBackScreen,
        moveToByeScreen4 = moveToByeScreen4
    )
}


@Composable
private fun ByeScreen3(
    isContent: Boolean,
    moveToBackScreen: () -> Unit,
    moveToByeScreen4: () -> Unit,
) {
    val canMove = remember { mutableStateOf(true) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = TOP_BAR_HEIGHT.dp)
    ) {
        // 회원 탈퇴 탑바
        DefaultTopBar(
            title = "회원탈퇴"
        ) {
            moveToBackScreen()
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = TOP_BAR_HEIGHT.dp, start = 16.dp, end = 16.dp)
        ) {

            Gap(30)

            Text(
                text = "정말 계정을 삭제하시겠어요?",
                style = medium20pt,
                color = Color.Black
            )

            Gap(7)

            Text(
                text = "아래 내용을 다시 한 번 확인해 주세요.",
                style = medium14pt,
                color = Color(0xFF767676)
            )

            Gap(30)

            Image(
                painter = painterResource(id = R.drawable.ic_areyourealbyebyedetail),
                contentDescription = "",

                )
            Spacer(Modifier.weight(1f))







            WithdrawlButton(
                text = "회원 탈퇴하기",
                canMove = canMove,
                onClicked = {
                    moveToByeScreen4()
                }
            )

//            Image(
//                painter = painterResource(id = R.drawable.ic_byebutton),
//                contentDescription = "",
//                modifier = Modifier
//                    .padding(bottom = 68.dp)
//                    .clickableNoEffect {
//                        moveToByeScreen4()
//                    }
//            )

        }
    }

}

@Preview
@Composable
fun PreviewByeScreen3() {
    ByeScreen3(
        isContent = true,
        moveToBackScreen = {},
        moveToByeScreen4 = {}
    )
}

