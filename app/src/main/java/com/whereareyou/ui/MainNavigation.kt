package com.whereareyou.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.whereareyou.data.Constants
import com.whereareyou.ui.home.HomeScreen
import com.whereareyou.ui.home.calendar.AddScheduleScreen
import com.whereareyou.ui.signin.ComposableA

@Composable
fun MainNavigation(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        modifier = Modifier
            .fillMaxSize(),
        navController = navController,
        startDestination = Constants.ROUTE_ADD_SCHEDULE
    ) {
        composable(
            route = Constants.ROUTE_A
        ) {
            ComposableA()
        }

        composable(
            route = Constants.ROUTE_MAIN_SIGNIN
        ) {

        }

        composable(
            route = Constants.ROUTE_MAIN_INTRO
        ) {

        }

        composable(
            route = Constants.ROUTE_MAIN_HOME
        ) {
            HomeScreen()
        }

        composable(
            route = Constants.ROUTE_ADD_SCHEDULE
        ) {
            AddScheduleScreen()
        }

        composable(
            route = Constants.ROUTE_ADD_FRIENDS
        ) {

        }

        composable(
            route = Constants.ROUTE_SEARCH_LOCATION
        ) {

        }
    }
}