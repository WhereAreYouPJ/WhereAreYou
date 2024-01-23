package com.whereareyounow.ui.home.schedule.detailschedule

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.whereareyounow.ui.home.schedule.editschedule.FriendsListScreen

@Composable
fun DetailScheduleContent(
    scheduleId: String,
    moveToBackScreen: () -> Unit,
    viewModel: DetailScheduleViewModel = hiltViewModel()
) {
    when (viewModel.screenState.collectAsState().value) {
        DetailScheduleViewModel.ScreenState.DetailSchedule -> {
            DetailScheduleScreen(
                scheduleId = scheduleId,
                moveToBackScreen = moveToBackScreen,
                moveToModifyScheduleScreen = { viewModel.updateScreenState(DetailScheduleViewModel.ScreenState.ModifySchedule) },
            )
        }
        DetailScheduleViewModel.ScreenState.UserMap -> {
            DetailScheduleMapScreen()
        }
        DetailScheduleViewModel.ScreenState.ModifySchedule -> {
            ModifyScheduleContent(
                scheduleId = viewModel.scheduleId.collectAsState().value,
                moveToBackScreen = { viewModel.updateScreenState(DetailScheduleViewModel.ScreenState.DetailSchedule) }
            )
        }
    }
}