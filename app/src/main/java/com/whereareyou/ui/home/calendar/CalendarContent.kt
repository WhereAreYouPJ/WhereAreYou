package com.whereareyou.ui.home.calendar

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.whereareyou.R
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
            .padding(start = 20.dp, end = 20.dp)
            .fillMaxSize()
            .background(
                color = Color(0xFFFAFAFA)
            ),
        contentAlignment = Alignment.Center
    ) {
        val selectedDate = Calendar.getInstance()
        val currMonthCalendarInfo = CalendarUtil.getCalendarInfo(selectedDate.get(Calendar.YEAR), selectedDate.get(Calendar.MONTH))
        val calendarState = viewModel.calendarState.collectAsState().value
        val topBarHeight = LocalConfiguration.current.screenHeightDp / 10

        Box() {
            // 상단바
            Row(
                modifier = Modifier
                    .padding()
                    .fillMaxWidth()
                    .height(topBarHeight.dp)
                    .background(
                        color = Color(0xFFCE93D8)
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
//                Image(
//                    painter = painterResource(id = R.drawable.baseline_arrow_back_ios_24),
//                    contentDescription = null
//                )
                Text(
                    modifier = Modifier
                        .clickable {
                            viewModel.updateCalendarState(CalendarViewModel.CalendarState.YEAR)
                        },
                    text = "${selectedDate.get(Calendar.YEAR)}년"
                )
                Spacer(
                    modifier = Modifier
                        .width(20.dp)
                )
                Text(
                    modifier = Modifier
                        .clickable {
                            viewModel.updateCalendarState(CalendarViewModel.CalendarState.MONTH)
                        },
                    text = "${selectedDate.get(Calendar.MONTH)}월"
                )
//                Image(
//                        painter = painterResource(id = R.drawable.baseline_arrow_forward_ios_24),
//                contentDescription = null
//                )
            }

            // 일자 선택 화면
            AnimatedVisibility(
                modifier = Modifier
                    .padding(top = topBarHeight.dp),
                visible = calendarState == CalendarViewModel.CalendarState.DATE,
                enter = AnimationUtil.enterTransition,
                exit = AnimationUtil.exitTransition
            ) {
                Column() {
                    Row(
                        modifier = Modifier
                            .height((topBarHeight / 2).dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        for (i in 0..6) {
                            Box(
                                modifier = Modifier
                                    .weight(1f),
                                contentAlignment = Alignment.TopCenter
                            ) {
                                Text(
                                    modifier = Modifier
                                        .padding(top = 4.dp),
                                    text = when (i) {
                                        0 -> "월"
                                        1 -> "화"
                                        2 -> "수"
                                        3 -> "목"
                                        4 -> "금"
                                        5 -> "토"
                                        else -> "일"
                                    }
                                )
                            }
                        }
                    }
                    for (date in 0 until (currMonthCalendarInfo.size / 7)) {
                        Spacer(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(1.dp)
                                .background(
                                    color = Color(0xFFDDDDDD)
                                )
                        )
                        Row(
                            modifier = Modifier
                                .weight(1f)
                        ) {
                            for (i in 0..6) {
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .fillMaxHeight()
                                        .clickable {
                                            selectedDate.set(
                                                Calendar.DATE,
                                                currMonthCalendarInfo[i + date * 7].split(" ")[2].toInt()
                                            )
                                        },
                                    contentAlignment = Alignment.TopCenter
                                ) {
                                    Text(
                                        modifier = Modifier
                                            .padding(top = 4.dp),
                                        text = currMonthCalendarInfo[i + date * 7].split(" ")[2]
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // 월 선택 화면
            AnimatedVisibility(
                modifier = Modifier
                    .padding(top = topBarHeight.dp),
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
                                            viewModel.updateCalendarState(CalendarViewModel.CalendarState.DATE)
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

            // 년도 선택 화면
            AnimatedVisibility(
                modifier = Modifier
                    .padding(top = topBarHeight.dp),
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
                                    viewModel.updateCalendarState(CalendarViewModel.CalendarState.MONTH)
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