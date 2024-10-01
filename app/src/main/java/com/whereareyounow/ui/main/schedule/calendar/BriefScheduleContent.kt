@file:OptIn(ExperimentalFoundationApi::class)

package com.whereareyounow.ui.main.schedule.calendar

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.whereareyounow.data.globalvalue.CALENDAR_VIEW_HEIGHT
import com.whereareyounow.data.globalvalue.DAILY_BRIEF_SCHEDULE_VIEW_HEIGHT
import com.whereareyounow.data.globalvalue.TOP_BAR_HEIGHT
import com.whereareyounow.domain.entity.schedule.BriefSchedule
import com.whereareyounow.ui.theme.OnMyWayTheme
import com.whereareyounow.ui.theme.lato
import com.whereareyounow.util.calendar.getCalendarFromString
import com.whereareyounow.util.clickableNoEffect
import java.util.Calendar
import kotlin.math.roundToInt

@OptIn(ExperimentalFoundationApi::class)
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
            .padding(top = ((CALENDAR_VIEW_HEIGHT + TOP_BAR_HEIGHT) / density + 2).dp)
            .height(((DAILY_BRIEF_SCHEDULE_VIEW_HEIGHT / density)).dp)
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
                shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp)
            )
            .background(
                color = Color(0xFFFFFFFF),
                shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp)
            )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(30.dp)
                .anchoredDraggable(state, Orientation.Vertical),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .width(26.dp)
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
                .height(((DAILY_BRIEF_SCHEDULE_VIEW_HEIGHT / density) - 20).dp)
                .background(
                    color = Color(0xFFFFFFFF)
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
            fontSize = 16.sp,
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
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = -(0.05).em,
            style = TextStyle(
                lineHeight = 28.sp
            )
        )

        LazyColumn(
            contentPadding = PaddingValues(start = 10.dp, top = 10.dp, end = 10.dp, bottom = 20.dp)
        ) {
            if (currentDateBriefSchedule.isEmpty()) {
                item {
                    Column(
                        modifier = Modifier
                            .padding(start = 10.dp, top = 10.dp, end = 10.dp)
                            .height(80.dp)
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(color = Color(0XFFF0F0F0))
                            .padding(start = 20.dp, top = 10.dp, end = 20.dp, bottom = 10.dp),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "오늘의 일정이 없습니다.",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF8D8D8D)
                        )
                    }
                }
            } else {
                itemsIndexed(currentDateBriefSchedule) { _, item ->
                    val calendar = getCalendarFromString(item.appointmentTime)
                    var appointmentHour = calendar.get(Calendar.HOUR)
                    val appointmentMinute = calendar.get(Calendar.MINUTE)
                    val appointmentTimeAMPM: String = if (calendar.get(Calendar.AM_PM) == 0) "오전" else "오후"
                    if (appointmentHour == 0) appointmentHour = 12

                    Column(
                        modifier = Modifier
                            .padding(start = 10.dp, top = 10.dp, end = 10.dp)
                            .height(80.dp)
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(
                                when (calendar.before(Calendar.getInstance())) {
                                    true -> Color(0XFFF5F5F5)
                                    false -> Color(0XFFFFD79B)
                                }
                            )
                            .clickableNoEffect {
                                moveToDetailScreen(item.scheduleId)
                            }
                            .padding(start = 20.dp, top = 10.dp, end = 20.dp, bottom = 10.dp),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = item.title,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Medium,
                            color = when (calendar.before(Calendar.getInstance())) {
                                true -> Color(0XFFA8A8A8)
                                false -> Color(0xFF4A302C)
                            },
                            letterSpacing = 0.em
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "$appointmentTimeAMPM ${appointmentHour}:${String.format("%02d", appointmentMinute)}",
                            fontSize = 14.sp,
                            color = when (calendar.before(Calendar.getInstance())) {
                                true -> Color(0XFFA8A8A8)
                                false -> Color(0xFF675555)
                            },
                            letterSpacing = 0.em
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
@Preview(showBackground = true, device = "spec:width=1440px,height=3200px,dpi=556")
private fun BriefScheduleContentPreview() {
    val briefScheduleList = listOf(
        BriefSchedule("", "title", "2023-01-02T10:20")
    )
//    val anchoredDraggableState = AnchoredDraggableState(
//        initialValue = DetailState.Open,
//        positionalThreshold = { it: Float -> it * 0.5f },
//        velocityThreshold = { 100f },
//        snapAnimationSpec = tween(400),
//        decayAnimationSpec = splineBasedDecay(density)
//    ).apply {
//        updateAnchors(
//            DraggableAnchors {
//                DetailState.Open at 0f
//                DetailState.Close at DAILY_BRIEF_SCHEDULE_VIEW_HEIGHT
//            }
//        )
//    }
    OnMyWayTheme {
//        BriefScheduleContent(
//            selectedYear = 2024,
//            selectedMonth = 1,
//            selectedDate = 2,
//            dayOfWeek = 1,
//            currentDateBriefSchedule = briefScheduleList,
//            moveToDetailScreen = {},
//            state = anchoredDraggableState
//        )
    }
}