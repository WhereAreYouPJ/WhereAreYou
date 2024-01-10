package com.whereareyounow.ui.findpw

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun FindPasswordContent(
    moveToSignInScreen: () -> Unit,
    viewModel: FindPasswordViewModel = hiltViewModel()
) {
    val screenState = viewModel.screenState.collectAsState().value
    when (screenState) {
        FindPasswordViewModel.ScreenState.EmailAuthentication -> FindPasswordScreen(moveToSignInScreen)
        FindPasswordViewModel.ScreenState.ResettingPassword -> PasswordResettingScreen(moveToSignInScreen)
    }
}