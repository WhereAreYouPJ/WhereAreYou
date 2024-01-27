package com.whereareyounow.ui.home.schedule.editschedule

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import com.whereareyounow.R
import com.whereareyounow.data.GlobalValue
import com.whereareyounow.domain.entity.schedule.Friend
import com.whereareyounow.ui.component.BottomOKButton
import com.whereareyounow.ui.component.CustomTopBar
import java.text.SimpleDateFormat

@Composable
fun ScheduleEditorScreen(
    selectedFriendsList: List<Friend>,
    scheduleName: String,
    updateScheduleName: (String) -> Unit,
    scheduleYear: String,
    scheduleMonth: String,
    scheduleDate: String,
    updateScheduleDate: (String) -> Unit,
    scheduleHour: String,
    scheduleMinute: String,
    updateScheduleTime: (String) -> Unit,
    destinationName: String,
    destinationAddress: String,
    memo: String,
    updateMemo: (String) -> Unit,
    onComplete: (() -> Unit) -> Unit,
    isDatePickerDialogShowing: MutableState<Boolean>,
    isTimePickerDialogShowing: MutableState<Boolean>,
    moveToBackScreen: () -> Unit,
    moveToFriendsListScreen: () -> Unit,
    moveToSearchLocationScreen: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 20.dp, end = 20.dp)
            .scrollable(
                state = rememberScrollState(),
                orientation = Orientation.Vertical
            )
            .imePadding()
    ) {
        // 상단바
        ScheduleEditorScreenTopBar(moveToBackScreen)

        Spacer(Modifier.height(40.dp))

        // 제목
        ScheduleTitleTextField(
            scheduleName = scheduleName,
            updateScheduleName = updateScheduleName
        )

        Spacer(Modifier.height(40.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier.size(24.dp),
                painter = painterResource(R.drawable.clock),
                contentDescription = null
            )

            Spacer(Modifier.width(12.dp))

            Column {
                // 날짜 선택
                DateSelector(
                    scheduleYear = scheduleYear,
                    scheduleMonth = scheduleMonth,
                    scheduleDate = scheduleDate,
                    isDatePickerDialogShowing = isDatePickerDialogShowing
                )

                // 시간 선택
                TimeSelector(
                    scheduleHour = scheduleHour,
                    scheduleMinute = scheduleMinute,
                    isTimePickerDialogShowing = isTimePickerDialogShowing
                )
            }
        }


        Spacer(Modifier.height(22.dp))

        // 위치 선택
        LocationSelector(
            destinationName = destinationName,
            destinationAddress = destinationAddress,
            moveToSearchLocationScreen = moveToSearchLocationScreen
        )

        Spacer(Modifier.height(22.dp))

        // 선택된 멤버 리스트
        SelectedMembersList(
            selectedFriendsList = selectedFriendsList,
            moveToFriendsListScreen = moveToFriendsListScreen
        )

        Spacer(Modifier.height(24.dp))

        // 메모
        ScheduleMemo(
            memo = memo,
            updateMemo = updateMemo
        )

        // 날짜 선택 다이얼로그
        if (isDatePickerDialogShowing.value) {
            MyDatePickerDialog(
                onDismissRequest = { isDatePickerDialogShowing.value = false },
                updateScheduleDate = updateScheduleDate
            )
        }

        // 시간 선택 다이얼로그
        if (isTimePickerDialogShowing.value) {
            MyTimePickerDialog(
                onDismissRequest = { isTimePickerDialogShowing.value = false },
                updateScheduleTime = updateScheduleTime
            )
        }

        Spacer(Modifier.weight(1f))

        // 하단 완료 버튼
        CompleteButton(
            moveToBackScreen = moveToBackScreen,
            onComplete = onComplete
        )

        Spacer(Modifier.height(20.dp))
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
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier.size(24.dp),
            painter = painterResource(id = R.drawable.plus_circle),
            contentDescription = null,
            colorFilter = ColorFilter.tint(Color(0xFFA9AAAC))
        )
        Spacer(Modifier.width(8.dp))
        if (selectedFriendsList.isEmpty()) {
            Text(
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .clickable { moveToFriendsListScreen() }
                    .padding(4.dp),
                text = "친구 추가",
                fontWeight = FontWeight.Medium,
                color = Color(0xFF7C7C7C),
            )
        } else {
            LazyRow(
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .clickable { moveToFriendsListScreen() }
            ) {
                itemsIndexed(selectedFriendsList) { _, friend ->
                    Column(
                        modifier = Modifier.padding(4.dp),
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
                    text = "일정명을 입력하세요",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF7C7C7C)
                )
            }
        }
    }
}

