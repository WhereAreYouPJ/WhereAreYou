package com.whereareyounow.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.whereareyounow.data.globalvalue.ROUTE
import com.whereareyounow.data.globalvalue.ROUTE_MAIN
import com.whereareyounow.data.globalvalue.ROUTE_SIGN_IN_METHOD_SELECTION
import com.whereareyounow.data.globalvalue.ROUTE_SPLASH
import com.whereareyounow.ui.splash.SplashScreen

fun NavGraphBuilder.splashScreen(navController: NavController) = composable<ROUTE.Splash> {
    SplashScreen(
        moveToSignInMethodSelectionScreen = {
            navController.navigate(ROUTE.SignInMethodSelection) {
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