package com.whereareyounow.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.whereareyounow.data.globalvalue.ROUTE
import com.whereareyounow.data.globalvalue.ROUTE_FIND_ID
import com.whereareyounow.data.globalvalue.ROUTE_MAIN
import com.whereareyounow.data.globalvalue.ROUTE_POLICY_AGREE
import com.whereareyounow.data.globalvalue.ROUTE_RESET_PASSWORD
import com.whereareyounow.data.globalvalue.ROUTE_SIGN_IN_METHOD_SELECTION
import com.whereareyounow.ui.signin.SignInWithAccountScreen

fun NavGraphBuilder.signInWithAccountScreenRoute(navController: NavController) = composable<ROUTE.SignInWithAccount> {
    SignInWithAccountScreen(
        moveToSignInMethodSelectionScreen = {
            navController.popBackStack(ROUTE_SIGN_IN_METHOD_SELECTION, false)
        },
        moveToMainHomeScreen = {
            navController.navigate(ROUTE_MAIN) {
                popUpTo(navController.graph.startDestinationId) { inclusive = true }
            }
        },
        moveToFindAccountScreen = { navController.navigate(ROUTE_FIND_ID) },
        moveToResetPasswordScreen = { navController.navigate(ROUTE_RESET_PASSWORD) },
        moveToSignUpScreen = { navController.navigate(ROUTE_POLICY_AGREE) }
    )
}