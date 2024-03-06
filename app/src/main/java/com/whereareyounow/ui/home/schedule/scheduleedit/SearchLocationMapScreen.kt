package com.whereareyounow.ui.home.schedule.scheduleedit

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
fun SearchLocationMapScreen(
    moveToBackScreen: () -> Unit,
    latitude: Double,
    longitude: Double
) {
    BackHandler {
        moveToBackScreen()
    }
    Text("")
    NaverMap(
        modifier = Modifier
            .fillMaxSize(),
        cameraPositionState = rememberCameraPositionState {
            this.position = CameraPosition(
                LatLng(latitude, longitude),
                NaverMapConstants.DefaultCameraPosition.zoom,
                0.0,
                0.0
            )
        }
    ) {
        Marker(
            state = rememberMarkerState(
                position = LatLng(
                    latitude, longitude
                )
            ),
            icon = MarkerIcons.LIGHTBLUE
        )
    }
}