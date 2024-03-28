package com.whereareyounow.ui.home.schedule.calendar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.whereareyounow.data.calendar.Schedule
import com.whereareyounow.ui.theme.WhereAreYouTheme
import com.whereareyounow.ui.theme.lato

@Composable
fun DateCalendar(
    currentMonthCalendarInfo: List<Schedule>,
    updateCurrentMonthCalendarInfo: () -> Unit,
    selectedYear: Int,
    updateYear: (Int) -> Unit,
    selectedMonth: Int,
    updateMonth: (Int) -> Unit,
    selectedDate: Int,
    updateDate: (Int) -> Unit,
    anchorOffset: Float,
    expandDetailContent: () -> Unit
) {
    // 일자 선택 화면
    Column {
        Row {
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
                            0, 6 -> Color(0xFFA8361D)
                            else -> Color(0xFF000000)
                        },
                        fontSize = 12.sp,
                        fontFamily = lato,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
        Spacer(Modifier.height(10.dp))

        for (idx in 0 until (currentMonthCalendarInfo.size / 7)) {
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
                                val year = currentMonthCalendarInfo[i + idx * 7].year
                                val month = currentMonthCalendarInfo[i + idx * 7].month
                                val date = currentMonthCalendarInfo[i + idx * 7].date
                                if (selectedYear != year || selectedMonth != month) {
                                    updateYear(year)
                                    updateMonth(month)
                                    updateCurrentMonthCalendarInfo()
                                }
                                updateDate(date)
                                expandDetailContent()
                            },
                        contentAlignment = Alignment.TopCenter
                    ) {
                        DateBox(
                            anchorOffset = anchorOffset,
                            year = currentMonthCalendarInfo[i + idx * 7].year,
                            month = currentMonthCalendarInfo[i + idx * 7].month,
                            date = currentMonthCalendarInfo[i + idx * 7].date,
                            scheduleCount = currentMonthCalendarInfo[i + idx * 7].scheduleCount,
                            isSelected = selectedYear == currentMonthCalendarInfo[i + idx * 7].year &&
                                    selectedDate == currentMonthCalendarInfo[i + idx * 7].date &&
                                    selectedMonth == currentMonthCalendarInfo[i + idx * 7].month,
                            textColor = when (i) {
                                0 -> if (currentMonthCalendarInfo[i + idx * 7].month == selectedMonth) Color(0xFFA8361D) else Color(0xFFD3AFAF)
                                else -> if (currentMonthCalendarInfo[i + idx * 7].month == selectedMonth) Color(0xFF000000) else Color(0xFFBDBDBD)
                            }
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, heightDp = 308)
@Composable
private fun DateCalendarPreview() {
    val previewSchedule = listOf(
        Schedule(2024, 12, 31),
        Schedule(2024, 1, 1),
        Schedule(2024, 1, 2),
        Schedule(2024, 1, 3),
        Schedule(2024, 1, 4, 1),
        Schedule(2024, 1, 5, 2),
        Schedule(2024, 1, 6, 3),
        Schedule(2024, 1, 7, 4),
        Schedule(2024, 1, 8, 5),
        Schedule(2024, 1, 9, 6),
        Schedule(2024, 1, 10),
        Schedule(2024, 1, 11),
        Schedule(2024, 1, 12),
        Schedule(2024, 1, 13),
        Schedule(2024, 1, 14),
        Schedule(2024, 1, 15),
        Schedule(2024, 1, 16),
        Schedule(2024, 1, 17),
        Schedule(2024, 1, 18),
        Schedule(2024, 1, 19),
        Schedule(2024, 1, 20),
        Schedule(2024, 1, 21),
        Schedule(2024, 1, 22),
        Schedule(2024, 1, 23),
        Schedule(2024, 1, 24),
        Schedule(2024, 1, 25),
        Schedule(2024, 1, 26),
        Schedule(2024, 1, 27),
        Schedule(2024, 1, 28),
        Schedule(2024, 1, 29),
        Schedule(2024, 1, 30),
        Schedule(2024, 1, 31),
        Schedule(2024, 2, 1),
        Schedule(2024, 2, 2),
        Schedule(2024, 2, 3),
    )
    WhereAreYouTheme {
        DateCalendar(
            currentMonthCalendarInfo = previewSchedule,
            updateCurrentMonthCalendarInfo = {},
            selectedYear = 2024,
            updateYear = {  },
            selectedMonth = 1,
            updateMonth = {  },
            selectedDate = 2,
            updateDate = {  },
            anchorOffset = 0f,
            expandDetailContent = {  }
        )
    }
}
