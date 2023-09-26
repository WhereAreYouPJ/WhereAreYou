package com.whereareyou.ui.home.calendar

import android.util.Log
import androidx.compose.animation.core.animateDpAsState
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
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.launch
import kotlin.math.roundToInt


enum class DetailState {
    Open, Close
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CalendarContent(
    paddingValues: PaddingValues,
    viewModel: CalendarViewModel = hiltViewModel(),
    topBarHeight: Int = LocalConfiguration.current.screenHeightDp / 12
) {
    var componentHeight by remember { mutableStateOf(0) }
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
                        DetailState.Close at componentHeight.toFloat()
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
                .background(
                    color = Color(0xFFFAFAFA)
                )
                .onGloballyPositioned {
                    componentHeight = (it.size.height / density.density).toInt()
                }
        ) {

            val year = viewModel.year.collectAsState().value
            val month = viewModel.month.collectAsState().value
            val date = viewModel.date.collectAsState().value
            val calendarState = viewModel.calendarState.collectAsState().value

            // 상단바
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(topBarHeight.dp)
                    .background(
                        color = Color(0xFFCE93D8)
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier
                        .clickable {
                            coroutineScope.launch {
                                state.animateTo(DetailState.Close)
                            }
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
                                coroutineScope.launch {
                                    state.animateTo(DetailState.Close)
                                }
                                viewModel.updateCalendarState(CalendarViewModel.CalendarState.MONTH)
                            },
                        text = "${month + 1}월"
                    )
                }
            }
            Box(
                modifier = Modifier
                    .height(((componentHeight - topBarHeight) *
                            (0.4f + 0.6f * (state.offset / density.density / ((componentHeight - topBarHeight) / 5 * 3)))).dp)
//                .height(state.requireOffset().dp)
            ) {
                DateCalendar(
                    expandDetailContent = {
                        coroutineScope.launch {
                            state.animateTo(DetailState.Open)
                        }
                    }
                )

                MonthContent(
                    hideDetailContent = {
                        coroutineScope.launch {
                            state.animateTo(DetailState.Open)
                        }
                    }
                )

                YearContent(
                    hideDetailContent = {
                    }
                )
            }
//        LaunchedEffect(true) {
//            state.animateTo(DetailState.Open)
//        }

        }

        LaunchedEffect(componentHeight) {
            state.updateAnchors(
                DraggableAnchors {
                    DetailState.Open at 0f
                    DetailState.Close at (topBarHeight + (componentHeight - topBarHeight) / 5 * 3) * density.density
                }
            )
        }
        Column(
            modifier = Modifier
                .padding(top = (topBarHeight + (componentHeight - topBarHeight) / 5 * 2).dp)
                .offset {
                    IntOffset(
                        x = 0,
                        y = state
                            .requireOffset()
                            .roundToInt()
                    )
                }
                .padding(start = 20.dp, end = 20.dp, top = 10.dp)
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
                    .height(((componentHeight - topBarHeight) / 5 * 3 - 20).dp)
                    .background(
                        color = Color(0xFFFFF9C4)
                    )
            ) {
                LazyColumn(

                ) {
                    item() {
                        Box(
                            modifier = Modifier
                                .padding(start = 20.dp, top = 20.dp, end = 20.dp)
                                .fillMaxWidth()
                                .height(40.dp)
                                .background(
                                    color = Color(0XFF80DEEA)
                                )
                        ) {

                        }
                    }
                    item() {
                        Box(
                            modifier = Modifier
                                .padding(start = 20.dp, top = 20.dp, end = 20.dp)
                                .fillMaxWidth()
                                .height(40.dp)
                                .background(
                                    color = Color(0XFF80DEEA)
                                )
                        ) {

                        }
                    }
                    item() {
                        Box(
                            modifier = Modifier
                                .padding(start = 20.dp, top = 20.dp, end = 20.dp)
                                .fillMaxWidth()
                                .height(40.dp)
                                .background(
                                    color = Color(0XFF80DEEA)
                                )
                        ) {

                        }
                    }
                    item() {
                        Box(
                            modifier = Modifier
                                .padding(start = 20.dp, top = 20.dp, end = 20.dp)
                                .fillMaxWidth()
                                .height(40.dp)
                                .background(
                                    color = Color(0XFF80DEEA)
                                )
                        ) {

                        }
                    }
                    item() {
                        Box(
                            modifier = Modifier
                                .padding(start = 20.dp, top = 20.dp, end = 20.dp)
                                .fillMaxWidth()
                                .height(40.dp)
                                .background(
                                    color = Color(0XFF80DEEA)
                                )
                        ) {

                        }
                    }
                    item() {
                        Box(
                            modifier = Modifier
                                .padding(start = 20.dp, top = 20.dp, end = 20.dp)
                                .fillMaxWidth()
                                .height(40.dp)
                                .background(
                                    color = Color(0XFF80DEEA)
                                )
                        ) {

                        }
                    }
                    item() {
                        Box(
                            modifier = Modifier
                                .padding(start = 20.dp, top = 20.dp, end = 20.dp)
                                .fillMaxWidth()
                                .height(40.dp)
                                .background(
                                    color = Color(0XFF80DEEA)
                                )
                        ) {

                        }
                    }
                    item() {
                        Box(
                            modifier = Modifier
                                .padding(start = 20.dp, top = 20.dp, end = 20.dp)
                                .fillMaxWidth()
                                .height(40.dp)
                                .background(
                                    color = Color(0XFF80DEEA)
                                )
                        ) {

                        }
                    }
                    item() {
                        Box(
                            modifier = Modifier
                                .padding(start = 20.dp, top = 20.dp, end = 20.dp)
                                .fillMaxWidth()
                                .height(40.dp)
                                .background(
                                    color = Color(0XFF80DEEA)
                                )
                        ) {

                        }
                    }
                    item() {
                        Box(
                            modifier = Modifier
                                .padding(start = 20.dp, top = 20.dp, end = 20.dp)
                                .fillMaxWidth()
                                .height(40.dp)
                                .background(
                                    color = Color(0XFF80DEEA)
                                )
                        ) {

                        }
                    }
                }
            }
        }
    }
}