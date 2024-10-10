package com.whereareyounow.ui.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.whereareyounow.data.globalvalue.ROUTE
import com.whereareyounow.ui.main.friend.feed.AddFeedScreen
import com.whereareyounow.ui.main.friend.feed.AddFeedViewModel

fun NavGraphBuilder.addFeedScreenRoute(navController: NavController) = composable<ROUTE.AddFeed> {

    val viewModel: AddFeedViewModel = hiltViewModel()
    AddFeedScreen(
        uiState = viewModel.uiState.collectAsStateWithLifecycle().value,
        updateSelectedSchedule = viewModel::updateSelectedSchedule,
        updateTitle = viewModel::updateTitle,
        updateContent = viewModel::updateContent,
        addImages = viewModel::addImages,
        createFeed = viewModel::createFeed,
        removeImage = viewModel::removeImage,
        moveToBackScreen = { navController.popBackStack() }
    )
}