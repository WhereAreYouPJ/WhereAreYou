package com.whereareyounow.ui.main.mypage.ask
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.whereareyounow.R
import com.whereareyounow.data.globalvalue.TOP_BAR_HEIGHT
import com.whereareyounow.ui.component.CustomTopBar
import com.whereareyounow.ui.main.mypage.MyPageViewModel
import com.whereareyounow.ui.main.mypage.byebye.Gap
import com.whereareyounow.ui.main.mypage.byebye.WithdrawlButton
import com.whereareyounow.ui.theme.getColor
import com.whereareyounow.ui.theme.notoSanskr
import com.whereareyounow.util.clickableNoEffect

@Composable
fun AskScreen(
    moveToBackScreen: () -> Unit

) {
    AnnouncementScreen(
        true,
        moveToBackScreen
    )
}

@Composable
private fun AnnouncementScreen(
    isContent: Boolean,
    moveToBackScreen: () -> Unit

) {
    val myPageViewModel : MyPageViewModel = hiltViewModel()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = TOP_BAR_HEIGHT.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CustomTopBar(
            title = "1 : 1 이용문의",
            onBackButtonClicked = {
                moveToBackScreen()
            }
        )

        Gap(24)

        Image(
            painter = painterResource(id = R.drawable.ic_areyouask),
            contentDescription = "",
            modifier = Modifier
                .padding(start = 68.dp, end = 68.dp)
                .size(width = 239.dp, height = 202.dp)
        )

        Gap(34)

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

        Gap(14)

        val defaultTrue = remember {
            mutableStateOf(true)
        }

        WithdrawlButton(
            text = "카카오채널 바로가기",
            canMove = defaultTrue
        ) {
            moveToBackScreen()
        }


//        Image(
//            painter = painterResource(id = R.drawable.ic_gotokakao),
//            contentDescription = "",
//            modifier = Modifier.padding(start = 15.dp , end = 15.dp , bottom = 68.dp)
//        )


    }
}

@Preview
@Composable
fun PreviewAnnouncementScreen() {
    AnnouncementScreen(
        true,
        {}
    )
}