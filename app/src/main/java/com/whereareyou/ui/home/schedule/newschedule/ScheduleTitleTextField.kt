package com.whereareyou.ui.home.schedule.newschedule

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.whereareyou.R

@Composable
fun ScheduleTitleTextField(
    title: String,
    updateTitle: (String) -> Unit,
    clearTitle: () -> Unit
) {
    if (title == "") updateTitle("제목을 입력하세요")
    BasicTextField(
        modifier = Modifier
            .onFocusChanged {
                if (it.isFocused && title == "제목을 입력하세요") { updateTitle("") }
                else if (!it.isFocused && title == "") { updateTitle("제목을 입력하세요") }
                Log.e("onFocusChanged", "${it}")
            },
        value = title,
        onValueChange = {
            if (title == "제목을 입력하세요") {
                Log.e("onValueChange", "${it}, $title")
                updateTitle("")
            } else {
                updateTitle(it)
            }
        },
        textStyle = TextStyle(fontSize = 30.sp),
        singleLine = true,
        decorationBox = {
            Box(
                modifier = Modifier
//                    .fillMaxWidth()
                    .width(IntrinsicSize.Min)
                    .background(color = Color.Cyan)
                    .drawBehind {
                        val borderSize = 1.dp.toPx()
                        drawLine(
                            color = Color(0xFF858585),
                            start = Offset(0f, size.height),
                            end = Offset(size.width, size.height),
                            strokeWidth = borderSize
                        )
                    },
                contentAlignment = Alignment.CenterStart
            ) {
                it()
//                Box(
//                    modifier = Modifier,
//                        .fillMaxWidth(),
//                    contentAlignment = Alignment.CenterEnd
//                ) {
//                    Image(
//                        modifier = Modifier
//                            .clickable {
//                                clearTitle()
//                            },
//                        painter = painterResource(id = R.drawable.baseline_cancel_24),
//                        contentDescription = null,
//                        colorFilter = ColorFilter.tint(color = Color(0xFF858585))
//                    )
//                }
            }
        }
    )
}