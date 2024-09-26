package com.onmyway.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.onmyway.data.globalvalue.ROUTE
import com.onmyway.data.globalvalue.ROUTE_SPLASH
import com.onmyway.ui.splash.SplashScreen

fun NavGraphBuilder.splashScreenRoute(navController: NavController) = composable<ROUTE.Splash> {
    SplashScreen(
        moveToSignInMethodSelectionScreen = {
            navController.navigate(ROUTE.Main) {
                popUpTo(ROUTE_SPLASH) { inclusive = true }
            }
        },
        moveToMainScreen = {
            navController.navigate(ROUTE.Main) {
                popUpTo(ROUTE_SPLASH) { inclusive = true }
            }
        },
    )
}