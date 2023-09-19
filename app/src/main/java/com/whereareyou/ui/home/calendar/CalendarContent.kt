package com.whereareyou.ui.home.calendar

import android.util.Log
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun CalendarContent(
    paddingValues: PaddingValues,
    viewModel: CalendarViewModel = hiltViewModel(),
    topBarHeight: Int = LocalConfiguration.current.screenHeightDp / 10
) {
    var componentHeight by remember { mutableStateOf(0) }
    val density = LocalDensity.current

    Column(
        modifier = Modifier
            .padding(paddingValues)
            .padding(start = 20.dp, end = 20.dp)
            .fillMaxSize()
            .background(
                color = Color(0xFFFAFAFA)
            )
            .onGloballyPositioned {
                componentHeight = (it.size.height / density.density).toInt()
            }
    ) {
        val year = viewModel.year.collectAsState().value
        val month = viewModel.month.collectAsState().value
        val date = viewModel.date.collectAsState().value
        val calendarState = viewModel.calendarState.collectAsState().value

        var visible by remember {
            mutableStateOf(false)
        }
        val animatedSize by animateDpAsState(
            targetValue = if (visible) (componentHeight / 2).dp else 0.dp,
            tween(
                durationMillis = 500
            )
        )
        // 상단바
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(topBarHeight.dp)
                .background(
                    color = Color(0xFFCE93D8)
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier
                    .clickable {
                        visible = false
                        viewModel.updateCalendarState(CalendarViewModel.CalendarState.YEAR)
                    },
                text = "${year}년"
            )
            Spacer(
                modifier = Modifier
                    .width(10.dp)
            )
            if (calendarState == CalendarViewModel.CalendarState.DATE) {
                Text(
                    modifier = Modifier
                        .clickable {
                            visible = false
                            viewModel.updateCalendarState(CalendarViewModel.CalendarState.MONTH)
                        },
                    text = "${month + 1}월"
                )
            }
        }
        Box(
            modifier = Modifier
                .height((componentHeight - animatedSize.value).dp)
        ) {
            DateContent(
                expandDetailContent = {
                    visible = !visible
                }
            )

            MonthContent(
                hideDetailContent = {
                    visible = false
                }
            )

            YearContent(
                hideDetailContent = {
                    visible = false
                }
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(animatedSize)
                .background(
                    color = Color(0x0000FF00)
                )
        )
    }
}