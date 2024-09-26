package com.onmyway.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.onmyway.data.globalvalue.ROUTE
import com.onmyway.ui.findaccount.findid.FindAccountEmailVerificationScreen

fun NavGraphBuilder.findAccountEmailVerificationScreenRoute(navController: NavController) = composable<ROUTE.FindAccountEmailVerification> {
    FindAccountEmailVerificationScreen(
        moveToBackScreen = { navController.popBackStack() }
    )
}