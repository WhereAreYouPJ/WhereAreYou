package com.whereareyounow.ui.home.schedule.scheduleedit

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
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
import com.whereareyounow.data.scheduleedit.ScheduleEditScreenSideEffect
import com.whereareyounow.data.scheduleedit.ScheduleEditScreenUIState
import com.whereareyounow.domain.entity.schedule.Friend
import com.whereareyounow.ui.component.BottomOKButton
import com.whereareyounow.ui.component.CustomTopBar
import com.whereareyounow.ui.component.ScrollablePicker
import com.whereareyounow.util.CalendarUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat

@Composable
fun ScheduleEditScreen(
    scheduleEditScreenUIState: ScheduleEditScreenUIState,
    scheduleEditScreenSideEffectFlow: SharedFlow<ScheduleEditScreenSideEffect>,
    updateScheduleName: (String) -> Unit,
    updateScheduleDate: (Int, Int, Int) -> Unit,
    updateScheduleTime: (Int, Int) -> Unit,
    updateMemo: (String) -> Unit,
    onComplete: (() -> Unit) -> Unit,
    moveToSearchLocationScreen: () -> Unit,
    moveToFriendsListScreen: () -> Unit,
    moveToBackScreen: () -> Unit
) {
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        scheduleEditScreenSideEffectFlow.collect { value ->
            when (value) {
                is ScheduleEditScreenSideEffect.Toast -> {
                    withContext(Dispatchers.Main) { Toast.makeText(context, value.text, Toast.LENGTH_SHORT).show() }
                }
            }
        }
    }
    val isDateTimePickerDialogShowing = remember { mutableStateOf(false) }
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 20.dp, end = 20.dp)
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
            // 상단바
            ScheduleEditorScreenTopBar(moveToBackScreen)

            Spacer(Modifier.height(40.dp))

            // 제목
            ScheduleTitleTextField(
                scheduleName = scheduleEditScreenUIState.scheduleName,
                updateScheduleName = updateScheduleName
            )

            Spacer(Modifier.height(40.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier.size(30.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        modifier = Modifier.size(26.dp),
                        painter = painterResource(R.drawable.clock),
                        contentDescription = null
                    )
                }
                Spacer(Modifier.width(10.dp))
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .clickable { isDateTimePickerDialogShowing.value = true }
                        .padding(4.dp)
                ) {
                    // 날짜 선택
                    DateComponent(
                        scheduleYear = scheduleEditScreenUIState.scheduleYear,
                        scheduleMonth = scheduleEditScreenUIState.scheduleMonth,
                        scheduleDate = scheduleEditScreenUIState.scheduleDate
                    )

                    // 시간 선택
                    TimeComponent(
                        scheduleHour = scheduleEditScreenUIState.scheduleHour,
                        scheduleMinute = scheduleEditScreenUIState.scheduleMinute
                    )
                }
            }


            Spacer(Modifier.height(22.dp))

            // 위치 선택
            LocationSelector(
                destinationName = scheduleEditScreenUIState.destinationName,
                destinationAddress = scheduleEditScreenUIState.destinationAddress,
                moveToSearchLocationScreen = moveToSearchLocationScreen
            )

            Spacer(Modifier.height(22.dp))

            // 선택된 멤버 리스트
            SelectedMembersList(
                selectedFriendsList = scheduleEditScreenUIState.selectedFriendsList,
                moveToFriendsListScreen = moveToFriendsListScreen
            )

            Spacer(Modifier.height(24.dp))

            // 메모
            ScheduleMemo(
                memo = scheduleEditScreenUIState.memo,
                updateMemo = updateMemo
            )

            if (isDateTimePickerDialogShowing.value) {
                // 날짜, 시간 선택 다이얼로그
                DateTimePickerDialog(
                    selectedYear = scheduleEditScreenUIState.scheduleYear,
                    selectedMonth = scheduleEditScreenUIState.scheduleMonth,
                    selectedDate = scheduleEditScreenUIState.scheduleDate,
                    selectedHour = scheduleEditScreenUIState.scheduleHour,
                    selectedMinute = scheduleEditScreenUIState.scheduleMinute,
                    updateScheduleDate = updateScheduleDate,
                    updateScheduleTime = updateScheduleTime,
                    closeDialog = { isDateTimePickerDialogShowing.value = false },
                )
            }

            Spacer(Modifier.height(20.dp))
        }

        item {
            // 하단 완료 버튼
            CompleteButton(
                moveToBackScreen = moveToBackScreen,
                onComplete = onComplete
            )

            Spacer(Modifier.height(20.dp))
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
fun ScheduleTitleTextField(
    scheduleName: String,
    updateScheduleName: (String) -> Unit
) {
    BasicTextField(
        value = scheduleName,
        onValueChange = { updateScheduleName(it) },
        textStyle = TextStyle(fontSize = 30.sp, color = Color(0xFF505050), fontWeight = FontWeight.Bold),
        singleLine = true
    ) {
        Box(
            modifier = Modifier
                .width(IntrinsicSize.Max)
                .drawBehind {
                    val borderSize = 1.dp.toPx()
                    drawLine(
                        color = Color(0xFF858585),
                        start = Offset(0f, size.height),
                        end = Offset(size.width, size.height),
                        strokeWidth = borderSize
                    )
                },
            contentAlignment = Alignment.CenterStart
        ) {
            it()
            if (scheduleName == "") {
                Text(
                    text = "제목",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF7C7C7C)
                )
            }
        }
    }
}

