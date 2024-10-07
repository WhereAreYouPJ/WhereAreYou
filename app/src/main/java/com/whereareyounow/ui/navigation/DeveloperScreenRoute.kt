package com.whereareyounow.ui.navigation

import androidx.compose.material3.Text
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.whereareyounow.data.globalvalue.ROUTE
import com.whereareyounow.ui.developer.DeveloperScreen

fun NavGraphBuilder.developerScreenRoute(navController: NavController) = navigation<ROUTE.Developer>(
    ROUTE.Developer.Screen1
) {
    composable<ROUTE.Developer.Screen1> {
        DeveloperScreen(

        )
    }

    composable<ROUTE.Developer.Screen2> {
        Text(
            text = "screen2"
        )
    }

    composable<ROUTE.Developer.Screen3> {
        Text(
            text = "screen3"
        )
    }

    composable<ROUTE.Developer.Screen4> {
        Text(
            text = "screen4"
        )
    }
}