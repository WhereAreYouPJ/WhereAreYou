package com.whereareyounow.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.whereareyounow.data.globalvalue.ROUTE
import com.whereareyounow.data.globalvalue.ROUTE_SPLASH
import com.whereareyounow.ui.splash.SplashScreen

fun NavGraphBuilder.splashScreenRoute(navController: NavController) = composable<ROUTE.Splash> {
    SplashScreen(
        moveToSignInMethodSelectionScreen = {
            navController.navigate(ROUTE.SignInMethodSelection) {
                popUpTo<ROUTE.Splash> { inclusive = true }
            }
        },
        moveToMainScreen = {
            navController.navigate(ROUTE.Main) {
                popUpTo<ROUTE.Splash> { inclusive = true }
            }
        },
    )
}