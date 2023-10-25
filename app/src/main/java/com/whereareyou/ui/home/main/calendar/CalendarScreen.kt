package com.whereareyou.ui.home.main.calendar

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.gestures.animateTo
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.whereareyou.data.GlobalValue
import kotlinx.coroutines.launch
import kotlin.math.roundToInt


enum class DetailState {
    Open, Close
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CalendarScreen(
    paddingValues: PaddingValues,
    toDetailScreen: (String) -> Unit,
    viewModel: CalendarViewModel = hiltViewModel()
) {

    val density = LocalDensity.current
    val coroutineScope = rememberCoroutineScope()

    val state = remember {
        AnchoredDraggableState(
            initialValue = DetailState.Open,
            positionalThreshold = { it: Float -> it * 0.5f },
            velocityThreshold = { 100f },
            animationSpec = tween(400)
        )
            .apply {
                updateAnchors(
                    DraggableAnchors {
                        DetailState.Open at 0f
                        DetailState.Close at GlobalValue.dailyScheduleViewHeight
                    }
                )
            }
    }

    Box() {
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(start = 20.dp, end = 20.dp)
                .fillMaxSize()
                .background(color = Color(0xFFFAFAFA))
        ) {

            val year = viewModel.year.collectAsState().value
            val month = viewModel.month.collectAsState().value
            val date = viewModel.date.collectAsState().value
            val calendarState = viewModel.calendarState.collectAsState().value

            // 상단바
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height((GlobalValue.topAppBarHeight / density.density).dp)
                    .background(color = Color(0xFFCE93D8)),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier
                        .clickable {
                            coroutineScope.launch { state.animateTo(DetailState.Close) }
                            viewModel.updateCalendarState(CalendarViewModel.CalendarState.YEAR)
                        },
                    text = "${year}년"
                )
                Spacer(
                    modifier = Modifier
                        .width(10.dp)
                )
                if (calendarState == CalendarViewModel.CalendarState.DATE) {
                    Text(
                        modifier = Modifier
                            .clickable {
                                coroutineScope.launch { state.animateTo(DetailState.Close) }
                                viewModel.updateCalendarState(CalendarViewModel.CalendarState.MONTH)
                            },
                        text = "${month + 1}월"
                    )
                }
            }

            // 달력 뷰
            Box(
                modifier = Modifier
                    .height(((GlobalValue.calendarViewHeight + state.offset) / GlobalValue.density).dp)
            ) {
                DateCalendar(expandDetailContent = { coroutineScope.launch { state.animateTo(DetailState.Open) } })

                MonthCalendar(hideDetailContent = { coroutineScope.launch { state.animateTo(DetailState.Open) } })

                YearCalendar(
                    hideDetailContent = {
                    }
                )
            }
        }

        // 일별 간략한 일정
        Column(
            modifier = Modifier
                .padding(top = ((GlobalValue.calendarViewHeight + GlobalValue.topAppBarHeight) / GlobalValue.density + 2).dp)
                .height(((GlobalValue.dailyScheduleViewHeight / GlobalValue.density)).dp)
                .offset {
                    IntOffset(
                        x = 0,
                        y = state
                            .requireOffset()
                            .roundToInt()
                    )
                }
                .padding(start = 20.dp, end = 20.dp)
                .shadow(
                    elevation = 10.dp,
                    shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
                )
                .background(
                    color = Color(0xFFFFFFFF),
                    shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
                )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(20.dp)
                    .anchoredDraggable(state, Orientation.Vertical),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .width(40.dp)
                        .height(4.dp)
                        .background(
                            color = Color(0xFF8C9EFF),
                            shape = RoundedCornerShape(100)
                        )
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(((GlobalValue.dailyScheduleViewHeight / GlobalValue.density) - 20).dp)
                    .background(
                        color = Color(0xFFFFF9C4)
                    )
            ) {
                val currentDateBriefSchedule = viewModel.currentDateBriefSchedule.collectAsState().value
                LazyColumn(
                    contentPadding = PaddingValues(start = 10.dp, top = 10.dp, end = 10.dp, bottom = 20.dp)
                ) {
                    itemsIndexed(currentDateBriefSchedule) { index, item ->
                        Box(
                            modifier = Modifier
                                .padding(start = 10.dp, top = 10.dp, end = 10.dp)
                                .fillMaxWidth()
                                .height(80.dp)
                                .clip(RoundedCornerShape(4.dp))
                                .background(color = Color(0XFF80DEEA))
                                .clickable {
                                    toDetailScreen(item.scheduleId)
                                }
                        ) {
                            Text(
                                text = "${item.title}" +
                                        "\n${item.scheduleId}" +
                                        "\n${item.start}" +
                                        "\n${item.end}"
                            )
                        }
                    }
                }
            }
        }
    }
}