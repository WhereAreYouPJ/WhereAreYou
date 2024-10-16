package com.whereareyounow.ui.main.schedule.calendar

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.animateTo
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.whereareyounow.R
import com.whereareyounow.data.calendar.CalendarScreenSideEffect
import com.whereareyounow.data.calendar.CalendarScreenUIState
import com.whereareyounow.data.globalvalue.TOP_BAR_HEIGHT
import com.whereareyounow.globalvalue.type.AnchoredDraggableContentState
import com.whereareyounow.ui.component.CustomSurfaceState
import com.whereareyounow.ui.component.DimBackground
import com.whereareyounow.ui.component.ScrollablePicker
import com.whereareyounow.ui.main.schedule.calendar.component.DailyScheduleBottomDialog
import com.whereareyounow.ui.main.schedule.calendar.component.DateBox
import com.whereareyounow.ui.main.schedule.calendar.component.getDailyScheduleAnchoredDraggableState
import com.whereareyounow.ui.theme.OnMyWayTheme
import com.whereareyounow.ui.theme.getColor
import com.whereareyounow.ui.theme.medium14pt
import com.whereareyounow.ui.theme.medium16pt
import com.whereareyounow.ui.theme.notoSanskr
import com.whereareyounow.util.CustomPreview
import com.whereareyounow.util.calendar.getLastDayOfMonth
import com.whereareyounow.util.clickableNoEffect
import com.whereareyounow.util.popupmenu.CustomPopup
import com.whereareyounow.util.popupmenu.PopupPosition
import com.whereareyounow.util.popupmenu.PopupState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import java.time.LocalDate

