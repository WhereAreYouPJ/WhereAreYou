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
            velocityThreshold = { 0.5f },
            animationSpec = tween(500)
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
                DateContent(
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
        Column() {
            Spacer(
                modifier = Modifier
                    .height((topBarHeight + (componentHeight - topBarHeight) / 5 * 2).dp)
            )
            Box(
                modifier = Modifier
                    .size(80.dp)
//                .height(animatedSize)
                    .offset {
                        IntOffset(
                            x = 0,
                            y = state
                                .requireOffset()
                                .roundToInt()
                        )
                    }
                    .anchoredDraggable(state, Orientation.Vertical)
                    .background(
                        color = Color(0xFF00FF00)
                    )
            )
        }
    }
}