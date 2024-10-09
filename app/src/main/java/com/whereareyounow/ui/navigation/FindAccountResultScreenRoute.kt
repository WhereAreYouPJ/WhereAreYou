package com.whereareyounow.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.whereareyounow.data.globalvalue.ROUTE
import com.whereareyounow.ui.findaccount.findaccount.FindAccountResultScreen

fun NavGraphBuilder.findAccountResultScreenRoute(navController: NavController) = composable<ROUTE.FindAccountResult> {
    val data: ROUTE.FindAccountResult = it.toRoute()

    FindAccountResultScreen(
        email = data.email,
        typeList = data.typeList,
        moveToSignInMethodSelectionScreen = {
            navController.popBackStack<ROUTE.SignInMethodSelection>(false)
        },
        moveToFindPasswordScreen = {
            navController.navigate(ROUTE.FindPassword) {
                popUpTo<ROUTE.SignInMethodSelection> { inclusive = false }
            }
        }
    )
}