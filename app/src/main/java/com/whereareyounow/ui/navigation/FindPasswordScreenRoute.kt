package com.whereareyounow.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.whereareyounow.data.globalvalue.ROUTE
import com.whereareyounow.ui.findaccount.findpw.FindPasswordScreen

fun NavGraphBuilder.findPasswordScreenRoute(navController: NavController) = composable<ROUTE.FindPassword> {
    FindPasswordScreen(
        moveToSignInMethodSelectionScreen = {
            navController.popBackStack<ROUTE.SignInMethodSelection>(false)
        },
        moveToPasswordResettingScreen = {
            navController.navigate(ROUTE.PasswordReset(it)) {
                popUpTo<ROUTE.SignInMethodSelection> { inclusive = false }
            }
        }
    )
}