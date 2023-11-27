package com.whereareyou.ui.home.schedule.detailschedule

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun DetailScheduleContent(
    moveToUserMapScreen: () -> Unit,
    viewModel: DetailScheduleViewModel = hiltViewModel()
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = Color.LightGray
                )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(
                        color = Color.Blue
                    )
            )
        }
        Column(
            modifier = Modifier
                .padding(start = 20.dp, top = 100.dp, end = 20.dp, bottom = 100.dp)
                .fillMaxSize()
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(20.dp)
                )
                .padding(20.dp)
        ) {

            Text(
                text = "제목 : ${viewModel.title.collectAsState().value}",
                fontSize = 30.sp,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = "시작 시간 : ${viewModel.startTime.collectAsState().value}"
            )
            Text(
                text = "종료 시간 : ${viewModel.endTime.collectAsState().value}"
            )
            Text(
                text = "장소 : ${viewModel.place.collectAsState().value}"
            )
            Text(
                text = "메모: ${viewModel.memo.collectAsState().value}"
            )
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .background(color = Color.Blue)
                    .clickable {
                        moveToUserMapScreen()
                    }
            )
        }
    }
}