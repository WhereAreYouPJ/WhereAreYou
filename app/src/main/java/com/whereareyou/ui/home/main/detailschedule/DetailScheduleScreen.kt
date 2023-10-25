package com.whereareyou.ui.home.main.detailschedule

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun DetailScheduleScreen(
    scheduleId: String?,
    viewModel: DetailScheduleViewModel = hiltViewModel()
) {
    LaunchedEffect(true) { viewModel.updateScheduleId(scheduleId) }

    Column(

    ) {
        Text(
            text = "${scheduleId}" +
                    "\n${viewModel.title.collectAsState().value}" +
                    "\n${viewModel.start.collectAsState().value}" +
                    "\n${viewModel.end.collectAsState().value}"
        )

    }
}