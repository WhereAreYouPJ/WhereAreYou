package com.whereareyounow.ui.home.schedule.detailschedule

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import com.whereareyounow.ui.home.schedule.editschedule.ScheduleEditorScreen
import com.whereareyounow.ui.home.schedule.editschedule.ScheduleEditorViewModel

@Composable
fun ModifyScheduleScreen(
    moveToBackScreen: () -> Unit,
    viewModel: ScheduleEditorViewModel = hiltViewModel()
) {
    val selectedFriendsList = viewModel.selectedFriendsList
    val scheduleName = viewModel.scheduleName.collectAsState().value
    val scheduleYear = viewModel.scheduleYear.collectAsState().value
    val scheduleMonth = viewModel.scheduleMonth.collectAsState().value
    val scheduleDate = viewModel.scheduleDate.collectAsState().value
    val scheduleHour = viewModel.scheduleHour.collectAsState().value
    val scheduleMinute = viewModel.scheduleMinute.collectAsState().value
    val destinationName = viewModel.destinationName.collectAsState().value
    val destinationAddress = viewModel.destinationAddress.collectAsState().value
    val memo = viewModel.memo.collectAsState().value
    val isDatePickerDialogShowing = remember { mutableStateOf(false) }
    val isTimePickerDialogShowing = remember { mutableStateOf(false) }
    ScheduleEditorScreen(
        selectedFriendsList = selectedFriendsList,
        scheduleName = scheduleName,
        updateScheduleName = viewModel::updateScheduleName,
        scheduleYear = scheduleYear,
        scheduleMonth = scheduleMonth,
        scheduleDate = scheduleDate,
        updateScheduleDate = viewModel::updateScheduleDate,
        scheduleHour = scheduleHour,
        scheduleMinute = scheduleMinute,
        updateScheduleTime = viewModel::updateScheduleTime,
        destinationName = destinationName,
        destinationAddress = destinationAddress,
        memo = memo,
        updateMemo = viewModel::updateMemo,
        onComplete = viewModel::modifySchedule,
        isDatePickerDialogShowing = isDatePickerDialogShowing,
        isTimePickerDialogShowing = isTimePickerDialogShowing,
        moveToBackScreen = moveToBackScreen,
        moveToFriendsListScreen = { viewModel.updateScreenState(ScheduleEditorViewModel.ScreenState.AddFriends) },
        moveToSearchLocationScreen = { viewModel.updateScreenState(ScheduleEditorViewModel.ScreenState.SearchLocation) }
    )
}