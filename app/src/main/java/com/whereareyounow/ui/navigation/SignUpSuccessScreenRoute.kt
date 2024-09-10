package com.whereareyounow.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.whereareyounow.data.globalvalue.ROUTE
import com.whereareyounow.ui.signup.SignUpSuccessScreen

fun NavGraphBuilder.signUpSuccessScreenRoute(navController: NavController) = composable<ROUTE.SignUpSuccess> {
    SignUpSuccessScreen(
        moveToBackScreen = { navController.popBackStack() }
    )
}