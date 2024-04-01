package com.whereareyounow.ui.home.schedule.detailschedule

import android.graphics.BitmapFactory
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
import com.naver.maps.map.overlay.OverlayImage
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import com.whereareyounow.R
import com.whereareyounow.data.detailschedule.MemberInfo
import com.whereareyounow.ui.theme.nanumSquareNeo
import com.whereareyounow.util.popupmenu.CustomPopup
import com.whereareyounow.util.popupmenu.PopupPosition
import com.whereareyounow.util.popupmenu.PopupState

@OptIn(ExperimentalNaverMapApi::class)
@Composable
fun DetailScheduleMapScreen(
    scheduleId: String,
    destinationLatitude: Double,
    destinationLongitude: Double,
    passedMemberInfosList: List<MemberInfo>,
    viewModel: DetailScheduleMapViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.updateScheduleInfo(
            scheduleId = scheduleId,
            destinationLatitude = destinationLatitude,
            destinationLongitude = destinationLongitude,
            memberInfosList = passedMemberInfosList
        )
    }

    DisposableEffect(Unit) {
        viewModel.sendUserLocation()
        onDispose {
            viewModel.stopUpdateLocation()
        }
    }

    val detailScheduleMapScreenUIState = viewModel.detailScheduleMapScreenUIState.collectAsState().value
    val context = LocalContext.current
    val density = LocalDensity.current.density
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


    val cameraPositionState = rememberCameraPositionState {
        this.position = CameraPosition(
            LatLng(destinationLatitude, destinationLongitude),
            NaverMapConstants.DefaultCameraPosition.zoom,
            0.0,
            0.0
        )
    }
    val popupState = remember { PopupState(false, PopupPosition.TopLeft) }
//    LaunchedEffect(detailScheduleMapScreenUIState) {
//        if (detailScheduleMapScreenUIState.memberInfosList.isNotEmpty()) {
//            cameraPositionState.position = CameraPosition(
//                LatLng(detailScheduleMapScreenUIState.memberInfosList[0].latitude ?: 0.0, detailScheduleMapScreenUIState.memberInfosList[0].longitude ?: 0.0),
//                NaverMapConstants.DefaultCameraPosition.zoom,
//                0.0,
//                0.0
//            )
//        }
//    }
    val mapProperties by remember {
        mutableStateOf(
            MapProperties(maxZoom = 20.0, minZoom = 5.0)
        )
    }
    val mapUiSettings by remember {
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
                    detailScheduleMapScreenUIState.destinationLatitude,
                    detailScheduleMapScreenUIState.destinationLongitude
                ),
                color = Color(0x22FF0000),
                radius = 300.0
            )
            Marker(
                state = rememberMarkerState(
                    position = LatLng(
                        detailScheduleMapScreenUIState.destinationLatitude,
                        detailScheduleMapScreenUIState.destinationLongitude
                    )
                ),
                icon = OverlayImage.fromResource(R.drawable.destination),
                captionText = "목적지"
            )
            for (info in detailScheduleMapScreenUIState.memberInfosList) {
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
                        icon = when (info.imageBitmap != null) {
                            true -> OverlayImage.fromBitmap(info.imageBitmap!!)
                            false -> OverlayImage.fromBitmap(
                                BitmapFactory.decodeResource(context.resources, R.drawable.idle_profile)
                                    .getCircledBitmap(context.applicationContext, (context.resources.displayMetrics.density * 40).toInt())
                            )
                        },
                        captionText = "${info.name}"
                    )
                }
            }
        }

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.BottomCenter
        ) {
            Row(
                modifier = Modifier
                    .padding(start = 40.dp, end = 40.dp, bottom = 40.dp)
                    .fillMaxWidth()
                    .height(40.dp)
            ) {
//                Box(
//                    modifier = Modifier
//                        .fillMaxHeight()
//                        .clip(RoundedCornerShape(50))
//                        .background(Color(0xFFFFD97E))
//                        .clickable {
//                            val locationManager =
//                                context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
//
//                            if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
//                                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
//                                locationServiceRequestLauncher.launch(intent)
//                            } else {
//                                if (memberInfosList.isNotEmpty()) {
//                                    cameraPositionState.position = CameraPosition(
//                                        LatLng(
//                                            memberInfosList[0].latitude ?: 37.2761893,
//                                            memberInfosList[0].latitude ?: 127.0442815
//                                        ),
//                                        NaverMapConstants.DefaultCameraPosition.zoom,
//                                        0.0,
//                                        0.0
//                                    )
//                                }
//                            }
//                        }
//                        .padding(start = 12.dp, top = 8.dp, end = 12.dp, bottom = 8.dp),
//                    contentAlignment = Alignment.Center
//                ) {
//                    Text(
//                        text = "업데이트",
//                        fontSize = 14.sp,
//                        fontFamily = nanumSquareNeo,
//                        fontWeight = FontWeight.Bold,
//                        color = Color(0xFF463119),
//                    )
//                }

                Spacer(Modifier.weight(1f))

                Box {
                    Row(
                        modifier = Modifier
                            .width(150.dp)
                            .clip(RoundedCornerShape(50))
                            .background(Color(0xFFFFD97E))
                    ) {
                        CustomPopup(
                            popupState = popupState,
                            onDismissRequest = { popupState.isVisible = false }
                        ) {
                            CompositionLocalProvider(LocalDensity provides Density(density, fontScale = 1f)) {
                                UserList(cameraPositionState)
                            }
                        }
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight()
                                .clickable {
                                    isUserListShowing = false
                                    cameraPositionState.position = CameraPosition(
                                        LatLng(
                                            detailScheduleMapScreenUIState.destinationLatitude,
                                            detailScheduleMapScreenUIState.destinationLongitude
                                        ),
                                        NaverMapConstants.DefaultCameraPosition.zoom,
                                        0.0,
                                        0.0
                                    )
                                }
                                .padding(start = 12.dp, top = 8.dp, end = 12.dp, bottom = 8.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "목적지",
                                fontSize = 14.sp,
                                fontFamily = nanumSquareNeo,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF463119),
                            )
                        }

                        Box(
                            modifier = Modifier
                                .width(0.5.dp)
                                .fillMaxHeight()
                                .background(Color(0xFF000000))
                        )

                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight()
                                .clickable {
//                                    isUserListShowing = !isUserListShowing
                                    popupState.isVisible = true
                                }
                                .padding(start = 12.dp, top = 8.dp, end = 12.dp, bottom = 8.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "멤버",
                                fontSize = 14.sp,
                                fontFamily = nanumSquareNeo,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF463119),
                            )
                        }
                    }
                }
            }
        }
