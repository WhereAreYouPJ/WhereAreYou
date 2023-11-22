package com.whereareyou.ui.home.schedule.calendar

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.whereareyou.data.GlobalValue
import com.whereareyou.util.AnimationUtil

@Composable
fun DateCalendar(
    expandDetailContent: () -> Unit,
    viewModel: CalendarViewModel = hiltViewModel()
) {
    val currMonthCalendarInfo = viewModel.currentMonthDateInfo
    val calendarState = viewModel.calendarState.collectAsState().value
    val selectedYear = viewModel.year.collectAsState().value
    val selectedMonth = viewModel.month.collectAsState().value
    val selectedDate = viewModel.date.collectAsState().value
    // 일자 선택 화면
    AnimatedVisibility(
        visible = calendarState == CalendarViewModel.CalendarState.DATE,
        enter = AnimationUtil.enterTransition,
        exit = AnimationUtil.exitTransition
    ) {
        Column() {
            Row(
                modifier = Modifier
                    .height(((GlobalValue.topAppBarHeight / GlobalValue.density) / 2).dp),
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
                                0 -> "일"
                                1 -> "월"
                                2 -> "화"
                                3 -> "수"
                                4 -> "목"
                                5 -> "금"
                                else -> "토"
                            }
                        )
                    }
                }
            }

            for (date in 0 until (currMonthCalendarInfo.size / 7)) {
                Row(
                    modifier = Modifier
                        .weight(1f)
                ) {
                    for (i in 0..6) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight()
                                .clickable(
//                                    indication = null,
//                                    interactionSource = remember { MutableInteractionSource() }
                                ) {
                                    viewModel.updateDate(currMonthCalendarInfo[i + date * 7].date)
                                    viewModel.updateMonth(currMonthCalendarInfo[i + date * 7].month)
                                    viewModel.updateYear(currMonthCalendarInfo[i + date * 7].year)
                                    expandDetailContent()
                                },
                            contentAlignment = Alignment.TopCenter
                        ) {
                            DateContent(
                                date = currMonthCalendarInfo[i + date * 7].date,
                                scheduleNumber = currMonthCalendarInfo[i + date * 7].scheduleCount,
                                isSelected = selectedYear == currMonthCalendarInfo[i + date * 7].year &&
                                        selectedDate == currMonthCalendarInfo[i + date * 7].date &&
                                        selectedMonth == currMonthCalendarInfo[i + date * 7].month
                            )
                        }
                    }
                }
            }
        }
    }
}