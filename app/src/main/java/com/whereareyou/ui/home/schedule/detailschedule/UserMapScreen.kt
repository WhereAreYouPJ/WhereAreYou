package com.whereareyou.ui.home.schedule.detailschedule

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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

@OptIn(ExperimentalNaverMapApi::class)
@Composable
fun UserMapScreen(
    moveToDetailScheduleScreen: () -> Unit,
    viewModel: DetailScheduleViewModel = hiltViewModel()
) {
    BackHandler {
        moveToDetailScheduleScreen()
    }
//    Text(text = "")
    val userInfos = viewModel.userInfos.collectAsState().value
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
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.BottomEnd
    ) {
        var isUserListShowing by remember { mutableStateOf(false) }
        NaverMap(
            modifier = Modifier
                .fillMaxSize(),
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
                    Marker(
                        state = rememberMarkerState(
                            position = LatLng(
                                info.latitude!!,
                                info.longitude!!
                            )
                        ),
                        icon = MarkerIcons.LIGHTBLUE,
                        captionText = "${info.name}"
                    )
                }
            }
        }
        Box(
            modifier = Modifier
                .padding(end = 40.dp, bottom = 40.dp)
                .clip(
                    RoundedCornerShape(50)
                )
                .width(80.dp)
                .height(40.dp)
                .background(
                    color = Color(0xffffd97e),
                    shape = RoundedCornerShape(50)
                )
                .clickable { isUserListShowing = !isUserListShowing }
        )
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
    val userInfos = viewModel.userInfos.collectAsState().value
    Column(
        modifier = Modifier
            .padding(end = 40.dp, bottom = 80.dp)
            .width(200.dp)
            .height(300.dp)
            .background(
                color = Color.White,
                shape = RoundedCornerShape(8.dp)
            )
    ) {
        Text("친구")
        LazyColumn() {
            itemsIndexed(userInfos) { index, item ->
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp)
                        .clickable {
                            cameraPositionState.position = CameraPosition(
                                LatLng(item.latitude!!, item.longitude!!),
                                NaverMapConstants.DefaultCameraPosition.zoom,
                                0.0,
                                0.0
                            )
                        },
                    text = item.name)
            }
        }
    }
}