//        if (isUserListShowing) {
//            UserList(cameraPositionState)
//        }
    }
}


@Composable
fun UserList(
    cameraPositionState: CameraPositionState,
    viewModel: DetailScheduleMapViewModel = hiltViewModel()
) {
    val detailScheduleMapScreenUIState = viewModel.detailScheduleMapScreenUIState.collectAsState().value
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .width(200.dp)
            .height(300.dp)
            .background(
                color = Color(0xFFFFFFFF),
                shape = RoundedCornerShape(10.dp)
            )
    ) {
        Text(
            modifier = Modifier.padding(start = 20.dp, top = 20.dp),
            text = "멤버",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
        )
        Spacer(Modifier.height(20.dp))
        LazyColumn {
            itemsIndexed(detailScheduleMapScreenUIState.memberInfosList) { _, memberInfo ->
                Row(
                    modifier = Modifier
                        .clickable {
                            if (memberInfo.latitude != null && memberInfo.longitude != null) {
                                cameraPositionState.position = CameraPosition(
                                    LatLng(memberInfo.latitude!!, memberInfo.longitude!!),
                                    NaverMapConstants.DefaultCameraPosition.zoom,
                                    0.0,
                                    0.0
                                )
                            } else {
                                Toast
                                    .makeText(
                                        context,
                                        "${memberInfo.name}의 위치가 존재하지 않습니다.",
                                        Toast.LENGTH_SHORT
                                    )
                                    .show()
                            }
                        }
                        .padding(start = 20.dp, top = 10.dp, bottom = 10.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    GlideImage(
                        modifier = Modifier
                            .clip(RoundedCornerShape(50))
                            .size(30.dp),
                        imageModel = { memberInfo.profileImage ?: R.drawable.idle_profile },
                        imageOptions = ImageOptions(contentScale = ContentScale.FillHeight)
                    )
                    Spacer(Modifier.width(10.dp))
                    Text(
                        text = memberInfo.name,
                        fontSize = 18.sp,
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