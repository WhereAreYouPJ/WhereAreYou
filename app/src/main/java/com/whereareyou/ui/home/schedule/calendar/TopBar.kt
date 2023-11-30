package com.whereareyou.ui.home.schedule.calendar

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.animateTo
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.whereareyou.R
import com.whereareyou.data.GlobalValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TopBar(
    drawerState: DrawerState,
    bottomContentState: AnchoredDraggableState<DetailState>,
    viewModel: CalendarViewModel = hiltViewModel()
) {
    val density = LocalDensity.current
    val calendarState = viewModel.calendarState.collectAsState().value
    val year = viewModel.year.collectAsState().value
    val month = viewModel.month.collectAsState().value
    val coroutineScope = rememberCoroutineScope()
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height((GlobalValue.topAppBarHeight / density.density).dp)
            .background(color = Color(0xFFCE93D8)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier
                .clickable {
                    coroutineScope.launch(Dispatchers.Default) { bottomContentState.animateTo(DetailState.Close) }
                    viewModel.updateCalendarState(CalendarViewModel.CalendarState.YEAR)
                },
            text = "${year}.",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(
            modifier = Modifier
                .width(10.dp)
        )
        if (calendarState == CalendarViewModel.CalendarState.DATE) {
            Text(
                modifier = Modifier
                    .clickable {
                        coroutineScope.launch { bottomContentState.animateTo(DetailState.Close) }
                        viewModel.updateCalendarState(CalendarViewModel.CalendarState.MONTH)
                    },
                text = "${month}",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.CenterEnd
        ) {
//            Text(
//                modifier = Modifier
//                    .clickable {
//                        coroutineScope.launch(Dispatchers.Default) { drawerState.open() }
//                    },
//                text = "알림",
//            )
            Image(
                modifier = Modifier
                    .size(30.dp)
                    .clickable { coroutineScope.launch(Dispatchers.Default) { drawerState.open() } },
                painter = painterResource(id = R.drawable.notifications_fill0_wght200_grad0_opsz24),
                contentDescription = null
            )
        }
    }
}