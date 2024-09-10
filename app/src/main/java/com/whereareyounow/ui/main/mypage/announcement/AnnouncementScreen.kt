package com.whereareyounow.ui.main.mypage.announcement

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.whereareyounow.R
import com.whereareyounow.data.globalvalue.TOP_BAR_HEIGHT
import com.whereareyounow.ui.component.CustomTopBar
import com.whereareyounow.ui.main.mypage.MyPageViewModel

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
        ScrollableImage()
    }
}

@Composable
fun ScrollableImage() {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_realangrygonee),
            contentDescription = "",
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        )
    }
}