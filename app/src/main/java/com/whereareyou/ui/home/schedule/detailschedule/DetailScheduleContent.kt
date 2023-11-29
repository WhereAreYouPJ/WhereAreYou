package com.whereareyou.ui.home.schedule.detailschedule

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.whereareyou.R

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
                        brush = Brush.horizontalGradient(listOf(Color.Red, Color.Blue)),
                        alpha = 0.4f
                    )
            )
        }
        Column(
            modifier = Modifier
                .padding(start = 20.dp, top = 80.dp, end = 20.dp, bottom = 80.dp)
                .fillMaxSize()
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(20.dp)
                )
                .padding(20.dp)
        ) {
            Text(
                text = "${viewModel.title.collectAsState().value}",
                fontSize = 30.sp,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(30.dp))
            // 날짜, 시간 정보
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier
                        .size(30.dp),
                    painter = painterResource(id = R.drawable.baseline_calendar_month_24),
                    contentDescription = null
                )
                Text(
                    text = "${viewModel.startTime.collectAsState().value.replace("T", "\n")}",
                    fontSize = 20.sp
                )
                Image(
                    modifier = Modifier
                        .padding(start = 10.dp, end = 10.dp),
                    painter = painterResource(id = R.drawable.baseline_arrow_forward_ios_24),
                    contentDescription = null
                )
                Text(
                    text = "${viewModel.endTime.collectAsState().value.replace("T", "\n")}",
                    fontSize = 20.sp
                )
            }
            Spacer(modifier = Modifier.height(20.dp))

            // 목적지 정보
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier
                        .size(30.dp),
                    painter = painterResource(id = R.drawable.outline_location_on_24),
                    contentDescription = null
                )
                Text(
                    text = "${viewModel.place.collectAsState().value}",
                    fontSize = 20.sp
                )
            }

            // 회원 리스트 정보
            val memberList = viewModel.userInfos.collectAsState().value
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier
                        .size(30.dp),
                    painter = painterResource(id = R.drawable.outline_location_on_24),
                    contentDescription = null
                )
                LazyRow() {
                    itemsIndexed(memberList) {_, userInfo ->
                        Text(
                            text = "${userInfo.name}"
                        )
                    }
                }
            }

            Text(
                text = "메모: ${viewModel.memo.collectAsState().value}",
                fontSize = 20.sp
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