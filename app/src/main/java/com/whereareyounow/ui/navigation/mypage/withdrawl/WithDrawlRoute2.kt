package com.whereareyounow.ui.navigation.mypage.withdrawl

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.whereareyounow.data.globalvalue.ROUTE
import com.whereareyounow.ui.main.mypage.withdrawl.WithDrawalScreen2

fun NavGraphBuilder.withDrawlRoute2(navController: NavController) = composable<ROUTE.WithDrawal2> {
    WithDrawalScreen2(
        moveToBackScreen = { navController.popBackStack() },
        moveToWithDrawalScreen3 = { navController.navigate(ROUTE.WithDrawal3) }
    )
}