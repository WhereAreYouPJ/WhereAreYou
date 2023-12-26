package com.whereareyounow.ui.home.schedule.calendar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.whereareyounow.data.GlobalValue
import com.whereareyounow.ui.theme.lato
import com.whereareyounow.util.AnimationUtil

@Composable
fun DateCalendar(
    expandDetailContent: () -> Unit,
    viewModel: CalendarViewModel = hiltViewModel()
) {
    val currMonthCalendarInfo = viewModel.currentMonthCalendarInfoList
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
                modifier = Modifier.height(((GlobalValue.topBarHeight / GlobalValue.density) / 2).dp),
                horizontalArrangement = Arrangement.Center
            ) {
                for (i in 0..6) {
                    Box(
                        modifier = Modifier.weight(1f),
                        contentAlignment = Alignment.TopCenter
                    ) {
                        Text(
                            modifier = Modifier.padding(top = 4.dp),
                            text = when (i) {
                                0 -> "일"
                                1 -> "월"
                                2 -> "화"
                                3 -> "수"
                                4 -> "목"
                                5 -> "금"
                                else -> "토"
                            },
                            color = when (i) {
                                0 -> Color.Red
                                else -> Color.Black
                            },
                            fontFamily = lato
                        )
                    }
                }
            }

            for (idx in 0 until (currMonthCalendarInfo.size / 7)) {
                Row(modifier = Modifier.weight(1f)) {
                    for (i in 0..6) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight()
                                .clickable(
                                    indication = null,
                                    interactionSource = remember { MutableInteractionSource() }
                                ) {
                                    val year = currMonthCalendarInfo[i + idx * 7].year
                                    val month = currMonthCalendarInfo[i + idx * 7].month
                                    val date = currMonthCalendarInfo[i + idx * 7].date
                                    viewModel.updateYear(year)
                                    viewModel.updateMonth(month)
                                    viewModel.updateDate(date)
                                    expandDetailContent()
                                },
                            contentAlignment = Alignment.TopCenter
                        ) {
                            DateContent(
                                date = currMonthCalendarInfo[i + idx * 7].date,
                                scheduleCount = currMonthCalendarInfo[i + idx * 7].scheduleCount,
                                isSelected = selectedYear == currMonthCalendarInfo[i + idx * 7].year &&
                                        selectedDate == currMonthCalendarInfo[i + idx * 7].date &&
                                        selectedMonth == currMonthCalendarInfo[i + idx * 7].month,
                                textColor = when (i) {
                                    0 -> Color.Red
                                    else -> Color.Black
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}