@Composable
private fun DateComponent(
    scheduleYear: Int,
    scheduleMonth: Int,
    scheduleDate: Int
) {
    Text(
        text = "${scheduleYear}년 ${scheduleMonth}월 ${scheduleDate}일(${CalendarUtil.getDayOfWeekString(scheduleYear, scheduleMonth, scheduleDate)})",
        textAlign = TextAlign.Center,
        fontSize = 18.sp,
        fontWeight = FontWeight.Medium,
        color = Color(0xFF7C7C7C),
    )
}

@Composable
private fun TimeComponent(
    scheduleHour: Int,
    scheduleMinute: Int,
) {
    var hour = scheduleHour
    val AMPM: String = if (hour < 12) "오전" else { hour -= 12; "오후"}
    if (hour == 0) hour = 12
    Text(
        text = "$AMPM ${hour}:${String.format("%02d", scheduleMinute)}",
        textAlign = TextAlign.Center,
        fontSize = 14.sp,
        fontWeight = FontWeight.Medium,
        color = Color(0xFF7C7C7C),
    )
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
                painter = painterResource(id = R.drawable.location),
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
                painter = painterResource(id = R.drawable.memo),
                contentDescription = null
            )
        }
        Spacer(Modifier.width(14.dp))
        Text(
            text = "메모",
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF7C7C7C),
        )
    }

    Spacer(Modifier.height(10.dp))

    BasicTextField(
        value = memo,
        onValueChange = { updateMemo(it) },
        textStyle = TextStyle(fontSize = 20.sp),
        decorationBox = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .border(
                        border = BorderStroke(
                            width = 1.dp,
                            color = Color(0xFF7C7C7C)
                        ),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(12.dp)
            ) {
                it()
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MyDatePickerDialog(
    onDismissRequest: () -> Unit,
    updateScheduleDate: (String) -> Unit
) {
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = System.currentTimeMillis()
    )
    DatePickerDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = {
            Text(
                modifier = Modifier
                    .padding(end = 10.dp)
                    .clip(RoundedCornerShape(25))
                    .clickable {
                        updateScheduleDate(SimpleDateFormat("yyyy-MM-dd").format(datePickerState.selectedDateMillis))
                        onDismissRequest()
                    }
                    .padding(10.dp),
                text = "확인"
            )
        }
    ) {
        DatePicker(
            state = datePickerState
        )
        if (datePickerState.selectedDateMillis != null)
            Log.e("datePickerState", "${datePickerState.selectedDateMillis}, ${SimpleDateFormat("yyyy-MM-dd").format(datePickerState.selectedDateMillis)}")
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MyTimePickerDialog(
    onDismissRequest: () -> Unit,
    updateScheduleTime: (String) -> Unit
) {
    val timePickerState = rememberTimePickerState()
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {
        Surface(
            shape = RoundedCornerShape(10),
            modifier = Modifier
                .width(IntrinsicSize.Min)
                .height(IntrinsicSize.Min)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 20.dp),
                    text = "시간 선택"
                )
                TimePicker(
                    state = timePickerState
                )
                Row(
                    modifier = Modifier
                        .height(40.dp)
                        .fillMaxWidth()
                ) {
//                    toggle()
                    Spacer(Modifier.weight(1f))
                    TextButton(
                        onClick = onDismissRequest
                    ) { Text("취소") }
                    TextButton(
                        onClick = {
                            updateScheduleTime(String.format("%02d:%02d", timePickerState.hour, timePickerState.minute))
                            onDismissRequest()
                        }
                    ) { Text("확인") }
                }
            }
        }
    }
}

