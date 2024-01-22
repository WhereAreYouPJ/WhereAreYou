package com.whereareyounow.ui.home.schedule.calendar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.whereareyounow.ui.theme.WhereAreYouTheme
import com.whereareyounow.util.AnimationUtil
import com.whereareyounow.util.CalendarUtil

@Composable
fun YearCalendar(
    selectedYear: Int,
    calendarState: CalendarViewModel.CalendarState,
    updateYear: (Int) -> Unit,
    updateCalendarState: (CalendarViewModel.CalendarState) -> Unit,
) {
    // 년도 선택 화면
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
                            updateYear(year)
                            updateCalendarState(CalendarViewModel.CalendarState.MONTH)
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        modifier = Modifier
                            .background(
                                color = if (selectedYear == year) Color.Cyan else Color(0xFFFFFFFF),
                                shape = RoundedCornerShape(10.dp)
                            )
                            .padding(10.dp),
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

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun YearCalendarPreview() {
    WhereAreYouTheme {
        YearCalendar(
            selectedYear = 2024,
            calendarState = CalendarViewModel.CalendarState.YEAR,
            updateYear = {  },
            updateCalendarState = {  }
        )
    }
}