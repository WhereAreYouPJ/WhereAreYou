package com.whereareyounow.ui.main.schedule.scheduleedit

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.CircleShape
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
import com.whereareyounow.ui.component.CustomSurface
import com.whereareyounow.ui.theme.OnMyWayTheme
import com.whereareyounow.ui.theme.getColor
import com.whereareyounow.ui.theme.medium18pt
import com.whereareyounow.util.clickableNoEffect

@Composable
fun SearchLocationScreen(
    moveToBackScreen: () -> Unit,
    moveToMapScreen: (String, String, Double, Double) -> Unit,
    scheduleEditViewModel: ScheduleEditViewModel,
    searchLocationViewModel: SearchLocationViewModel = hiltViewModel()
) {
    val uiState = searchLocationViewModel.uiState.collectAsState().value
    SearchLocationScreen(
        uiState = uiState,
        updateInputLocationName = searchLocationViewModel::updateInputLocationText,
        searchLocation = searchLocationViewModel::searchLocation,
        updateDestinationInformation = scheduleEditViewModel::updateDestinationInformation,
        moveToBackScreen = moveToBackScreen,
        moveToMapScreen = moveToMapScreen,
    )
}

@Composable
private fun SearchLocationScreen(
    uiState: SearchLocationScreenUIState,
    updateInputLocationName: (String) -> Unit,
    searchLocation: () -> Unit,
    updateDestinationInformation: (String, String, Double, Double) -> Unit,
    moveToBackScreen: () -> Unit,
    moveToMapScreen: (String, String, Double, Double) -> Unit
) {
    val uriHandler = LocalUriHandler.current
    CustomSurface {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Spacer(Modifier.height(10.dp))

            // 장소 검색창
            LocationSearchTextField(
                inputLocationName = uiState.inputLocationName,
                updateInputText = updateInputLocationName,
                searchLocation = searchLocation,
            )

            Box(
                Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Color(0xFFD4D4D4)))

            Spacer(Modifier.height(30.dp))

            SearchedLocationList(
                locationInfoList = uiState.locationInfosList,
                updateDestinationInformation = updateDestinationInformation,
                moveToMapScreen = moveToMapScreen,
                moveToNewScheduleScreen = moveToBackScreen
            )
        }
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
        textStyle = medium18pt.copy(color = Color(0xFF222222)),
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
                .padding(start = 15.dp, top = 10.dp, end = 15.dp, bottom = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier.weight(1f)
            ) {
                it()
                if (inputLocationName == "") {
                    Text(
                        text = "장소를 검색해주세요.",
                        color = getColor().dark,
                        style = medium18pt
                    )
                }
            }

            Spacer(Modifier.width(10.dp))

            Image(
                painter = painterResource(id = R.drawable.ic_search),
                contentDescription = null,
                colorFilter = ColorFilter.tint(color = Color(0xFF9B99AB))
            )
        }
    }
}

@Composable
fun SearchedLocationList(
    locationInfoList: List<LocationInformation> = listOf(),
    updateDestinationInformation: (String, String, Double, Double) -> Unit,
    moveToMapScreen: (String, String, Double, Double) -> Unit,
    moveToNewScheduleScreen: () -> Unit
) {
    LazyColumn(
        modifier = Modifier.padding(start = 15.dp, end = 15.dp)
    ) {
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
                    .height(66.dp)
                    .clickableNoEffect {
                        moveToMapScreen(item.title.replace("<b>", "").replace("</b>", ""), item.roadAddress, lat, lng)
                    }
                    .padding(top = 6.dp)
            ) {
                Image(
                    modifier = Modifier
                        .padding(start = 3.dp, top = 6.dp, end = 3.dp)
                        .height(20.dp),
                    painter = painterResource(id = R.drawable.ic_location2),
                    contentDescription = null
                )

                Column(
                    modifier = Modifier
                        .weight(1f)
                ) {
                    Text(
                        modifier = Modifier.padding(6.dp, 4.dp, 6.dp, 4.dp),
                        text = item.title.replace("<b>", "").replace("</b>", ""),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF585858),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        modifier = Modifier.padding(6.dp, 2.dp, 6.dp, 2.dp),
                        text = item.roadAddress,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF8D8D8D),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
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
    OnMyWayTheme {
//        SearchLocationScreen(
//            uiState = SearchLocationScreenUIState(),
//            updateInputLocationName = {},
//            searchLocation = {},
//            updateDestinationInformation = { _, _, _, _ -> },
//            moveToBackScreen = {},
//            moveToMapScreen = { _, _ -> }
//        )
    }
}