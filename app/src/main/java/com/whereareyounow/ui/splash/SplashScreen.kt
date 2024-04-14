package com.whereareyounow.ui.splash

import android.Manifest
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.whereareyounow.R
import com.whereareyounow.ui.theme.WhereAreYouTheme
import com.whereareyounow.ui.theme.ttangs
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SplashScreen(
    moveToSignInScreen: () -> Unit,
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
        updateIsNetworkConnectionErrorDialogShowing = viewModel::updateIsNetworkConnectionErrorDialogShowing,
        checkIsSignedIn = viewModel::checkIsSignedIn,
        moveToSignInScreen = moveToSignInScreen,
        moveToMainScreen = moveToMainScreen
    )
}

@Composable
private fun SplashScreen(
    screenState: SplashViewModel.ScreenState,
    updateScreenState: (SplashViewModel.ScreenState) -> Unit,
    checkingState: SplashViewModel.CheckingState,
    isNetworkConnectionErrorDialogShowing: Boolean,
    checkNetworkState: () -> Boolean,
    updateCheckingState: (SplashViewModel.CheckingState) -> Unit,
    updateIsNetworkConnectionErrorDialogShowing: (Boolean) -> Unit,
    checkIsSignedIn: suspend () -> Boolean,
    moveToSignInScreen: () -> Unit,
    moveToMainScreen: () -> Unit
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val locationPermissions = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION
    )
    LaunchedEffect(checkingState) {
        when (checkingState) {
            SplashViewModel.CheckingState.Network -> {
                coroutineScope.launch {
                    delay(1000)
                    if (checkNetworkState()) {
                        updateCheckingState(SplashViewModel.CheckingState.LocationPermission)
                    } else {
                        updateIsNetworkConnectionErrorDialogShowing(true)
                    }
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
                    checkNetworkState = checkNetworkState,
                    updateCheckingState = updateCheckingState,
                    updateIsNetworkConnectionErrorDialogShowing = updateIsNetworkConnectionErrorDialogShowing
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
            Spacer(Modifier.height(10.dp))
            Text(
                text = "위치기반 일정관리 플랫폼",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFFFFFFFF),
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
    checkNetworkState: () -> Boolean,
    updateCheckingState: (SplashViewModel.CheckingState) -> Unit,
    updateIsNetworkConnectionErrorDialogShowing: (Boolean) -> Unit
) {
    val context = LocalContext.current
    val density = LocalDensity.current.density
    Dialog(
        onDismissRequest = {}
    ) {
        CompositionLocalProvider(LocalDensity provides Density(density, fontScale = 1f)) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .background(
                        color = Color(0xFFFFFFFF),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    modifier = Modifier.size(40.dp),
                    painter = painterResource(R.drawable.warning_gray),
                    contentDescription = null
                )
                Spacer(Modifier.weight(1f))
                Text(
                    text = "인터넷 연결을 확인해주세요.",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
                Spacer(Modifier.weight(1.5f))
                Row {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(46.dp)
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
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                    Spacer(Modifier.width(10.dp))
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(46.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(
                                color = Color(0xFF6236E9),
                                shape = RoundedCornerShape(10.dp)
                            )
                            .clickable {
                                if (checkNetworkState()) {
                                    updateIsNetworkConnectionErrorDialogShowing(false)
                                    updateCheckingState(SplashViewModel.CheckingState.LocationPermission)
                                } else {
                                    Toast
                                        .makeText(context, "네트워크 연결을 확인해주세요", Toast.LENGTH_SHORT)
                                        .show()
                                }
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "확인",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFFFFFFFF)
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
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
            updateIsNetworkConnectionErrorDialogShowing = {},
            checkIsSignedIn = { true },
            moveToSignInScreen = { /*TODO*/ },
            moveToMainScreen = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun NetworkConnectionErrorDialogPreview() {
    WhereAreYouTheme {
        NetworkConnectionErrorDialog(
            checkNetworkState = { true },
            updateCheckingState = {},
            updateIsNetworkConnectionErrorDialogShowing = {}
        )
    }
}
