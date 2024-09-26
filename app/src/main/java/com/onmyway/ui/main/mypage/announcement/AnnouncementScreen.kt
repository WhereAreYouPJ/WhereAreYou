package com.onmyway.ui.main.mypage.announcement

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.onmyway.R
import com.onmyway.data.globalvalue.TOP_BAR_HEIGHT
import com.onmyway.ui.main.friend.GrayLine
import com.onmyway.ui.main.mypage.MyPageViewModel
import com.onmyway.ui.main.mypage.byebye.Gap
import com.onmyway.ui.main.mypage.myinfo.OneTextOneIconTobBar
import com.onmyway.ui.theme.medium12pt
import com.onmyway.ui.theme.medium16pt
import com.onmyway.util.clickableNoEffect
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun AnnouncementScreen(
    moveToBackScreen: () -> Unit,
    moveToAdminImageScreen : () -> Unit
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
    moveToAdminImageScreen : () -> Unit

) {
    val myPageViewModel : MyPageViewModel = hiltViewModel()
    val currentDate : String = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))

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
            Gap(12)
            Row(
                modifier = Modifier.fillMaxWidth().clickableNoEffect {
                    moveToAdminImageScreen()
                },
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = "온마이웨이가 전하는 온.마.웨 새출발 사용가이드",
                    color = Color(0xFF222222),
                    style = medium16pt,
                    modifier = Modifier.padding(start = 16.dp , top = 2.dp)
                )

            }
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {

                Text(
                    text = currentDate,
                    color = Color(0xFF999999),
                    style = medium12pt,
                    modifier = Modifier.padding(start = 16.dp , top = 2.dp)
                )

            }

            Gap(13)

            GrayLine()

        }

        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Gap(12)
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = "온마이웨이가 전하는 온.마.웨 새출발 사용가이드",
                    color = Color(0xFF222222),
                    style = medium16pt,
                    modifier = Modifier.padding(start = 16.dp , top = 2.dp)
                )

            }
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {

                Text(
                    text = currentDate,
                    color = Color(0xFF999999),
                    style = medium12pt,
                    modifier = Modifier.padding(start = 16.dp , top = 2.dp)
                )

            }

            Gap(13)

            GrayLine()

        }

        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Gap(12)
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = "온마이웨이가 전하는 온.마.웨 새출발 사용가이드",
                    color = Color(0xFF222222),
                    style = medium16pt,
                    modifier = Modifier.padding(start = 16.dp , top = 2.dp)
                )

            }
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {

                Text(
                    text = currentDate,
                    color = Color(0xFF999999),
                    style = medium12pt,
                    modifier = Modifier.padding(start = 16.dp , top = 2.dp)
                )

            }

            Gap(13)

            GrayLine()

        }

        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Gap(12)
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = "온마이웨이가 전하는 온.마.웨 새출발 사용가이드",
                    color = Color(0xFF222222),
                    style = medium16pt,
                    modifier = Modifier.padding(start = 16.dp , top = 2.dp)
                )

            }
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {

                Text(
                    text = currentDate,
                    color = Color(0xFF999999),
                    style = medium12pt,
                    modifier = Modifier.padding(start = 16.dp , top = 2.dp)
                )

            }

            Gap(13)

            GrayLine()

        }



    }
}

//@Composable
//fun ScrollableImage() {
//    val scrollState = rememberScrollState()
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .verticalScroll(scrollState)
//    ) {
//        Image(
//            painter = painterResource(id = R.drawable.ic_realangrygonee),
//            contentDescription = "",
//            contentScale = ContentScale.FillWidth,
//            modifier = Modifier
//                .fillMaxWidth()
//                .wrapContentHeight()
//        )
//    }
//}

@Preview
@Composable
fun PreviewAnnouncementScreen() {
    AnnouncementScreen(
        isContent = true,
        moveToBackScreen = {},
        moveToAdminImageScreen = {}
    )
}