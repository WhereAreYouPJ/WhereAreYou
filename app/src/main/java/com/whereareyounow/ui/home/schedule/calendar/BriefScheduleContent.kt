@file:OptIn(ExperimentalFoundationApi::class)

package com.whereareyounow.ui.home.schedule.calendar

import android.util.Log
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.whereareyounow.data.GlobalValue
import com.whereareyounow.domain.entity.schedule.BriefSchedule
import com.whereareyounow.ui.theme.WhereAreYouTheme
import com.whereareyounow.ui.theme.lato
import kotlin.math.roundToInt

@Composable
fun BriefScheduleContent(
    selectedYear: Int,
    selectedMonth: Int,
    selectedDate: Int,
    dayOfWeek: Int,
    currentDateBriefSchedule: List<BriefSchedule>,
    moveToDetailScreen: (String) -> Unit,
    state: AnchoredDraggableState<DetailState>
) {
    BriefScheduleContainer(state) {
        BriefScheduleList(
            selectedYear = selectedYear,
            selectedMonth = selectedMonth,
            selectedDate = selectedDate,
            dayOfWeek = dayOfWeek,
            currentDateBriefSchedule = currentDateBriefSchedule,
            moveToDetailScreen = moveToDetailScreen
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BriefScheduleContainer(
    state: AnchoredDraggableState<DetailState>,
    briefScheduleList: @Composable () -> Unit
) {
    val density = LocalDensity.current.density
    Column(
        modifier = Modifier
            .padding(top = ((GlobalValue.calendarViewHeight + GlobalValue.topBarHeight) / density + 2).dp)
            .height(((GlobalValue.dailyScheduleViewHeight / density)).dp)
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
                        color = Color(0xFFC9C9D1),
                        shape = RoundedCornerShape(100)
                    )
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(((GlobalValue.dailyScheduleViewHeight / density) - 20).dp)
                .background(
                    color = Color(0xFFFFFF)
                )
        ) {
            briefScheduleList()
        }
    }
}

@Composable
fun BriefScheduleList(
    selectedYear: Int,
    selectedMonth: Int,
    selectedDate: Int,
    dayOfWeek: Int,
    currentDateBriefSchedule: List<BriefSchedule>,
    moveToDetailScreen: (String) -> Unit
) {
    Column {
        Text(
            modifier = Modifier.padding(start = 20.dp, top = 20.dp),
            text = "$selectedYear.$selectedMonth.$selectedDate",
            color = Color(0xFF878787),
            fontSize = 20.sp,
            fontFamily = lato,
            letterSpacing = 0.em
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
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        if (currentDateBriefSchedule.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "오늘의 일정이 없습니다.",
                    fontSize = 20.sp,
                )
            }
        } else {
            LazyColumn(
                contentPadding = PaddingValues(start = 10.dp, top = 10.dp, end = 10.dp, bottom = 20.dp)
            ) {
                itemsIndexed(currentDateBriefSchedule) { _, item ->
                    var appointmentHour = item.appointmentTime.split("T")[1].split(":")[0].toInt()
                    val appointmentMinute = item.appointmentTime.split("T")[1].split(":")[1].toInt()
                    val appointmentTimeAMPM: String = if (appointmentHour < 12) "오전" else { appointmentHour -= 12; "오후"}
                    if (appointmentHour == 0) appointmentHour = 12

                    Column(
                        modifier = Modifier
                            .padding(start = 10.dp, top = 10.dp, end = 10.dp)
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(color = Color(0XFFFFD79B))
                            .clickable {
                                moveToDetailScreen(item.scheduleId)
                            }
                            .padding(start = 20.dp, top = 10.dp, end = 20.dp, bottom = 10.dp)
                    ) {
                        Text(
                            text = item.title,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF4A302C)
                        )
                        Text(
                            text = "$appointmentTimeAMPM ${appointmentHour}:${String.format("%02d", appointmentMinute)}",
                            fontSize = 16.sp,
                            color = Color(0xFF675555)
                        )
                    }
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true, device = "spec:width=1440px,height=3200px,dpi=556")
private fun BriefScheduleContentPreview() {
    val briefScheduleList = listOf(
        BriefSchedule("", "title", "2023-01-02T10:20")
    )
    val anchoredDraggableState = AnchoredDraggableState(
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
    WhereAreYouTheme {
        BriefScheduleContent(
            selectedYear = 2024,
            selectedMonth = 1,
            selectedDate = 2,
            dayOfWeek = 1,
            currentDateBriefSchedule = briefScheduleList,
            moveToDetailScreen = {  },
            state = anchoredDraggableState
        )
    }
}