@Composable
private fun DateTimePickerDialog(
    selectedYear: Int,
    selectedMonth: Int,
    selectedDate: Int,
    selectedHour: Int,
    selectedMinute: Int,
    updateScheduleDate: (Int, Int, Int) -> Unit,
    updateScheduleTime: (Int, Int) -> Unit,
    closeDialog: () -> Unit
) {
    val density = LocalDensity.current.density
    Dialog(
        onDismissRequest = closeDialog,
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {
        CompositionLocalProvider(LocalDensity provides Density(density, fontScale = 1f)) {
            val yearScrollableState = remember { mutableIntStateOf(20) }
            val monthScrollableState = remember { mutableIntStateOf(200) }
            val dateScrollableState = remember { mutableIntStateOf(200) }
            val hourScrollableState = remember { mutableIntStateOf(200) }
            val minuteScrollableState = remember { mutableIntStateOf(200) }
            val currDate = remember { mutableIntStateOf(selectedDate) }
            val yearMap = (0..40).associateWith { "${selectedYear + it - 20}년" }
            val monthMap = (0..400).associateWith { "${((selectedMonth + it + 3) % 12) + 1}월" }
            val dateMap = remember(yearScrollableState.intValue, monthScrollableState.intValue) {
                val lastDate = CalendarUtil.getLastDayOfMonth(
                    yearMap[yearScrollableState.intValue]!!.replace(
                        "년",
                        ""
                    ).toInt(), monthMap[monthScrollableState.intValue]!!.replace("월", "").toInt()
                )
                if (lastDate < currDate.intValue) currDate.intValue = lastDate
                val map = (0..400).associateWith { "${(it % lastDate) + 1}일" }
                dateScrollableState.intValue = 200
                while (map[dateScrollableState.intValue]!!.replace("일", "")
                        .toInt() != currDate.intValue
                ) {
                    dateScrollableState.intValue++
                }
                map
            }
            val ampmMap = mapOf(0 to "오전", 1 to "오후")
            val ampmScrollableState =
                remember { mutableIntStateOf(if (selectedHour < 12) 0 else 1) }
            val hourMap = (0..400).associateWith { "${((selectedHour + it + 3) % 12) + 1}" }
            val minuteMap = (0..400).associateWith {
                String.format(
                    "%02d",
                    (((selectedMinute / 5) + it + 4) * 5) % 60
                )
            }
            Surface(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .height(220.dp),
                shape = RoundedCornerShape(10.dp),
                color = Color(0xFFFFFFFF)
            ) {
                Column(
                    modifier = Modifier.padding(bottom = 10.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp)
                            .drawBehind {
                                val width = this.size.width
                                val height = this.size.height
                                drawRoundRect(
                                    color = Color(0xFFDDDDDD),
                                    topLeft = Offset(20.dp.toPx(), height / 2 - (20.dp.toPx())),
                                    size = Size(width - 40.dp.toPx(), 40.dp.toPx()),
                                    cornerRadius = CornerRadius(10f, 10f)
                                )
                            }
                            .padding(start = 20.dp, end = 20.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // 년도
                        Box(
                            modifier = Modifier.weight(2f)
                        ) {
                            ScrollablePicker(
                                map = yearMap,
                                state = yearScrollableState,
                                range = (yearMap.keys.min()..yearMap.keys.max())
                            )
                        }
                        // 월
                        Box(
                            modifier = Modifier.weight(2f)
                        ) {
                            ScrollablePicker(
                                map = monthMap,
                                state = monthScrollableState,
                                range = (monthMap.keys.min()..monthMap.keys.max())
                            )
                        }
                        // 일
                        Box(
                            modifier = Modifier.weight(2f)
                        ) {
                            ScrollablePicker(
                                map = dateMap,
                                state = dateScrollableState,
                                range = (dateMap.keys.min()..dateMap.keys.max())
                            ) {
                                currDate.intValue = dateMap[it]!!.replace("일", "").toInt()
                            }
                        }
                        // 오전, 오후
                        Box(
                            modifier = Modifier.weight(2f)
                        ) {
                            ScrollablePicker(
                                map = ampmMap,
                                state = ampmScrollableState,
                                range = (ampmMap.keys.min()..ampmMap.keys.max())
                            )
                        }
                        // 시
                        Box(
                            modifier = Modifier.weight(1f)
                        ) {
                            ScrollablePicker(
                                map = hourMap,
                                state = hourScrollableState,
                                range = (hourMap.keys.min()..hourMap.keys.max())
                            )
                        }
                        //분
                        Box(
                            modifier = Modifier.weight(1f)
                        ) {
                            ScrollablePicker(
                                map = minuteMap,
                                state = minuteScrollableState,
                                range = (minuteMap.keys.min()..minuteMap.keys.max())
                            )
                        }
                    }
                    Spacer(Modifier.weight(1f))
                    Box(
                        modifier = Modifier
                            .padding(end = 20.dp)
                            .fillMaxWidth(),
                        contentAlignment = Alignment.CenterEnd
                    ) {
                        Text(
                            modifier = Modifier
                                .clip(RoundedCornerShape(10.dp))
                                .clickable {
                                    val dateFormat = SimpleDateFormat("yyyy년 MM월 dd일(E)")
                                    val year =
                                        yearMap[yearScrollableState.intValue]!!.replace("년", "")
                                            .toInt()
                                    val month =
                                        monthMap[monthScrollableState.intValue]!!.replace("월", "")
                                            .toInt()
                                    val date =
                                        dateMap[dateScrollableState.intValue]!!.replace("일", "")
                                            .toInt()
                                    val ampm = ampmScrollableState.intValue
                                    var hour = hourMap[hourScrollableState.intValue]!!.toInt()
                                    if (ampm == 1 && hour != 12) hour += 12
                                    if (ampm == 0 && hour == 12) hour = 0
                                    val minute = minuteMap[minuteScrollableState.intValue]!!.toInt()
                                    updateScheduleDate(year, month, date)
                                    updateScheduleTime(hour, minute)
                                    closeDialog()
                                }
                                .padding(10.dp),
                            text = "확인",
                            fontSize = 20.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun CompleteButton(
    moveToBackScreen: () -> Unit,
    onComplete: (() -> Unit) -> Unit
) {
    BottomOKButton(
        text = "완료",
        onClick = { onComplete(moveToBackScreen) }
    )
}

@SuppressLint("UnrememberedMutableState")
@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun EmptyNewScheduleScreenPreview() {
    val selectedFriendsList = listOf(
        Friend(number = 0, memberId = "", name = "name1111111111"),
        Friend(number = 0, memberId = "", name = "name2"),
        Friend(number = 0, memberId = "", name = "name3"),
        Friend(number = 0, memberId = "", name = "name4")
    )
    ScheduleEditScreen(
        scheduleEditScreenUIState = ScheduleEditScreenUIState(),
        scheduleEditScreenSideEffectFlow = MutableSharedFlow(),
        updateScheduleName = {},
        updateScheduleDate = { _, _, _ -> },
        updateScheduleTime = { _, _ -> },
        updateMemo = {},
        onComplete = {},
        moveToBackScreen = {},
        moveToFriendsListScreen = {},
        moveToSearchLocationScreen = {}
    )
}

@SuppressLint("UnrememberedMutableState")
@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun FilledNewScheduleScreenPreview() {
    ScheduleEditScreen(
        scheduleEditScreenUIState = ScheduleEditScreenUIState(),
        scheduleEditScreenSideEffectFlow = MutableSharedFlow(),
        updateScheduleName = {},
        updateScheduleDate = { _, _, _ -> },
        updateScheduleTime = { _, _ -> },
        updateMemo = {},
        onComplete = {},
        moveToBackScreen = {},
        moveToFriendsListScreen = {},
        moveToSearchLocationScreen = {}
    )
}