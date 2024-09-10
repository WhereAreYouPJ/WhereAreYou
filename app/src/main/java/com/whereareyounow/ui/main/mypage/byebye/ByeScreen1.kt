package com.whereareyounow.ui.main.mypage.byebye

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.content.MediaType.Companion.Text
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.whereareyounow.ui.component.CustomTopBar
import com.whereareyounow.ui.component.tobbar.DefaultTopBar
import com.whereareyounow.ui.main.mypage.MyPageViewModel
import com.whereareyounow.ui.theme.medium12pt
import com.whereareyounow.ui.theme.medium14pt
import com.whereareyounow.ui.theme.medium16pt
import com.whereareyounow.ui.theme.medium20pt
import com.whereareyounow.util.clickableNoEffect

@Composable
fun ByeScreen1(
    moveToBackScreen: () -> Unit,
    moveToByeScreen2: () -> Unit,
) {

    ByeScreen1(
        isContent = true,
        moveToBackScreen = moveToBackScreen,
        moveToByeScreen2 = moveToByeScreen2
    )
}


@Composable
private fun ByeScreen1(
    isContent: Boolean,
    moveToBackScreen: () -> Unit,
    moveToByeScreen2: () -> Unit

) {
    val viewModel: MyPageViewModel = hiltViewModel()

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
            Gap(30)
            ByeScreenTopTitle(
                title = "${viewModel.name.value} 님, \n" +
                        "탈퇴하기 전에 꼭 확인해주세요.",
                titleStyle = medium20pt,
                titleColor = Color.Black
            )
            Gap(5)
            ByeScreenTopContent(
                content = "탈퇴 후 재가입은 14일이 지나야 할 수 있어요.",
                contentStyle = medium14pt,
                contentColor = Color(0xFF767676)
            )
            Gap(19)

            ByeDetailBox(
                title = "모든 피드와 일정 등\n" +
                        "유민혁님의 소중한 기록이 모두 사라져요.",
                content = "온마이웨이에서 계정 삭제 시 지금어디를 이용하며 기록된 모든\n" +
                        "내용이 삭제돼요."
            )
            Gap(10)
            ByeDetailBox(
                title = "친구들로부터\n" +
                        "유민혁님의 계정이 사라져요.",
                content = "또한 다시 가입하더라도 친구를 다시 추가하려면 처음부터 친구\n" +
                        "찾기와 요청 및 수락 확인을 다시 해야해요."
            )
//            Column(
//                modifier = Modifier
//                    .border(
//                        border = BorderStroke(
//                            width = 1.dp,
//                            color = Color(0xFFDDDDDD)
//                        ),
//                        shape = RoundedCornerShape(10.dp)
//                    )
//                    .fillMaxWidth()
//            ) {
//                Gap(20)
//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(start = 16.dp)
//                ) {
//                    Text(
//                        text = "모든 피드와 일정 등\n" +
//                                "유민혁님의 소중한 기록이 모두 사라져요.",
//                        style = medium16pt,
//                        color = Color.Black,
//                    )
//                }
//                Gap(7)
//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(start = 16.dp)
//                ) {
//                    Text(
//                        text = "온마이웨이에서 계정 삭제 시 지금어디를 이용하며 기록된 모든\n" +
//                                "내용이 삭제돼요.",
//                        style = medium12pt,
//                        color = Color(0xFF767676)
//                    )
//                }
//                Gap(24)
//            }


            Spacer(
                Modifier.weight(1f)
            )
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_circlechecked),
                    contentDescription = "",
                    modifier = Modifier.clickableNoEffect {

                    }
                )
                Image(
                    painter = painterResource(id = R.drawable.ic_readconfirm),
                    contentDescription = "",
                )
            }
            Spacer(
                Modifier.height(18.dp)
            )
            Image(
                painter = painterResource(id = R.drawable.ic_nextbutton),
                contentDescription = "",
                modifier = Modifier
                    .padding(bottom = 68.dp)
                    .clickableNoEffect {
                        moveToByeScreen2()
                    }
            )
        }
    }

}

@Preview
@Composable
fun PreviewByeScreen1() {
    ByeScreen1(
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
fun ByeDetailBox(
    title: String,
    content: String
) {
    Column(
        modifier = Modifier
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
fun ByeScreenTopTitle(
    title: String,
    titleStyle: TextStyle,
    titleColor: Color
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 6.dp)
    ) {
        Text(
            text = title,
            style = titleStyle,
            color = titleColor
        )
    }
}


@Composable
fun ByeScreenTopTitleAndImage(
    title: String,
    titleStyle: TextStyle,
    titleColor: Color,
    titleImage: Int
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 6.dp)
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
fun ByeScreenTopContent(
    content: String,
    contentStyle: TextStyle,
    contentColor: Color
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 6.dp)
    ) {
        Text(
            text = content,
            style = contentStyle,
            color = contentColor
        )
    }
}