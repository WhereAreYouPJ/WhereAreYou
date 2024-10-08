package com.whereareyounow.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.whereareyounow.data.globalvalue.ROUTE
import com.whereareyounow.ui.signup.policy.PrivacyPolicyDetailsScreen

fun NavGraphBuilder.privacyPolicyDetailsScreenRoute(navController: NavController) = composable<ROUTE.PolicyAgree.Privacy> {
    PrivacyPolicyDetailsScreen(
        moveToPolicyAgreeScreen = { navController.popBackStack() }
    )
}