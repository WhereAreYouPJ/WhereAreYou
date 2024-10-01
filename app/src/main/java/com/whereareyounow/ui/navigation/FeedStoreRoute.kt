package com.whereareyounow.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.whereareyounow.data.globalvalue.ROUTE
import com.whereareyounow.ui.main.mypage.FeedStore.FeedStoreScreen

fun NavGraphBuilder.feedStoreRoute(navController: NavController) = composable<ROUTE.FeedStore> {
    FeedStoreScreen(
        moveToBackScreen = { navController.popBackStack() }
    )
}