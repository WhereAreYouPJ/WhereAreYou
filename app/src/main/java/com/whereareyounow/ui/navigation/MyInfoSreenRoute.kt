package com.whereareyounow.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.whereareyounow.data.globalvalue.ROUTE
import com.whereareyounow.ui.main.mypage.myinfo.MyInfoScreen

fun NavGraphBuilder.myInfoScreenRoute(navController: NavController) = composable<ROUTE.MyInfo> {
    MyInfoScreen(
        moveToBackScreen = { navController.popBackStack() },
        moveToEditMyInfoScreen = { navController.navigate(ROUTE.EditMyInfo) }
    )
}