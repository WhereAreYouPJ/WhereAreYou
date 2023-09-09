package com.whereareyou.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.whereareyou.constants.Constants
import com.whereareyou.ui.signin.ComposableA

@Composable
fun MainNavigation(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        modifier = Modifier,
        navController = navController,
        startDestination = ""
    ) {
        composable(
            route = Constants.ROUTE_A
        ) {
            ComposableA()
        }

        composable(
            route = Constants.ROUTE_B
        ) {

        }

        composable(
            route = Constants.ROUTE_C
        ) {

        }
    }
}