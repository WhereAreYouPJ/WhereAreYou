package com.whereareyounow.ui.navigation.mypage.withdrawl

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.whereareyounow.data.globalvalue.ROUTE
import com.whereareyounow.ui.main.mypage.withdrawl.WithDrawalScreen4

fun NavGraphBuilder.withDrawlRoute4(navController: NavController) = composable<ROUTE.WithDrawal4> {
    WithDrawalScreen4(
        moveToBackScreen = { navController.popBackStack() },
        moveToWithDrawalScreen5 = { navController.navigate(ROUTE.WithDrawal5) }
    )
}