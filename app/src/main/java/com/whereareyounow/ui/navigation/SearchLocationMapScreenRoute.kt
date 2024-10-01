package com.whereareyounow.ui.navigation

import android.annotation.SuppressLint
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.whereareyounow.data.globalvalue.ROUTE
import com.whereareyounow.ui.main.schedule.scheduleedit.ScheduleEditViewModel
import com.whereareyounow.ui.main.schedule.scheduleedit.SearchLocationMapScreen

@SuppressLint("RestrictedApi")
fun NavGraphBuilder.searchLocationMapScreenRoute(navController: NavController) = composable<ROUTE.LocationMap> {
    val data: ROUTE.LocationMap = it.toRoute()
    val editScheduleEntry = remember(it) {
        navController
            .currentBackStack.value.first { it.destination.route?.contains("ROUTE.AddSchedule") == true }
    }
    val scheduleEditViewModel: ScheduleEditViewModel = hiltViewModel(editScheduleEntry)


    SearchLocationMapScreen(
        moveToBackScreen = { navController.popBackStack() },
        name = data.name,
        address = data.address,
        lat = data.lat.toDouble(),
        lng = data.lng.toDouble(),
        isBookmarked = false,
        toggleBookmark = {  },
        moveToScheduleEditScreen = { navController.popBackStack<ROUTE.AddSchedule>(false) },
        scheduleEditViewModel = scheduleEditViewModel
    )
}