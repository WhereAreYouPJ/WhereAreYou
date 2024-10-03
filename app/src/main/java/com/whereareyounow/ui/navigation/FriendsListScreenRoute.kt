package com.whereareyounow.ui.navigation

import android.annotation.SuppressLint
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.whereareyounow.data.globalvalue.ROUTE
import com.whereareyounow.ui.main.schedule.scheduleedit.FriendsListScreen
import com.whereareyounow.ui.main.schedule.scheduleedit.ScheduleEditViewModel

@SuppressLint("RestrictedApi")
fun NavGraphBuilder.friendsListScreenRoute(navController: NavController) = composable<ROUTE.AddFriend> {
    val data: ROUTE.AddFriend = it.toRoute()
    val editScheduleEntry = remember(it) {
        navController.currentBackStack.value.first { it.destination.route?.contains("ROUTE.AddSchedule") == true || it.destination.route?.contains("ROUTE.ScheduleModification") == true }
    }
    val scheduleEditViewModel: ScheduleEditViewModel = hiltViewModel(editScheduleEntry)

    FriendsListScreen(
        moveToBackScreen = { navController.popBackStack() },
        scheduleEditViewModel = scheduleEditViewModel
    )
}