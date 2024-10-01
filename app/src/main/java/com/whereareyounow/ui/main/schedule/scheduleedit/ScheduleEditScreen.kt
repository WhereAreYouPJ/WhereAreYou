package com.whereareyounow.ui.main.schedule.scheduleedit

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import com.whereareyounow.R
import com.whereareyounow.data.globalvalue.BOTTOM_NAVIGATION_BAR_HEIGHT
import com.whereareyounow.data.scheduleedit.ScheduleEditScreenSideEffect
import com.whereareyounow.data.scheduleedit.ScheduleEditScreenUIState
import com.whereareyounow.domain.entity.schedule.Friend
import com.whereareyounow.globalvalue.type.ScheduleColor
import com.whereareyounow.ui.component.CustomSurface
import com.whereareyounow.ui.component.CustomTopBar
import com.whereareyounow.ui.component.ScrollablePicker
import com.whereareyounow.ui.main.schedule.scheduleedit.component.ScheduleEditScreenScheduleTitle
import com.whereareyounow.ui.theme.OnMyWayTheme
import com.whereareyounow.ui.theme.getColor
import com.whereareyounow.ui.theme.medium14pt
import com.whereareyounow.ui.theme.medium16pt
import com.whereareyounow.ui.theme.pretendard
import com.whereareyounow.util.calendar.getLastDayOfMonth
import com.whereareyounow.util.clickableNoEffect
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.withContext
import java.time.DayOfWeek
import java.time.LocalDate

