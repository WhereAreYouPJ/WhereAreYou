package com.whereareyounow.ui.signup

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun SignUpContent(
    moveToBackScreen: () -> Unit,
    viewModel: SignUpViewModel = hiltViewModel(),
) {
    val screenState = viewModel.screenState.collectAsState().value

    when (screenState) {
        SignUpViewModel.ScreenState.PolicyAgree -> PolicyAgreeScreen(
            moveToBackScreen = moveToBackScreen,
            moveToSignUpScreen = { viewModel.updateScreenState(SignUpViewModel.ScreenState.SignUp) },
            moveToTermsOfServiceDetailsScreen = { viewModel.updateScreenState(SignUpViewModel.ScreenState.TermsOfService) },
            moveToPrivacyPolicyDetailsScreen = { viewModel.updateScreenState(SignUpViewModel.ScreenState.PrivacyPolicy) }
        )
        SignUpViewModel.ScreenState.SignUp -> SignUpScreen(
            moveToBackScreen = moveToBackScreen,
            moveToSignUpSuccessScreen = { viewModel.updateScreenState(SignUpViewModel.ScreenState.SignUpSuccess) },
        )
        SignUpViewModel.ScreenState.PrivacyPolicy -> PrivacyPolicyDetailsScreen(
            moveToPolicyAgreeScreen = { viewModel.updateScreenState(SignUpViewModel.ScreenState.PolicyAgree) }
        )
        SignUpViewModel.ScreenState.TermsOfService -> TermsOfServiceDetailsScreen(
            moveToPolicyAgreeScreen = { viewModel.updateScreenState(SignUpViewModel.ScreenState.PolicyAgree) }
        )
        SignUpViewModel.ScreenState.SignUpSuccess -> SignUpSuccessScreen(moveToBackScreen)
    }
}