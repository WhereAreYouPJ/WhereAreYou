package com.whereareyounow.ui.main.schedule.detailschedule

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.whereareyounow.R
import com.whereareyounow.data.detailschedule.DetailScheduleScreenUIState
import com.whereareyounow.globalvalue.type.ScheduleColor
import com.whereareyounow.ui.main.schedule.scheduleedit.component.ScheduleEditScreenScheduleTitle
import com.whereareyounow.ui.theme.getColor
import com.whereareyounow.ui.theme.medium14pt
import com.whereareyounow.ui.theme.medium16pt

@Composable
fun DetailScheduleScreen(
    scheduleSeq: Int,
    moveToBackScreen: () -> Unit,
    viewModel: DetailScheduleViewModel = hiltViewModel(),
) {
    val uiState = viewModel.uiState.collectAsState().value
    LaunchedEffect(Unit) {
        viewModel.getDetailSchedule(scheduleSeq)
    }

    DetailScheduleScreen(
        uiState = uiState
    )
}

@Composable
private fun DetailScheduleScreen(
    uiState: DetailScheduleScreenUIState
) {
    val thinnest = getColor().thinnest
    val context = LocalContext.current
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
                    .fillMaxSize()
            ) {
                // 제목
                ScheduleEditScreenScheduleTitle(
                    scheduleName = uiState.scheduleName,
                    updateScheduleName = {  }
                )

                Spacer(Modifier.height(16.dp))

                Row(
                    modifier = Modifier.height(40.dp)
                ) {

                    Spacer(Modifier.width(4.dp))

                    Text(
                        text = "하루 종일",
                        color = Color(0xFF222222),
                        style = medium16pt
                    )

                    Spacer(Modifier.weight(1f))

                    Switch(
                        checked = true,
                        onCheckedChange = {}
                    )

                    Spacer(Modifier.width(10.dp))
                }

                Spacer(Modifier.height(4.dp))

                Row(
                    modifier = Modifier
                        .height(50.dp)
                        .drawBehind {
                            drawLine(
                                color = thinnest,
                                start = Offset(0f, size.height),
                                end = Offset(size.width, size.height),
                                strokeWidth = 1.dp.toPx()
                            )
                        }
                ) {
                    Spacer(Modifier.width(26.dp))

                    Text(
                        text = "4월 8일 (월)",
                        color = Color(0xFF444444),
                        style = medium16pt
                    )

                    Spacer(Modifier.weight(1f))

                    Image(
                        modifier = Modifier.size(28.dp),
                        painter = painterResource(R.drawable.ic_arrow_right),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(Color(0xFF727272))
                    )

                    Spacer(Modifier.weight(1f))

                    Text(
                        text = "4월 10일 (수)",
                        color = Color(0xFF444444),
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
                ) {
                    Text(
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
                        text = "위치 추가",
                        color = getColor().dark,
                        style = medium16pt
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
                        painter = painterResource(R.drawable.ic_location),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(getColor().brandColor)
                    )

                    Spacer(Modifier.width(6.dp))

                    Text(
                        text = "친구 추가",
                        color = getColor().dark,
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
                                .border(
                                    border = BorderStroke(
                                        width = 1.dp,
                                        color = Color(0xFF8E8E8E)
                                    ),
                                    shape = CircleShape
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
//                ScheduleMemo(
//                    memo = scheduleEditScreenUIState.memo,
//                    updateMemo = updateMemo
//                )
            }
        }

        item {
            // 하단 완료 버튼


            Spacer(Modifier.height(20.dp))
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun DetailScheduleScreenPreview() {
}