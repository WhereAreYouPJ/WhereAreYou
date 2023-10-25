package com.whereareyou.ui.home.main.newschedule

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun NewScheduleContent(
    viewModel: NewScheduleViewModel = hiltViewModel()
) {
    when (viewModel.screenState.collectAsState().value) {
        NewScheduleViewModel.ScreenState.NewSchedule -> {
            NewScheduleScreen(
                toFriendsListScreen = { viewModel.updateScreenState(NewScheduleViewModel.ScreenState.AddFriends) },
                toSearchLocationScreen = { viewModel.updateScreenState(NewScheduleViewModel.ScreenState.SearchLocation) }
            )
        }
        NewScheduleViewModel.ScreenState.AddFriends -> {
            FriendsListScreen(
                updateFriendsList = { viewModel.updateFriendsList(it) },
                toNewScheduleScreen = { viewModel.updateScreenState(NewScheduleViewModel.ScreenState.NewSchedule) }
            )
        }
        NewScheduleViewModel.ScreenState.SearchLocation -> {
            NewLocationScreen(
                updateDestinationInformation = { name, address -> viewModel.updateDestinationInformation(name, address) },
                toNewScheduleScreen = { viewModel.updateScreenState(NewScheduleViewModel.ScreenState.NewSchedule) },
                toMapScreen = {  viewModel.updateScreenState(NewScheduleViewModel.ScreenState.Map) }
            )
        }
        NewScheduleViewModel.ScreenState.Map -> {
            MapScreen(
                toSearchLocationScreen = {  viewModel.updateScreenState(NewScheduleViewModel.ScreenState.SearchLocation) }
            )
        }
    }
}