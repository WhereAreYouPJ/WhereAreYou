package com.onmyway.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.onmyway.data.globalvalue.ROUTE
import com.onmyway.data.globalvalue.ROUTE_POLICY_AGREE
import com.onmyway.ui.signup.SignUpScreen

fun NavGraphBuilder.signUpScreenRoute(navController: NavController) = composable<ROUTE.SignUp> {
    SignUpScreen(
        moveToBackScreen = { navController.popBackStack(ROUTE.PolicyAgree.Main, false) },
        moveToSignUpSuccessScreen = {
            navController.navigate(ROUTE.SignUpSuccess) {
                popUpTo(ROUTE_POLICY_AGREE) { inclusive = true }
            }
        }
    )
}