package com.whereareyounow.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.whereareyounow.data.globalvalue.ROUTE
import com.whereareyounow.ui.findaccount.findpw.PasswordResetSuccessScreen

fun NavGraphBuilder.passwordResetSuccessScreenRoute(navController: NavController) = composable<ROUTE.PasswordResetSuccess> {
    PasswordResetSuccessScreen(
        moveToSignInMethodSelectionScreen = {
            navController.popBackStack<ROUTE.SignInMethodSelection>(false)
        }
    )
}