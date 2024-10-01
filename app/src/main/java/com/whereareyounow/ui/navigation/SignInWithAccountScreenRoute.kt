package com.whereareyounow.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.whereareyounow.data.globalvalue.ROUTE
import com.whereareyounow.ui.signin.SignInWithAccountScreen

fun NavGraphBuilder.signInWithAccountScreenRoute(navController: NavController) = composable<ROUTE.SignInWithAccount> {
    SignInWithAccountScreen(
        moveToSignInMethodSelectionScreen = {
            navController.popBackStack(ROUTE.SignInMethodSelection, false)
        },
        moveToMainHomeScreen = {
            navController.navigate(ROUTE.Main) {
                popUpTo(navController.graph.startDestinationId) { inclusive = true }
            }
        },
        moveToFindAccountScreen = { navController.navigate(ROUTE.FindAccountEmailVerification) },
        moveToResetPasswordScreen = { navController.navigate(ROUTE.FindAccountEmailVerification) },
        moveToSignUpScreen = { navController.navigate(ROUTE.PolicyAgree.Main) }
    )
}