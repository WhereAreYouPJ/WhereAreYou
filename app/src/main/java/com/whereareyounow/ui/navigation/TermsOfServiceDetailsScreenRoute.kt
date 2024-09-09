package com.whereareyounow.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.whereareyounow.data.globalvalue.ROUTE
import com.whereareyounow.ui.signup.TermsOfServiceDetailsScreen

fun NavGraphBuilder.termsOfServiceDetailsScreenRoute(navController: NavController) = composable<ROUTE.PolicyAgree.TermsOfService> {
    TermsOfServiceDetailsScreen(
        moveToPolicyAgreeScreen = { navController.popBackStack() }
    )
}