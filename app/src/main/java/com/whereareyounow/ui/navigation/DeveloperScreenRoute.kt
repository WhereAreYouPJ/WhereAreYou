package com.whereareyounow.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.whereareyounow.data.globalvalue.ROUTE
import com.whereareyounow.ui.developer.DeveloperScreen

fun NavGraphBuilder.developerScreenRoute(navController: NavController) = composable<ROUTE.Developer> {
    DeveloperScreen(

    )
}