@Composable
fun CalendarScreen(
    customSurfaceState: CustomSurfaceState,
    moveToAddScheduleScreen: (Int, Int, Int) -> Unit,
    moveToDetailScheduleScreen: (Int) -> Unit,
    viewModel: CalendarViewModel = hiltViewModel(),
) {
    val uiState = viewModel.uiState.collectAsState().value
    val sideEffectFlow = viewModel.calendarScreenSideEffectFlow
    LaunchedEffect(Unit) {
        customSurfaceState.statusBarColor = Color.Transparent
        viewModel.updateCurrentMonthCalendarInfo()
    }
    CalendarScreen(
        uiState = uiState,
        sideEffectFlow = sideEffectFlow,
        updateSelectedYear = viewModel::updateYear,
        updateSelectedMonth = viewModel::updateMonth,
        updateSelectedDate = viewModel::updateDate,
        deleteSchedule = viewModel::deleteSchedule,
        moveToAddScheduleScreen = moveToAddScheduleScreen,
        moveToDetailScheduleScreen = moveToDetailScheduleScreen
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CalendarScreen(
    uiState: CalendarScreenUIState,
    sideEffectFlow: MutableSharedFlow<CalendarScreenSideEffect>,
    updateSelectedYear: (Int) -> Unit,
    updateSelectedMonth: (Int, Int) -> Unit,
    updateSelectedDate: (Int, Int, Int) -> Unit,
    deleteSchedule: (Int) -> Unit,
    moveToAddScheduleScreen: (Int, Int, Int) -> Unit,
    moveToDetailScheduleScreen: (Int) -> Unit,
) {
    var isMonthPickerShowing by remember { mutableStateOf(false) }
    val yearScrollableState = remember { mutableIntStateOf(uiState.selectedYear) }
    val monthScrollableState = remember { mutableIntStateOf(uiState.selectedMonth) }
    val dateScrollableState = remember(uiState.selectedYear, uiState.selectedMonth) { mutableIntStateOf(uiState.selectedDate) }
    val yearMap = (2000..2100).associateWith { "${it}년" }
    val monthMap = (1 .. 12).associateWith { "${it}월" }
    val dateMap = (1 .. getLastDayOfMonth(yearScrollableState.intValue, monthScrollableState.intValue)).associateWith { "${it}일" }
    val dialogSelectedYear = remember(uiState.selectedYear) { mutableIntStateOf(uiState.selectedYear) }
    val dialogSelectedMonth = remember(uiState.selectedMonth) { mutableIntStateOf(uiState.selectedMonth) }
    val dialogSelectedDate = remember(uiState.selectedDate) { mutableIntStateOf(uiState.selectedDate) }
    val dailyScheduleAnchoredDraggableState = remember { getDailyScheduleAnchoredDraggableState() }
    val isDimBackgroundShowing = remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val today = remember { LocalDate.now() }
    val popupState = remember { PopupState(false, PopupPosition.BottomLeft) }
    val density = LocalDensity.current.density
    val isDeleteWarningDialogShowing = remember { mutableStateOf(false) }
    val isGroupSelected = remember { mutableStateOf(false) }
    val deleteTargetScheduleSeq = remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {

    }
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .padding(start = 20.dp, end = 15.dp)
                    .height(TOP_BAR_HEIGHT.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier
                        .clickableNoEffect {
                            dialogSelectedYear.intValue = uiState.selectedYear
                            dialogSelectedMonth.intValue = uiState.selectedMonth
                            dialogSelectedDate.intValue = uiState.selectedDate
                            isMonthPickerShowing = true
                        }
                        .padding(start = 10.dp, top = 4.dp, end = 10.dp, bottom = 4.dp),
                    text = "${uiState.selectedYear}-${String.format("%02d", uiState.selectedMonth)}",
                    color = Color(0xFF111111),
                    fontFamily = notoSanskr,
                    fontWeight = FontWeight.Medium,
                    fontSize = 22.sp
                )

                Image(
                    modifier = Modifier
                        .size(18.dp)
                        .clickableNoEffect {
                            dialogSelectedYear.intValue = uiState.selectedYear
                            dialogSelectedMonth.intValue = uiState.selectedMonth
                            dialogSelectedDate.intValue = uiState.selectedDate
                            isMonthPickerShowing = true
                        },
                    painter = painterResource(R.drawable.ic_up_down_button),
                    contentDescription = null
                )

                Spacer(Modifier.weight(1f))

                Image(
                    modifier = Modifier.size(34.dp),
                    painter = painterResource(R.drawable.ic_bell),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(getColor().brandColor)
                )

                Box(
                    modifier = Modifier.clickableNoEffect {
                        popupState.isVisible = true
                    }
                ) {
                    Image(
                        modifier = Modifier.size(34.dp),
                        painter = painterResource(R.drawable.ic_plus),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(getColor().brandColor)
                    )

                    if (popupState.isVisible) {
                        CustomPopup(
                            popupState = popupState,
                            onDismissRequest = { popupState.isVisible = false }
                        ) {
                            CompositionLocalProvider(LocalDensity provides Density(density, fontScale = 1f)) {
                                Box(
                                    modifier = Modifier
                                        .width(160.dp)
                                        .height(38.dp)
                                        .clip(RoundedCornerShape(10.dp))
                                        .background(color = Color(0xFF7262A8))
                                        .clickableNoEffect {
                                            popupState.isVisible = false
                                            moveToAddScheduleScreen(
                                                uiState.selectedYear,
                                                uiState.selectedMonth,
                                                uiState.selectedDate
                                            )
                                        }
                                ) {
                                    Column(
                                        modifier = Modifier.fillMaxSize(),
                                        verticalArrangement = Arrangement.Center,
                                        horizontalAlignment = Alignment.Start
                                    ) {
                                        Text(
                                            modifier = Modifier
                                                .padding(
                                                    start = 14.dp,
                                                    top = 8.dp,
                                                    bottom = 6.dp
                                                ),
                                            text = "일정 추가",
                                            style = medium14pt,
                                            color = Color.White
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            Spacer(Modifier.height(20.dp))

            Row(
                modifier = Modifier
                    .padding(start = 20.dp, end = 20.dp)
                    .fillMaxWidth()
            ) {
                repeat(7) {
                    Text(
                        modifier = Modifier.weight(1f),
                        text = when (it) {
                            0 -> "일"
                            1 -> "월"
                            2 -> "화"
                            3 -> "수"
                            4 -> "목"
                            5 -> "금"
                            else -> "토"
                        },
                        style = medium14pt,
                        color = when (it) {
                            0 -> Color(0xFFFF7D96)
                            6 -> Color(0xFF397DFF)
                            else -> Color(0xFF666666)
                        },
                        textAlign = TextAlign.Center
                    )
                }
            }

            Spacer(Modifier.height(10.dp))

            Spacer(
                modifier = Modifier
                    .padding(start = 20.dp, end = 20.dp)
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Color(0xFFC9C9C9))
            )

            Column(
                modifier = Modifier
                    .padding(start = 20.dp, end = 20.dp)
                    .fillMaxHeight()
            ) {
                repeat(uiState.dateList.size / 7) { week ->
                    Row(
                        modifier = Modifier.weight(1f),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        repeat(7) { idx ->
                            DateBox(
                                date = uiState.dateList[week * 7 + idx].dayOfMonth,
                                isSelected = uiState.selectedDate == uiState.dateList[week * 7 + idx].dayOfMonth && uiState.selectedMonth == uiState.dateList[week * 7 + idx].monthValue,
                                isToday = uiState.dateList[week * 7 + idx].year == today.year && uiState.dateList[week * 7 + idx].monthValue == today.monthValue && uiState.dateList[week * 7 + idx].dayOfMonth == today.dayOfMonth,
                                isCurrentMonth = uiState.selectedMonth == uiState.dateList[week * 7 + idx].monthValue,
                                scheduleList = uiState.selectedMonthCalendarInfoMap[uiState.dateList[week * 7 + idx].toString()]!!.toList(),
                                onClick = {
                                    updateSelectedYear(uiState.dateList[week * 7 + idx].year)
                                    updateSelectedMonth(uiState.dateList[week * 7 + idx].year, uiState.dateList[week * 7 + idx].monthValue)
                                    updateSelectedDate(uiState.dateList[week * 7 + idx].year, uiState.dateList[week * 7 + idx].monthValue, uiState.dateList[week * 7 + idx].dayOfMonth)
                                    coroutineScope.launch {
                                        isDimBackgroundShowing.value = true
                                        dailyScheduleAnchoredDraggableState.animateTo(AnchoredDraggableContentState.Open)
                                    }
                                }
                            )
                        }
                    }

                    if (week < uiState.selectedMonthCalendarInfoMap.size / 7 - 1) {
                        Spacer(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(1.dp)
                                .background(Color(0xFFEEEEEE))
                        )
                    }
                }
            }

            Spacer(Modifier.height(12.dp))

            if (isMonthPickerShowing) {
                Dialog(
                    onDismissRequest = { isMonthPickerShowing = false },
                    properties = DialogProperties(
                        usePlatformDefaultWidth = false
                    )
                ) {
                    OnMyWayTheme {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .clickableNoEffect { isMonthPickerShowing = false },
                            contentAlignment = Alignment.BottomCenter
                        ) {
                            Box(
                                modifier = Modifier
                                    .padding(start = 10.dp, end = 10.dp, bottom = 10.dp)
                                    .fillMaxWidth()
                                    .height(315.dp)
                                    .clickableNoEffect {}
                                    .clip(RoundedCornerShape(20.dp))
                                    .background(Color.White),
                            ) {
                                Row(
                                    modifier = Modifier
                                        .align(Alignment.TopCenter)
                                        .offset(x = 0.dp, y = (-30).dp)
                                ) {
                                    ScrollablePicker(
                                        modifier = Modifier.width(110.dp),
                                        map = yearMap,
                                        state = yearScrollableState,
                                        range = yearMap.keys.min() .. yearMap.keys.max()
                                    ) {
                                        dialogSelectedYear.intValue = it
                                    }
                                    ScrollablePicker(
                                        modifier = Modifier.width(62.dp),
                                        map = monthMap,
                                        state = monthScrollableState,
                                        range = monthMap.keys.min() .. monthMap.keys.max()
                                    ) {
                                        dialogSelectedMonth.intValue = it
                                    }
                                    ScrollablePicker(
                                        modifier = Modifier.width(78.dp),
                                        map = dateMap,
                                        state = dateScrollableState,
                                        range = dateMap.keys.min() .. dateMap.keys.max()
                                    ) {
                                        dialogSelectedDate.intValue = it
                                    }
                                }

                                Row(
                                    modifier = Modifier
                                        .align(Alignment.BottomCenter)
                                        .padding(bottom = 24.dp)
                                ) {
                                    Text(
                                        modifier = Modifier
                                            .weight(1f)
                                            .clickableNoEffect { isMonthPickerShowing = false },
                                        text = "취소",
                                        color = Color(0xFF222222),
                                        style = medium16pt,
                                        textAlign = TextAlign.Center
                                    )

                                    Spacer(
                                        modifier = Modifier
                                            .width(2.dp)
                                            .height(22.dp)
                                            .background(getColor().thin)
                                    )

                                    Text(
                                        modifier = Modifier
                                            .weight(1f)
                                            .clickableNoEffect {
                                                updateSelectedYear(dialogSelectedYear.intValue)
                                                updateSelectedMonth(dialogSelectedYear.intValue, dialogSelectedMonth.intValue)
                                                updateSelectedDate(dialogSelectedYear.intValue, dialogSelectedMonth.intValue, dialogSelectedDate.intValue)
                                                isMonthPickerShowing = false
                                            },
                                        text = "완료",
                                        color = Color(0xFF222222),
                                        style = medium16pt,
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        if (((dailyScheduleAnchoredDraggableState.anchors.maxAnchor()) - (dailyScheduleAnchoredDraggableState.offset)) /
            ((dailyScheduleAnchoredDraggableState.anchors.maxAnchor()) - (dailyScheduleAnchoredDraggableState.anchors.minAnchor())) > 0.03f) {
            DimBackground(
                anchoredDraggableState = dailyScheduleAnchoredDraggableState,
                closeBottomDialog = {
                    coroutineScope.launch {
                        if (dailyScheduleAnchoredDraggableState.targetValue == AnchoredDraggableContentState.Open) {
                            dailyScheduleAnchoredDraggableState.animateTo(AnchoredDraggableContentState.Closed)
                        }
                    }
                }
            )
        }

        DailyScheduleBottomDialog(
            anchoredDraggableState = dailyScheduleAnchoredDraggableState,
            selectedMonth = uiState.selectedMonth,
            selectedDate = uiState.selectedDate,
            dailyScheduleList = uiState.selectedDateDailyScheduleInfoList,
            isGroup = isGroupSelected,
            isDeleteDialogShowing = isDeleteWarningDialogShowing,
            deleteTargetScheduleSeq = deleteTargetScheduleSeq,
            moveToDetailScheduleScreen = moveToDetailScheduleScreen
        )

        if (isDeleteWarningDialogShowing.value) {
            Dialog(
                onDismissRequest = { isDeleteWarningDialogShowing.value = false },
                properties = DialogProperties(
                    usePlatformDefaultWidth = false
                )
            ) {
                OnMyWayTheme {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .clickableNoEffect { isDeleteWarningDialogShowing.value = false },
                        contentAlignment = Alignment.TopCenter
                    ) {

                        Box(
                            modifier = Modifier
                                .padding(start = 40.dp, top = 210.dp, end = 40.dp)
                                .fillMaxWidth()
                                .clickableNoEffect {}
                                .clip(RoundedCornerShape(20.dp))
                                .background(Color(0xFF333333))
                        ) {
                            Column(
                                modifier = Modifier.padding(start = 10.dp, end = 10.dp)
                            ) {
                                Spacer(Modifier.height(20.dp))

                                Text(
                                    modifier = Modifier.padding(6.dp, 4.dp, 6.dp, 4.dp),
                                    text = if (isGroupSelected.value) "그룹 일정 삭제" else "일정 삭제",
                                    color = Color(0xFFFFFFFF),
                                    fontFamily = notoSanskr,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 18.sp
                                )

                                Spacer(Modifier.height(8.dp))

                                Text(
                                    modifier = Modifier.padding(6.dp, 4.dp, 6.dp, 4.dp),
                                    text = if (!isGroupSelected.value) "일정을 삭제합니다.\n" +
                                            "연관된 피드가 있을 경우 같이 삭제됩니다." else "일정을 삭제합니다.\n" +
                                            "일정을 만들었을 경우 함께한 인원의 일정과 연관된 피드가 같이 삭제되며,\n" +
                                            "일정에 참여만 했을 경우 자신의 일정과\n" +
                                            "연관된 피드만 삭제됩니다.",
                                    color = Color(0xFFA0A0A0),
                                    fontFamily = notoSanskr,
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 14.sp
                                )

                                Spacer(Modifier.height(24.dp))

                                Row(
                                    modifier = Modifier.padding(start = 10.dp, end = 10.dp)
                                ) {
                                    Spacer(Modifier.weight(1f))

                                    Text(
                                        modifier = Modifier
                                            .clickableNoEffect { isDeleteWarningDialogShowing.value = false }
                                            .padding(6.dp, 4.dp, 6.dp, 4.dp),
                                        text = "취소",
                                        color = Color(0xFFFFFFFF),
                                        fontFamily = notoSanskr,
                                        fontWeight = FontWeight.Medium,
                                        fontSize = 14.sp
                                    )

                                    Spacer(Modifier.width(10.dp))

                                    Text(
                                        modifier = Modifier
                                            .clickableNoEffect {
                                                deleteSchedule(deleteTargetScheduleSeq.value)
                                                isDeleteWarningDialogShowing.value = false
                                            }
                                            .padding(6.dp, 4.dp, 6.dp, 4.dp),
                                        text = "삭제",
                                        color = Color(0xFFD49EFF),
                                        fontFamily = notoSanskr,
                                        fontWeight = FontWeight.Medium,
                                        fontSize = 14.sp
                                    )

                                    Spacer(Modifier.width(10.dp))
                                }

                                Spacer(Modifier.height(20.dp))
                            }
                        }
                    }
                }
            }
        }
    }
}

enum class DetailState

@OptIn(ExperimentalFoundationApi::class)
@CustomPreview
@Composable
private fun ScheduleScreenPreview2() {
}