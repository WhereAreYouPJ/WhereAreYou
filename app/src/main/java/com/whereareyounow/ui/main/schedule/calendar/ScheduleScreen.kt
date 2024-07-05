package com.whereareyounow.ui.main.schedule.calendar

import android.widget.Toast
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.animateTo
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.whereareyounow.R
import com.whereareyounow.data.calendar.CalendarScreenSideEffect
import com.whereareyounow.data.calendar.CalendarScreenUIState
import com.whereareyounow.data.calendar.Schedule
import com.whereareyounow.data.globalvalue.DAILY_BRIEF_SCHEDULE_VIEW_HEIGHT
import com.whereareyounow.data.globalvalue.TOP_BAR_HEIGHT
import com.whereareyounow.data.notification.DrawerNotificationContentSideEffect
import com.whereareyounow.data.notification.DrawerNotificationContentUIState
import com.whereareyounow.domain.entity.friend.FriendRequest
import com.whereareyounow.domain.entity.schedule.BriefSchedule
import com.whereareyounow.ui.main.schedule.notification.DrawerNotificationContent
import com.whereareyounow.ui.main.schedule.notification.DrawerNotificationContentViewModel
import com.whereareyounow.ui.theme.getColor
import com.whereareyounow.ui.theme.lato
import com.whereareyounow.util.CustomPreview
import com.whereareyounow.util.clickableNoEffect
import com.whereareyounow.util.popupmenu.CustomPopup
import com.whereareyounow.util.popupmenu.PopupPosition
import com.whereareyounow.util.popupmenu.PopupState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Calendar

