package com.whereareyou.ui.home.schedule.detailschedule

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun DetailScheduleContent(
    moveToUserMapScreen: () -> Unit,
    viewModel: DetailScheduleViewModel = hiltViewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text(
            text = "제목 : ${viewModel.title.collectAsState().value}"
        )
        Text(
            text = "시작 시간 : ${viewModel.start.collectAsState().value}"
        )
        Text(
            text = "종료 시간 : ${viewModel.end.collectAsState().value}"
        )
        Text(
            text = "장소 : ${viewModel.place.collectAsState().value}"
        )
        Text(
            text = "메모: ${viewModel.memo.collectAsState().value}"
        )
        Box(
            modifier = Modifier
                .size(100.dp)
                .background(color = Color.Blue)
                .clickable {
                    moveToUserMapScreen()
                }
        )
    }
}