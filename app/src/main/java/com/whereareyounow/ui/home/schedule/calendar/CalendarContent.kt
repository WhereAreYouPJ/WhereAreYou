package com.whereareyounow.ui.home.schedule.calendar

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.gestures.animateTo
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.whereareyounow.data.calendar.Schedule
import com.whereareyounow.data.globalvalue.CALENDAR_VIEW_HEIGHT
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CalendarContent(
    currentMonthCalendarInfo: List<Schedule>,
    updateCurrentMonthCalendarInfo: () -> Unit,
    selectedYear: Int,
    updateYear: (Int) -> Unit,
    selectedMonth: Int,
    updateMonth: (Int) -> Unit,
    selectedDate: Int,
    updateDate: (Int) -> Unit,
    state: AnchoredDraggableState<DetailState>,
) {
    val density = LocalDensity.current
    val coroutineScope = rememberCoroutineScope()
    Box(
        modifier = Modifier
            .height(((CALENDAR_VIEW_HEIGHT + state.offset) / density.density).dp)
            .anchoredDraggable(state, Orientation.Vertical)
    ) {
        DateCalendar(
            currentMonthCalendarInfo = currentMonthCalendarInfo,
            updateCurrentMonthCalendarInfo = updateCurrentMonthCalendarInfo,
            selectedYear = selectedYear,
            updateYear = updateYear,
            selectedMonth = selectedMonth,
            updateMonth = updateMonth,
            selectedDate = selectedDate,
            updateDate = updateDate,
            anchorOffset = state.offset,
            expandDetailContent = { coroutineScope.launch(Dispatchers.Default) { state.animateTo(DetailState.Open) } }
        )
    }
}