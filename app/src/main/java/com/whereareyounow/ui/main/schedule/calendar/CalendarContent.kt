package com.whereareyounow.ui.main.schedule.calendar

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.gestures.animateTo
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.boundsInParent
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.test.core.app.ActivityScenario.launch
import com.whereareyounow.data.calendar.Schedule
import com.whereareyounow.data.globalvalue.BOTTOM_NAVIGATION_BAR_HEIGHT
import com.whereareyounow.data.globalvalue.CALENDAR_VIEW_HEIGHT
import com.whereareyounow.data.globalvalue.TOP_BAR_HEIGHT
import com.whereareyounow.ui.theme.getColor
import com.whereareyounow.ui.theme.medium14pt
import com.whereareyounow.ui.theme.notoSanskr
import com.whereareyounow.util.getYearCalendars
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.random.Random

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CalendarContent(
    currentMonthCalendarInfo: List<Schedule>,
    updateCurrentMonthCalendarInfo: () -> Unit,
    selectedYear: Int,
    updateYear: (Int) -> Unit,
    selectedMonth: Int,
    updateMonth: (Int) -> Unit,
    selectedDate: Int,
    updateDate: (Int) -> Unit,
    state: AnchoredDraggableState<DetailState>,
) {
    val durationMillis = 500
    val density = LocalDensity.current.density
    val coroutineScope = rememberCoroutineScope()
    val calendarInfo = remember { getYearCalendars(2014, 2034) }
    val isShowing = remember { mutableStateOf(false) }
    val animatableXOffset = remember { Animatable(0f) }
    val animatableYOffset = remember { Animatable(0f) }
    val prevXOffset = remember { mutableFloatStateOf(0f) }
    val prevYOffset = remember { mutableFloatStateOf(0f) }
    val size by animateFloatAsState(if (isShowing.value) 1f else 1f / 3f, label = "", animationSpec = tween(durationMillis = durationMillis))
    val alpha by animateFloatAsState(if (isShowing.value) 1f else 0f, label = "", animationSpec = tween(durationMillis = durationMillis))
    val transformOrigin = remember { mutableFloatStateOf(0f) }
    val listState = rememberLazyListState()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 15.dp, end = 15.dp)
                .graphicsLayer(
                    alpha = 1f - alpha
                ),
            columns = GridCells.Fixed(3),
        ) {
            calendarInfo.forEachIndexed { yearIdx, calendars ->
                item {
                    Box(
                        modifier = Modifier
                            .height(50.dp)
                            .padding(start = 5.dp),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Text(
                            text = "${calendars[0][15].year}년",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Medium,
                            color = getColor().brandColor,
                            fontFamily = notoSanskr
                        )
                    }
                }
                item {  }
                item {  }

                itemsIndexed(calendars) { monthIdx, item ->
                    val xOffset = remember { mutableIntStateOf(0) }
                    val yOffset = remember { mutableIntStateOf(0) }
//                    val color = remember { Color(Random.nextInt(255), Random.nextInt(255), Random.nextInt(255)) }
                    Column(
                        modifier = Modifier
                            .padding(top = 10.dp, bottom = 10.dp)
                            .onGloballyPositioned {
                                xOffset.intValue = it.boundsInParent().topLeft.x.toInt()
                                yOffset.intValue = it.boundsInParent().topLeft.y.toInt()
                            }
                            .height(120.dp)
//                            .background(color)
                            .clickable {
                                coroutineScope.launch {
                                    prevXOffset.floatValue = animatableXOffset.value
                                    prevYOffset.floatValue = yOffset.intValue.toFloat()
                                    listState.scrollToItem(yearIdx * 12 + monthIdx)
                                    transformOrigin.floatValue = 0.5f * (monthIdx % 3)
                                    animatableYOffset.snapTo(yOffset.intValue.toFloat())
                                    launch {
                                        animatableXOffset.animateTo(
                                            targetValue = 0f,
                                            animationSpec = tween(durationMillis = durationMillis)
                                        )
                                    }
                                    launch {
                                        animatableYOffset.animateTo(
                                            targetValue = 0f,
                                            animationSpec = tween(durationMillis = durationMillis)
                                        )
                                    }
                                    isShowing.value = true
                                }
                            },
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 8.dp),
                            text = String.format("%02d", monthIdx + 1),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF222222),
                            fontFamily = notoSanskr
                        )

                        Row(
                            modifier = Modifier
                                .height(20.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            repeat(7) { idx ->
                                Text(
                                    modifier = Modifier.width(15.dp),
                                    text = when (idx) {
                                        0 -> "일"
                                        1 -> "월"
                                        2 -> "화"
                                        3 -> "수"
                                        4 -> "목"
                                        5 -> "금"
                                        else -> "토"
                                    },
                                    fontSize = 8.sp,
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Center,
                                    color = when (idx) {
                                        0 -> Color(0xFFD14343)
                                        6 -> Color(0xFF3369D1)
                                        else -> Color(0xFF222222)
                                    },
                                    fontFamily = notoSanskr
                                )
                            }
                        }

                        Spacer(
                            modifier = Modifier
                                .width(105.dp)
                                .height((0.5).dp)
                                .background(Color(0xFFC9C9C9))
                        )

                        Column(
                            modifier = Modifier.height(100.dp)
                        ) {
                            repeat(item.size / 7) { week ->
                                Row(
                                    modifier = Modifier.weight(1f),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    repeat(7) { idx ->
                                        Text(
                                            modifier = Modifier.width(15.dp),
                                            text = "${item[week * 7 + idx].dayOfMonth}",
                                            fontSize = 8.sp,
                                            fontWeight = FontWeight.Bold,
                                            textAlign = TextAlign.Center,
                                            color = if (item[week * 7 + idx].monthValue != item[15].monthValue) Color(0xFF999999) else
                                                when (idx) {
                                                    0 -> Color(0xFFD14343)
                                                    6 -> Color(0xFF3369D1)
                                                    else -> Color(0xFF222222)
                                                },
                                            fontFamily = notoSanskr
                                        )
                                    }
                                }

                                if (week < item.size / 7 - 1) {
                                    Spacer(
                                        modifier = Modifier
                                            .width(105.dp)
                                            .height((0.5).dp)
                                            .background(Color(0xFFEEEEEE))
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .offset {
                    IntOffset(
                        (animatableXOffset.value / 2).toInt(),
                        animatableYOffset.value.toInt()
                    )
                }
                .graphicsLayer(
                    scaleX = size,
                    scaleY = size,
                    transformOrigin = TransformOrigin(transformOrigin.floatValue, 0f),
                    alpha = alpha
                )
                .zIndex(if (size > 0.34f) 1f else -1f),
            state = listState
        ) {
            itemsIndexed(calendarInfo.flatten()) { month, item ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
//                        .background(Color.LightGray)
                        .then(
                            if (isShowing.value) {
                                Modifier.clickable {
                                    coroutineScope.launch {
                                        launch {
                                            animatableYOffset.animateTo(
                                                targetValue = prevYOffset.floatValue,
                                                animationSpec = tween(durationMillis = durationMillis)
                                            )
                                        }
                                        isShowing.value = false
                                    }
                                }
                            } else Modifier
                        )
                        .padding(start = 15.dp, end = 15.dp)
                ) {
                    Text(
                        text = "${item[15].year}.${item[15].monthValue}",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Medium,
                        fontFamily = notoSanskr
                    )

                    Row(
                        modifier = Modifier.weight(1f)
                    ) {
                        repeat(7) { idx ->
                            Text(
                                modifier = Modifier.weight(1f),
                                text = when (idx) {
                                    0 -> "일"
                                    1 -> "월"
                                    2 -> "화"
                                    3 -> "수"
                                    4 -> "목"
                                    5 -> "금"
                                    else -> "토"
                                },
                                style = medium14pt,
                                textAlign = TextAlign.Center,
                                color = when (idx) {
                                    0 -> Color(0xFFD14343)
                                    6 -> Color(0xFF3369D1)
                                    else -> Color(0xFF222222)
                                }
                            )
                        }
                    }
                    repeat(item.size / 7) { week ->
                        Row(
                            modifier = Modifier.weight(1f)
                        ) {
                            repeat(7) { idx ->
                                Text(
                                    modifier = Modifier.weight(1f),
                                    text = "${item[week * 7 + idx].dayOfMonth}",
                                    style = medium14pt,
                                    textAlign = TextAlign.Center,
                                    color = if (item[week * 7 + idx].monthValue != item[15].monthValue) Color(0xFF999999) else
                                        when (idx) {
                                            0 -> Color(0xFFD14343)
                                            6 -> Color(0xFF3369D1)
                                            else -> Color(0xFF222222)
                                        }
                                )
                            }
                        }
                    }

                    Spacer(Modifier.height(30.dp))
                }
            }
        }
    }

//    Box(
//        modifier = Modifier
//            .height(((CALENDAR_VIEW_HEIGHT + state.offset) / density).dp)
//            .anchoredDraggable(state, Orientation.Vertical)
//    ) {
//        DateCalendar(
//            currentMonthCalendarInfo = currentMonthCalendarInfo,
//            updateCurrentMonthCalendarInfo = updateCurrentMonthCalendarInfo,
//            selectedYear = selectedYear,
//            updateYear = updateYear,
//            selectedMonth = selectedMonth,
//            updateMonth = updateMonth,
//            selectedDate = selectedDate,
//            updateDate = updateDate,
//            anchorOffset = state.offset,
//            expandDetailContent = { coroutineScope.launch(Dispatchers.Default) { state.animateTo(DetailState.Open) } }
//        )
//    }
}