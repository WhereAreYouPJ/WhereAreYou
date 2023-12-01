package com.whereareyou.ui.home.schedule.detailschedule

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
    when (viewModel.screenState.collectAsState().value) {
        DetailScheduleViewModel.ScreenState.DetailSchedule -> {
            DetailScheduleContent()
        }
        DetailScheduleViewModel.ScreenState.UserMap -> {
            UserMapScreen()
        }
    }
}