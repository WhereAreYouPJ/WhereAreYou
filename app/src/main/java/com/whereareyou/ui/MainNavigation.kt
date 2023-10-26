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
import com.whereareyou.ui.home.Tablayout
import com.whereareyou.ui.home.main.detailschedule.DetailScheduleScreen
import com.whereareyou.ui.home.main.newschedule.NewScheduleContent
import com.whereareyou.ui.signin.AgreeScreen
import com.whereareyou.ui.signin.LoginScreen
import com.whereareyou.ui.signin.SignUpScreen
import com.whereareyou.ui.signin.SuccessScreen
import com.whereareyou.ui.signin.SuccessScreenPw
import test

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



        composable(
            route = Constants.ROUTE_MAIN_SIGNUP
        ) {
            SignUpScreen(navController = navController)
        }
        composable(route=Constants.ROUTE_MAIN_LOGIN){
            LoginScreen(navController = navController)
        }
        composable(route=Constants.ROUTE_MAIN_START){
            StartScreen(navController = navController)
        }


        composable(route=Constants.ROUTE_MAIN_FINDID){
            Tablayout(navController = navController,0)
        }
        composable(route=Constants.ROUTE_MAIN_FINDIDSUCCESS){
            Tablayout(navController = navController,2)
        }
        composable(route=Constants.ROUTE_MAIN_FINDPW){
            Tablayout(navController = navController,1)
        }
        composable(route=Constants.ROUTE_MAIN_FINDPWSUCCESS){
            Tablayout(navController = navController,3)
        }


        composable(route=Constants.ROUTE_MAIN_AGREE){
            AgreeScreen(navController = navController)
        }
        composable(route=Constants.ROUTE_MAIN_SUCCESS){
            SuccessScreen(navController = navController)
        }
        composable(route=Constants.ROUTE_MAIN_SUCCESSPW){
            SuccessScreenPw(navController = navController)
        }
        composable(route=Constants.ROUTE_TEST){
            test()
        }
        composable(
            route = Constants.ROUTE_MAIN_HOME
        ) {
            HomeScreen()
        }




    }
}