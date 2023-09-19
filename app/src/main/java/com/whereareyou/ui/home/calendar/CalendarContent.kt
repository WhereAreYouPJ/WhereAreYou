package com.whereareyou.ui.home.calendar

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.whereareyou.R
import com.whereareyou.util.AnimationUtil
import com.whereareyou.util.CalendarUtil
import kotlinx.coroutines.launch
import java.util.Calendar

@Composable
fun CalendarContent(
    paddingValues: PaddingValues,
    viewModel: CalendarViewModel = hiltViewModel(),
    topBarHeight: Int = LocalConfiguration.current.screenHeightDp / 10
) {
    Column(
        modifier = Modifier
            .padding(paddingValues)
            .padding(start = 20.dp, end = 20.dp)
            .fillMaxSize()
            .background(
                color = Color(0xFFFAFAFA)
            )
    ) {
        val year = viewModel.year.collectAsState().value
        val month = viewModel.month.collectAsState().value
        val date = viewModel.date.collectAsState().value
        val calendarState = viewModel.calendarState.collectAsState().value

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
//                Image(
//                    painter = painterResource(id = R.drawable.baseline_arrow_back_ios_24),
//                    contentDescription = null
//                )
            Text(
                modifier = Modifier
                    .clickable {
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
                            viewModel.updateCalendarState(CalendarViewModel.CalendarState.MONTH)
                        },
                    text = "${month + 1}월"
                )
            }
//                Image(
//                        painter = painterResource(id = R.drawable.baseline_arrow_forward_ios_24),
//                contentDescription = null
//                )
        }
        Box(
            modifier = Modifier
                .weight(1f)
        ) {
            DateContent()

            MonthContent()

            YearContent()
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(
                    color = Color(0xFF00FF00)
                )
        ) {
            Text(
                text = "hello"
            )
        }
    }
}