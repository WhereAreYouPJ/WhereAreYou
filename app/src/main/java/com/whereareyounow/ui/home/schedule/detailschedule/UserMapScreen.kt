package com.whereareyounow.ui.home.schedule.detailschedule

import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.provider.Settings
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.compose.CameraPositionState
import com.naver.maps.map.compose.CircleOverlay
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.MapProperties
import com.naver.maps.map.compose.MapUiSettings
import com.naver.maps.map.compose.Marker
import com.naver.maps.map.compose.NaverMap
import com.naver.maps.map.compose.NaverMapConstants
import com.naver.maps.map.compose.rememberCameraPositionState
import com.naver.maps.map.compose.rememberMarkerState
import com.naver.maps.map.util.MarkerIcons
import com.skydoves.landscapist.glide.GlideImage
import com.whereareyounow.R

@OptIn(ExperimentalNaverMapApi::class)
@Composable
fun UserMapScreen(
    viewModel: DetailScheduleViewModel = hiltViewModel()
) {
    BackHandler {
        viewModel.updateScreenState(DetailScheduleViewModel.ScreenState.DetailSchedule)
    }
//    Text(text = "")
    val context = LocalContext.current
    val locationServiceRequestLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        activityResult ->
        if (activityResult.resultCode == ComponentActivity.RESULT_OK)
            Log.e("locationServiceRequest", "location service accepted")
        else {
            Log.e("locationServiceRequest", "location service denied")
        }
    }

    val userInfos = viewModel.userInfos
    val initLocation = viewModel.myLocation.collectAsState().value
    val destinationLatitude = viewModel.destinationLatitude.collectAsState().value
    val destinationLongitude = viewModel.destinationLongitude.collectAsState().value
    val cameraPositionState = rememberCameraPositionState {
        this.position = CameraPosition(
            LatLng(initLocation.latitude, initLocation.longitude),
            NaverMapConstants.DefaultCameraPosition.zoom,
            0.0,
            0.0
        )
    }
    var mapProperties by remember {
        mutableStateOf(
            MapProperties(maxZoom = 20.0, minZoom = 5.0)
        )
    }
    var mapUiSettings by remember {
        mutableStateOf(
            MapUiSettings(
                isLocationButtonEnabled = false,
                isTiltGesturesEnabled = false,
                isRotateGesturesEnabled = false
            )
        )
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomEnd
    ) {
        var isUserListShowing by remember { mutableStateOf(false) }
        NaverMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            properties = mapProperties,
            uiSettings = mapUiSettings
        ) {

            CircleOverlay(
                center = LatLng(
                    destinationLatitude,
                    destinationLongitude
                ),
                color = Color(0x22FF0000),
                radius = 300.0
            )
            Marker(
                state = rememberMarkerState(
                    position = LatLng(
                        destinationLatitude,
                        destinationLongitude
                    )
                ),
                captionText = "목적지"
            )
            for (info in userInfos) {
                if (info.longitude != null && info.latitude != null) {
                    val state = rememberMarkerState(
                        position = LatLng(
                            info.latitude!!,
                            info.longitude!!
                        )
                    ).apply {
                        position = LatLng(info.latitude!!, info.longitude!!)
                    }
                    Marker(
                        state = state,
                        icon = MarkerIcons.LIGHTBLUE,
                        captionText = "${info.name}"
                    )
                }
            }
        }

        // 멤버 위치 업데이트 버튼
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.BottomStart
        ) {
            Box(
                modifier = Modifier
                    .padding(start = 40.dp, bottom = 40.dp)
                    .clip(RoundedCornerShape(topStart = 50f, bottomStart = 50f))
                    .width(80.dp)
                    .height(40.dp)
                    .background(
                        color = Color(0xffffd97e),
                        shape = RoundedCornerShape(topStart = 50f, bottomStart = 50f)
                    )
                    .clickable {
                        viewModel.getUserLocation()
                    },
                contentAlignment = Alignment.Center
            ) {
                Text("업데이트")
            }
            Box(
                modifier = Modifier
                    .padding(start = 120.dp, bottom = 40.dp)
                    .width(1.dp)
                    .height(40.dp)
                    .background(Color.Black)
            )

            Box(
                modifier = Modifier
                    .padding(end = 120.dp, bottom = 40.dp)
                    .width(1.dp)
                    .height(40.dp)
                    .background(Color.Black)
            )
            // 내 위치 전송 버튼
            Box(
                modifier = Modifier
                    .padding(start = 121.dp, bottom = 40.dp)
                    .clip(RoundedCornerShape(topEnd = 50f, bottomEnd = 50f))
                    .width(80.dp)
                    .height(40.dp)
                    .background(
                        color = Color(0xffffd97e),
                        shape = RoundedCornerShape(topEnd = 50f, bottomEnd = 50f)
                    )
                    .clickable {
                        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

                        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                            val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                            locationServiceRequestLauncher.launch(intent)
                        } else {
                            viewModel.sendUserLocation()
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                Text("내 위치")
            }
        }

        // 유저 선택 버튼
        Box(
            modifier = Modifier
                .padding(end = 40.dp, bottom = 40.dp)
                .clip(RoundedCornerShape(topEnd = 50f, bottomEnd = 50f))
                .width(80.dp)
                .height(40.dp)
                .background(
                    color = Color(0xffffd97e),
                    shape = RoundedCornerShape(topEnd = 50f, bottomEnd = 50f)
                )
                .clickable { isUserListShowing = !isUserListShowing },
            contentAlignment = Alignment.Center
        ) {
            Text("친구")
        }
        Box(
            modifier = Modifier
                .padding(end = 120.dp, bottom = 40.dp)
                .width(1.dp)
                .height(40.dp)
                .background(Color.Black)
        )
        // 목적지 버튼
        Box(
            modifier = Modifier
                .padding(end = 121.dp, bottom = 40.dp)
                .clip(RoundedCornerShape(topStart = 50f, bottomStart = 50f))
                .width(80.dp)
                .height(40.dp)
                .background(
                    color = Color(0xffffd97e),
                    shape = RoundedCornerShape(topStart = 50f, bottomStart = 50f)
                )
                .clickable {
                    isUserListShowing = false
                    cameraPositionState.position = CameraPosition(
                        LatLng(
                            destinationLatitude,
                            destinationLongitude
                        ),
                        NaverMapConstants.DefaultCameraPosition.zoom,
                        0.0,
                        0.0
                    )

                },
                contentAlignment = Alignment.Center
        ) {
            Text("목적지")
        }
        if (isUserListShowing) {
            UserList(cameraPositionState)
        }
    }
}

