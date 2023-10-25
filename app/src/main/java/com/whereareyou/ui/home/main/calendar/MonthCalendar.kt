package com.whereareyou.ui.home.main.calendar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.whereareyou.util.AnimationUtil

@Composable
fun MonthCalendar(
    hideDetailContent: () -> Unit,
    viewModel: CalendarViewModel = hiltViewModel()
) {
    val calendarState = viewModel.calendarState.collectAsState().value

    // 월 선택 화면
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
                    for (j in 0..2) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight()
                                .clickable {
                                    hideDetailContent()
                                    viewModel.updateMonth(i * 3 + j)
                                    viewModel.updateCalendarState(CalendarViewModel.CalendarState.DATE)
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "${i * 3 + j + 1}월"
                            )
                        }
                    }
                }
            }
        }
    }
}