package com.whereareyounow.ui.home.schedule.newschedule

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.Marker
import com.naver.maps.map.compose.NaverMap
import com.naver.maps.map.compose.NaverMapConstants
import com.naver.maps.map.compose.rememberCameraPositionState
import com.naver.maps.map.compose.rememberMarkerState
import com.naver.maps.map.util.MarkerIcons

@OptIn(ExperimentalNaverMapApi::class)
@Composable
fun MapScreen(
    moveToSearchLocationScreen: () -> Unit,
    newLocationViewModel: NewLocationViewModel = hiltViewModel()
) {
    BackHandler() {
        moveToSearchLocationScreen()
    }
    NaverMap(
        modifier = Modifier
            .fillMaxSize(),
        cameraPositionState = rememberCameraPositionState {
            this.position = CameraPosition(
                LatLng(37.28309904245045, 127.04564397832894),
                NaverMapConstants.DefaultCameraPosition.zoom,
                0.0,
                0.0
            )
        }
    ) {
        for (item in newLocationViewModel.locationInformationList.collectAsState().value) {
            Log.e("position", "${item.mapx.toDouble() / 10000000}, ${item.mapy.toDouble() / 10000000}")
            Marker(
                state = rememberMarkerState(
                    position = LatLng(
                        item.mapy.toDouble() / 10000000, item.mapx.toDouble() / 10000000
                    )
                ),
                icon = MarkerIcons.LIGHTBLUE,
                captionText = item.title
            )
        }
    }
}