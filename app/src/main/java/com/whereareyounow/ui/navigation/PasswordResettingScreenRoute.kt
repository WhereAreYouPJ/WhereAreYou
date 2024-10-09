package com.whereareyounow.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.whereareyounow.data.globalvalue.ROUTE
import com.whereareyounow.ui.findaccount.findpw.PasswordResettingScreen

fun NavGraphBuilder.passwordResettingScreenRoute(navController: NavController) = composable<ROUTE.PasswordReset> {
    val data: ROUTE.PasswordReset = it.toRoute()

    PasswordResettingScreen(
        email = data.email,
        moveToSignInScreen = { navController.popBackStack<ROUTE.SignInMethodSelection>(false) },
        moveToPasswordResetSuccessScreen = { navController.navigate(ROUTE.PasswordResetSuccess) {
            popUpTo<ROUTE.SignInMethodSelection> { inclusive = false }
        } })
}