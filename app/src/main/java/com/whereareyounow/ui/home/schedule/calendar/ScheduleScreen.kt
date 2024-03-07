package com.whereareyounow.ui.home.schedule.calendar

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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.whereareyounow.R
import com.whereareyounow.data.GlobalValue
import com.whereareyounow.data.calendar.CalendarScreenSideEffect
import com.whereareyounow.data.calendar.CalendarScreenUIState
import com.whereareyounow.data.calendar.Schedule
import com.whereareyounow.data.notification.DrawerNotificationContentSideEffect
import com.whereareyounow.data.notification.DrawerNotificationContentUIState
import com.whereareyounow.domain.entity.friend.FriendRequest
import com.whereareyounow.domain.entity.schedule.BriefSchedule
import com.whereareyounow.ui.home.schedule.notification.DrawerNotificationContent
import com.whereareyounow.ui.home.schedule.notification.DrawerNotificationContentViewModel
import com.whereareyounow.ui.theme.lato
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
    val coroutineScope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(
        initialValue = DrawerValue.Closed
    )
    // 사이드바가 오른쪽에서 열리게 하기 위한 Rtl
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        ModalNavigationDrawer(
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
            gesturesEnabled = false
        ) {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                // 하단 콘텐츠 상태
                val bottomContentState = remember { anchoredDraggableState }
                Column(
                    modifier = Modifier
                        .background(color = Color(0xFFF8F8F8))
                        .fillMaxSize()
                ) {
                    // 상단바
                    ScheduleScreenTopBar(
                        updateCurrentMonthCalendarInfo = updateCurrentMonthCalendarInfo,
                        selectedYear = calendarScreenUIState.selectedYear,
                        updateYear = updateYear,
                        selectedMonth = calendarScreenUIState.selectedMonth,
                        updateMonth = updateMonth,
                        drawerState = drawerState,
                        bottomContentState = bottomContentState
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
                }
                // 일별 간략한 일정
                BriefScheduleContent(
                    selectedYear = calendarScreenUIState.selectedYear,
                    selectedMonth = calendarScreenUIState.selectedMonth,
                    selectedDate = calendarScreenUIState.selectedDate,
                    dayOfWeek = calendarScreenUIState.selectedDayOfWeek,
                    currentDateBriefSchedule = calendarScreenUIState.selectedDateBriefScheduleInfoList,
                    moveToDetailScreen = moveToDetailScreen,
                    state = bottomContentState
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ScheduleScreenTopBar(
    updateCurrentMonthCalendarInfo: () -> Unit,
    selectedYear: Int,
    updateYear: (Int) -> Unit,
    selectedMonth: Int,
    updateMonth: (Int) -> Unit,
    drawerState: DrawerState,
    bottomContentState: AnchoredDraggableState<DetailState>
) {
    val density = LocalDensity.current
    val coroutineScope = rememberCoroutineScope()
    val yearDropdownPopupState = remember { PopupState(false, PopupPosition.BottomRight) }
    val monthDropdownPopupState = remember { PopupState(false, PopupPosition.BottomRight) }
    val currentYear = Calendar.getInstance().get(Calendar.YEAR)
    val yearList = (currentYear - 20 .. currentYear + 20).toList()
    val monthList = (1..12).toList()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height((GlobalValue.topBarHeight / density.density).dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(Modifier.width(20.dp))
        // 년도 선택
        Box {
            Text(
                modifier = Modifier.clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) { yearDropdownPopupState.isVisible = true },
                text = "${selectedYear}.",
                fontSize = 26.sp,
                fontFamily = lato,
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.em,
                color = Color(0xFF373737)
            )
            // 년도 선택 팝업
            CustomPopup(
                popupState = yearDropdownPopupState,
                onDismissRequest = { yearDropdownPopupState.isVisible = false }
            ) {
                Box(
                    modifier = Modifier
                        .width(100.dp)
                        .height(200.dp)
                        .padding(10.dp)
                        .offset(x = (-10).dp, y = (-8).dp)
                ) {
                    Surface(
                        color = Color(0xFFFFFFFF),
                        shadowElevation = 2.dp,
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        val listState = rememberLazyListState()
                        LaunchedEffect(Unit) { listState.scrollToItem(20) }
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                                .padding(top = 10.dp, bottom = 10.dp),
                            state = listState
                        ) {
                            itemsIndexed(yearList) {_, year ->
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(40.dp)
                                        .clickable {
                                            updateYear(year)
                                            updateCurrentMonthCalendarInfo()
                                            coroutineScope.launch {
                                                bottomContentState.animateTo(
                                                    DetailState.Close
                                                )
                                            }
                                            yearDropdownPopupState.isVisible = false
                                        },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "$year",
                                        color = Color(0xFF000000),
                                        fontSize = 20.sp
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
        Spacer(Modifier.width(10.dp))
        // 월 선택
        Box {
            Text(
                modifier = Modifier.clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) { monthDropdownPopupState.isVisible = true },
                text = "$selectedMonth",
                fontSize = 26.sp,
                fontFamily = lato,
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.em
            )
            // 월 선택 팝업
            CustomPopup(
                popupState = monthDropdownPopupState,
                onDismissRequest = { monthDropdownPopupState.isVisible = false }
            ) {
                Box(
                    modifier = Modifier
                        .width(100.dp)
                        .height(200.dp)
                        .padding(10.dp)
                        .offset(x = (-10).dp, y = (-8).dp)
                ) {
                    Surface(
                        color = Color(0xFFFFFFFF),
                        shadowElevation = 2.dp,
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        val listState = rememberLazyListState()
                        LaunchedEffect(Unit) { listState.scrollToItem(selectedMonth) }
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            itemsIndexed(monthList) {_, month ->
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(40.dp)
                                        .clickable {
                                            updateMonth(month)
                                            updateCurrentMonthCalendarInfo()
                                            coroutineScope.launch {
                                                bottomContentState.animateTo(
                                                    DetailState.Close
                                                )
                                            }
                                            monthDropdownPopupState.isVisible = false
                                        },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "$month",
                                        color = Color(0xFF000000),
                                        fontSize = 20.sp
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(end = 20.dp),
            contentAlignment = Alignment.CenterEnd
        ) {
            Image(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .clickable {
                        coroutineScope.launch(Dispatchers.Default) { drawerState.open() }
                    }
                    .padding(8.dp),
                painter = painterResource(id = R.drawable.icon_bell),
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
).apply {
        updateAnchors(
            DraggableAnchors {
                DetailState.Open at 0f
                DetailState.Close at GlobalValue.dailyScheduleViewHeight
            }
        )
    }

enum class DetailState {
    Open, Close
}



@OptIn(ExperimentalFoundationApi::class)
@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun ScheduleScreenPreview2() {
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
    val briefScheduleList = listOf(
        BriefSchedule("", "title", "2023-01-02T10:20:00")
    )
    val drawerState = rememberDrawerState(
        initialValue = DrawerValue.Closed,
        confirmStateChange = { true }
    )
    val bottomContentState = remember { anchoredDraggableState }
    Column(
        modifier = Modifier
            .background(color = Color(0xFFF8F8F8))
            .fillMaxSize()
    ) {
        // 상단바
        ScheduleScreenTopBar(
            updateCurrentMonthCalendarInfo = {},
            selectedYear = 2024,
            updateYear = {},
            selectedMonth = 1,
            updateMonth = {},
            drawerState = drawerState,
            bottomContentState = bottomContentState
        )
        // 달력 뷰
        CalendarContent(
            currentMonthCalendarInfo = previewSchedule,
            updateCurrentMonthCalendarInfo = {},
            selectedYear = 2024,
            updateYear = {},
            selectedMonth = 1,
            updateMonth = {},
            selectedDate = 2,
            updateDate = {},
            state = bottomContentState
        )
    }
    // 일별 간략한 일정
    BriefScheduleContent(
        selectedYear = 2024,
        selectedMonth = 1,
        selectedDate = 2,
        dayOfWeek = 3,
        currentDateBriefSchedule = briefScheduleList,
        moveToDetailScreen = {},
        state = bottomContentState
    )
}