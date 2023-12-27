package com.whereareyounow.ui.home.schedule.newschedule

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import com.whereareyounow.R
import com.whereareyounow.data.GlobalValue
import com.whereareyounow.domain.entity.schedule.Friend
import java.text.SimpleDateFormat


@OptIn(ExperimentalNaverMapApi::class)
@Composable
fun NewScheduleContent(
    selectedFriendsList: List<Friend>,
    moveToBackScreen: () -> Unit,
    moveToFriendsListScreen: () -> Unit,
    moveToSearchLocationScreen: () -> Unit,
    viewModel: NewScheduleViewModel = hiltViewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 20.dp, end = 20.dp)
            .scrollable(
                state = rememberScrollState(),
                orientation = Orientation.Vertical
            )
    ) {
        val density = LocalDensity.current.density
        val isDatePickerDialogShowing = remember { mutableStateOf(false) }
        val isTimePickerDialogShowing = remember { mutableStateOf(false) }

        // 상단바
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height((GlobalValue.topBarHeight / density).dp),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.CenterStart
            ) {
                Image(
                    modifier = Modifier
                        .size((GlobalValue.topBarHeight / density / 3 * 2).dp)
                        .clip(RoundedCornerShape(50))
                        .clickable { moveToBackScreen() },
                    painter = painterResource(id = R.drawable.arrow_back),
                    contentDescription = null
                )
            }
            Text(
                text = "일정",
                fontSize = 30.sp,
                fontWeight = FontWeight.Medium
            )
        }

        Spacer(Modifier.height(10.dp))
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .fillMaxWidth()
                .height(60.dp)
                .clickable {
                    moveToFriendsListScreen()
                }
                .padding(start = 10.dp, end = 10.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            if (selectedFriendsList.isEmpty()) {
                Row {
                    Image(
                        modifier = Modifier.size(30.dp),
                        painter = painterResource(id = R.drawable.message_add),
                        contentDescription = null
                    )
                    Spacer(Modifier.width(10.dp))
                    Image(
                        modifier = Modifier.size(30.dp),
                        painter = painterResource(id = R.drawable.profile_circle),
                        contentDescription = null
                    )
                }
            } else {
                LazyRow {
                    itemsIndexed(selectedFriendsList) { _, friend ->
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            GlideImage(
                                modifier = Modifier
                                    .size(30.dp)
                                    .clip(RoundedCornerShape(50)),
                                imageModel = { friend.profileImgUrl ?: R.drawable.account_circle_fill0_wght200_grad0_opsz24 },
                                imageOptions = ImageOptions(
                                    contentScale = ContentScale.FillWidth,
                                )
                            )
                            Text(
                                text = friend.name
                            )
                        }
                        Spacer(Modifier.width(10.dp))
                    }
                }
            }
        }
        Spacer(Modifier.height(10.dp))

        // 제목
        val title = viewModel.title.collectAsState().value
        ScheduleTitleTextField(
            title = title,
            updateTitle = viewModel::updateTitle,
            clearTitle = viewModel::clearTitle
        )
        Spacer(Modifier.height(20.dp))

        // 날짜,시간 선택
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier.size(30.dp),
                painter = painterResource(id = R.drawable.baseline_calendar_month_24),
                contentDescription = null
            )
            Spacer(Modifier.width(10.dp))
            // 시작 날짜 선택
            Text(
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .clickable { isDatePickerDialogShowing.value = true }
                    .padding(10.dp),
                text = viewModel.appointmentDate.collectAsState().value,
                textAlign = TextAlign.Center,
                fontSize = 20.sp
            )
            // 시작 시간 선택
            Text(
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .clickable { isTimePickerDialogShowing.value = true }
                    .padding(10.dp),
                text = viewModel.appointmentTime.collectAsState().value,
                textAlign = TextAlign.Center,
                fontSize = 20.sp
            )
        }


        // 위치 선택
        val destinationName = viewModel.destinationName.collectAsState().value
        val destinationAddress = viewModel.destinationAddress.collectAsState().value
        Spacer(Modifier.height(20.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.CenterStart
        ) {
            Image(
                modifier = Modifier
                    .size(30.dp),
                painter = painterResource(id = R.drawable.outline_location_on_24),
                contentDescription = null
            )
            Text(
                modifier = Modifier
                    .padding(start = 30.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .fillMaxWidth()
                    .clickable {
                        moveToSearchLocationScreen()
                    }
                    .padding(10.dp),
                text = when (destinationName == "") {
                    true -> "장소 선택"
                    false -> "${destinationName.replace("<b>", "").replace("</b>", "")}\n$destinationAddress"
                },
                fontSize = 20.sp
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.CenterEnd
            ) {
                Image(
                    modifier = Modifier
                        .size(30.dp),
                    painter = painterResource(id = R.drawable.arrow_forward_ios_fill0_wght100_grad0_opsz24),
                    contentDescription = null
                )
            }
        }

        // 메모
        Spacer(Modifier.height(30.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier
                    .size(30.dp),
                painter = painterResource(id = R.drawable.article_fill0_wght200_grad0_opsz24),
                contentDescription = null
            )
            Spacer(Modifier.width(10.dp))
            Text(
                text = "메모",
                fontSize = 20.sp
            )
        }
        Spacer(Modifier.height(10.dp))
        BasicTextField(
            value = viewModel.memo.collectAsState().value,
            onValueChange = {
                viewModel.updateMemo(it)
            },
            textStyle = TextStyle(fontSize = 20.sp),
            singleLine = true,
            decorationBox = {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .background(
                            color = Color.LightGray,
                            shape = RoundedCornerShape(4.dp)
                        )
                        .padding(12.dp)
                ) {
                    it()
                }
            }
        )


        if (isDatePickerDialogShowing.value) {
            MyDatePickerDialog(
                onDismissRequest = { isDatePickerDialogShowing.value = false }
            )
        }
        if (isTimePickerDialogShowing.value) {
            MyTimePickerDialog(
                onDismissRequest = { isTimePickerDialogShowing.value = false }
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
                    .padding(bottom = 20.dp)
                    .fillMaxWidth()
                    .height(80.dp)
                    .background(
                        color = Color(0xFF2D2573),
                        shape = RoundedCornerShape(10.dp)
                    )
                    .clickable {
                        viewModel.addNewSchedule(moveToBackScreen = moveToBackScreen)
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "완료",
                    color = Color.White,
                    fontSize = 30.sp
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyDatePickerDialog(
    onDismissRequest: () -> Unit,
    viewModel: NewScheduleViewModel = hiltViewModel()
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
                        viewModel.updateAppointmentDate(
                            SimpleDateFormat("yyyy-MM-dd").format(
                                datePickerState.selectedDateMillis
                            )
                        )
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
                    Spacer(Modifier.weight(1f))
                    TextButton(
                        onClick = onDismissRequest
                    ) { Text("Cancel") }
                    TextButton(
                        onClick = {
                            viewModel.updateAppointmentTime(String.format("%02d:%02d", timePickerState.hour, timePickerState.minute))
                            onDismissRequest()
                        }
                    ) { Text("OK") }
                }
            }
        }
    }
}