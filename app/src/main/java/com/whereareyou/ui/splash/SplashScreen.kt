package com.whereareyou.ui.splash

import android.provider.Settings.Global
import android.window.SplashScreen
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.whereareyou.GlobalViewModel

@Composable
fun SplashScreen(
    checkIsSignedIn: () -> Boolean,
    moveToSignInScreen: () -> Unit,
    moveToMainScreen: () -> Unit
) {
    LaunchedEffect(true) {
        if (checkIsSignedIn()) {
            moveToMainScreen()
        } else {
            moveToSignInScreen()
        }
    }
    Text(
        text = "Splash Screen"
    )
}