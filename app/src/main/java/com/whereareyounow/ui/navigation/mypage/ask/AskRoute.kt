package com.whereareyounow.ui.navigation.mypage.ask

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.whereareyounow.data.globalvalue.ROUTE
import com.whereareyounow.ui.main.mypage.ask.AskScreen

fun NavGraphBuilder.askRoute(navController: NavController) = composable<ROUTE.Ask> {
    AskScreen(
        moveToBackScreen = { navController.popBackStack() }
    )
}