@Composable
private fun DateSelector(
    scheduleYear: String,
    scheduleMonth: String,
    scheduleDate: String,
    isDatePickerDialogShowing: MutableState<Boolean>
) {
    if (scheduleYear != "" && scheduleMonth != "" && scheduleDate != "") {
        Text(
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .clickable { isDatePickerDialogShowing.value = true }
                .padding(4.dp),
            text = "${scheduleYear}년 ${scheduleMonth.toInt()}월 ${scheduleDate.toInt()}일",
            textAlign = TextAlign.Center,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF7C7C7C),
        )
    } else {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .clickable { isDatePickerDialogShowing.value = true }
                .padding(4.dp),
            color = Color(0xFF7C7C7C),
            text = "날짜 선택",
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
private fun TimeSelector(
    scheduleHour: String,
    scheduleMinute: String,
    isTimePickerDialogShowing: MutableState<Boolean>
) {
    if (scheduleHour != "" && scheduleMinute != "") {
        var hour = scheduleHour.toInt()
        val minute = scheduleMinute.toInt()
        val AMPM: String = if (hour < 12) "오전" else { hour -= 12; "오후"}
        if (hour == 0) hour = 12
        Text(
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .clickable { isTimePickerDialogShowing.value = true }
                .padding(4.dp),
            text = "$AMPM ${hour}:${String.format("%02d", minute)}",
            textAlign = TextAlign.Center,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF7C7C7C),
        )
    } else {
        Text(
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .clickable { isTimePickerDialogShowing.value = true }
                .padding(4.dp),
            text = "시간 선택",
            textAlign = TextAlign.Center,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF7C7C7C),
        )
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
        Image(
            modifier = Modifier.size(28.dp),
            painter = painterResource(id = R.drawable.location),
            contentDescription = null
        )
        Spacer(Modifier.width(8.dp))
        if (destinationName == "") {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .clickable { moveToSearchLocationScreen() }
                    .padding(4.dp),
                text = "장소 선택",
                fontSize = 16.sp,
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
        Image(
            modifier = Modifier.size(26.dp),
            painter = painterResource(id = R.drawable.memo),
            contentDescription = null
        )
        Spacer(Modifier.width(14.dp))
        Text(
            text = "메모",
            fontSize = 16.sp,
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
                            color = Color(0xFFCCCCCC)
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
    ScheduleEditorScreen(
        selectedFriendsList = selectedFriendsList,
        scheduleName = "",
        updateScheduleName = {},
        scheduleYear = "",
        scheduleMonth = "",
        scheduleDate = "",
        updateScheduleDate = {},
        scheduleHour = "",
        scheduleMinute = "",
        updateScheduleTime = {},
        destinationName = "",
        destinationAddress = "",
        memo = "",
        updateMemo = {},
        onComplete = {},
        isDatePickerDialogShowing = mutableStateOf(false),
        isTimePickerDialogShowing = mutableStateOf(false),
        moveToBackScreen = {},
        moveToFriendsListScreen = {},
        moveToSearchLocationScreen = {}
    )
}

@SuppressLint("UnrememberedMutableState")
@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun FilledNewScheduleScreenPreview() {
    ScheduleEditorScreen(
        selectedFriendsList = emptyList(),
        scheduleName = "일정 제목",
        updateScheduleName = {},
        scheduleYear = "2024",
        scheduleMonth = "07",
        scheduleDate = "08",
        updateScheduleDate = {},
        scheduleHour = "01",
        scheduleMinute = "02",
        updateScheduleTime = {},
        destinationName = "목적지명",
        destinationAddress = "목적지 주소",
        memo = "메모",
        updateMemo = {},
        onComplete = {},
        isDatePickerDialogShowing = mutableStateOf(false),
        isTimePickerDialogShowing = mutableStateOf(false),
        moveToBackScreen = {},
        moveToFriendsListScreen = {},
        moveToSearchLocationScreen = {}
    )
}