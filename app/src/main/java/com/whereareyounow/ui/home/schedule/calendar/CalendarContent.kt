package com.whereareyounow.ui.home.schedule.calendar

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.animateTo
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.whereareyounow.data.GlobalValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CalendarContent(
    state: AnchoredDraggableState<DetailState>,
) {
    val density = LocalDensity.current
    val coroutineScope = rememberCoroutineScope()
    Box(
        modifier = Modifier.height(((GlobalValue.calendarViewHeight + state.offset) / density.density).dp)
    ) {
        DateCalendar(expandDetailContent = { coroutineScope.launch(Dispatchers.Default) { state.animateTo(DetailState.Open) } })
        MonthCalendar(expandDetailContent = { coroutineScope.launch(Dispatchers.Default) { state.animateTo(DetailState.Open) } })
        YearCalendar()
    }
}