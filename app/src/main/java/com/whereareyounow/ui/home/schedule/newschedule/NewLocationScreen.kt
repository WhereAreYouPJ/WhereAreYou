package com.whereareyounow.ui.home.schedule.newschedule

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
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
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.whereareyounow.R
import com.whereareyounow.data.GlobalValue


@Composable
fun NewLocationScreen(
    updateDestinationInformation: (String, String, Double, Double) -> Unit,
    moveToNewScheduleScreen: () -> Unit,
    moveToMapScreen: () -> Unit,
    viewModel: NewLocationViewModel = hiltViewModel()
) {
    val uriHandler = LocalUriHandler.current
    val density = LocalDensity.current.density
    val inputLocationText = viewModel.inputLocationText.collectAsState().value

    BackHandler {
        moveToNewScheduleScreen()
    }
    Column(
        modifier = Modifier
            .padding(start = 20.dp, end = 20.dp)
            .fillMaxSize()
    ) {
        // 상단바
        NewLocationScreenTopBar(moveToNewScheduleScreen)

        Spacer(Modifier.height(10.dp))

        // 장소 검색창
        Row(
            modifier = Modifier
                .height(IntrinsicSize.Min)
        ) {
            LocationSearchTextField(
                inputLocationName = inputLocationText,
                updateInputText = viewModel::updateInputLocationText
            )
            Box(
                modifier = Modifier
                    .width(60.dp)
                    .fillMaxHeight()
                    .padding(start = 4.dp, end = 4.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .clickable { viewModel.searchLocation() },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "검색",
                    color = Color.Blue
                )
            }
        }

        Spacer(Modifier.height(10.dp))

        Text(
            modifier = Modifier
                .padding(4.dp)
                .clip(RoundedCornerShape(10.dp))
                .clickable { uriHandler.openUri("https://m.map.naver.com") },
            text = "네이버 지도에서 장소 찾아보기",
            color = Color.Blue
        )

        Spacer(
            modifier = Modifier
                .padding(top = 10.dp)
                .fillMaxWidth()
                .height((0.5).dp)
                .background(color = Color.Gray)
        )

        val locationInfoList = viewModel.locationInformationList.collectAsState().value
        LazyColumn {
            itemsIndexed(locationInfoList) { index, item ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                        .clickable {
                            val lat =
                                (item.mapy.substring(0 until item.mapy.length - 7) + "." + item.mapy.substring(
                                    item.mapy.length - 7 until item.mapy.length
                                )).toDouble()
                            val lng =
                                (item.mapx.substring(0 until item.mapx.length - 7) + "." + item.mapx.substring(
                                    item.mapx.length - 7 until item.mapx.length
                                )).toDouble()
                            updateDestinationInformation(item.title, item.roadAddress, lat, lng)
                            moveToNewScheduleScreen()
                        },
                    contentAlignment = Alignment.CenterStart
                ) {
                    Text(
                        modifier = Modifier
                            .padding(start = 20.dp),
                        text = item.title.replace("<b>", "").replace("</b>", "") +
                                "\n${item.roadAddress}" +
                                "\n${(item.mapx.substring(0 until item.mapx.length - 7) + "." + item.mapx.substring(item.mapx.length - 7 until item.mapx.length)).toDouble()}" +
                                "\n${(item.mapy.substring(0 until item.mapy.length - 7) + "." + item.mapy.substring(item.mapy.length - 7 until item.mapy.length)).toDouble()}"
                    )
                }
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height((0.5).dp)
                        .background(
                            color = Color(0xFFAAAAAA)
                        )
                )
            }
        }
        Text(
            modifier = Modifier
                .padding(top = 20.dp)
                .align(Alignment.CenterHorizontally)
                .clickable {
                    moveToMapScreen()
                },
            text = "지도에서 위치 보기",
            color = Color.Blue
        )
    }
}

@Composable
fun NewLocationScreenTopBar(
    moveToNewScheduleScreen: () -> Unit
) {
    val density = LocalDensity.current.density
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height((GlobalValue.topBarHeight / density).dp),
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .size((GlobalValue.topBarHeight / density / 3 * 2).dp)
                .clip(RoundedCornerShape(50))
                .clickable { moveToNewScheduleScreen() },
            painter = painterResource(id = R.drawable.arrow_back),
            contentDescription = null
        )
        Text(
            text = "장소검색",
            fontSize = 30.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun RowScope.LocationSearchTextField(
    inputLocationName: String,
    updateInputText: (String) -> Unit,
) {
    BasicTextField(
        modifier = Modifier.weight(1f),
        value = inputLocationName,
        onValueChange = updateInputText,
        textStyle = TextStyle(fontSize = 20.sp),
        singleLine = true,
        decorationBox = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        border = BorderStroke(
                            width = 1.dp,
                            color = Color(0xFF9B99AB)
                        ),
                        shape = RoundedCornerShape(50)
                    )
                    .padding(start = 20.dp, top = 10.dp, end = 20.dp, bottom = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.search_24px),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(color = Color(0xFF9B99AB))
                )
                Spacer(Modifier.width(10.dp))
                it()
            }
        }
    )
}