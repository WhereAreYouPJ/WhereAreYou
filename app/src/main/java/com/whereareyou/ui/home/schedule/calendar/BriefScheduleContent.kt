@file:OptIn(ExperimentalFoundationApi::class)

package com.whereareyou.ui.home.schedule.calendar

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.whereareyou.data.GlobalValue
import com.whereareyou.domain.entity.schedule.BriefSchedule
import kotlin.math.roundToInt

@Composable
fun BriefScheduleContent(
    moveToDetailScreen: (String) -> Unit,
    state: AnchoredDraggableState<DetailState>
) {
    BriefScheduleContainer(state) {
        BriefScheduleList(
            moveToDetailScreen = moveToDetailScreen
        )
    }
}

@Composable
fun BriefScheduleContainer(
    state: AnchoredDraggableState<DetailState>,
    briefScheduleList: @Composable () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(top = ((GlobalValue.calendarViewHeight + GlobalValue.topBarHeight) / GlobalValue.density + 2).dp)
            .height(((GlobalValue.dailyScheduleViewHeight / GlobalValue.density)).dp)
            .offset {
                IntOffset(
                    x = 0,
                    y = state
                        .requireOffset()
                        .roundToInt()
                )
            }
            .padding(start = 20.dp, end = 20.dp)
            .shadow(
                elevation = 10.dp,
                shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
            )
            .background(
                color = Color(0xFFFFFFFF),
                shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
            )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(20.dp)
                .anchoredDraggable(state, Orientation.Vertical),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .width(40.dp)
                    .height(4.dp)
                    .background(
                        color = Color(0xFF8C9EFF),
                        shape = RoundedCornerShape(100)
                    )
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(((GlobalValue.dailyScheduleViewHeight / GlobalValue.density) - 20).dp)
                .background(
                    color = Color(0xFFFFF9C4)
                )
        ) {
            briefScheduleList()
        }
    }
}

@Composable
fun BriefScheduleList(
    moveToDetailScreen: (String) -> Unit,
    viewModel: CalendarViewModel = hiltViewModel(),
) {
    val year = viewModel.year.collectAsState().value
    val month = viewModel.month.collectAsState().value
    val date = viewModel.date.collectAsState().value
    val dayOfWeek = viewModel.dayOfWeek.collectAsState().value
    val currentDateBriefSchedule = viewModel.currentDateBriefSchedule
    Column() {
        Text(
            modifier = Modifier.padding(start = 20.dp, top = 20.dp),
            text = "$year.$month.$date",
            fontSize = 20.sp
        )
        Text(
            modifier = Modifier.padding(start = 20.dp),
            text = "" + when (dayOfWeek) {
                1 -> "일"
                2 -> "월"
                3 -> "화"
                4 -> "수"
                5 -> "목"
                6 -> "금"
                else -> "토"
            } + "요일",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )
        LazyColumn(
            contentPadding = PaddingValues(start = 10.dp, top = 10.dp, end = 10.dp, bottom = 20.dp)
        ) {
            itemsIndexed(currentDateBriefSchedule) { _, item ->
                var startHour = item.start.split("T")[1].split(":")[0].toInt()
                val startMinute = item.start.split("T")[1].split(":")[1].toInt()
                var endHour = item.end.split("T")[1].split(":")[0].toInt()
                val endMinute = item.end.split("T")[1].split(":")[1].toInt()
                var startAMPM: String = if (startHour < 12) "오전" else { startHour -= 12; "오후"}
                var endAMPM: String = if (endHour < 12) "오전" else {endHour -= 12; "오후"}

                Column(
                    modifier = Modifier
                        .padding(start = 10.dp, top = 10.dp, end = 10.dp)
                        .fillMaxWidth()
                        .height(80.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(color = Color(0XFF80DEEA))
                        .clickable {
                            moveToDetailScreen(item.scheduleId)
                        }
                        .padding(20.dp)
                ) {

                    Text(
                        text = "${item.title}",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = String.format("$startAMPM %02d:%02d - $endAMPM %02d:%02d", startHour, startMinute, endHour, endMinute),
                        fontSize = 20.sp
                    )
                }
            }
        }
    }
}