package com.whereareyounow.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.whereareyounow.data.globalvalue.ROUTE
import com.whereareyounow.ui.signup.policy.PolicyAgreeScreen

fun NavGraphBuilder.policyAgreeScreenRoute(navController: NavController) = composable<ROUTE.PolicyAgree.Main> {
    PolicyAgreeScreen(
        moveToBackScreen = { navController.popBackStack() },
        moveToSignUpScreen = { navController.navigate(ROUTE.SignUp) },
        moveToTermsOfServiceDetailsScreen = { navController.navigate(ROUTE.PolicyAgree.TermsOfService) },
        moveToPrivacyPolicyDetailsScreen = { navController.navigate(ROUTE.PolicyAgree.Privacy) },
        moveToLocationPolicyDetailScreen = { navController.navigate(ROUTE.PolicyAgree.Location) }
    )
}