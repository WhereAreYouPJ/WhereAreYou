package com.whereareyounow.ui.main.schedule.calendar.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.whereareyounow.globalvalue.type.ScheduleColor
import com.whereareyounow.globalvalue.type.VisualType
import com.whereareyounow.ui.theme.notoSanskr

@Composable
fun ScheduleBar(
    scheduleColor: ScheduleColor,
    visualType: VisualType,
    scheduleName: String
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(13.dp)
            .padding(start = when (visualType) {
                VisualType.One, VisualType.Start -> 1.dp
                else -> 0.dp
            }, end = when (visualType) {
                VisualType.One, VisualType.End -> 1.dp
                else -> 0.dp
            })
            .clip(RoundedCornerShape(topStart = when (visualType) {
                VisualType.One, VisualType.Start -> 2.dp
                else -> 0.dp
            }, bottomStart = when (visualType) {
                VisualType.One, VisualType.Start -> 2.dp
                else -> 0.dp
            }, topEnd = when (visualType) {
                VisualType.One, VisualType.End -> 2.dp
                else -> 0.dp
            }, bottomEnd = when (visualType) {
                VisualType.One, VisualType.End -> 2.dp
                else -> 0.dp
            }))
            .background(scheduleColor.color),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = scheduleName,
            color = Color(0xFF222222),
            fontFamily = notoSanskr,
            fontWeight = FontWeight.Medium,
            fontSize = 9.sp,
//            lineHeight = 9.sp,
//            style = TextStyle(
//                platformStyle = PlatformTextStyle(false),
//                lineHeightStyle = LineHeightStyle(
//                    alignment = LineHeightStyle.Alignment.Top,
//                    trim = LineHeightStyle.Trim.None
//                )
//            )
        )
    }
}