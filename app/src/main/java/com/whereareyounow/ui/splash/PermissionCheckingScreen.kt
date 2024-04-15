package com.whereareyounow.ui.splash

import android.Manifest
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.whereareyounow.R
import com.whereareyounow.data.TOP_BAR_HEIGHT
import com.whereareyounow.ui.component.RoundedCornerButton


@Composable
fun PermissionCheckingScreen(
    locationPermissions: Array<String>,
    updateScreenState: (SplashViewModel.ScreenState) -> Unit,
    updateCheckingState: (SplashViewModel.CheckingState) -> Unit
) {

    val context = LocalContext.current
    val density = LocalDensity.current.density

    // ACCESS_FINE_LOCATION: 정확한 위치
    // ACCESS_COARSE_LOCATION: 대략적인 위치
    val permissionRequestLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { result ->
        if(
            result.entries.contains(mapOf(Manifest.permission.ACCESS_FINE_LOCATION to true).entries.first())
            && result.entries.contains(mapOf(Manifest.permission.ACCESS_COARSE_LOCATION to true).entries.first())) {
            Toast.makeText(context, "위치 권한이 허용되었습니다.", Toast.LENGTH_SHORT).show()
            updateScreenState(SplashViewModel.ScreenState.Splash)
            updateCheckingState(SplashViewModel.CheckingState.SignIn)
        } else {
            Toast.makeText(context, "위치 권한이 허용되지 않았습니다.", Toast.LENGTH_SHORT).show()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 20.dp, end = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(40.dp))
        Text(
            modifier = Modifier.height((TOP_BAR_HEIGHT / density).dp),
            text = "앱 접근 권한 안내",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(Modifier.height(20.dp))
        Text(
            text = "지금어디의 서비스를 이용하기 위해서는\n아래 권한들을 필요로 합니다.",
            fontSize = 20.sp,
            color = Color(0xFF878787),
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(40.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(
                    color = Color(0xFFF5F5F5),
                    shape = RoundedCornerShape(10.dp)
                )
                .padding(20.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier
                        .size(30.dp),
                    painter = painterResource(R.drawable.location_on_fill0_wght300_grad0_opsz24),
                    contentDescription = null
                )
                Spacer(Modifier.width(10.dp))
                Column() {
                    Text(
                        text = "위치정보 (필수)",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = "본인 위치 확인에 이용",
                        fontSize = 16.sp,
                        color = Color(0xFF878787)
                    )
                }
            }
        }

        Spacer(Modifier.weight(1f))

        RoundedCornerButton(
            text = "확인",
            onClick = {
                permissionRequestLauncher.launch(locationPermissions)
            }
        )

        Spacer(Modifier.height(20.dp))
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PermissionCheckingScreenPreview() {
    PermissionCheckingScreen(
        locationPermissions = arrayOf(),
        updateScreenState = {},
        updateCheckingState = {}
    )
}