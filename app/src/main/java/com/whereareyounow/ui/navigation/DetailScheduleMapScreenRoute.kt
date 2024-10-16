package com.whereareyounow.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.whereareyounow.data.globalvalue.ROUTE
import com.whereareyounow.ui.main.schedule.detailschedule.DetailScheduleMapScreen

fun NavGraphBuilder.detailScheduleMapScreenRoute(navController: NavController) = composable<ROUTE.DetailScheduleMap> {
    val data: ROUTE.DetailScheduleMap = it.toRoute()

    DetailScheduleMapScreen(
        scheduleSeq = data.scheduleSeq
    )
}