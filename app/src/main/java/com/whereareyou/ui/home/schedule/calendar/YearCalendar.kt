package com.whereareyou.ui.home.schedule.calendar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.whereareyou.util.AnimationUtil
import com.whereareyou.util.CalendarUtil

@Composable
fun YearCalendar(
    viewModel: CalendarViewModel = hiltViewModel()
) {

    val calendarState = viewModel.calendarState.collectAsState().value

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
                            viewModel.updateYear(year)
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