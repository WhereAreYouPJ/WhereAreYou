package com.whereareyou.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.Navigator
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.whereareyou.data.Constants
import com.whereareyou.ui.home.HomeScreen
import com.whereareyou.ui.home.main.detailschedule.DetailScheduleScreen
import com.whereareyou.ui.home.main.newschedule.NewScheduleContent
import com.whereareyou.ui.signin.SignUpScreen

@Composable
fun MainNavigation(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        modifier = Modifier
            .fillMaxSize(),
        navController = navController,
        startDestination = Constants.ROUTE_MAIN_HOME
    ) {
        composable(
            route = Constants.ROUTE_MAIN_SIGNUP
        ) {
            SignUpScreen()
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
            HomeScreen(
                moveToAddScheduleScreen = { navController.navigate(Constants.ROUTE_NEW_SCHEDULE) },
                toDetailScreen = { navController.navigate(Constants.ROUTE_DETAIL_SCHEDULE + "/$it") }
            )
        }

        composable(
            route = Constants.ROUTE_NEW_SCHEDULE
        ) {
            NewScheduleContent()
        }

        composable(
            route = Constants.ROUTE_DETAIL_SCHEDULE + "/{scheduleId}"
        ) {
            DetailScheduleScreen(it.arguments?.getString("scheduleId"))
        }
    }
}