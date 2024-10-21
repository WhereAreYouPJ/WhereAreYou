package com.whereareyounow.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.whereareyounow.data.globalvalue.ROUTE
import com.whereareyounow.ui.main.mypage.myinfo.MyInfoEditScreen

fun NavGraphBuilder.myInfoEditScreenRoute(navController: NavController) = composable<ROUTE.EditMyInfo> {
    MyInfoEditScreen(
        moveToBackScreen = { navController.popBackStack() }
    )
}