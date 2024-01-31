package com.whereareyounow.ui.splash

import android.Manifest
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.whereareyounow.R
import com.whereareyounow.ui.theme.WhereAreYouTheme
import com.whereareyounow.ui.theme.nanumSquareAc
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
            SplashViewModel.CheckingState.NETWORK -> {
                coroutineScope.launch {
                    delay(1000)
                    if (checkNetworkState()) {
                        updateCheckingState(SplashViewModel.CheckingState.LOCATION_PERMISSION)
                    } else {
                        updateIsNetworkConnectionErrorDialogShowing(true)
                    }
                }
            }
            SplashViewModel.CheckingState.LOCATION_PERMISSION -> {
                var flag = true
                for (permission in locationPermissions) {
                    if (ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_DENIED) {
                        updateScreenState(SplashViewModel.ScreenState.PERMISSION)
                        flag = false
                        break
                    }
                }
                if (flag) updateCheckingState(SplashViewModel.CheckingState.SIGN_IN)
            }
            SplashViewModel.CheckingState.SIGN_IN -> {
                coroutineScope.launch {
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
        SplashViewModel.ScreenState.SPLASH -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color(0xFF2D2573)),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(Modifier.width(50.dp))
                    Image(
                        painterResource(R.drawable.bottomnavbar_home),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(Color(0xFFFFD390))
                    )
                    Spacer(Modifier.width(6.dp))
                    Text(
                        text = "가장 실용적인 약속관리",
                        fontSize = 20.sp,
                        fontFamily = nanumSquareAc,
                        color = Color(0xFFFFD390),
                    )
                }
                Spacer(Modifier.height(6.dp))
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 50.dp),
                    text = "지금 어디?",
                    fontSize = 44.sp,
                    fontFamily = nanumSquareAc,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.End,
                    color = Color(0xFFFFFFFF),
                )
                Spacer(Modifier.height(30.dp))
                Image(
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .height(IntrinsicSize.Min),
                    painter = painterResource(id = R.drawable.splash_logo),
                    contentDescription = null
                )
            }
            if (isNetworkConnectionErrorDialogShowing) {
                NetworkConnectionErrorDialog(
                    checkNetworkState = checkNetworkState,
                    updateCheckingState = updateCheckingState,
                    updateIsNetworkConnectionErrorDialogShowing = updateIsNetworkConnectionErrorDialogShowing
                )
            }
        }
        SplashViewModel.ScreenState.PERMISSION -> {
            PermissionCheckingScreen(
                locationPermissions = locationPermissions,
                updateScreenState = updateScreenState,
                updateCheckingState = updateCheckingState
            )
        }
    }
}

@Composable
fun NetworkConnectionErrorDialog(
    checkNetworkState: () -> Boolean,
    updateCheckingState: (SplashViewModel.CheckingState) -> Unit,
    updateIsNetworkConnectionErrorDialogShowing: (Boolean) -> Unit
) {
    val context = LocalContext.current
    Dialog(
        onDismissRequest = {}
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .background(
                    color = Color(0xFFFFFFFF),
                    shape = RoundedCornerShape(10.dp)
                )
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "네트워크 연결을 확인해주세요"
            )
            Spacer(Modifier.weight(1f))
            Row {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(40.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(
                            color = Color(0xFF2D2573),
                            shape = RoundedCornerShape(10.dp)
                        )
                        .clickable {
                            if (checkNetworkState()) {
                                updateIsNetworkConnectionErrorDialogShowing(false)
                                updateCheckingState(SplashViewModel.CheckingState.LOCATION_PERMISSION)
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
                        color = Color(0xFFFFFFFF)
                    )
                }
                Spacer(Modifier.width(10.dp))
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(40.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(
                            color = Color(0xFFD9DCE7),
                            shape = RoundedCornerShape(10.dp)
                        )
                        .clickable {

                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "닫기"
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SplashScreenPreview() {
    WhereAreYouTheme {
        SplashScreen(
            screenState = SplashViewModel.ScreenState.SPLASH,
            updateScreenState = {  },
            checkingState = SplashViewModel.CheckingState.SIGN_IN,
            isNetworkConnectionErrorDialogShowing = false,
            checkNetworkState = { true },
            updateCheckingState = {  },
            updateIsNetworkConnectionErrorDialogShowing = {  },
            checkIsSignedIn = { true },
            moveToSignInScreen = { /*TODO*/ },
            moveToMainScreen = {  }
        )
    }
}

