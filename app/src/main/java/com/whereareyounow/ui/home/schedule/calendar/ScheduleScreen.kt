package com.whereareyounow.ui.home.schedule.calendar

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.animateTo
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.whereareyounow.R
import com.whereareyounow.data.GlobalValue
import com.whereareyounow.ui.home.schedule.notification.DrawerNotification
import com.whereareyounow.ui.home.schedule.notification.DrawerNotificationViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


enum class DetailState {
    Open, Close
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ScheduleScreen(
    paddingValues: PaddingValues,
    moveToDetailScreen: (String) -> Unit,
    viewModel: CalendarViewModel = hiltViewModel(),
    notificationViewModel: DrawerNotificationViewModel = hiltViewModel(),
) {
    val date = viewModel.date.collectAsState().value

    // 사이드바가 오른쪽에서 열리게 하기 위한 Rtl
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        val drawerState = rememberDrawerState(
            initialValue = DrawerValue.Closed,
            confirmStateChange = {
                notificationViewModel.loadFriendRequests()
                notificationViewModel.loadScheduleRequests()
                true
            }
        )
        ModalNavigationDrawer(
            drawerContent = { DrawerNotification(
                updateCalendar = viewModel::updateCurrentMonthCalendarInfo,
                updateBriefCalendar = { viewModel.updateDate(date) }
            ) },
            drawerState = drawerState,
        ) {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                // 하단 콘텐츠 상태
                val bottomContentState = remember { anchoredDraggableState }
                Column(
                    modifier = Modifier
                        .padding(start = 20.dp, end = 20.dp)
                        .fillMaxSize()
                        .background(color = Color(0xFFFAFAFA))
                ) {
                    // 상단바
                    TopBar(
                        drawerState = drawerState,
                        bottomContentState = bottomContentState
                    )
                    // 달력 뷰
                    CalendarContent(bottomContentState)
                }
                // 일별 간략한 일정
                BriefScheduleContent(
                    moveToDetailScreen = moveToDetailScreen,
                    state = bottomContentState
                )
            }
        }
    }
}

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
            .height((GlobalValue.topBarHeight / density.density).dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(Modifier.width(20.dp))
        Text(
            modifier = Modifier.clickable {
                    coroutineScope.launch(Dispatchers.Default) { bottomContentState.animateTo(DetailState.Close) }
                    viewModel.updateCalendarState(CalendarViewModel.CalendarState.YEAR)
                },
            text = "${year}.",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.width(10.dp))
        if (calendarState == CalendarViewModel.CalendarState.DATE) {
            Text(
                modifier = Modifier.clickable {
                        coroutineScope.launch { bottomContentState.animateTo(DetailState.Close) }
                        viewModel.updateCalendarState(CalendarViewModel.CalendarState.MONTH)
                    },
                text = "${month}",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.CenterEnd
        ) {
            Image(
                modifier = Modifier
                    .size(((GlobalValue.topBarHeight / 2) / density.density).dp)
                    .clickable { coroutineScope.launch(Dispatchers.Default) { drawerState.open() } },
                painter = painterResource(id = R.drawable.notifications_fill0_wght200_grad0_opsz24),
                contentDescription = null
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
private val anchoredDraggableState = AnchoredDraggableState(
    initialValue = DetailState.Open,
    positionalThreshold = { it: Float -> it * 0.5f },
    velocityThreshold = { 100f },
    animationSpec = tween(400)
)
    .apply {
        updateAnchors(
            DraggableAnchors {
                DetailState.Open at 0f
                DetailState.Close at GlobalValue.dailyScheduleViewHeight
            }
        )
    }