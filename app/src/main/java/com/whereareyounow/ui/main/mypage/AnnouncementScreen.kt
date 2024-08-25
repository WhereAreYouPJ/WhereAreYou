package com.whereareyounow.ui.main.mypage

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun AnnouncementScreen(

) {
    AnnouncementScreen(
        true
    )
}

@Composable
private fun AnnouncementScreen(
    isContent: Boolean
) {
    val myPageViewModel : MyPageViewModel = hiltViewModel()

    Column(
        modifier = Modifier.fillMaxSize()
    ) {

    }
}