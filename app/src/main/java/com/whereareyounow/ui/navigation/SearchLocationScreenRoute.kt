package com.whereareyounow.ui.navigation

import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.whereareyounow.data.globalvalue.ROUTE
import com.whereareyounow.ui.main.schedule.scheduleedit.SearchLocationScreen

fun NavGraphBuilder.searchLocationScreenRoute(navController: NavController) = composable<ROUTE.SearchLocation> {
    val parentEntry = remember(it) { navController.previousBackStackEntry!! }
    val data: ROUTE.SearchLocation = it.toRoute()
    SearchLocationScreen(
        moveToBackScreen = { navController.popBackStack() },
        moveToMapScreen = { name, address, lat, lng ->
            navController.navigate(ROUTE.LocationMap(name, address, lat.toString(), lng.toString())
            )
        },
        scheduleEditViewModel = hiltViewModel(parentEntry)
    )
}