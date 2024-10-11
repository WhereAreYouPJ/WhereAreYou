package com.whereareyounow.ui.navigation.mypage.withdrawl

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.whereareyounow.data.globalvalue.ROUTE
import com.whereareyounow.ui.main.mypage.withdrawl.WithDrawalScreen5

fun NavGraphBuilder.withDrawlRoute5(navController: NavController) = composable<ROUTE.WithDrawal5> {
    WithDrawalScreen5(
        moveToBackScreen = { navController.popBackStack() },
        moveToLoginScreen = { }
    )
}