package com.whereareyounow.ui.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.whereareyounow.data.globalvalue.ROUTE
import com.whereareyounow.ui.main.notification.NotificationScreen
import com.whereareyounow.ui.main.notification.NotificationViewModel

fun NavGraphBuilder.notificationScreenRoute(navController: NavController) = composable<ROUTE.Notification> {
    val viewModel: NotificationViewModel = hiltViewModel()

    NotificationScreen(
        moveToBackScreen = { navController.popBackStack() }
    )
}