package com.whereareyounow.ui.main.mypage
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.whereareyounow.R
import com.whereareyounow.data.globalvalue.TOP_BAR_HEIGHT
import com.whereareyounow.ui.component.CustomTopBar
import com.whereareyounow.ui.component.tobbar.OneTextTwoIconTobBar

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
        modifier = Modifier.fillMaxSize().padding(top = TOP_BAR_HEIGHT.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CustomTopBar(
            title = "1 : 1 이용문의",
            onBackButtonClicked = {
                moveToBackScreen()
            }
        )

        Image(
            painter = painterResource(id = R.drawable.ic_areyouask),
            contentDescription = "",
            modifier = Modifier.padding(top = 24.dp , start = 68.dp , end = 68.dp).size(width = 239.dp , height = 202.dp)
        )

        Image(
            painter = painterResource(id = R.drawable.ic_baskkakao),
            contentDescription = "",
            modifier = Modifier.padding(top = 34.dp , start = 37.dp , end = 36.dp).size(width = 302.dp , height = 269.dp)
        )

        Image(
            painter = painterResource(id = R.drawable.ic_caution),
            contentDescription = "",
            modifier = Modifier.padding(top = 15.dp).fillMaxWidth().size(width = 302.dp , height = 84.dp)
        )

        Spacer(Modifier.weight(1f))

        Image(
            painter = painterResource(id = R.drawable.ic_gotokakao),
            contentDescription = "",
            modifier = Modifier.padding(top = 22.dp , start = 15.dp , end = 15.dp , bottom = 24.dp)
        )


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