package com.whereareyou.ui.home.calendar

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.whereareyou.util.AnimationUtil

@Composable
fun DateContent(
    expandDetailContent: () -> Unit,
    topBarHeight: Int = LocalConfiguration.current.screenHeightDp / 10,
    viewModel: CalendarViewModel = hiltViewModel()
) {
    val currMonthCalendarInfo = viewModel.currentMonthDateInfo.collectAsState().value
    val calendarState = viewModel.calendarState.collectAsState().value

    Log.e("composed", "composed")
    // 일자 선택 화면
    AnimatedVisibility(
        visible = calendarState == CalendarViewModel.CalendarState.DATE,
        enter = AnimationUtil.enterTransition,
        exit = AnimationUtil.exitTransition
    ) {
        Column() {
            Row(
                modifier = Modifier
                    .height((topBarHeight / 2).dp),
                horizontalArrangement = Arrangement.Center
            ) {
                for (i in 0..6) {
                    Box(
                        modifier = Modifier
                            .weight(1f),
                        contentAlignment = Alignment.TopCenter
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(top = 4.dp),
                            text = when (i) {
                                0 -> "일"
                                1 -> "월"
                                2 -> "화"
                                3 -> "수"
                                4 -> "목"
                                5 -> "금"
                                else -> "토"
                            }
                        )
                    }
                }
            }
            for (date in 0 until (currMonthCalendarInfo.size / 7)) {
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(
                            color = Color(0xFFDDDDDD)
                        )
                )
                Row(
                    modifier = Modifier
                        .weight(1f)
                ) {
                    for (i in 0..6) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight()
                                .clickable {
                                    expandDetailContent()
                                },
                            contentAlignment = Alignment.TopCenter
                        ) {
                            Text(
                                modifier = Modifier
                                    .padding(top = 4.dp),
                                text = currMonthCalendarInfo[i + date * 7].toString()
                            )
                        }
                    }
                }
            }
        }
    }
}