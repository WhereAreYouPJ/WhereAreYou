package com.whereareyounow.ui.main.mypage.withdrawl

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.whereareyounow.R
import com.whereareyounow.data.globalvalue.TOP_BAR_HEIGHT
import com.whereareyounow.ui.component.button.padding_yes.VariableButtonColorTextDefaultSizeButton
import com.whereareyounow.ui.component.tobbar.OneTextOneIconTobBar
import com.whereareyounow.ui.main.mypage.MyPageViewModel
import com.whereareyounow.ui.theme.medium12pt
import com.whereareyounow.ui.theme.medium14pt
import com.whereareyounow.ui.theme.medium16pt
import com.whereareyounow.ui.theme.medium20pt
import com.whereareyounow.util.clickableNoEffect

@Composable
fun WithDrawalScreen1(
    moveToBackScreen: () -> Unit,
    moveToWithDrawalScreen2: () -> Unit,
) {
    WithDrawalScreen1(
        isContent = true,
        moveToBackScreen = moveToBackScreen,
        moveToWithDrawalScreen2 = moveToWithDrawalScreen2
    )
}

@Composable
private fun WithDrawalScreen1(
    isContent: Boolean,
    moveToBackScreen: () -> Unit,
    moveToWithDrawalScreen2: () -> Unit
) {
    val viewModel: MyPageViewModel = hiltViewModel()
    val myName = viewModel.name.collectAsState().value
    val canMove = remember {
        mutableStateOf(false)
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
            WithDrawlScreenTopTitle(
                title = "$myName 님, \n" +
                        "탈퇴하기 전에 꼭 확인해주세요.",
                titleStyle = medium20pt,
                titleColor = Color.Black
            )
            Gap(5)
            WithDrawlScreenTopTContent(
                content = "탈퇴 후 재가입은 14일이 지나야 할 수 있어요.",
                contentStyle = medium14pt,
                contentColor = Color(0xFF767676)
            )
            Gap(19)
            WithDrawalExplainTextInColumn(
                title = "모든 피드와 일정 등\n" +
                        "${myName}님의 소중한 기록이 모두 사라져요.",
                content = "온마이웨이에서 계정 삭제 시 지금어디를 이용하며 기록된 모든\n" +
                        "내용이 삭제돼요."
            )
            Gap(10)
            WithDrawalExplainTextInColumn(
                title = "친구들로부터\n" +
                        "${myName}님의 계정이 사라져요.",
                content = "또한 다시 가입하더라도 친구를 다시 추가하려면 처음부터 친구\n" +
                        "찾기와 요청 및 수락 확인을 다시 해야해요."
            )
            Spacer(Modifier.weight(1f))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 15.dp, end = 15.dp)
                    .clickableNoEffect {
                        canMove.value = !canMove.value
                    }
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(22.dp)
                ) {
                    Canvas(modifier = Modifier.fillMaxSize()) {
                        drawCircle(
                            color = if (canMove.value) Color(0xFF7B50FF) else Color(0xFFABABAB),
                        )
                    }
                    Image(
                        painter = painterResource(id = R.drawable.ic_checked),
                        contentDescription = ""
                    )
                }
                Gap(10)
                Text(
                    text = "모든 내용을 확인했으며 동의합니다.",
                    style = medium14pt,
                    color = Color(0xFF222222)
                )
            }
            Gap(18)
            VariableButtonColorTextDefaultSizeButton(
                "다음",
                isVerified = canMove,
                onClicked = {
                    if (canMove.value) {
                        moveToWithDrawalScreen2()
                    } else {

                    }
                }
            )
        }
    }
}

@Preview
@Composable
fun PreviewWithDrawalScreen1() {
    WithDrawalScreen1(
        true,
        {},
        {}
    )
}

@Composable
fun Gap(
    int: Int
) {
    Spacer(
        Modifier.size(int.dp)
    )
}

@Composable
fun WithDrawalExplainTextInColumn(
    title: String,
    content: String
) {
    Column(
        modifier = Modifier
            .padding(start = 15.dp, end = 15.dp)
            .border(
                border = BorderStroke(
                    width = 1.dp,
                    color = Color(0xFFDDDDDD)
                ),
                shape = RoundedCornerShape(10.dp)
            )
            .fillMaxWidth()
    ) {
        Gap(20)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp)
        ) {
            Text(
                text = title,
                style = medium16pt,
                color = Color.Black,
            )
        }
        Gap(7)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp)
        ) {
            Text(
                text = content,
                style = medium12pt,
                color = Color(0xFF767676)
            )
        }
        Gap(24)
    }
}

@Composable
fun WithDrawlScreenTopTitle(
    title: String,
    titleStyle: TextStyle,
    titleColor: Color
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 21.dp, end = 15.dp)
    ) {
        Text(
            text = title,
            style = titleStyle,
            color = titleColor
        )
    }
}


@Composable
fun WithDrawlScreenTopTitleAndImage(
    title: String,
    titleStyle: TextStyle,
    titleColor: Color,
    titleImage: Int
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 21.dp, end = 15.dp)
    ) {
        Text(
            text = title,
            style = titleStyle,
            color = titleColor
        )
        Gap(2)
        Image(
            painter = painterResource(id = titleImage),
            contentDescription = "",
            modifier = Modifier.size(23.dp)
        )
    }
}

@Composable
fun WithDrawlScreenTopTContent(
    content: String,
    contentStyle: TextStyle,
    contentColor: Color
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 21.dp, end = 15.dp)
    ) {
        Text(
            text = content,
            style = contentStyle,
            color = contentColor
        )
    }
}
