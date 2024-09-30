package com.whereareyounow.ui.main.schedule.scheduleedit.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.whereareyounow.ui.theme.getColor
import com.whereareyounow.ui.theme.medium16pt

@Composable
fun ScheduleEditScreenScheduleTitle(
    scheduleName: String,
    updateScheduleName: (String) -> Unit
) {
    val thinnest = getColor().thinnest
    BasicTextField(
        modifier = Modifier.fillMaxWidth(),
        value = scheduleName,
        onValueChange = { updateScheduleName(it) },
        textStyle = medium16pt.copy(color = Color(0xFF222222)),
        singleLine = true
    ) {
        Box(
            modifier = Modifier
                .width(IntrinsicSize.Max)
                .height(36.dp)
                .drawBehind {
                    drawLine(
                        color = thinnest,
                        start = Offset(0f, size.height),
                        end = Offset(size.width, size.height),
                        strokeWidth = 1.dp.toPx()
                    )
                }
                .padding(start = 6.dp, top = 4.dp)
        ) {
            it()
            if (scheduleName == "") {
                Text(
                    text = "일정명을 입력해주세요.",
                    color = getColor().dark,
                    style = medium16pt
                )
            }
        }
    }
}