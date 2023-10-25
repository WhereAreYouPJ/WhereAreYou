package com.whereareyou.ui.home.main.newschedule

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.NaverMap
import com.whereareyou.R
import com.whereareyou.data.FriendProvider
import com.whereareyou.data.GlobalValue
import java.text.SimpleDateFormat


@OptIn(ExperimentalNaverMapApi::class)
@Composable
fun NewScheduleScreen(
    toFriendsListScreen: () -> Unit,
    toSearchLocationScreen: () -> Unit,
    viewModel: NewScheduleViewModel = hiltViewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 20.dp, end = 20.dp)
    ) {
        val density = LocalDensity.current
        val isDatePickerDialogShowing = remember { mutableStateOf(false) }
        val isTimePickerDialogShowing = remember { mutableStateOf(false) }
        val isStartDate = remember { mutableStateOf(true) }
        val isStartTime = remember { mutableStateOf(true) }

        // 상단바
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height((GlobalValue.topAppBarHeight / density.density).dp)
                .background(
                    color = Color(0xFFCE93D8)
                ),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.CenterStart
            ) {
                Image(
                    painter = painterResource(id = R.drawable.baseline_arrow_back_ios_24),
                    contentDescription = null
                )
            }
            Text(
                text = "일정"
            )
        }

        // 추가된 친구 리스트
        val friendsList = viewModel.friendsList.collectAsState().value
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .clickable {
                    toFriendsListScreen()
                },
            contentAlignment = Alignment.CenterStart
        ) {
            if (friendsList.isEmpty()) {
                Text(
                    modifier = Modifier
                        .clickable { toFriendsListScreen() },
                    text = "친구 선택"
                )
            } else {
                LazyRow {
                    itemsIndexed(friendsList) { index, friend ->
                        Text(
                            modifier = Modifier
                                .padding(end = 10.dp),
                            text = friend.name
                        )
                    }
                }
            }
        }

        Text(
            modifier = Modifier
                .padding(top = 20.dp),
            text = "일정을 입력하세요"
        )

        // 날짜, 시간 선택
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.calendar_outlined),
                contentDescription = null
            )
            Text(
                modifier = Modifier
                    .width(100.dp)
                    .clickable {
                        isStartDate.value = true
                        isDatePickerDialogShowing.value = true
                    },
                text = viewModel.startDate.collectAsState().value,
                textAlign = TextAlign.Center
            )
            Image(
                modifier = Modifier
                    .padding(start = 10.dp, end = 10.dp),
                painter = painterResource(id = R.drawable.baseline_arrow_forward_ios_24),
                contentDescription = null
            )
            Text(
                modifier = Modifier
                    .width(100.dp)
                    .clickable {
                        isStartDate.value = false
                        isDatePickerDialogShowing.value = true
                    },
                text = viewModel.endDate.collectAsState().value,
                textAlign = TextAlign.Center
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.calendar_outlined),
                contentDescription = null
            )
            Text(
                modifier = Modifier
                    .width(100.dp)
                    .clickable {
                        isStartTime.value = true
                        isTimePickerDialogShowing.value = true
                    },
                text = viewModel.startTime.collectAsState().value,
                textAlign = TextAlign.Center
            )
            Image(
                modifier = Modifier
                    .padding(start = 10.dp, end = 10.dp),
                painter = painterResource(id = R.drawable.baseline_arrow_forward_ios_24),
                contentDescription = null
            )
            Text(
                modifier = Modifier
                    .width(100.dp)
                    .clickable {
                        isStartTime.value = false
                        isTimePickerDialogShowing.value = true
                    },
                text = viewModel.endTime.collectAsState().value,
                textAlign = TextAlign.Center
            )

        }

        // 위치 선택
        val destinationName = viewModel.destinationName.collectAsState().value
        val destinationAddress = viewModel.destinationAddress.collectAsState().value
        Text(
            modifier = Modifier
                .padding(top = 20.dp)
                .clickable {
                    toSearchLocationScreen()
                },
            text = when (destinationName == "") {
                true -> "장소 선택"
                false -> "${destinationName.replace("<b>", "").replace("</b>", "")}\n$destinationAddress"
            }
        )
        // 알림 선택


        // 메모

        if (isDatePickerDialogShowing.value) {
            MyDatePickerDialog(
                onDismissRequest = { isDatePickerDialogShowing.value = false },
                isStartDate = isStartDate.value
            )
        }
        if (isTimePickerDialogShowing.value) {
            MyTimePickerDialog(
                onDismissRequest = { isTimePickerDialogShowing.value = false },
                isStartTime = isStartTime.value
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            contentAlignment = Alignment.BottomCenter
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .background(
                        color = Color.DarkGray,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .clickable {

                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "완료",
                    color = Color.White
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyDatePickerDialog(
    onDismissRequest: () -> Unit,
    isStartDate: Boolean,
    viewModel: NewScheduleViewModel = hiltViewModel()
) {
    val context = LocalContext.current
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
                        if (isStartDate) {
                            viewModel.updateStartDate(
                                SimpleDateFormat("yyyy-MM-dd").format(
                                    datePickerState.selectedDateMillis
                                )
                            )
                        } else {
                            viewModel.updateEndDate(
                                SimpleDateFormat("yyyy-MM-dd").format(
                                    datePickerState.selectedDateMillis
                                )
                            )
                        }
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
fun MyTimePickerDialog(
    onDismissRequest: () -> Unit,
    isStartTime: Boolean,
    viewModel: NewScheduleViewModel = hiltViewModel()
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
                    Spacer(modifier = Modifier.weight(1f))
                    TextButton(
                        onClick = onDismissRequest
                    ) { Text("Cancel") }
                    TextButton(
                        onClick = {
                            if (isStartTime) {
                                viewModel.updateStartTime(String.format("%02d:%02d", timePickerState.hour, timePickerState.minute))
                            } else {
                                viewModel.updateEndTime(String.format("%02d:%02d", timePickerState.hour, timePickerState.minute))
                            }
                            onDismissRequest()
                        }
                    ) { Text("OK") }
                }
            }
        }
    }
}