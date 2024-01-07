package com.whereareyounow.ui.findid

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun FindIdContent(
    moveToSignInScreen: () -> Unit,
    viewModel: FindIdViewModel = hiltViewModel()
) {
    val screenState = viewModel.screenState.collectAsState().value
    when (screenState) {
        FindIdViewModel.ScreenState.EmailAuthentication -> FindIdScreen(moveToSignInScreen)
        FindIdViewModel.ScreenState.ShowingUserId -> UserIdCheckingScreen(moveToSignInScreen)
    }
}