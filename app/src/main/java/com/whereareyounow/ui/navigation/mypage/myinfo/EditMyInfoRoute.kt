package com.whereareyounow.ui.navigation.mypage.myinfo

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.whereareyounow.data.globalvalue.ROUTE
import com.whereareyounow.ui.main.mypage.myinfo.EditMyInfoScreen

fun NavGraphBuilder.editMyInfo(navController: NavController) = composable<ROUTE.EditMyInfo> {
    EditMyInfoScreen(
        moveToMyInfoScreen = { navController.popBackStack() },
        moveToBackScreen = { navController.popBackStack() }
    )
}