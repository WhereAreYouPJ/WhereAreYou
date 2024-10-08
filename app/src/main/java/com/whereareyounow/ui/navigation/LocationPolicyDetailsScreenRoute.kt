package com.whereareyounow.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.whereareyounow.data.globalvalue.ROUTE
import com.whereareyounow.ui.signup.policy.LocationPolicyDetailsScreen

fun NavGraphBuilder.locationPolicyDetailsScreenRoute(navController: NavController) = composable<ROUTE.PolicyAgree.Location> {
    LocationPolicyDetailsScreen()
}