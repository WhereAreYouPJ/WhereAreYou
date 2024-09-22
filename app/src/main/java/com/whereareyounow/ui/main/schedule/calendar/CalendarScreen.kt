package com.whereareyounow.ui.main.schedule.calendar

import androidx.compose.animation.core.tween
import androidx.compose.animation.splineBasedDecay
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.animateTo
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
import com.whereareyounow.WhereAreYouApplication.Companion.applicationContext
import com.whereareyounow.data.calendar.CalendarScreenSideEffect
import com.whereareyounow.data.calendar.CalendarScreenUIState
import com.whereareyounow.data.globalvalue.DAILY_BRIEF_SCHEDULE_VIEW_HEIGHT
import com.whereareyounow.data.globalvalue.TOP_BAR_HEIGHT
import com.whereareyounow.domain.util.LogUtil
import com.whereareyounow.globalvalue.type.AnchoredDraggableContentState
import com.whereareyounow.ui.component.DimBackground
import com.whereareyounow.ui.component.ScrollablePicker
import com.whereareyounow.ui.main.schedule.calendar.component.DailyScheduleBottomDialog
import com.whereareyounow.ui.main.schedule.calendar.component.DateBox
import com.whereareyounow.ui.main.schedule.calendar.component.getDailyScheduleAnchoredDraggableState
import com.whereareyounow.ui.theme.WhereAreYouTheme
import com.whereareyounow.ui.theme.getColor
import com.whereareyounow.ui.theme.medium14pt
import com.whereareyounow.ui.theme.medium16pt
import com.whereareyounow.ui.theme.notoSanskr
import com.whereareyounow.util.CustomPreview
import com.whereareyounow.util.calendar.getLastDayOfMonth
import com.whereareyounow.util.clickableNoEffect
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime

@Composable
fun CalendarScreen(
    paddingValues: PaddingValues,
    moveToDetailScheduleScreen: (Int) -> Unit,
    viewModel: CalendarViewModel = hiltViewModel(),
) {
    val uiState = viewModel.uiState.collectAsState().value
    val sideEffectFlow = viewModel.calendarScreenSideEffectFlow
    LaunchedEffect(Unit) {

    }
    CalendarScreen(
        uiState = uiState,
        sideEffectFlow = sideEffectFlow,
        updateSelectedYear = viewModel::updateYear,
        updateSelectedMonth = viewModel::updateMonth,
        updateSelectedDate = viewModel::updateDate,
        moveToDetailScheduleScreen = moveToDetailScheduleScreen
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CalendarScreen(
    uiState: CalendarScreenUIState,
    sideEffectFlow: MutableSharedFlow<CalendarScreenSideEffect>,
    updateSelectedYear: (Int) -> Unit,
    updateSelectedMonth: (Int) -> Unit,
    updateSelectedDate: (Int) -> Unit,
    moveToDetailScheduleScreen: (Int) -> Unit,
) {
    var isMonthPickerShowing by remember { mutableStateOf(false) }

    val yearMap = (2000..2100).associateWith { "${it}년" }
    val monthMap = (1 .. 12).associateWith { "${it}월" }
    val dateMap = (1 .. getLastDayOfMonth(uiState.selectedYear, uiState.selectedMonth)).associateWith { "${it}일" }
    val yearScrollableState = remember { mutableIntStateOf(uiState.selectedYear) }
    val monthScrollableState = remember { mutableIntStateOf(uiState.selectedMonth) }
    val dateScrollableState = remember(uiState.selectedYear, uiState.selectedMonth) { mutableIntStateOf(uiState.selectedDate) }
    val dialogSelectedYear = remember(uiState.selectedYear) { mutableIntStateOf(uiState.selectedYear) }
    val dialogSelectedMonth = remember(uiState.selectedMonth) { mutableIntStateOf(uiState.selectedMonth) }
    val dialogSelectedDate = remember(uiState.selectedDate) { mutableIntStateOf(uiState.selectedDate) }
    val dailyScheduleAnchoredDraggableState = remember { getDailyScheduleAnchoredDraggableState() }
    val isDimBackgroundShowing = remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val today = remember { LocalDate.now() }

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
                    .padding(start = 20.dp, end = 11.dp)
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

                Image(
                    modifier = Modifier.size(34.dp),
                    painter = painterResource(R.drawable.ic_plus),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(getColor().brandColor)
                )
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
                                    updateSelectedMonth(uiState.dateList[week * 7 + idx].monthValue)
                                    updateSelectedDate(uiState.dateList[week * 7 + idx].dayOfMonth)
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
                    WhereAreYouTheme {
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
                                    .clickableNoEffect { }
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
                                                updateSelectedMonth(dialogSelectedMonth.intValue)
                                                updateSelectedDate(dialogSelectedDate.intValue)
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

        if (isDimBackgroundShowing.value) {
            DimBackground(
                isDimBackgroundShowing = isDimBackgroundShowing,
                anchoredDraggableState = dailyScheduleAnchoredDraggableState
            )
        }

        DailyScheduleBottomDialog(
            anchoredDraggableState = dailyScheduleAnchoredDraggableState,
            selectedMonth = uiState.selectedMonth,
            selectedDate = uiState.selectedDate,
            dailyScheduleList = uiState.selectedDateDailyScheduleInfoList,
            moveToDetailScheduleScreen = moveToDetailScheduleScreen
        )
    }
}

enum class DetailState

@OptIn(ExperimentalFoundationApi::class)
@CustomPreview
@Composable
private fun ScheduleScreenPreview2() {
}