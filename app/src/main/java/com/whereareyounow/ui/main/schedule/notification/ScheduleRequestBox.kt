package com.whereareyounow.ui.main.schedule.notification

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.whereareyounow.R
import com.whereareyounow.data.notification.ScheduleInvitationInfo
import com.whereareyounow.util.calendar.getTimeDiffWithCurrentTime

@Composable
fun ScheduleRequestBox(
    scheduleRequest: ScheduleInvitationInfo,
    acceptScheduleRequest: () -> Unit,
    refuseScheduleRequest: () -> Unit
) {
    val timePassed = getTimeDiffWithCurrentTime(scheduleRequest.invitedTime.split(".")[0])
    var hour = scheduleRequest.hour.toInt()
    val minute = scheduleRequest.minute.toInt()
    val AMPM: String = if (hour < 12) "오전" else { hour -= 12; "오후"}
    if (hour == 0) hour = 12

    Column(
        modifier = Modifier
            .padding(start = 20.dp, top = 10.dp, end = 20.dp, bottom = 10.dp)
            .fillMaxWidth()
            .shadow(elevation = 4.dp, shape = RoundedCornerShape(20.dp))
            .background(
                color = Color(0xFFFFFFFF),
                shape = RoundedCornerShape(20.dp)
            ),
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
            Spacer(Modifier.width(20.dp))
            Column {
                Row {
                    Text(
                        text = "${scheduleRequest.title}",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        letterSpacing = 0.05.em,
                        color = Color(0xFF030408)
                    )

                    Spacer(Modifier.weight(1f))

                    Text(
                        text = timePassed,
                        fontSize = 14.sp,
                        style = TextStyle(
                            lineHeight = 14.sp
                        ),
                        color = Color(0xFFB3B3B3)
                    )
                }
                Text(
                    text = "${scheduleRequest.year}년 ${scheduleRequest.month}월 ${scheduleRequest.date}일",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF7A7B7C)
                )
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height((0.6).dp)
                .background(color = Color(0xFFDCDCDC))
        )
        Column(
            modifier = Modifier
                .padding(10.dp)
        ) {
            Row {
                Text(
                    text = "초대",
                    color = Color(0xFFACACAC),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
                Spacer(Modifier.width(10.dp))
                Text(
                    text = "${scheduleRequest.inviterUserName}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = Color(0xFF7A7B7C)
                )
            }
            Row {
                Text(
                    text = "시간",
                    color = Color(0xFFACACAC),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                )
                Spacer(Modifier.width(10.dp))
                Text(
                    text = "$AMPM ${hour}:${String.format("%02d", minute)}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF7A7B7C)
                )
            }
            Spacer(Modifier.height(10.dp))
            Row {
                ClickableBox(color = Color(0xFF9286FF), text = "수락", textColor = Color(0xFFFFFFFF)) { acceptScheduleRequest() }
                Spacer(Modifier.width(10.dp))
                ClickableBox(color = Color(0xFFE4E4E6), text = "거절") { refuseScheduleRequest() }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ScheduleRequestBoxPreview() {
//    ScheduleRequestBox(
//        scheduleRequest = ScheduleInvitationInfo(
//            scheduleId = "",
//            title = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
//            inviterUserName = "bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb",
//            year = "2023",
//            month = "12",
//            date = "12",
//            hour = "13",
//            minute = "45",
//            invitedTime = ""
//        ),
//        acceptScheduleRequest = {},
//        refuseScheduleRequest = {}
//    )
}