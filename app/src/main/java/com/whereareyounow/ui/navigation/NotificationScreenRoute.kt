package com.whereareyounow.ui.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.whereareyounow.data.globalvalue.ROUTE
import com.whereareyounow.ui.main.notification.NotificationScreen
import com.whereareyounow.ui.main.notification.NotificationViewModel

fun NavGraphBuilder.notificationScreenRoute(navController: NavController) = composable<ROUTE.Notification> {
    val viewModel: NotificationViewModel = hiltViewModel()

    NotificationScreen(
        uiState = viewModel.uiState.collectAsStateWithLifecycle().value,
        moveToBackScreen = { navController.popBackStack() }
    )
}