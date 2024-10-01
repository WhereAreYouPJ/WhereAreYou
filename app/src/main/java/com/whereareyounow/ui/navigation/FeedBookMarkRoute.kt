package com.whereareyounow.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.whereareyounow.data.globalvalue.ROUTE
import com.whereareyounow.ui.main.mypage.FeedBookMark.FeedBookMarkScreen

fun NavGraphBuilder.feedBookMarkRoute(navController: NavController) = composable<ROUTE.FeedBookMark> {
    FeedBookMarkScreen(
        moveToBackScreen = { navController.popBackStack() }
    )
}