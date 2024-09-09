package com.whereareyounow.ui.main.mypage

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.whereareyounow.data.globalvalue.TOP_BAR_HEIGHT
import com.whereareyounow.ui.component.CustomTopBar
import com.whereareyounow.ui.component.tobbar.OneTextTwoIconTobBar

@Composable
fun AnnouncementScreen(
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
        modifier = Modifier.fillMaxSize().padding(top = TOP_BAR_HEIGHT.dp)
    ) {
        CustomTopBar(
            title = "공지사항",
            onBackButtonClicked = {
                moveToBackScreen()
            }
        )
    }
}