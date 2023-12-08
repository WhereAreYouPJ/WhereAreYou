package com.whereareyounow.ui.home.schedule.notification

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.whereareyounow.R
import com.whereareyounow.domain.entity.apimessage.schedule.ScheduleInvitation

@Composable
fun ScheduleRequestBox(
    scheduleRequest: ScheduleInvitation,
    acceptScheduleRequest: () -> Unit,
    refuseScheduleRequest: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(
                        color = Color(0xFF555AC7),
                        shape = RoundedCornerShape(50)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    modifier = Modifier
                        .size(30.dp),
                    painter = painterResource(R.drawable.event_note_fill0_wght300_grad0_opsz24),
                    contentDescription = null,
//                    contentScale = ContentScale.Fit
                )
            }
            Spacer(modifier = Modifier.width(20.dp))
            Column() {
                Text(
                    text = "${scheduleRequest.title}",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = "${scheduleRequest.start}",
                    fontSize = 16.sp
                )
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height((0.6).dp)
                .background(color = Color.Black)
        )
        Column(
            modifier = Modifier
                .padding(10.dp)
        ) {
            Text(
                text = "초대 ${scheduleRequest.userName}",
                fontSize = 20.sp
            )
            Text(
                text = "시간 ${scheduleRequest.start}",
                fontSize = 20.sp
            )
            Spacer(modifier = Modifier.height(10.dp))
            Row(
            ) {
                ClickableBox(color = Color(0xFF2D2573), text = "수락", textColor = Color.White) { acceptScheduleRequest() }
                Spacer(modifier = Modifier.width(10.dp))
                ClickableBox(color = Color(0xFFE4E4E6), text = "거절") { refuseScheduleRequest() }
            }
        }
    }
}