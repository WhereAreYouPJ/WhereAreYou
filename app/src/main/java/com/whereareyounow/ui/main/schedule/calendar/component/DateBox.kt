package com.whereareyounow.ui.main.schedule.calendar.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.whereareyounow.domain.entity.schedule.MonthlySchedule
import com.whereareyounow.globalvalue.type.ScheduleColor
import com.whereareyounow.globalvalue.type.VisualType
import com.whereareyounow.ui.theme.getColor
import com.whereareyounow.ui.theme.medium14pt
import com.whereareyounow.util.clickableNoEffect
import com.whereareyounow.util.getScheduleColor
import org.jetbrains.annotations.Async.Schedule

@Composable
fun RowScope.DateBox(
    date: Int,
    isSelected: Boolean,
    isToday: Boolean,
    isCurrentMonth: Boolean,
    scheduleList: List<Pair<MonthlySchedule?, VisualType>>,
    onClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .weight(1f)
            .clickableNoEffect { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.weight(1f))

        Box(
            modifier = Modifier
                .size(24.dp)
                .clip(CircleShape)
                .background(if (isSelected) getColor().brandColor else Color.Transparent)
                .border(
                    border = BorderStroke(
                        width = 1.dp,
                        color = if (isToday) getColor().brandColor else Color.Transparent
                    ),
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                modifier = Modifier.offset(x = 0.dp, y = (-1).dp),
                text = date.toString(),
                textAlign = TextAlign.Center,
                color = if (isSelected) Color.White else Color(0xFF111111),
                style = medium14pt,
                lineHeight = 14.sp
            )
        }

        Spacer(Modifier.weight(1f))

        scheduleList.forEachIndexed { idx, item ->
            if (idx <= 3) {
                if (item.first == null) {
                    Spacer(Modifier.height(15.dp))
                } else {
                    ScheduleBar(
                        scheduleColor = getScheduleColor(item.first!!.color),
                        visualType = item.second,
                        scheduleName = item.first!!.title
                    )

                    Spacer(Modifier.height(2.dp))
                }
            }
        }

        Spacer(Modifier.weight(1f))
    }
}