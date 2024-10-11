package com.whereareyounow.ui.main.mypage.ask

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.whereareyounow.R
import com.whereareyounow.data.globalvalue.TOP_BAR_HEIGHT
import com.whereareyounow.ui.component.button.padding_yes.VariableButtonColorTextDefaultSizeButton
import com.whereareyounow.ui.component.tobbar.OneTextOneIconTobBar
import com.whereareyounow.ui.main.mypage.withdrawl.Gap
import com.whereareyounow.ui.theme.notoSanskr

@Composable
fun AskScreen(
    moveToBackScreen: () -> Unit

) {
    AnnouncementScreen(
        true, moveToBackScreen
    )
}

@Composable
private fun AnnouncementScreen(
    isContent: Boolean, moveToBackScreen: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = TOP_BAR_HEIGHT.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OneTextOneIconTobBar(title = "1:1 이용문의", firstIcon = R.drawable.ic_backarrow) {
            moveToBackScreen()
        }
        Gap(24)
        Image(
            painter = painterResource(id = R.drawable.ic_ask), contentDescription = ""
        )
        Gap(10)
        Text(
            text = "온마이웨이에서\n" + "궁금하신 사항이 있으신가요?",
            fontFamily = notoSanskr,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF222222),
            fontSize = 20.sp
        )
        Gap(17)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 151.5.dp, end = 151.5.dp)
                .background(Color(0xFFDBDBDB))
                .border(
                    BorderStroke(
                        width = 1.dp, color = Color(0xFFDBDBDB)
                    )
                )
                .height(1.dp)
        )
        Gap(17)
        Text(
            text = "카카오톡 온마이웨이 채널을 추가하고\n" + "             1:1 문의를 남겨주시면\n" + "최대한 빠르게 도와드리겠습니다.",
            fontFamily = notoSanskr,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            color = Color(0xFF222222)
        )
        Gap(38)
        Row(
            modifier = Modifier.background(Color(0xFFF5F5F5))
        ) {
            Column {
                Gap(10)
                Image(
                    painter = painterResource(id = R.drawable.ic_baskkakao),
                    contentDescription = "",
                    modifier = Modifier
                        .padding(start = 37.dp, end = 36.dp)
                        .size(width = 302.dp, height = 269.dp)
                )
                Gap(15)
                Image(
                    painter = painterResource(id = R.drawable.ic_caution),
                    contentDescription = "",
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(width = 302.dp, height = 84.dp)
                )
            }
        }
        Spacer(Modifier.weight(1f))
        val defaultTrue = remember {
            mutableStateOf(true)
        }
        VariableButtonColorTextDefaultSizeButton(
            text = "카카오채널 바로가기", isVerified = defaultTrue
        ) {
            moveToBackScreen()
        }
    }
}

@Preview
@Composable
fun PreviewAnnouncementScreen() {
    AnnouncementScreen(true, {})
}