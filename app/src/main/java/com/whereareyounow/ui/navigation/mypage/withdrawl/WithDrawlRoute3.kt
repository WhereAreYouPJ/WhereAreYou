package com.whereareyounow.ui.navigation.mypage.withdrawl

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.whereareyounow.data.globalvalue.ROUTE
import com.whereareyounow.ui.main.mypage.withdrawl.WithDrawalScreen3

fun NavGraphBuilder.withDrawlRoute3(navController: NavController) = composable<ROUTE.WithDrawal3> {
    WithDrawalScreen3(
        moveToBackScreen = { navController.popBackStack() },
        moveToWithDrawalScreen4 = { navController.navigate(ROUTE.WithDrawal4) }
    )
}