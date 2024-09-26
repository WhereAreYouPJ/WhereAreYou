package com.onmyway.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.onmyway.data.globalvalue.ROUTE
import com.onmyway.ui.main.schedule.detailschedule.DetailScheduleScreen

fun NavGraphBuilder.detailScheduleScreenRoute(navController: NavController) = composable<ROUTE.DetailSchedule> {

    val data: ROUTE.DetailSchedule = it.toRoute()

    DetailScheduleScreen(
        scheduleSeq = data.scheduleSeq,
        moveToBackScreen = { navController.popBackStack() })
}