package com.whereareyounow.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.whereareyounow.data.globalvalue.ROUTE
import com.whereareyounow.ui.signup.accountduplicate.AccountDuplicateScreen

fun NavGraphBuilder.accountDuplicateScreenRoute(navController: NavController) = composable<ROUTE.AccountDuplicate> {
    val data: ROUTE.AccountDuplicate = it.toRoute()

    AccountDuplicateScreen(
        accountType = data.accountType,
        email = data.email,
        typeList = data.type,
        userName = data.userName,
        password = data.password,
        moveToBackScreen = { navController.popBackStack<ROUTE.SignInMethodSelection>(false) },
        moveToSignUpSuccessScreen = {
            navController.navigate(ROUTE.SignUpSuccess) {
                popUpTo<ROUTE.SignInMethodSelection> { inclusive = false }
            }
        }
    )
}