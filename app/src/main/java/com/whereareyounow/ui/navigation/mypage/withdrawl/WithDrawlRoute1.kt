package com.whereareyounow.ui.navigation.mypage.withdrawl

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.whereareyounow.data.globalvalue.ROUTE
import com.whereareyounow.ui.main.mypage.withdrawl.WithDrawalScreen1

fun NavGraphBuilder.withDrawlRoute1(navController: NavController) = composable<ROUTE.WithDrawal1> {
    WithDrawalScreen1(
        moveToBackScreen = { navController.popBackStack() },
        moveToWithDrawalScreen2 = { navController.navigate(ROUTE.WithDrawal2) }
    )
}