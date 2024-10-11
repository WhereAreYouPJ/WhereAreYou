package com.whereareyounow.ui.main.mypage.announcement

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.whereareyounow.R
import com.whereareyounow.data.globalvalue.TOP_BAR_HEIGHT
import com.whereareyounow.ui.component.tobbar.OneTextOneIconTobBar
import com.whereareyounow.ui.main.friend.GrayLine
import com.whereareyounow.ui.main.mypage.MyPageViewModel
import com.whereareyounow.ui.main.mypage.withdrawl.Gap
import com.whereareyounow.ui.theme.medium12pt
import com.whereareyounow.ui.theme.medium16pt
import com.whereareyounow.util.clickableNoEffect
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun AnnouncementScreen(
    moveToBackScreen: () -> Unit,
    moveToAdminImageScreen: () -> Unit
) {
    AnnouncementScreen(
        isContent = true,
        moveToBackScreen = moveToBackScreen,
        moveToAdminImageScreen = moveToAdminImageScreen
    )
}

@Composable
private fun AnnouncementScreen(
    isContent: Boolean,
    moveToBackScreen: () -> Unit,
    moveToAdminImageScreen: () -> Unit
) {
    val myPageViewModel: MyPageViewModel = hiltViewModel()
    val currentDate: String = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = TOP_BAR_HEIGHT.dp)
    ) {
        OneTextOneIconTobBar(
            title = "공지사항",
            firstIcon = R.drawable.ic_back,
            firstIconClicked = { moveToBackScreen() }
        )
        Gap(20)
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            AnnouncementContent(
                title = "온마이웨이가 전하는 온.마.웨 새출발 사용가이드",
                currentDate = currentDate,
                moveToAnotherScreen = moveToAdminImageScreen
            )
            AnnouncementContent(
                title = "온마이웨이 리뉴얼 관련 안내 전달드립니다.",
                currentDate = currentDate,
                moveToAnotherScreen = {  }
            )
            AnnouncementContent(
                title = "온마이웨이 리뉴얼 관련 안내 전달드립니다.",
                currentDate = currentDate,
                moveToAnotherScreen = {  }
            )
            AnnouncementContent(
                title = "온마이웨이 리뉴얼 관련 안내 전달드립니다.",
                currentDate = currentDate,
                moveToAnotherScreen = {  }
            )
        }
    }
}

@Composable
fun AnnouncementContent(
    title: String,
    currentDate: String,
    moveToAnotherScreen: () -> Unit,
) {
    Gap(14)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickableNoEffect {
                moveToAnotherScreen()
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            color = Color(0xFF222222),
            style = medium16pt,
            modifier = Modifier.padding(start = 16.dp, top = 2.dp)
        )
    }
    Gap(2)
    Text(
        text = currentDate,
        color = Color(0xFF999999),
        style = medium12pt,
        modifier = Modifier.padding(start = 16.dp, top = 2.dp)
    )
    Gap(13)
    GrayLine()
}

@Preview
@Composable
fun PreviewAnnouncementScreen() {
    AnnouncementScreen(
        isContent = true,
        moveToBackScreen = {},
        moveToAdminImageScreen = {}
    )
}