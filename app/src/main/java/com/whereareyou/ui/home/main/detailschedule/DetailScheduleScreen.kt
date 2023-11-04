package com.whereareyou.ui.home.main.detailschedule

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun DetailScheduleScreen(
    scheduleId: String?,
    viewModel: DetailScheduleViewModel = hiltViewModel()
) {
    LaunchedEffect(true) { viewModel.updateScheduleId(scheduleId) }

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

    }
}