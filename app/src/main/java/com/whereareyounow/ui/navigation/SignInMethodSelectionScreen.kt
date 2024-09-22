package com.whereareyounow.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.whereareyounow.data.globalvalue.ROUTE
import com.whereareyounow.data.globalvalue.ROUTE_FIND_ACCOUNT
import com.whereareyounow.ui.signin.SignInMethodSelectionScreen

fun NavGraphBuilder.signInMethodSelectionScreenRoute(navController: NavController) = composable<ROUTE.SignInMethodSelection> {
    SignInMethodSelectionScreen(
        moveToSignInWithAccountScreen = { navController.navigate(ROUTE.SignInWithAccount) },
        moveToSignUpScreen = { navController.navigate(ROUTE.PolicyAgree.Main) },
        moveToFindAccountScreen = { navController.navigate(ROUTE_FIND_ACCOUNT) }
    )
}