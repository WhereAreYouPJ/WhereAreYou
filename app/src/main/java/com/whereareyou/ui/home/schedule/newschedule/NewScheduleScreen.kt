package com.whereareyou.ui.home.schedule.newschedule

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun NewScheduleScreen(
    moveToCalendarScreen: () -> Unit,
    viewModel: NewScheduleViewModel = hiltViewModel()
) {
    when (viewModel.screenState.collectAsState().value) {
        NewScheduleViewModel.ScreenState.NewSchedule -> {
            NewScheduleContent(
                moveToCalendarScreen = moveToCalendarScreen,
                moveToFriendsListScreen = { viewModel.updateScreenState(NewScheduleViewModel.ScreenState.AddFriends) },
                moveToSearchLocationScreen = { viewModel.updateScreenState(NewScheduleViewModel.ScreenState.SearchLocation) }
            )
        }
        NewScheduleViewModel.ScreenState.AddFriends -> {
            FriendsListScreen(
                updateFriendsList = { viewModel.updateFriendsList(it) },
                moveToNewScheduleScreen = { viewModel.updateScreenState(NewScheduleViewModel.ScreenState.NewSchedule) }
            )
        }
        NewScheduleViewModel.ScreenState.SearchLocation -> {
            NewLocationScreen(
                updateDestinationInformation = { name, address -> viewModel.updateDestinationInformation(name, address) },
                moveToNewScheduleScreen = { viewModel.updateScreenState(NewScheduleViewModel.ScreenState.NewSchedule) },
                moveToMapScreen = {  viewModel.updateScreenState(NewScheduleViewModel.ScreenState.Map) }
            )
        }
        NewScheduleViewModel.ScreenState.Map -> {
            MapScreen(
                moveToSearchLocationScreen = {  viewModel.updateScreenState(NewScheduleViewModel.ScreenState.SearchLocation) }
            )
        }
    }
}