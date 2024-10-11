package com.whereareyounow.ui.navigation.mypage.announcement

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.whereareyounow.data.globalvalue.ROUTE
import com.whereareyounow.ui.main.mypage.announcement.AnnouncementScreen

fun NavGraphBuilder.announcementRoute(navController: NavController) = composable<ROUTE.Announcement> {
    AnnouncementScreen(
        moveToBackScreen = { navController.popBackStack() },
        moveToAdminImageScreen = { navController.navigate(ROUTE.AdminImageScreen) }
    )
}