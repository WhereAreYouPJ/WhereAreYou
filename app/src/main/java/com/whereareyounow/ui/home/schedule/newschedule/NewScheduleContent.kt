package com.whereareyounow.ui.home.schedule.newschedule

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.whereareyounow.ui.home.schedule.editschedule.FriendsListScreen
import com.whereareyounow.ui.home.schedule.editschedule.MapScreen
import com.whereareyounow.ui.home.schedule.editschedule.NewLocationScreen
import com.whereareyounow.ui.home.schedule.editschedule.ScheduleEditorViewModel

@Composable
fun NewScheduleContent(
    moveToBackScreen: () -> Unit,
    viewModel: ScheduleEditorViewModel = hiltViewModel()
) {
    when (viewModel.screenState.collectAsState().value) {
        ScheduleEditorViewModel.ScreenState.EditSchedule -> {
            NewScheduleScreen(moveToBackScreen)
        }
        ScheduleEditorViewModel.ScreenState.AddFriends -> {
            FriendsListScreen(
                updateFriendsList = { viewModel.updateSelectedFriendsList(it) },
                moveToNewScheduleScreen = { viewModel.updateScreenState(ScheduleEditorViewModel.ScreenState.EditSchedule) }
            )
        }
        ScheduleEditorViewModel.ScreenState.SearchLocation -> {
            NewLocationScreen(
                updateDestinationInformation = viewModel::updateDestinationInformation,
                clearDestinationInformation = viewModel::clearDestinationInformation,
                moveToNewScheduleScreen = { viewModel.updateScreenState(ScheduleEditorViewModel.ScreenState.EditSchedule) },
                moveToMapScreen = { lat, lng ->
                    viewModel.updateDestinationInformation("", "", lat, lng)
                    viewModel.updateScreenState(ScheduleEditorViewModel.ScreenState.Map)
                }
            )
        }
        ScheduleEditorViewModel.ScreenState.Map -> {
            val latitude = viewModel.destinationLatitude.collectAsState().value
            val longitude = viewModel.destinationLongitude.collectAsState().value
            MapScreen(
                moveToSearchLocationScreen = {  viewModel.updateScreenState(ScheduleEditorViewModel.ScreenState.SearchLocation) },
                latitude = latitude,
                longitude = longitude
            )
        }
    }
}