package com.whereareyounow.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.whereareyounow.data.globalvalue.ROUTE
import com.whereareyounow.ui.findaccount.findid.FindAccountEmailVerificationScreen

fun NavGraphBuilder.findAccountEmailVerificationScreenRoute(navController: NavController) = composable<ROUTE.FindAccountEmailVerification> {
    FindAccountEmailVerificationScreen(
        moveToBackScreen = { navController.popBackStack() }
    )
}