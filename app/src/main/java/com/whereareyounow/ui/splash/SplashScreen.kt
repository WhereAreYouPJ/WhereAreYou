package com.whereareyounow.ui.splash

import android.Manifest
import android.content.pm.PackageManager
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.whereareyounow.R
import com.whereareyounow.ui.theme.WhereAreYouTheme
import com.whereareyounow.ui.theme.medium14pt
import com.whereareyounow.ui.theme.medium16pt
import com.whereareyounow.ui.theme.ttangs
import com.whereareyounow.util.CustomPreview
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SplashScreen(
    moveToSignInMethodSelectionScreen: () -> Unit,
    moveToMainScreen: () -> Unit,
    viewModel: SplashViewModel = hiltViewModel()
) {
    val screenState = viewModel.screenState.collectAsState().value
    val checkingState = viewModel.checkingState.collectAsState().value
    val isNetworkConnectionErrorDialogShowing = viewModel.isNetworkConnectionErrorDialogShowing.collectAsState().value
    SplashScreen(
        screenState = screenState,
        updateScreenState = viewModel::updateScreenState,
        checkingState = checkingState,
        isNetworkConnectionErrorDialogShowing = isNetworkConnectionErrorDialogShowing,
        checkNetworkState = viewModel::checkNetworkState,
        updateCheckingState = viewModel::updateCheckingState,
        checkIsSignedIn = viewModel::checkIsSignedIn,
        moveToSignInScreen = moveToSignInMethodSelectionScreen,
        moveToMainScreen = moveToMainScreen
    )
}

@Composable
private fun SplashScreen(
    screenState: SplashViewModel.ScreenState,
    updateScreenState: (SplashViewModel.ScreenState) -> Unit,
    checkingState: SplashViewModel.CheckingState,
    isNetworkConnectionErrorDialogShowing: Boolean,
    checkNetworkState: () -> Unit,
    updateCheckingState: (SplashViewModel.CheckingState) -> Unit,
    checkIsSignedIn: suspend () -> Boolean,
    moveToSignInScreen: () -> Unit,
    moveToMainScreen: () -> Unit
) {
    val systemUiController = rememberSystemUiController()
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val locationPermissions = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION
    )
    LaunchedEffect(checkingState) {
        when (checkingState) {
            SplashViewModel.CheckingState.Network -> {
                coroutineScope.launch {
                    systemUiController.setStatusBarColor(
                        color = Color(0x00000000),
                        darkIcons = false
                    )
                    systemUiController.setNavigationBarColor(
                        color = Color(0x00000000),
                        darkIcons = false
                    )
                    delay(1000)
                    checkNetworkState()
                }
            }
            SplashViewModel.CheckingState.LocationPermission -> {
                var flag = true
                for (permission in locationPermissions) {
                    if (ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_DENIED) {
                        updateScreenState(SplashViewModel.ScreenState.Permission)
                        flag = false
                        break
                    }
                }
                if (flag) updateCheckingState(SplashViewModel.CheckingState.SignIn)
            }
            SplashViewModel.CheckingState.SignIn -> {
                coroutineScope.launch(Dispatchers.Main) {
                    delay(1000)
                    systemUiController.setStatusBarColor(
                        color = Color(0x00000000),
                        darkIcons = true
                    )
                    systemUiController.setNavigationBarColor(
                        color = Color(0x00000000),
                        darkIcons = true
                    )
                    if (checkIsSignedIn()) {
                        moveToMainScreen()
                    } else {
                        moveToSignInScreen()
                    }
                }
            }
        }
    }
    when (screenState) {
        SplashViewModel.ScreenState.Splash -> {
            SplashContent()
            if (isNetworkConnectionErrorDialogShowing) {
                NetworkConnectionErrorDialog(
                    checkNetworkState = checkNetworkState
                )
            }
        }
        SplashViewModel.ScreenState.Permission -> {
            PermissionCheckingScreen(
                locationPermissions = locationPermissions,
                updateScreenState = updateScreenState,
                updateCheckingState = updateCheckingState
            )
        }
    }
}

@Composable
private fun SplashContent() {
    Layout(
        content = {
            Text(
                modifier = Modifier,
                text = "지금 어디?",
                fontSize = 52.sp,
                fontFamily = ttangs,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFFFFFFF),
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = "위치기반 일정관리 플랫폼",
                color = Color(0xFFFFFFFF),
                style = medium14pt
            )
        },
        modifier = Modifier
            .wrapContentSize()
            .background(color = Color(0xFF7B50FF)),
    ) { measurables, constraint ->
        val placeables = measurables.map { it.measure(constraint) }
        layout(constraint.maxWidth, constraint.maxHeight) {
            var currentY = (constraint.maxHeight / 5)
            placeables.forEach { placeable ->
                placeable.place(x = (constraint.maxWidth - placeable.width) / 2, y = currentY)
                currentY += placeable.height
            }
        }
    }
}

@Composable
private fun NetworkConnectionErrorDialog(
    checkNetworkState: () -> Unit
) {
    Dialog(
        onDismissRequest = {}
    ) {
        WhereAreYouTheme {
            Column(
                modifier = Modifier
                    .width(280.dp)
                    .height(160.dp)
                    .background(
                        color = Color(0xFFFFFFFF),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(start = 14.dp, end = 14.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(Modifier.height(18.dp))

                Image(
                    modifier = Modifier.size(32.dp),
                    painter = painterResource(R.drawable.warning_gray),
                    contentDescription = null
                )

                Spacer(Modifier.height(12.dp))

                Text(
                    text = "인터넷 연결을 확인해주세요.",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF222222)
                )

                Spacer(Modifier.weight(1f))

                Row {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(42.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(
                                color = Color(0xFFD9DCE7),
                                shape = RoundedCornerShape(10.dp)
                            )
                            .clickable {

                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "닫기",
                            color = Color(0xFF444444),
                            style = medium16pt
                        )
                    }
                    Spacer(Modifier.width(8.dp))
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(42.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(
                                color = Color(0xFF6236E9),
                                shape = RoundedCornerShape(10.dp)
                            )
                            .clickable { checkNetworkState() },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "확인",
                            color = Color(0xFFFFFFFF),
                            style = medium16pt
                        )
                    }
                }

                Spacer(Modifier.height(14.dp))
            }
        }
    }
}

@CustomPreview
@Composable
private fun SplashScreenPreview() {
    WhereAreYouTheme {
        SplashScreen(
            screenState = SplashViewModel.ScreenState.Splash,
            updateScreenState = {},
            checkingState = SplashViewModel.CheckingState.SignIn,
            isNetworkConnectionErrorDialogShowing = false,
            checkNetworkState = { true },
            updateCheckingState = {},
            checkIsSignedIn = { true },
            moveToSignInScreen = { /*TODO*/ },
            moveToMainScreen = {}
        )
    }
}

@CustomPreview
@Composable
private fun NetworkConnectionErrorDialogPreview() {
    WhereAreYouTheme {
        NetworkConnectionErrorDialog(
            checkNetworkState = { true }
        )
    }
}
