package com.whereareyou.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.Navigator
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.whereareyou.GlobalViewModel
import com.whereareyou.data.Constants
import com.whereareyou.ui.home.HomeScreen
import com.whereareyou.ui.home.main.detailschedule.DetailScheduleScreen
import com.whereareyou.ui.home.main.newschedule.NewScheduleContent
import com.whereareyou.ui.home.main.newschedule.NewScheduleScreen
import com.whereareyou.ui.signin.SignUpScreen
import com.whereareyou.ui.splash.SplashScreen

@Composable
fun MainNavigation(
    navController: NavHostController = rememberNavController(),
    viewModel: GlobalViewModel = hiltViewModel()
) {
    NavHost(
        modifier = Modifier
            .fillMaxSize(),
        navController = navController,
        startDestination = Constants.ROUTE_MAIN_SPLASH
    ) {
        composable(
            route = Constants.ROUTE_MAIN_SPLASH
        ) {
            SplashScreen(
                checkIsSignedIn = { viewModel.checkIsSignedIn() },
                moveToSignInScreen = {
                },
                moveToMainScreen = {
                    navController.navigate(Constants.ROUTE_MAIN_HOME)
                }
            )
        }

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
            NewScheduleScreen(
                moveToCalendarScreen = { navController.navigate(Constants.ROUTE_MAIN_HOME) },
            )
        }

        composable(
            route = Constants.ROUTE_DETAIL_SCHEDULE + "/{scheduleId}"
        ) {
            DetailScheduleScreen(it.arguments?.getString("scheduleId"))
        }
    }
}