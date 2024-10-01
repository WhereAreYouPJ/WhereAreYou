package com.whereareyounow.ui.main.schedule.newschedule

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.whereareyounow.ui.main.schedule.scheduleedit.ScheduleEditViewModel

@Composable
fun NewScheduleScreen(
    initialYear: Int,
    initialMonth: Int,
    initialDate: Int,
    moveToSearchLocationScreen: () -> Unit,
    moveToFriendsListScreen: () -> Unit,
    moveToBackScreen: () -> Unit,
    viewModel: ScheduleEditViewModel = hiltViewModel(),
) {
    LaunchedEffect(Unit) {
        if (!viewModel.isInitialized) {
            viewModel.isInitialized = true
            viewModel.updateStartDate(
                year = initialYear,
                month = initialMonth,
                date = initialDate
            )
        }
    }
    val scheduleEditScreenUIState = viewModel.uiState.collectAsState().value
    val scheduleEditScreenSideEffectFlow = viewModel.sideEffectFlow
//    ScheduleEditScreen(
//        uiState = scheduleEditScreenUIState,
//        sideEffectFlow = scheduleEditScreenSideEffectFlow,
//        updateScheduleName = viewModel::updateScheduleName,
//        updateStartDate = viewModel::updateStartDate,
//        updateStartTime = viewModel::updateStartTime,
//        updateEndDate = viewModel::updateEndDate,
//        updateEndTime = viewModel::updateEndTime,
//        updateMemo = viewModel::updateMemo,
//        moveToSearchLocationScreen = moveToSearchLocationScreen,
//        moveToFriendsListScreen = moveToFriendsListScreen,
//        moveToBackScreen = moveToBackScreen
//    )
}