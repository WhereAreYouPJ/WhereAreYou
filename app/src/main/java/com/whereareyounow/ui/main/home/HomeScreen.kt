package com.whereareyounow.ui.main.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.animateTo
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.whereareyounow.R
import com.whereareyounow.data.globalvalue.TOP_BAR_HEIGHT
import com.whereareyounow.data.home.HomeScreenData
import com.whereareyounow.globalvalue.type.AnchoredDraggableContentState
import com.whereareyounow.ui.component.CustomSurfaceState
import com.whereareyounow.ui.component.DimBackground
import com.whereareyounow.ui.main.MainViewModel
import com.whereareyounow.ui.main.home.component.HomeBannerPager
import com.whereareyounow.ui.main.home.component.HomeBottomDialog
import com.whereareyounow.ui.main.home.component.getHomeAnchoredDraggableState
import com.whereareyounow.ui.theme.getColor
import com.whereareyounow.ui.theme.medium14pt
import com.whereareyounow.ui.theme.medium16pt
import com.whereareyounow.ui.theme.medium20pt
import com.whereareyounow.util.clickableNoEffect
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    customSurfaceState: CustomSurfaceState,
    moveToNotificationScreen: () -> Unit,
    moveToMapScreen: (Int) -> Unit,
    viewModel: HomeViewModel = hiltViewModel(),
    mainViewModel: MainViewModel = hiltViewModel(),
) {
    LaunchedEffect(Unit) {
        customSurfaceState.statusBarColor = Color.Transparent
        viewModel.getBannerImageList()
        viewModel.getDailyScheduleInfo()
        viewModel.getScheduleDday()
    }
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value

    HomeScreen(
        uiState = uiState,
        moveToNotificationScreen = moveToNotificationScreen,
        moveToMapScreen = moveToMapScreen,
        isContent = true,
    )
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun HomeScreen(
    uiState: HomeScreenData,
    moveToNotificationScreen: () -> Unit,
    moveToMapScreen: (Int) -> Unit,
    isContent: Boolean,
) {
    val homeAnchoredDraggableState = remember { getHomeAnchoredDraggableState() }
    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .padding(start = 15.dp, end = 15.dp)
                    .fillMaxWidth()
                    .height(TOP_BAR_HEIGHT.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(Modifier.width(7.dp))

                Image(
                    painter = painterResource(id = R.drawable.ic_onmyway),
                    contentDescription = ""
                )

                Spacer(Modifier.weight(1f))

//        if (alarmBoolean) {
//            Image(
//                painter = painterResource(id = R.drawable.ic_bellred),
//                contentDescription = "",
//                modifier = Modifier
//                    .size(34.dp)
//                    .clickableNoEffect {
//                        onIconClick()
//                    }
//            )
//        } else {
                Image(
                    modifier = Modifier
                        .size(34.dp)
                        .clickableNoEffect { moveToNotificationScreen() },
                    painter = painterResource(id = R.drawable.ic_bell),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(getColor().brandColor)
                )
//        }

                Image(
                    modifier = Modifier
                        .size(34.dp)
                        .clickableNoEffect {  },
                    painter = painterResource(id = R.drawable.ic_userd),
                    contentDescription = "",
                )
            }

            Spacer(Modifier.height(10.dp))

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 15.dp, end = 15.dp)
            ) {
                HomeBannerPager(imageList = uiState.imageList)

                Spacer(Modifier.height(16.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .border(
                            border = BorderStroke(
                                width = 1.dp,
                                color = if (uiState.dDayScheduleList.isEmpty()) getColor().dark else getColor().brandColor
                            ),
                            shape = RoundedCornerShape(6.dp)
                        )
                )
                {
                    if (uiState.dDayScheduleList.isEmpty()) {
                        Text(
                            modifier = Modifier.align(Alignment.Center),
                            text = "일정이 없습니다.",
                            color = Color(0xFF767676),
                            style = medium16pt
                        )
                    } else {
                        Row(
                            modifier = Modifier.align(Alignment.CenterStart)
                        ) {
                            Spacer(Modifier.width(12.dp))

                            Text(
                                text = "D - ${uiState.dDayScheduleList.first().dDay}",
                                color = Color(0xFFFC2F2F),
                                style = medium14pt
                            )

                            Spacer(Modifier.width(18.dp))

                            Text(
                                text = "${uiState.dDayScheduleList.first().title}",
                                color = Color(0xFF222222),
                                style = medium16pt
                            )
                        }
                    }
                }

                Spacer(Modifier.height(30.dp))

                Text(
                    modifier = Modifier.padding(12.dp, 4.dp, 12.dp, 4.dp),
                    text = "함께한 추억을 확인해보세요!",
                    color = Color(0xFF222222),
                    style = medium20pt
                )
            }
        }

        if (((homeAnchoredDraggableState.anchors.maxAnchor()) - (homeAnchoredDraggableState.offset)) /
            ((homeAnchoredDraggableState.anchors.maxAnchor()) - (homeAnchoredDraggableState.anchors.minAnchor())) > 0.03f) {
            DimBackground(
                anchoredDraggableState = homeAnchoredDraggableState,
                closeBottomDialog = {
                    coroutineScope.launch {
                        if (homeAnchoredDraggableState.targetValue == AnchoredDraggableContentState.Open) {
                            homeAnchoredDraggableState.animateTo(AnchoredDraggableContentState.Closed)
                        }
                    }
                }
            )
        }

        if (uiState.imageList.isNotEmpty()) {
            HomeBottomDialog(
                anchoredDraggableState = homeAnchoredDraggableState,
                dailyScheduleList = uiState.dailyScheduleList,
                moveToMapScreen = moveToMapScreen
            )
        }
    }
}
