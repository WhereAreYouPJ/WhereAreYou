package com.whereareyou.ui.splash

import android.Manifest
import android.provider.Settings.Global
import android.util.Log
import android.widget.Toast
import android.window.SplashScreen
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.common.api.Status
import com.whereareyou.GlobalViewModel

@Composable
fun SplashScreen(
    checkIsSignedIn: () -> Boolean,
    moveToSignInScreen: () -> Unit,
    moveToMainScreen: () -> Unit
) {

    val context = LocalContext.current
    val locationPermissions = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION
    )
    val permissionRequestLauncher = rememberLauncherForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
        permissions ->
        if(
            permissions.entries.contains(mapOf(Manifest.permission.ACCESS_FINE_LOCATION to true).entries.first())
            && permissions.entries.contains(mapOf(Manifest.permission.ACCESS_COARSE_LOCATION to true).entries.first())) {
            Toast.makeText(context, "위치 권한이 허용되었습니다.", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "위치 권한이 허용되지 않았습니다.", Toast.LENGTH_SHORT).show()
        }
    }
    val locationServiceRequestLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartIntentSenderForResult()
    ) {
            activityResult ->
        if (activityResult.resultCode == ComponentActivity.RESULT_OK)
            Log.e("locationServiceRequest", "location service accepted")
        else {
            Log.e("locationServiceRequest", "location service denied")
        }
    }
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
    val exception = ResolvableApiException(Status.RESULT_CANCELED)
    Box(
        modifier = Modifier
            .size(200.dp)
            .background(color = Color.Blue)
            .clickable {
                permissionRequestLauncher.launch(locationPermissions)
            }
    )
}