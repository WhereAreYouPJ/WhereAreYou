package com.whereareyou.ui.home.schedule.newschedule

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.whereareyou.R
import com.whereareyou.data.GlobalValue


@Composable
fun NewLocationScreen(
    updateDestinationInformation: (String, String, Double, Double) -> Unit,
    moveToNewScheduleScreen: () -> Unit,
    moveToMapScreen: () -> Unit,
    viewModel: NewLocationViewModel = hiltViewModel()
) {
    val uriHandler = LocalUriHandler.current
    val density = LocalDensity.current

    BackHandler() {
        moveToNewScheduleScreen()
    }
    Column() {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height((GlobalValue.topBarHeight / density.density).dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                modifier = Modifier
                    .width(60.dp)
                    .height(28.dp)
                    .align(Alignment.CenterStart)
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() },
                    ) {
                        moveToNewScheduleScreen()
                    },
                painter = painterResource(id = R.drawable.arrow_back_ios_new_fill0_wght100_grad0_opsz24),
                contentDescription = null
            )
            BasicTextField(
                modifier = Modifier.padding(start = 60.dp, end = 60.dp),
                value = viewModel.inputLocationText.collectAsState().value,
                onValueChange = {
                    viewModel.updateInputLocationText(it)
                },
                textStyle = TextStyle(fontSize = 20.sp),
                decorationBox = {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                color = Color.LightGray,
                                shape = RoundedCornerShape(4.dp)
                            )
                            .padding(12.dp)
                    ) {
                        it()
                    }
                },
                singleLine = true
            )
            Box(
                modifier = Modifier
                    .width(60.dp)
                    .fillMaxHeight()
                    .align(Alignment.CenterEnd)
                    .padding(4.dp)
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
        Text(
            modifier = Modifier
                .padding(start = 60.dp)
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
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(10.dp)
                .background(color = Color.LightGray)
        )

        val locationInfoList = viewModel.locationInformationList.collectAsState().value
        LazyColumn() {
            itemsIndexed(locationInfoList) { index, item ->

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                        .clickable {
                            val lat = (item.mapy.substring(0 until item.mapy.length - 7) + "." + item.mapy.substring(item.mapy.length - 7 until item.mapy.length)).toDouble()
                            val lng = (item.mapx.substring(0 until item.mapx.length - 7) + "." + item.mapx.substring(item.mapx.length - 7 until item.mapx.length)).toDouble()
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