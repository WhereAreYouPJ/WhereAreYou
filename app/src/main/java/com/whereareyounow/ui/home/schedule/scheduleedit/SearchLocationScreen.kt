package com.whereareyounow.ui.home.schedule.scheduleedit

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.whereareyounow.R
import com.whereareyounow.data.searchlocation.SearchLocationScreenUIState
import com.whereareyounow.domain.entity.schedule.LocationInformation
import com.whereareyounow.ui.component.CustomTopBar
import com.whereareyounow.ui.theme.WhereAreYouTheme
import com.whereareyounow.ui.theme.nanumSquareNeo

@Composable
fun SearchLocationScreen(
    moveToBackScreen: () -> Unit,
    moveToMapScreen: (Double, Double) -> Unit,
    scheduleEditViewModel: ScheduleEditViewModel,
    searchLocationViewModel: SearchLocationViewModel = hiltViewModel()
) {
    val searchLocationScreenUIState = searchLocationViewModel.searchLocationScreenUIState.collectAsState().value
    SearchLocationScreen(
        searchLocationScreenUIState = searchLocationScreenUIState,
        updateInputLocationName = searchLocationViewModel::updateInputLocationText,
        searchLocation = searchLocationViewModel::searchLocation,
        updateDestinationInformation = scheduleEditViewModel::updateDestinationInformation,
        moveToBackScreen = moveToBackScreen,
        moveToMapScreen = moveToMapScreen,
    )
}

@Composable
private fun SearchLocationScreen(
    searchLocationScreenUIState: SearchLocationScreenUIState,
    updateInputLocationName: (String) -> Unit,
    searchLocation: () -> Unit,
    updateDestinationInformation: (String, String, Double, Double) -> Unit,
    moveToBackScreen: () -> Unit,
    moveToMapScreen: (Double, Double) -> Unit
) {
    val uriHandler = LocalUriHandler.current

    BackHandler {
        moveToBackScreen()
    }
    Column(
        modifier = Modifier
            .padding(start = 20.dp, end = 20.dp)
            .fillMaxSize()
    ) {
        // 상단바
        NewLocationScreenTopBar(
            moveToBackScreen = moveToBackScreen
        )

        Spacer(Modifier.height(10.dp))

        // 네이버 지도에서 검색
        NaverMapSearchButton(
            openUri = { uriHandler.openUri("https://m.map.naver.com") }
        )

        Spacer(Modifier.height(10.dp))

        // 장소 검색창
        LocationSearchTextField(
            inputLocationName = searchLocationScreenUIState.inputLocationName,
            updateInputText = updateInputLocationName,
            searchLocation = searchLocation,
        )

        Spacer(Modifier.height(30.dp))

        SearchedLocationList(
            locationInfoList = searchLocationScreenUIState.locationInfosList,
            updateDestinationInformation = updateDestinationInformation,
            moveToMapScreen = moveToMapScreen,
            moveToNewScheduleScreen = moveToBackScreen
        )
    }
}

@Composable
fun NewLocationScreenTopBar(
    moveToBackScreen: () -> Unit
) {
    CustomTopBar(
        title = "장소검색",
        onBackButtonClicked = {
            moveToBackScreen()
        }
    )
}

@Composable
fun NaverMapSearchButton(
    openUri: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.CenterEnd
    ) {
        Text(
            modifier = Modifier
                .clip(RoundedCornerShape(50))
                .background(Color(0xFFF9D889))
                .clickable { openUri() }
                .padding(start = 12.dp, top = 8.dp, end = 12.dp, bottom = 8.dp),
            text = "네이버 지도 검색",
            color = Color(0xFF6E6255),
            fontSize = 14.sp,
            fontFamily = nanumSquareNeo
        )
    }
}

@Composable
fun LocationSearchTextField(
    inputLocationName: String,
    updateInputText: (String) -> Unit,
    searchLocation: () -> Unit,
) {
    val focusManager = LocalFocusManager.current
    BasicTextField(
        modifier = Modifier.fillMaxWidth(),
        value = inputLocationName,
        onValueChange = updateInputText,
        textStyle = TextStyle(fontSize = 20.sp),
        singleLine = true,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(
            onSearch = {
                searchLocation()
                focusManager.clearFocus()
            }
        )
    ) {
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
}

@Composable
fun SearchedLocationList(
    locationInfoList: List<LocationInformation> = listOf(),
    updateDestinationInformation: (String, String, Double, Double) -> Unit,
    moveToMapScreen: (Double, Double) -> Unit,
    moveToNewScheduleScreen: () -> Unit
) {
    LazyColumn {
        itemsIndexed(locationInfoList) { _, item ->
            val lat =
                (item.mapy.substring(0 until item.mapy.length - 7) + "." + item.mapy.substring(
                    item.mapy.length - 7 until item.mapy.length
                )).toDouble()
            val lng =
                (item.mapx.substring(0 until item.mapx.length - 7) + "." + item.mapx.substring(
                    item.mapx.length - 7 until item.mapx.length
                )).toDouble()
            Row(
                modifier = Modifier
                    .height(80.dp)
                    .clickable {
                        updateDestinationInformation(item.title, item.roadAddress, lat, lng)
                        moveToNewScheduleScreen()
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                ) {
                    Text(
                        text = item.title.replace("<b>", "").replace("</b>", ""),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF585858),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis

                    )
                    Text(
                        text = item.roadAddress,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF8D8D8D),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Image(
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                        .clickable { moveToMapScreen(lat, lng) }
                        .padding(10.dp),
                    painter = painterResource(id = R.drawable.location),
                    contentDescription = null
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun NewLocationScreenPreview() {
    val locationInfoList = listOf(
        LocationInformation("지역1", "", "", "", "", "주소1", "도로명주소1", "1234567890", "1234567890"),
        LocationInformation("지역2222222222222222222222222222222222222222222", "", "", "", "", "주소2", "도로명주소2", "1234567890", "1234567890"),
        LocationInformation("지역3", "", "", "", "", "주소3", "도로명주소3333333333333333333333333", "1234567890", "1234567890"),
        LocationInformation("지역4444444444444444444444444444444444444444", "", "", "", "", "주소4", "도로명주소4444444444444444444444444444444", "1234567890", "1234567890"),
        LocationInformation("지역5", "", "", "", "", "주소5", "도로명주소5", "1234567890", "1234567890"),
    )
    WhereAreYouTheme {
        SearchLocationScreen(
            searchLocationScreenUIState = SearchLocationScreenUIState(),
            updateInputLocationName = {},
            searchLocation = {},
            updateDestinationInformation = { _, _, _, _ -> },
            moveToBackScreen = {},
            moveToMapScreen = { _, _ -> }
        )
    }
}