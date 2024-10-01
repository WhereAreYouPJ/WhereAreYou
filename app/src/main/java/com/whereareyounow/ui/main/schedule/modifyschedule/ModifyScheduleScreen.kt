package com.whereareyounow.ui.main.schedule.modifyschedule

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.whereareyounow.data.detailschedule.DetailScheduleScreenUIState
import com.whereareyounow.ui.main.schedule.scheduleedit.ScheduleEditViewModel

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
    val scheduleEditScreenUIState = viewModel.uiState.collectAsState().value
    val scheduleEditScreenSideEffectFlow = viewModel.sideEffectFlow
//    ScheduleEditScreen(
//        uiState = scheduleEditScreenUIState,
//        sideEffectFlow = scheduleEditScreenSideEffectFlow,
//        updateScheduleName = viewModel::updateScheduleName,
//        updateScheduleDate = viewModel::updateScheduleDate,
//        updateScheduleTime = viewModel::updateScheduleTime,
//        updateMemo = viewModel::updateMemo,
//        onComplete = viewModel::modifySchedule,
//        moveToBackScreen = moveToBackScreen,
//        moveToSearchLocationScreen = moveToSearchLocationScreen,
//        moveToFriendsListScreen = {
//            moveToFriendsListScreen(scheduleEditScreenUIState.selectedFriendsList.map { it.memberId })
//        }
//    )
}