@Composable
fun UserList(
    cameraPositionState: CameraPositionState,
    viewModel: DetailScheduleViewModel = hiltViewModel()
) {
    val userInfos = viewModel.userInfos
    Column(
        modifier = Modifier
            .padding(end = 40.dp, bottom = 80.dp)
            .width(200.dp)
            .height(300.dp)
            .background(
                color = Color.White,
                shape = RoundedCornerShape(10.dp)
            )
    ) {
        Text(
            modifier = Modifier.padding(start = 20.dp, top = 20.dp),
            text = "친구",
            fontWeight = FontWeight.Bold,
        )
        LazyColumn() {
            itemsIndexed(userInfos) { _, item ->
                Row(
                    modifier = Modifier
                        .clickable {
                            cameraPositionState.position = CameraPosition(
                                LatLng(item.latitude!!, item.longitude!!),
                                NaverMapConstants.DefaultCameraPosition.zoom,
                                0.0,
                                0.0
                            )
                        }
                        .padding(start = 20.dp, top = 10.dp, bottom = 10.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    GlideImage(
                        modifier = Modifier
                            .clip(RoundedCornerShape(50))
                            .size(30.dp),
                        imageModel = {
                            item.profileImage ?: R.drawable.account_circle_fill0_wght200_grad0_opsz24
                        }
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = item.name
                    )
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height((0.6).dp)
                        .background(color = Color(0xFFC2C2C2))
                )
            }
        }
    }
}