package com.whereareyounow.ui.home.schedule.calendar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.whereareyounow.ui.theme.WhereAreYouTheme
import com.whereareyounow.util.AnimationUtil

@Composable
fun MonthCalendar(
    calendarState: CalendarViewModel.CalendarState,
    updateMonth: (Int) -> Unit,
    updateCalendarState: (CalendarViewModel.CalendarState) -> Unit,
    expandDetailContent: () -> Unit
) {
    // 월 선택 화면
    AnimatedVisibility(
        visible = calendarState == CalendarViewModel.CalendarState.MONTH,
        enter = AnimationUtil.enterTransition,
        exit = AnimationUtil.exitTransition
    ) {
        Column {
            for (i in 0..3) {
                Row(modifier = Modifier.weight(1f)) {
                    for (j in 0..2) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight()
                                .clickable {
                                    expandDetailContent()
                                    updateMonth(i * 3 + j + 1)
                                    updateCalendarState(CalendarViewModel.CalendarState.DATE)
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "${i * 3 + j + 1}월",
                                fontSize = 20.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun MonthCalendarPreview() {
    WhereAreYouTheme {
        MonthCalendar(
            calendarState = CalendarViewModel.CalendarState.MONTH,
            updateMonth = {  },
            updateCalendarState = {  },
            expandDetailContent = {  }
        )
    }
}