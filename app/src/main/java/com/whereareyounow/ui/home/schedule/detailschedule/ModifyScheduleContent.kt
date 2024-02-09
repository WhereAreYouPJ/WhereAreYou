package com.whereareyounow.ui.home.schedule.detailschedule

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.whereareyounow.data.FriendProvider
import com.whereareyounow.domain.entity.schedule.Friend
import com.whereareyounow.ui.home.schedule.editschedule.FriendsListScreen
import com.whereareyounow.ui.home.schedule.editschedule.FriendsListScreenViewModel
import com.whereareyounow.ui.home.schedule.editschedule.MapScreen
import com.whereareyounow.ui.home.schedule.editschedule.NewLocationScreen
import com.whereareyounow.ui.home.schedule.editschedule.ScheduleEditorViewModel

@Composable
fun ModifyScheduleContent(
    scheduleId: String,
    moveToBackScreen: () -> Unit,
    detailScheduleViewModel: DetailScheduleViewModel = hiltViewModel(),
    scheduleEditorViewModel: ScheduleEditorViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        scheduleEditorViewModel.updateScheduleId(scheduleId)
        scheduleEditorViewModel.updateSelectedFriendsList(
            detailScheduleViewModel.memberInfos.map {
                Friend(
                    number = 0,
                    memberId = it.memberId,
                    name = it.name,
                    userId = it.userId,
                    profileImgUrl = null,
                    isPinned = false
                )
            }.filter {
                it.memberId != detailScheduleViewModel.creatorId.value
            }
        )
        scheduleEditorViewModel.updateScheduleName(detailScheduleViewModel.scheduleName.value)
        scheduleEditorViewModel.updateScheduleDate("${detailScheduleViewModel.scheduleYear.value}-${detailScheduleViewModel.scheduleMonth.value}-${detailScheduleViewModel.scheduleDate.value}")
        scheduleEditorViewModel.updateScheduleTime("${detailScheduleViewModel.scheduleHour.value}:${detailScheduleViewModel.scheduleMinute.value}")
        scheduleEditorViewModel.updateDestinationInformation(
            name = detailScheduleViewModel.destinationName.value,
            address = detailScheduleViewModel.destinationAddress.value,
            lat = detailScheduleViewModel.destinationLatitude.value,
            lng = detailScheduleViewModel.destinationLongitude.value
        )
        scheduleEditorViewModel.updateMemo(detailScheduleViewModel.memo.value)
    }
    when (scheduleEditorViewModel.screenState.collectAsState().value) {
        ScheduleEditorViewModel.ScreenState.EditSchedule -> {
            ModifyScheduleScreen(moveToBackScreen)
        }
        ScheduleEditorViewModel.ScreenState.AddFriends -> {
            FriendsListScreen(
                initialFriendIdsList = scheduleEditorViewModel.selectedFriendsList.map { it.memberId },
                updateFriendsList = { scheduleEditorViewModel.updateSelectedFriendsList(it) },
                moveToNewScheduleScreen = { scheduleEditorViewModel.updateScreenState(ScheduleEditorViewModel.ScreenState.EditSchedule) }
            )
        }
        ScheduleEditorViewModel.ScreenState.SearchLocation -> {
            NewLocationScreen(
                updateDestinationInformation = scheduleEditorViewModel::updateDestinationInformation,
                clearDestinationInformation = scheduleEditorViewModel::clearDestinationInformation,
                moveToNewScheduleScreen = { scheduleEditorViewModel.updateScreenState(ScheduleEditorViewModel.ScreenState.EditSchedule) },
                moveToMapScreen = { lat, lng ->
                    scheduleEditorViewModel.updateDestinationInformation("", "", lat, lng)
                    scheduleEditorViewModel.updateScreenState(ScheduleEditorViewModel.ScreenState.Map)
                }
            )
        }
        ScheduleEditorViewModel.ScreenState.Map -> {
            val latitude = scheduleEditorViewModel.destinationLatitude.collectAsState().value
            val longitude = scheduleEditorViewModel.destinationLongitude.collectAsState().value
            MapScreen(
                moveToSearchLocationScreen = {  scheduleEditorViewModel.updateScreenState(ScheduleEditorViewModel.ScreenState.SearchLocation) },
                latitude = latitude,
                longitude = longitude
            )
        }
    }
}