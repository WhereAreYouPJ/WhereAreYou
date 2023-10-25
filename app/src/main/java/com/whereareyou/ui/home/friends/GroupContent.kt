package com.whereareyou.ui.home.friends

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.LocationOverlay
import com.naver.maps.map.compose.NaverMap
import com.naver.maps.map.compose.rememberMarkerState
import com.naver.maps.map.compose.Marker
import com.naver.maps.map.compose.NaverMapConstants
import com.naver.maps.map.compose.rememberCameraPositionState
import com.naver.maps.map.util.MarkerIcons

@OptIn(ExperimentalNaverMapApi::class)
@Composable
fun GroupContent() {
    Text(text = "GroupContent")
    NaverMap(
        modifier = Modifier.
            fillMaxSize(),
        cameraPositionState = rememberCameraPositionState {
            this.position = CameraPosition(
                LatLng(37.28309904245045, 127.04564397832894),
                NaverMapConstants.DefaultCameraPosition.zoom,
                0.0,
                0.0
            )
        }
    ) {
        Marker(
            state = rememberMarkerState(
                position = LatLng(37.3004755, 127.034374)
            ),
            icon = MarkerIcons.LIGHTBLUE,
            captionText = "박종훈",
            captionHaloColor = Color.White
        )
        Marker(
            state = rememberMarkerState(
                position = LatLng(37.2823046, 127.069717)
            ),
            icon = MarkerIcons.RED,
            captionText = "김찬휘"
        )
        LocationOverlay(
            position = LatLng(37.28309904245045, 127.04564397832894),

        )
    }
}