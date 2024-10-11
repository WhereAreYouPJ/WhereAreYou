package com.whereareyounow.ui.navigation.mypage.myinfo

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.whereareyounow.data.globalvalue.ROUTE
import com.whereareyounow.ui.main.mypage.myinfo.MyInfoScreen

fun NavGraphBuilder.myInfo(navController: NavController) = composable<ROUTE.MyInfo> {
    MyInfoScreen(
        moveToMyPageScreen = { navController.popBackStack() },
        moveToEditMyInfoScreen = { navController.navigate(ROUTE.EditMyInfo) }
    )
}