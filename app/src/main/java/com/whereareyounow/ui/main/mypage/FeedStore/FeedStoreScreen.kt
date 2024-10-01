package com.whereareyounow.ui.main.mypage.FeedStore

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.whereareyounow.R
import com.whereareyounow.data.globalvalue.TOP_BAR_HEIGHT
import com.whereareyounow.ui.main.mypage.myinfo.OneTextOneIconTobBar

@Composable
fun FeedStoreScreen(
    moveToBackScreen: () -> Unit
) {
    FeedStoreScreen(
        isContent = true,
        moveToBackScreen = moveToBackScreen
    )
}

@Composable
private fun FeedStoreScreen(
    isContent: Boolean,
    moveToBackScreen: () -> Unit
) {

    Column(
        modifier = Modifier.fillMaxSize()
            .padding(top = TOP_BAR_HEIGHT.dp)
    ) {
        OneTextOneIconTobBar(
            title = "피드 보관함",
            firstIcon = R.drawable.ic_back,
            firstIconClicked = { moveToBackScreen() }
        )
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {

        }
    }

}


