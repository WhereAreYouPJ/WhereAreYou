package com.whereareyounow.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.whereareyounow.data.globalvalue.ROUTE
import com.whereareyounow.data.globalvalue.ROUTE_POLICY_AGREE
import com.whereareyounow.data.globalvalue.ROUTE_SIGN_UP_SUCCESS
import com.whereareyounow.ui.signup.SignUpScreen

fun NavGraphBuilder.signUpScreenRoute(navController: NavController) = composable<ROUTE.SignUp> {
    SignUpScreen(
        moveToBackScreen = { navController.popBackStack(ROUTE.PolicyAgree.Main, false) },
        moveToSignUpSuccessScreen = {
            navController.navigate(ROUTE_SIGN_UP_SUCCESS) {
                popUpTo(ROUTE_POLICY_AGREE) { inclusive = true }
            }
        }
    )
}