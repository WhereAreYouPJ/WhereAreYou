package com.whereareyounow.ui.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.whereareyounow.data.globalvalue.ROUTE
import com.whereareyounow.ui.main.schedule.scheduleedit.ScheduleEditViewModel

fun NavGraphBuilder.scheduleModificationScreenRoute(navController: NavController) = composable<ROUTE.ScheduleModification> {

    val data: ROUTE.ScheduleModification = it.toRoute()
    val viewModel: ScheduleEditViewModel = hiltViewModel()

//    ScheduleEditScreen(
//        uiState = viewModel.uiState.collectAsStateWithLifecycle().value,
//        sideEffectFlow = viewModel.sideEffectFlow,
//        updateScheduleName = viewModel::updateScheduleName,
//        updateScheduleDate = viewModel::updateStartDate,
//        updateScheduleTime = viewModel::updateStartTime,
//        updateMemo = viewModel::updateMemo,
//        onComplete = {},
//        moveToSearchLocationScreen = { /*TODO*/ },
//        moveToFriendsListScreen = { /*TODO*/ },
//        moveToBackScreen = { navController.popBackStack() }
//    )
}