@Composable
fun ScheduleScreen(
    paddingValues: PaddingValues,
    moveToDetailScreen: (String) -> Unit,
    notificationViewModel: DrawerNotificationContentViewModel = hiltViewModel(),
    calendarViewModel: CalendarViewModel = hiltViewModel(),
) {
    val calendarScreenUIState = calendarViewModel.calendarScreenUIState.collectAsState().value
    val calendarScreenSideEffectFlow = calendarViewModel.calendarScreenSideEffectFlow
    val drawerNotificationContentUIState = notificationViewModel.drawerNotificationUIState.collectAsState().value
    val drawerNotificationContentSideEffectFlow = notificationViewModel.drawerNotificationContentSideEffectFlow
    ScheduleScreen(
        paddingValues = paddingValues,
        calendarScreenUIState = calendarScreenUIState,
        calendarScreenSideEffectFlow = calendarScreenSideEffectFlow,
        drawerNotificationContentUIState = drawerNotificationContentUIState,
        drawerNotificationContentSideEffectFlow = drawerNotificationContentSideEffectFlow,
        acceptFriendRequest = notificationViewModel::acceptFriendRequest,
        refuseFriendRequest = notificationViewModel::refuseFriendRequest,
        acceptScheduleRequest = notificationViewModel::acceptScheduleRequest,
        refuseScheduleRequest = notificationViewModel::refuseScheduleRequest,
        updateYear = calendarViewModel::updateYear,
        updateMonth = calendarViewModel::updateMonth,
        updateDate = calendarViewModel::updateDate,
        updateCurrentMonthCalendarInfo = calendarViewModel::updateCurrentMonthCalendarInfo,
        updateCurrentDateBriefScheduleInfo = calendarViewModel::updateCurrentDateBriefScheduleInfo,
        loadFriendRequests = notificationViewModel::loadFriendRequests,
        loadScheduleRequests = notificationViewModel::loadScheduleRequests,
        getTodayScheduleCount = notificationViewModel::getTodayScheduleCount,
        moveToDetailScreen = moveToDetailScreen
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ScheduleScreen(
    paddingValues: PaddingValues,
    calendarScreenUIState: CalendarScreenUIState,
    calendarScreenSideEffectFlow: SharedFlow<CalendarScreenSideEffect>,
    drawerNotificationContentUIState: DrawerNotificationContentUIState,
    drawerNotificationContentSideEffectFlow: SharedFlow<DrawerNotificationContentSideEffect>,
    acceptFriendRequest: (FriendRequest) -> Unit,
    refuseFriendRequest: (FriendRequest) -> Unit,
    acceptScheduleRequest: (String, () -> Unit, () -> Unit) -> Unit,
    refuseScheduleRequest: (String, () -> Unit, () -> Unit) -> Unit,
    updateYear: (Int) -> Unit,
    updateMonth: (Int) -> Unit,
    updateDate: (Int) -> Unit,
    updateCurrentMonthCalendarInfo: () -> Unit,
    updateCurrentDateBriefScheduleInfo: () -> Unit,
    loadFriendRequests: () -> Unit,
    loadScheduleRequests: () -> Unit,
    getTodayScheduleCount: () -> Unit,
    moveToDetailScreen: (String) -> Unit
) {
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        loadFriendRequests()
        loadScheduleRequests()
        getTodayScheduleCount()
        updateCurrentMonthCalendarInfo()
        updateCurrentDateBriefScheduleInfo()
        launch {
            calendarScreenSideEffectFlow.collect { value ->
                when (value) {
                    is CalendarScreenSideEffect.Toast -> {
                        withContext(Dispatchers.Main) { Toast.makeText(context, value.text, Toast.LENGTH_SHORT).show() }
                    }
                }
            }
        }
        launch {
            drawerNotificationContentSideEffectFlow.collect { value ->
                when (value) {
                    is DrawerNotificationContentSideEffect.Toast -> {
                        withContext(Dispatchers.Main) { Toast.makeText(context, value.text, Toast.LENGTH_SHORT).show() }
                    }
                }
            }
        }
    }
    val drawerState = rememberDrawerState(
        initialValue = DrawerValue.Closed
    )
    // 사이드바가 오른쪽에서 열리게 하기 위한 Rtl
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        ModalNavigationDrawer(
            modifier = Modifier
                .semantics { contentDescription = "Main Calendar Screen" },
//            modifier = Modifier.padding(bottom = paddingValues.calculateBottomPadding()),
            drawerContent = {
                DrawerNotificationContent(
                    drawerNotificationContentUIState = drawerNotificationContentUIState,
                    updateCalendar = updateCurrentMonthCalendarInfo,
                    updateBriefCalendar = updateCurrentDateBriefScheduleInfo,
                    acceptFriendRequest = acceptFriendRequest,
                    refuseFriendRequest = refuseFriendRequest,
                    acceptScheduleRequest = acceptScheduleRequest,
                    refuseScheduleRequest = refuseScheduleRequest,
                    drawerState = drawerState
                )
            },
            drawerState = drawerState,
            gesturesEnabled = drawerState.isOpen
        ) {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                // 하단 콘텐츠 상태
                val bottomContentState = remember { anchoredDraggableState }
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    // 상단바
                    ScheduleScreenTopBar(
                        onNotificationIconClicked = {},
                        onPlusIconClicked = {}
                    )
                    // 달력 뷰
                    CalendarContent(
                        currentMonthCalendarInfo = calendarScreenUIState.selectedMonthCalendarInfoList,
                        updateCurrentMonthCalendarInfo = updateCurrentMonthCalendarInfo,
                        selectedYear = calendarScreenUIState.selectedYear,
                        updateYear = updateYear,
                        selectedMonth = calendarScreenUIState.selectedMonth,
                        updateMonth = updateMonth,
                        selectedDate = calendarScreenUIState.selectedDate,
                        updateDate = updateDate,
                        state = bottomContentState
                    )

                    Spacer(Modifier.height(paddingValues.calculateBottomPadding()))
                }
            }
        }
    }
}

@Composable
fun ScheduleScreenTopBar(
    onNotificationIconClicked: () -> Unit,
    onPlusIconClicked: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(TOP_BAR_HEIGHT.dp)
            .padding(start = 15.dp, end = 15.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(Modifier.weight(1f))

        Image(
            modifier = Modifier
                .size(34.dp)
                .clickableNoEffect { onNotificationIconClicked() },
            painter = painterResource(R.drawable.ic_bell),
            contentDescription = null,
            colorFilter = ColorFilter.tint(getColor().brandColor)
        )

        Image(
            modifier = Modifier
                .size(34.dp)
                .clickableNoEffect { onPlusIconClicked() },
            painter = painterResource(id = R.drawable.ic_plus),
            contentDescription = null,
            colorFilter = ColorFilter.tint(getColor().brandColor)
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
private val anchoredDraggableState = AnchoredDraggableState(
    initialValue = DetailState.Open,
    positionalThreshold = { it: Float -> it * 0.5f },
    velocityThreshold = { 100f },
    animationSpec = tween(200)
).apply {
        updateAnchors(
            DraggableAnchors {
                DetailState.Open at 0f
                DetailState.Close at DAILY_BRIEF_SCHEDULE_VIEW_HEIGHT
            }
        )
    }

enum class DetailState {
    Open, Close
}



@OptIn(ExperimentalFoundationApi::class)
@CustomPreview
@Composable
private fun ScheduleScreenPreview2() {
}