package com.whereareyounow.ui.home.schedule.modifyschedule

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.whereareyounow.data.detailschedule.DetailScheduleScreenUIState
import com.whereareyounow.ui.home.schedule.scheduleedit.ScheduleEditScreen
import com.whereareyounow.ui.home.schedule.scheduleedit.ScheduleEditViewModel

@Composable
fun ModifyScheduleScreen(
    scheduleId: String,
    initialDestinationLatitude: Double,
    initialDestinationLongitude: Double,
    initialScheduleDetails: DetailScheduleScreenUIState,
    moveToFriendsListScreen: (List<String>) -> Unit,
    moveToSearchLocationScreen: () -> Unit,
    moveToBackScreen: () -> Unit,
    viewModel: ScheduleEditViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        if (!viewModel.isInitialized) {
            viewModel.updateScheduleId(scheduleId)
            viewModel.updateScheduleDetails(initialScheduleDetails)
            viewModel.updateDestinationLocation(
                lat = initialDestinationLatitude,
                lng = initialDestinationLongitude
            )
        }
    }
    val scheduleEditScreenUIState = viewModel.scheduleEditScreenUIState.collectAsState().value
    val scheduleEditScreenSideEffectFlow = viewModel.scheduleEditScreenSideEffectFlow
    ScheduleEditScreen(
        scheduleEditScreenUIState = scheduleEditScreenUIState,
        scheduleEditScreenSideEffectFlow = scheduleEditScreenSideEffectFlow,
        updateScheduleName = viewModel::updateScheduleName,
        updateScheduleDate = viewModel::updateScheduleDate,
        updateScheduleTime = viewModel::updateScheduleTime,
        updateMemo = viewModel::updateMemo,
        onComplete = viewModel::modifySchedule,
        moveToBackScreen = moveToBackScreen,
        moveToSearchLocationScreen = moveToSearchLocationScreen,
        moveToFriendsListScreen = {
            moveToFriendsListScreen(scheduleEditScreenUIState.selectedFriendsList.map { it.memberId })
        }
    )
}