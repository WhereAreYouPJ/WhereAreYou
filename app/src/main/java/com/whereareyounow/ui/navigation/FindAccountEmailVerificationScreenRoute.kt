package com.whereareyounow.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.whereareyounow.data.globalvalue.ROUTE
import com.whereareyounow.ui.findaccount.findaccount.FindAccountEmailVerificationScreen

fun NavGraphBuilder.findAccountEmailVerificationScreenRoute(navController: NavController) = composable<ROUTE.FindAccountEmailVerification> {
    FindAccountEmailVerificationScreen(
        moveToBackScreen = { navController.popBackStack() },
        moveToFindAccountResultScreen = { email, typeList ->
            navController.navigate(ROUTE.FindAccountResult(
                email = email,
                typeList = typeList
            )) {
            popUpTo<ROUTE.SignInMethodSelection> { inclusive = false }
        } }
    )
}