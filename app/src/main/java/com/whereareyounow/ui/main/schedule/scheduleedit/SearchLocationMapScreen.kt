package com.whereareyounow.ui.main.schedule.scheduleedit

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.Marker
import com.naver.maps.map.compose.NaverMap
import com.naver.maps.map.compose.NaverMapConstants
import com.naver.maps.map.compose.rememberCameraPositionState
import com.naver.maps.map.compose.rememberMarkerState
import com.naver.maps.map.util.MarkerIcons
import com.whereareyounow.R
import com.whereareyounow.data.globalvalue.BOTTOM_NAVIGATION_BAR_HEIGHT
import com.whereareyounow.ui.component.RoundedCornerButton
import com.whereareyounow.ui.theme.getColor
import com.whereareyounow.ui.theme.medium14pt
import com.whereareyounow.ui.theme.medium20pt
import com.whereareyounow.ui.theme.notoSanskr

@OptIn(ExperimentalNaverMapApi::class)
@Composable
fun SearchLocationMapScreen(
    moveToBackScreen: () -> Unit,
    name: String,
    address: String,
    lat: Double,
    lng: Double,
    isBookmarked: Boolean,
    toggleBookmark: () -> Unit,
    moveToScheduleEditScreen: () -> Unit,
    scheduleEditViewModel: ScheduleEditViewModel
) {
    BackHandler {
        moveToBackScreen()
    }
//    Text("")
    Box(
        modifier = Modifier
            .padding(bottom = BOTTOM_NAVIGATION_BAR_HEIGHT.dp)
    ) {
        NaverMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = rememberCameraPositionState {
                this.position = CameraPosition(
                    LatLng(lat, lng),
                    NaverMapConstants.DefaultCameraPosition.zoom,
                    0.0,
                    0.0
                )
            }
        ) {
            Marker(
                state = rememberMarkerState(
                    position = LatLng(
                        lat, lng
                    )
                ),
                icon = MarkerIcons.LIGHTBLUE
            )
        }

        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .background(Color(0xFFFFFFFF))
        ) {
            Row(
                modifier = Modifier
                    .padding(start = 15.dp, end = 15.dp)
                    .fillMaxWidth()
                    .height(85.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        modifier = Modifier.padding(start = 6.dp, top = 2.dp, end = 6.dp, bottom = 2.dp),
                        text = name,
                        color = Color(0xFF444444),
                        style = medium20pt,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    Text(
                        modifier = Modifier.padding(start = 6.dp, top = 2.dp, end = 6.dp, bottom = 2.dp),
                        text = address,
                        color = Color(0xFF999999),
                        style = medium14pt,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }

                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(getColor().brandColor),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        modifier = Modifier.size(30.dp),
                        painter = painterResource(R.drawable.ic_bookmark_outlined_white),
                        contentDescription = null
                    )
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(getColor().thinnest)
            )

            Box(
                modifier = Modifier
                    .padding(start = 15.dp, top = 12.dp, end = 15.dp)
            ) {
                RoundedCornerButton(
                    onClick = {
                        scheduleEditViewModel.updateDestinationInformation(name, address, lat, lng)
                        moveToScheduleEditScreen()
                    }
                ) {
                    Text(
                        text = "확인",
                        color = Color(0xFFFFFFFF),
                        fontFamily = notoSanskr,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                }
            }
        }
    }
}