@Composable
fun ScheduleEditScreen(
    uiState: ScheduleEditScreenUIState,
    sideEffectFlow: SharedFlow<ScheduleEditScreenSideEffect>,
    updateScheduleName: (String) -> Unit,
    toggleAllDay: () -> Unit,
    updateStartDate: (Int, Int, Int) -> Unit,
    updateStartTime: (Int, Int) -> Unit,
    updateEndDate: (Int, Int, Int) -> Unit,
    updateEndTime: (Int, Int) -> Unit,
    updateMemo: (String) -> Unit,
    updateColor: (ScheduleColor) -> Unit,
    moveToSearchLocationScreen: (String) -> Unit,
    moveToFriendsListScreen: (List<Int>) -> Unit,
    moveToBackScreen: () -> Unit
) {
    val thinnest = getColor().thinnest
    val context = LocalContext.current
    var isDatePickerShowing by remember { mutableStateOf(false) }
    var isTimePickerShowing by remember { mutableStateOf(false) }
    val yearMap = (2000..2100).associateWith { "${it}년" }
    val monthMap = (1 .. 12).associateWith { "${it}월" }
    var isStartDateSelected by remember { mutableStateOf(false) }
    val yearScrollableState = remember { mutableIntStateOf(2000) }
    val monthScrollableState = remember { mutableIntStateOf(1) }
    val dateScrollableState = remember { mutableIntStateOf(1) }
    val ampmMap = mapOf(0 to "오전", 1 to "오후")
    val hourMap = (1 .. 12).associateWith { "${it}시" }
    val minuteMap = (0 .. 59).associateWith { String.format("%02d분", it) }
    val dateMap = (1 .. getLastDayOfMonth(yearScrollableState.intValue, monthScrollableState.intValue)).associateWith { "${it}일" }
    val ampmScrollableState = remember { mutableIntStateOf(0) }
    val hourScrollableState = remember { mutableIntStateOf(1) }
    val minuteScrollableState = remember { mutableIntStateOf(1) }

    LaunchedEffect(Unit) {
        sideEffectFlow.collect { value ->
            when (value) {
                is ScheduleEditScreenSideEffect.Toast -> {
                    withContext(Dispatchers.Main) { Toast.makeText(context, value.text, Toast.LENGTH_SHORT).show() }
                }
            }
        }
    }
    CustomSurface {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 15.dp, end = 15.dp, top = 40.dp)
                    .imePadding(),
                verticalArrangement = remember {
                    object : Arrangement.Vertical {
                        override fun Density.arrange(
                            totalSize: Int,
                            sizes: IntArray,
                            outPositions: IntArray
                        ) {
                            var currentOffset = 0
                            Log.e("Arrangement.Vertical.Start", "${totalSize.toDp()}\n${sizes.map { it.toDp() }}\n${outPositions.map { it.toDp() }}")
                            sizes.forEachIndexed { index, size ->
                                if (index == sizes.lastIndex) {
                                    outPositions[index] = totalSize - size
                                } else {
                                    outPositions[index] = currentOffset
                                    currentOffset += size
                                }
                            }
                            Log.e("Arrangement.Vertical.End", "${totalSize.toDp()}\n${sizes.map { it.toDp() }}\n${outPositions.map { it.toDp() }}")
                        }
                    }
                }
            ) {
                item {
                    Column(
                        modifier = Modifier
                            .padding(top = 40.dp)
                            .fillMaxSize()
                    ) {
                        // 제목
                        ScheduleEditScreenScheduleTitle(
                            scheduleName = uiState.scheduleName,
                            updateScheduleName = updateScheduleName
                        )

                        Spacer(Modifier.height(16.dp))

                        // 하루종일 토글
                        Row(
                            modifier = Modifier.height(40.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                modifier = Modifier.padding(start = 8.dp, top = 4.dp, end = 8.dp, bottom = 4.dp),
                                text = "하루 종일",
                                color = Color(0xFF222222),
                                style = medium16pt
                            )

                            Spacer(Modifier.weight(1f))

                            Switch(
                                checked = uiState.isAllDay,
                                onCheckedChange = { toggleAllDay() }
                            )

                            Spacer(Modifier.width(10.dp))
                        }

                        Spacer(Modifier.height(12.dp))

                        Row {
                            // 시작 날짜
                            Column(
                                modifier = Modifier
                                    .weight(1f)
                                    .animateContentSize(),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    modifier = Modifier
                                        .background(
                                            color = if (isDatePickerShowing && isStartDateSelected) getColor().thinner else Color.Transparent,
                                            shape = RoundedCornerShape(50)
                                        )
                                        .clickableNoEffect {
                                            yearScrollableState.intValue = uiState.startYear
                                            monthScrollableState.intValue = uiState.startMonth
                                            dateScrollableState.intValue = uiState.startDate
                                            isStartDateSelected = true
                                            isDatePickerShowing = true
                                        }
                                        .padding(
                                            start = 6.dp,
                                            top = 4.dp,
                                            end = 6.dp,
                                            bottom = 4.dp
                                        ),
                                    text = "${uiState.startMonth}월 ${uiState.startDate}일 (${when (LocalDate.of(uiState.startYear, uiState.startMonth, uiState.startDate).dayOfWeek) {
                                        DayOfWeek.MONDAY -> "월"
                                        DayOfWeek.TUESDAY -> "화"
                                        DayOfWeek.WEDNESDAY -> "수"
                                        DayOfWeek.THURSDAY -> "목"
                                        DayOfWeek.FRIDAY -> "금"
                                        DayOfWeek.SATURDAY -> "토"
                                        DayOfWeek.SUNDAY -> "일"
                                    }})",
                                    color = if (isDatePickerShowing && isStartDateSelected) getColor().brandText else Color(0xFF444444),
                                    style = medium16pt
                                )

                                Spacer(Modifier.height(8.dp))

                                if (!uiState.isAllDay) {
                                    Text(
                                        modifier = Modifier
                                            .background(
                                                color = if (isTimePickerShowing && isStartDateSelected) getColor().thinner else Color.Transparent,
                                                shape = RoundedCornerShape(50)
                                            )
                                            .clickableNoEffect {
                                                ampmScrollableState.intValue = if (uiState.startHour >= 12) 1 else 0
                                                hourScrollableState.intValue = if (uiState.startHour >= 13) uiState.startHour - 12 else uiState.startHour
                                                if (hourScrollableState.intValue == 0) hourScrollableState.intValue = 12
                                                minuteScrollableState.intValue = uiState.startMinute
                                                isTimePickerShowing = true
                                                isStartDateSelected = true
                                            }
                                            .padding(start = 8.dp, top = 4.dp, end = 8.dp, bottom = 4.dp),
                                        text = if (uiState.startHour >= 12) "오후 ${if (uiState.startHour == 12) 12 else (uiState.startHour - 12)}:${String.format("%02d", uiState.startMinute)}"
                                            else "오전 ${if (uiState.startHour == 0) 12 else uiState.startHour}:${String.format("%02d", uiState.startMinute)}",
                                        color = if (isTimePickerShowing && isStartDateSelected) getColor().brandText else Color(0xFF444444),
                                        style = medium16pt
                                    )
                                }
                            }

                            Image(
                                modifier = Modifier.size(28.dp),
                                painter = painterResource(R.drawable.ic_arrow_right),
                                contentDescription = null,
                                colorFilter = ColorFilter.tint(Color(0xFF727272))
                            )

                            // 끝 날짜
                            Column(
                                modifier = Modifier
                                    .weight(1f)
                                    .animateContentSize(),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    modifier = Modifier
                                        .background(
                                            color = if (isDatePickerShowing && !isStartDateSelected) getColor().thinner else Color.Transparent,
                                            shape = RoundedCornerShape(50)
                                        )
                                        .clickableNoEffect {
                                            yearScrollableState.intValue = uiState.endYear
                                            monthScrollableState.intValue = uiState.endMonth
                                            dateScrollableState.intValue = uiState.endDate
                                            isStartDateSelected = false
                                            isDatePickerShowing = true
                                        }
                                        .padding(
                                            start = 6.dp,
                                            top = 4.dp,
                                            end = 6.dp,
                                            bottom = 4.dp
                                        ),
                                    text = "${uiState.endMonth}월 ${uiState.endDate}일 (${when (LocalDate.of(uiState.endYear, uiState.endMonth, uiState.endDate).dayOfWeek) {
                                        DayOfWeek.MONDAY -> "월"
                                        DayOfWeek.TUESDAY -> "화"
                                        DayOfWeek.WEDNESDAY -> "수"
                                        DayOfWeek.THURSDAY -> "목"
                                        DayOfWeek.FRIDAY -> "금"
                                        DayOfWeek.SATURDAY -> "토"
                                        DayOfWeek.SUNDAY -> "일"
                                    }})",
                                    color = if (isDatePickerShowing && !isStartDateSelected) getColor().brandText else Color(0xFF444444),
                                    style = medium16pt
                                )

                                Spacer(Modifier.height(8.dp))

                                if (!uiState.isAllDay) {
                                    Text(
                                        modifier = Modifier
                                            .background(
                                                color = if (isTimePickerShowing && !isStartDateSelected) getColor().thinner else Color.Transparent,
                                                shape = RoundedCornerShape(50)
                                            )
                                            .clickableNoEffect {
                                                ampmScrollableState.intValue = if (uiState.endHour >= 12) 1 else 0
                                                hourScrollableState.intValue = if (uiState.endHour >= 13) uiState.endHour - 12 else uiState.endHour
                                                if (hourScrollableState.intValue == 0) hourScrollableState.intValue = 12
                                                minuteScrollableState.intValue = uiState.endMinute
                                                isTimePickerShowing = true
                                                isStartDateSelected = false
                                            }
                                            .padding(start = 8.dp, top = 4.dp, end = 8.dp, bottom = 4.dp),
                                        text = if (uiState.endHour >= 12) "오후 ${if (uiState.endHour == 12) 12 else (uiState.endHour - 12)}:${String.format("%02d", uiState.endMinute)}"
                                        else "오전 ${if (uiState.endHour == 0) 12 else uiState.endHour}:${String.format("%02d", uiState.endMinute)}",
                                        color = if (isTimePickerShowing && !isStartDateSelected) getColor().brandText else Color(0xFF444444),
                                        style = medium16pt
                                    )
                                }
                            }
                        }

                        Spacer(Modifier.height(20.dp))

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(1.dp)
                                .background(getColor().thinnest)
                        )

                        Spacer(Modifier.height(20.dp))

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(38.dp)
                                .drawBehind {
                                    drawLine(
                                        color = thinnest,
                                        start = Offset(0f, size.height),
                                        end = Offset(size.width, size.height),
                                        strokeWidth = 1.dp.toPx()
                                    )
                                }
                        ) {
                            Text(
                                modifier = Modifier.padding(start = 6.dp, top = 4.dp, end = 6.dp, bottom = 4.dp),
                                text = "위치추가",
                                color = Color(0xFF222222),
                                style = medium16pt
                            )
                        }

                        Spacer(Modifier.height(4.dp))

                        Row(
                            modifier = Modifier
                                .height(44.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Spacer(Modifier.width(4.dp))

                            Image(
                                modifier = Modifier.size(30.dp),
                                painter = painterResource(R.drawable.ic_location),
                                contentDescription = null,
                                colorFilter = ColorFilter.tint(getColor().brandColor)
                            )

                            Spacer(Modifier.width(6.dp))

                            Text(
                                modifier = Modifier.clickableNoEffect { moveToSearchLocationScreen(uiState.destinationName) },
                                text = if (uiState.destinationName == "") "위치 추가" else uiState.destinationName,
                                color = getColor().dark,
                                style = medium16pt,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }

                        Spacer(Modifier.height(4.dp))

                        LazyRow {
                            itemsIndexed(listOf("서울대", "여의도공원")) { idx, item ->
                                Box(
                                    modifier = Modifier
                                        .padding(end = 4.dp)
                                        .height(28.dp)
                                        .clip(RoundedCornerShape(50))
                                        .background(
                                            color = Color(0xFFF0F0F0)
                                        )
                                        .padding(start = 14.dp, end = 14.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = item,
                                        color = Color(0xFF444444),
                                        style = medium14pt
                                    )
                                }
                            }
                        }

                        Spacer(Modifier.height(20.dp))

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(38.dp)
                                .drawBehind {
                                    drawLine(
                                        color = thinnest,
                                        start = Offset(0f, size.height),
                                        end = Offset(size.width, size.height),
                                        strokeWidth = 1.dp.toPx()
                                    )
                                }
                                .padding(start = 6.dp, top = 4.dp)
                        ) {
                            Text(
                                text = "친구추가",
                                color = Color(0xFF222222),
                                style = medium16pt
                            )
                        }

                        Spacer(Modifier.height(4.dp))

                        Row(
                            modifier = Modifier
                                .height(44.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Spacer(Modifier.width(4.dp))

                            Image(
                                modifier = Modifier.size(30.dp),
                                painter = painterResource(R.drawable.ic_users),
                                contentDescription = null,
                            )

                            Spacer(Modifier.width(6.dp))

                            Text(
                                modifier = Modifier.clickableNoEffect { moveToFriendsListScreen(uiState.selectedFriendsList.map { it.memberSeq }) },
                                text = if (uiState.selectedFriendsList.isEmpty()) "친구 추가" else uiState.selectedFriendsList.map { it.name }.joinToString(", "),
                                color = if (uiState.selectedFriendsList.isEmpty()) getColor().dark else Color(0xFF444444),
                                style = medium16pt
                            )
                        }

                        Spacer(Modifier.height(20.dp))

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(38.dp)
                                .drawBehind {
                                    drawLine(
                                        color = thinnest,
                                        start = Offset(0f, size.height),
                                        end = Offset(size.width, size.height),
                                        strokeWidth = 1.dp.toPx()
                                    )
                                }
                                .padding(start = 6.dp, top = 4.dp)
                        ) {
                            Text(
                                text = "일정컬러",
                                color = Color(0xFF222222),
                                style = medium16pt
                            )
                        }

                        Spacer(Modifier.height(4.dp))

                        Row(
                            modifier = Modifier.height(24.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            listOf(ScheduleColor.Red, ScheduleColor.Yellow, ScheduleColor.Green, ScheduleColor.Blue, ScheduleColor.Purple, ScheduleColor.Pink).forEachIndexed { idx, item ->
                                Box(
                                    modifier = Modifier
                                        .size(20.dp)
                                        .clip(CircleShape)
                                        .then(
                                            if (uiState.color == item) {
                                                Modifier
                                                    .border(
                                                        border = BorderStroke(
                                                            width = 1.dp,
                                                            color = Color(0xFF8E8E8E)
                                                        ),
                                                        shape = CircleShape
                                                    )
                                            }
                                            else Modifier
                                        )
                                        .clickableNoEffect { updateColor(item) },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(14.dp)
                                            .clip(CircleShape)
                                            .background(item.color)
                                    )
                                }

                                Spacer(Modifier.width(12.dp))
                            }
                        }

                        Spacer(Modifier.height(20.dp))

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(38.dp)
                                .drawBehind {
                                    drawLine(
                                        color = thinnest,
                                        start = Offset(0f, size.height),
                                        end = Offset(size.width, size.height),
                                        strokeWidth = 1.dp.toPx()
                                    )
                                }
                                .padding(start = 6.dp, top = 4.dp)
                        ) {
                            Text(
                                text = "메모",
                                color = Color(0xFF222222),
                                style = medium16pt
                            )
                        }

                        Spacer(Modifier.height(6.dp))

                        // 메모
                        ScheduleMemo(
                            memo = uiState.memo,
                            updateMemo = updateMemo
                        )
                    }
                }

                item {
                    Spacer(Modifier.height((BOTTOM_NAVIGATION_BAR_HEIGHT + 20).dp))
                }
            }

            Row(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .height(BOTTOM_NAVIGATION_BAR_HEIGHT.dp)
                    .background(Color(0xFFF6F6F8)),
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .clickableNoEffect { moveToBackScreen() },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "취소",
                        color = Color(0xFF222222),
                        fontFamily = pretendard,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                    )
                }

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .clickableNoEffect { },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "저장",
                        color = Color(0xFF222222),
                        fontFamily = pretendard,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                    )
                }
            }
        }
    }

    if (isDatePickerShowing) {
        Dialog(
            onDismissRequest = { isDatePickerShowing = false },
            properties = DialogProperties(
                usePlatformDefaultWidth = false
            )
        ) {
            OnMyWayTheme {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clickableNoEffect { isDatePickerShowing = false },
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
                                modifier = Modifier.width(100.dp),
                                map = yearMap,
                                state = yearScrollableState,
                                range = yearMap.keys.min() .. yearMap.keys.max()
                            )

                            Spacer(Modifier.width(8.dp))

                            ScrollablePicker(
                                modifier = Modifier.width(60.dp),
                                map = monthMap,
                                state = monthScrollableState,
                                range = monthMap.keys.min() .. monthMap.keys.max()
                            )

                            Spacer(Modifier.width(8.dp))

                            ScrollablePicker(
                                modifier = Modifier.width(60.dp),
                                map = dateMap,
                                state = dateScrollableState,
                                range = dateMap.keys.min() .. dateMap.keys.max()
                            )
                        }

                        Row(
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .padding(bottom = 24.dp)
                        ) {
                            Text(
                                modifier = Modifier
                                    .weight(1f)
                                    .clickableNoEffect { isDatePickerShowing = false },
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
                                        if (isStartDateSelected) {
                                            updateStartDate(
                                                yearScrollableState.intValue,
                                                monthScrollableState.intValue,
                                                dateScrollableState.intValue
                                            )
                                        } else {
                                            updateEndDate(
                                                yearScrollableState.intValue,
                                                monthScrollableState.intValue,
                                                dateScrollableState.intValue
                                            )
                                        }
                                        isDatePickerShowing = false
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

    if (isTimePickerShowing) {
        Dialog(
            onDismissRequest = { isTimePickerShowing = false },
            properties = DialogProperties(
                usePlatformDefaultWidth = false
            )
        ) {
            OnMyWayTheme {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clickableNoEffect { isTimePickerShowing = false },
                    contentAlignment = Alignment.BottomCenter
                ) {
                    Box(
                        modifier = Modifier
                            .padding(start = 10.dp, end = 10.dp, bottom = 10.dp)
                            .fillMaxWidth()
                            .height(315.dp)
                            .clickableNoEffect {}
                            .clip(RoundedCornerShape(20.dp))
                            .background(Color.White)
                    ) {
                        Row(
                            modifier = Modifier
                                .align(Alignment.TopCenter)
                                .offset(x = 0.dp, y = (-30).dp)
                        ) {
                            ScrollablePicker(
                                modifier = Modifier.width(60.dp),
                                map = ampmMap,
                                state = ampmScrollableState,
                                range = ampmMap.keys.min() .. ampmMap.keys.max()
                            )

                            Spacer(Modifier.width(8.dp))

                            ScrollablePicker(
                                modifier = Modifier.width(60.dp),
                                map = hourMap,
                                state = hourScrollableState,
                                range = hourMap.keys.min() .. hourMap.keys.max()
                            )

                            Spacer(Modifier.width(8.dp))

                            ScrollablePicker(
                                modifier = Modifier.width(60.dp),
                                map = minuteMap,
                                state = minuteScrollableState,
                                range = minuteMap.keys.min() .. minuteMap.keys.max()
                            )
                        }

                        Row(
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .padding(bottom = 24.dp)
                        ) {
                            Text(
                                modifier = Modifier
                                    .weight(1f)
                                    .clickableNoEffect { isTimePickerShowing = false },
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
                                        var hour = if (ampmScrollableState.intValue == 0) hourScrollableState.intValue else (hourScrollableState.intValue + 12)
                                        if (ampmScrollableState.intValue == 0 && hourScrollableState.intValue == 12) hour = 0
                                        val minute = minuteScrollableState.intValue
                                        if (isStartDateSelected) {
                                            updateStartTime(hour, minute)
                                        } else {
                                            updateEndTime(hour, minute)
                                        }
                                        isTimePickerShowing = false
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

@Composable
private fun ScheduleEditorScreenTopBar(
    moveToBackScreen: () -> Unit
) {
    CustomTopBar(
        title = "일정",
        onBackButtonClicked = moveToBackScreen
    )
}

@Composable
private fun SelectedMembersList(
    selectedFriendsList: List<Friend>,
    moveToFriendsListScreen: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier.size(30.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                modifier = Modifier.size(26.dp),
                painter = painterResource(id = R.drawable.users),
                contentDescription = null,
                colorFilter = ColorFilter.tint(Color(0xFFA9AAAC))
            )
        }
        Spacer(Modifier.width(10.dp))
        if (selectedFriendsList.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .clickable { moveToFriendsListScreen() }
                    .padding(4.dp),
            ) {
                Text(
                    text = "친구 추가",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF7C7C7C),
                )
            }
        } else {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .clickable { moveToFriendsListScreen() }
                    .padding(4.dp)
            ) {
                itemsIndexed(selectedFriendsList) { _, friend ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        GlideImage(
                            modifier = Modifier
                                .size(30.dp)
                                .clip(RoundedCornerShape(50)),
                            imageModel = { friend.profileImgUrl ?: R.drawable.idle_profile },
                            imageOptions = ImageOptions(contentScale = ContentScale.Crop)
                        )
                        Text(
                            modifier = Modifier
                                .width(50.dp),
                            text = friend.name,
                            fontSize = 16.sp,
                            color = Color(0xFF7C7C7C),
                            fontWeight = FontWeight.Medium,
                            textAlign = TextAlign.Center,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun LocationSelector(
    destinationName: String,
    destinationAddress: String,
    moveToSearchLocationScreen: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier.size(30.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                modifier = Modifier.size(30.dp),
                painter = painterResource(id = R.drawable.ic_location),
                contentDescription = null
            )
        }
        Spacer(Modifier.width(10.dp))
        if (destinationName == "") {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .clickable { moveToSearchLocationScreen() }
                    .padding(4.dp),
                text = "장소 선택",
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF7C7C7C)
            )
        } else {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .clickable { moveToSearchLocationScreen() }
                    .padding(4.dp),
            ) {
                Text(
                    text = destinationName.replace("<b>", "").replace("</b>", ""),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF7C7C7C)
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = destinationAddress,
                    fontSize = 14.sp,
                    color = Color(0xFF8D8D8D)
                )
            }
        }
    }
}

@Composable
private fun ScheduleMemo(
    memo: String,
    updateMemo: (String) -> Unit,
) {
    BasicTextField(
        value = memo,
        onValueChange = { updateMemo(it) },
        textStyle = medium14pt,
        decorationBox = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .border(
                        border = BorderStroke(
                            width = 1.dp,
                            color = Color(0xFFD4D4D4)
                        ),
                        shape = RoundedCornerShape(6.dp)
                    )
                    .padding(12.dp)
            ) {
                it()
                if (memo.isEmpty()) {
                    Text(
                        text = "메모를 작성해주세요.",
                        color = getColor().dark,
                        style = medium14pt
                    )
                }
            }
        }
    )
}

@SuppressLint("UnrememberedMutableState")
@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun EmptyNewScheduleScreenPreview() {
}

@SuppressLint("UnrememberedMutableState")
@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun FilledNewScheduleScreenPreview() {
}