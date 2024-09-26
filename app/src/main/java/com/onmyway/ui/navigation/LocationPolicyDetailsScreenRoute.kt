package com.onmyway.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.onmyway.data.globalvalue.ROUTE
import com.onmyway.ui.signup.LocationPolicyDetailsScreen

fun NavGraphBuilder.locationPolicyDetailsScreenRoute(navController: NavController) = composable<ROUTE.PolicyAgree.Location> {
    LocationPolicyDetailsScreen()
}