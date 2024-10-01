package com.whereareyounow.ui.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.whereareyounow.data.globalvalue.ROUTE
import com.whereareyounow.data.globalvalue.ROUTE_SEARCH_LOCATION
import com.whereareyounow.ui.main.schedule.scheduleedit.ScheduleEditScreen
import com.whereareyounow.ui.main.schedule.scheduleedit.ScheduleEditViewModel

fun NavGraphBuilder.newScheduleScreenRoute(navController: NavController) = composable<ROUTE.AddSchedule> {

    val data: ROUTE.AddSchedule = it.toRoute()
    val viewModel: ScheduleEditViewModel = hiltViewModel()

    LaunchedEffect(Unit) {
        viewModel.updateStartDate(data.year, data.month, data.date)
        viewModel.updateEndDate(data.year, data.month, data.date)
    }

    ScheduleEditScreen(
        uiState = viewModel.uiState.collectAsStateWithLifecycle().value,
        sideEffectFlow = viewModel.sideEffectFlow,
        updateScheduleName = viewModel::updateScheduleName,
        toggleAllDay = viewModel::toggleAllDay,
        updateStartDate = viewModel::updateStartDate,
        updateStartTime = viewModel::updateStartTime,
        updateEndDate = viewModel::updateEndDate,
        updateEndTime = viewModel::updateEndTime,
        updateMemo = viewModel::updateMemo,
        updateColor = viewModel::updateScheduleColor,
        moveToSearchLocationScreen = { navController.navigate(ROUTE.SearchLocation(location = it)) },
        moveToFriendsListScreen = { navController.navigate(ROUTE.AddFriend(it)) },
        moveToBackScreen = { navController.popBackStack() },
        onDone = {
            viewModel.addNewSchedule(
                moveToBackScreen = { navController.popBackStack() }
            )
        }
    )
}