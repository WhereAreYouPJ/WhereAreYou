package com.onmyway.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.onmyway.data.globalvalue.ROUTE
import com.onmyway.ui.signup.PrivacyPolicyDetailsScreen

fun NavGraphBuilder.privacyPolicyDetailsScreenRoute(navController: NavController) = composable<ROUTE.PolicyAgree.Privacy> {
    PrivacyPolicyDetailsScreen(
        moveToPolicyAgreeScreen = { navController.popBackStack() }
    )
}