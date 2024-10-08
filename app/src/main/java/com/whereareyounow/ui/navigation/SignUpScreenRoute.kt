package com.whereareyounow.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.whereareyounow.data.globalvalue.ROUTE
import com.whereareyounow.data.globalvalue.ROUTE_POLICY_AGREE
import com.whereareyounow.ui.signup.SignUpScreen

fun NavGraphBuilder.signUpScreenRoute(navController: NavController) = composable<ROUTE.SignUp> {
    SignUpScreen(
        moveToBackScreen = { navController.popBackStack<ROUTE.PolicyAgree.Main>(false) },
        moveToSignUpSuccessScreen = {
            navController.navigate(ROUTE.SignUpSuccess) {
                popUpTo<ROUTE.SignInMethodSelection> { inclusive = false }
            }
        },
        moveToAccountDuplicateScreen = { accountType, email, type, userName, password ->
            navController.navigate(ROUTE.AccountDuplicate(
                accountType, email, type, userName, password
            ))
        }
    )
}