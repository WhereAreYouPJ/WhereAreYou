package com.whereareyounow.ui.main.schedule.detailschedule

import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.whereareyounow.R
import com.whereareyounow.data.detailschedule.DetailScheduleScreenUIState
import com.whereareyounow.data.globalvalue.BOTTOM_NAVIGATION_BAR_HEIGHT
import com.whereareyounow.globalvalue.type.ScheduleColor
import com.whereareyounow.ui.component.CustomSurface
import com.whereareyounow.ui.theme.getColor
import com.whereareyounow.ui.theme.medium12pt
import com.whereareyounow.ui.theme.medium14pt
import com.whereareyounow.ui.theme.medium16pt
import com.whereareyounow.ui.theme.pretendard
import com.whereareyounow.util.calendar.parseLocalDate
import com.whereareyounow.util.calendar.parseLocalDateTime
import com.whereareyounow.util.clickableNoEffect
import java.time.DayOfWeek
import java.time.LocalDate

@Composable
fun DetailScheduleScreen(
    scheduleSeq: Int,
    moveToBackScreen: () -> Unit,
    moveToScheduleModificationScreen: () -> Unit,
    viewModel: DetailScheduleViewModel = hiltViewModel(),
) {
    val uiState = viewModel.uiState.collectAsState().value
    LaunchedEffect(Unit) {
        viewModel.getDetailSchedule(scheduleSeq)
    }

    DetailScheduleScreen(
        uiState = uiState,
        moveToBackScreen = moveToBackScreen,
        moveToScheduleModificationScreen = moveToScheduleModificationScreen
    )
}

@Composable
private fun DetailScheduleScreen(
    uiState: DetailScheduleScreenUIState,
    moveToBackScreen: () -> Unit,
    moveToScheduleModificationScreen: () -> Unit,
) {
    val thinnest = getColor().thinnest
    val context = LocalContext.current
    val startLDT = remember(uiState) { parseLocalDateTime(uiState.scheduleInfo.startTime) }
    val endLDT = remember(uiState) {parseLocalDateTime(uiState.scheduleInfo.endTime) }

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
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(36.dp)
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
                                text = uiState.scheduleInfo.title,
                                color = Color(0xFF222222),
                                style = medium16pt
                            )
                        }

                        Spacer(Modifier.height(16.dp))

                        Row(
                            modifier = Modifier.height(40.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                modifier = Modifier.padding(start = 6.dp, top = 4.dp, end = 6.dp, bottom = 4.dp),
                                text = "하루 종일",
                                color = Color(0xFF222222),
                                style = medium16pt
                            )

                            Spacer(Modifier.weight(1f))

                            Switch(
                                checked = uiState.scheduleInfo.allDay,
                                onCheckedChange = {},
                                enabled = false
                            )

                            Spacer(Modifier.width(10.dp))
                        }

                        Spacer(Modifier.height(6.dp))

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 6.dp)
                                .animateContentSize(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            if (uiState.scheduleInfo.allDay) {
                                Image(
                                    modifier = Modifier.size(12.dp),
                                    painter = painterResource(R.drawable.ic_warning_red),
                                    contentDescription = null
                                )

                                Spacer(Modifier.width(4.dp))

                                Text(
                                    text = "위치 확인하기 기능이 제공되지 않습니다.",
                                    color = getColor().warning,
                                    style = medium12pt
                                )

                                Spacer(Modifier.height(12.dp))
                            }
                        }

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
                                        .padding(
                                            start = 6.dp,
                                            top = 4.dp,
                                            end = 6.dp,
                                            bottom = 4.dp
                                        ),
                                    text = "${startLDT.monthValue}월 ${startLDT.dayOfMonth}일 (${when (startLDT.dayOfWeek) {
                                        DayOfWeek.MONDAY -> "월"
                                        DayOfWeek.TUESDAY -> "화"
                                        DayOfWeek.WEDNESDAY -> "수"
                                        DayOfWeek.THURSDAY -> "목"
                                        DayOfWeek.FRIDAY -> "금"
                                        DayOfWeek.SATURDAY -> "토"
                                        DayOfWeek.SUNDAY -> "일"
                                    }})",
                                    color = Color(0xFF444444),
                                    style = medium16pt
                                )

                                Spacer(Modifier.height(8.dp))

                                if (!uiState.scheduleInfo.allDay) {
                                    Text(
                                        modifier = Modifier
                                            .padding(
                                                start = 8.dp,
                                                top = 4.dp,
                                                end = 8.dp,
                                                bottom = 4.dp
                                            ),
                                        text = if (startLDT.hour >= 12) "오후 ${if (startLDT.hour == 12) 12 else (startLDT.hour - 12)}:${String.format("%02d", startLDT.minute)}"
                                        else "오전 ${if (startLDT.hour == 0) 12 else startLDT.hour}:${String.format("%02d", startLDT.minute)}",
                                        color = Color(0xFF444444),
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
                                        .padding(
                                            start = 6.dp,
                                            top = 4.dp,
                                            end = 6.dp,
                                            bottom = 4.dp
                                        ),
                                    text = "${endLDT.monthValue}월 ${endLDT.dayOfMonth}일 (${when (endLDT.dayOfWeek) {
                                        DayOfWeek.MONDAY -> "월"
                                        DayOfWeek.TUESDAY -> "화"
                                        DayOfWeek.WEDNESDAY -> "수"
                                        DayOfWeek.THURSDAY -> "목"
                                        DayOfWeek.FRIDAY -> "금"
                                        DayOfWeek.SATURDAY -> "토"
                                        DayOfWeek.SUNDAY -> "일"
                                    }})",
                                    color = Color(0xFF444444),
                                    style = medium16pt
                                )

                                Spacer(Modifier.height(8.dp))

                                if (!uiState.scheduleInfo.allDay) {
                                    Text(
                                        modifier = Modifier
                                            .padding(
                                                start = 8.dp,
                                                top = 4.dp,
                                                end = 8.dp,
                                                bottom = 4.dp
                                            ),
                                        text = if (endLDT.hour >= 12) "오후 ${if (endLDT.hour == 12) 12 else (endLDT.hour - 12)}:${String.format("%02d", endLDT.minute)}"
                                        else "오전 ${if (endLDT.hour == 0) 12 else endLDT.hour}:${String.format("%02d", endLDT.minute)}",
                                        color = Color(0xFF444444),
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
                                text = uiState.scheduleInfo.streetName,
                                color = Color(0xFF222222),
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
                                text = uiState.scheduleInfo.memberInfos.map { it.userName }.joinToString(", "),
                                color = Color(0xFF222222),
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
                                            if (uiState.scheduleInfo.color == item.colorName) {
                                                Modifier.border(
                                                    border = BorderStroke(
                                                        width = 1.dp,
                                                        color = Color(0xFF8E8E8E)
                                                    ),
                                                    shape = CircleShape
                                                )
                                            } else Modifier
                                        ),
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
                                .padding(8.dp)
                        ) {
                            Text(
                                text = uiState.scheduleInfo.memo,
                                color = Color(0xFF222222),
                                style = medium14pt
                            )
                        }
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
                        .clickableNoEffect { moveToScheduleModificationScreen() },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "수정",
                        color = Color(0xFF222222),
                        fontFamily = pretendard,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun DetailScheduleScreenPreview() {
}