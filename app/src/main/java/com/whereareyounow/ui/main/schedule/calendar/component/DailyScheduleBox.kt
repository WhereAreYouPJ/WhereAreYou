package com.whereareyounow.ui.main.schedule.calendar.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.whereareyounow.domain.entity.schedule.DailyScheduleInfo
import com.whereareyounow.globalvalue.type.ScheduleColor
import com.whereareyounow.ui.theme.medium14pt
import com.whereareyounow.ui.theme.medium16pt
import com.whereareyounow.util.calendar.parseLocalDate
import com.whereareyounow.util.calendar.parseLocalDateTime

@Composable
fun DailyScheduleBox(
    info: DailyScheduleInfo
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(10.dp)
                .clip(CircleShape)
                .background(
                    color = when (info.color) {
                        "Red" -> ScheduleColor.Red.color
                        "Yellow" -> ScheduleColor.Yellow.color
                        "Green" -> ScheduleColor.Green.color
                        "Blue" -> ScheduleColor.Blue.color
                        "Purple" -> ScheduleColor.Purple.color
                        else -> ScheduleColor.Pink.color
                    }
                )
        )

        Spacer(Modifier.width(4.dp))

        Column {
            Text(
                modifier = Modifier.padding(start = 6.dp, top = 2.dp, end = 6.dp, bottom = 2.dp),
                text = info.title,
                color = Color(0xFF222222),
                style = medium16pt
            )

            Text(
                modifier = Modifier.padding(start = 6.dp, top = 2.dp, end = 6.dp, bottom = 2.dp),
                text = info.location,
                color = Color(0xFF767676),
                style = medium14pt
            )

            if (!info.allDay) {
                Text(
                    modifier = Modifier.padding(start = 8.dp, top = 2.dp, bottom = 2.dp),
                    text = if (parseLocalDate(info.startTime).year == parseLocalDate(info.endTime).year &&
                        parseLocalDate(info.startTime).monthValue == parseLocalDate(info.endTime).monthValue &&
                        parseLocalDate(info.startTime).dayOfMonth == parseLocalDate(info.endTime).dayOfMonth) "${parseLocalDateTime(info.startTime).hour}시 - ${parseLocalDateTime(info.endTime).hour}시"
                    else "${parseLocalDateTime(info.startTime).monthValue}월 ${parseLocalDateTime(info.startTime).dayOfMonth}일 ${parseLocalDateTime(info.startTime).hour}시 - " +
                            "${parseLocalDateTime(info.endTime).monthValue}월 ${parseLocalDateTime(info.endTime).dayOfMonth}일 ${parseLocalDateTime(info.endTime).hour}시",
                    color = Color(0xFF767676),
                    style = medium14pt
                )
            }
        }

        Spacer(Modifier.weight(1f))

        Text(
            modifier = Modifier.padding(end = 6.dp),
            text = "삭제"
        )
    }
}