package com.whereareyou.ui.home.calendar

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.whereareyou.util.AnimationUtil
import com.whereareyou.util.CalendarUtil
import kotlinx.coroutines.launch
import java.util.Calendar

@Composable
fun CalendarContent(
    paddingValues: PaddingValues,
    viewModel: CalendarViewModel = hiltViewModel()
) {
    Box(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize()
            .background(
                color = Color(0xFFE3F2FD)
            ),
        contentAlignment = Alignment.Center
    ) {
        val calendar = Calendar.getInstance()
        val currMonthCalendarInfo = CalendarUtil.getCalendarInfo(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH))
        val calendarState = viewModel.calendarState.collectAsState().value
        val screenHeight = LocalConfiguration.current.screenHeightDp

        Column() {
            Row(
                modifier = Modifier
                    .height((screenHeight / 15).dp)
            ) {
                Text(
                    text = "${calendar.get(Calendar.YEAR)}"
                )
                Text(
                    text = "${calendar.get(Calendar.MONTH)}"
                )
            }
            AnimatedVisibility(
                visible = calendarState == CalendarViewModel.CalendarState.DATE,
                enter = AnimationUtil.enterTransition,
                exit = AnimationUtil.exitTransition
            ) {
                Column() {
                    for (date in 0 until (currMonthCalendarInfo.size / 7)) {
                        Row(
                            modifier = Modifier
                                .weight(1f)
                        ) {
                            for (i in 0..6)
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .fillMaxHeight()
                                        .clickable {
                                            viewModel.updateCalendarState(CalendarViewModel.CalendarState.MONTH)
                                            calendar.set(Calendar.DATE, currMonthCalendarInfo[i + date * 7].split(" ")[2].toInt())
                                        },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = currMonthCalendarInfo[i + date * 7].split(" ")[2]
                                    )
                                }
                        }
                    }
                }
            }

            AnimatedVisibility(
                visible = calendarState == CalendarViewModel.CalendarState.MONTH,
                enter = AnimationUtil.enterTransition,
                exit = AnimationUtil.exitTransition
            ) {
                Column() {
                    for (i in 0..3) {
                        Row(
                            modifier = Modifier
                                .weight(1f)
                        ) {
                            for (j in 1..3) {
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .fillMaxHeight()
                                        .clickable {
                                            viewModel.updateCalendarState(CalendarViewModel.CalendarState.YEAR)
                                        },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "${i * 3 + j}월"
                                    )
                                }
                            }
                        }
                    }
                }
            }

            AnimatedVisibility(
                visible = calendarState == CalendarViewModel.CalendarState.YEAR,
                enter = AnimationUtil.enterTransition,
                exit = AnimationUtil.exitTransition

            ) {
                val listState = rememberLazyGridState()
                LazyVerticalGrid(
                    columns = GridCells.Fixed(4),
                    state = listState
                ) {
                    itemsIndexed(CalendarUtil.getYearList()) { _, year ->
                        Box(
                            modifier = Modifier
                                .height(100.dp)
                                .clickable {
                                    viewModel.updateCalendarState(CalendarViewModel.CalendarState.DATE)
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "${year}년"
                            )
                        }
                    }
                }
                LaunchedEffect(true) {
                    val targetColumnIndex = 99
                    listState.scrollToItem(targetColumnIndex)
                }
            }
        }
    }
}