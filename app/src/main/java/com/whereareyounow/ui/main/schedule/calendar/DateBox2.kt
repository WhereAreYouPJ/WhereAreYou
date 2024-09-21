package com.whereareyounow.ui.main.schedule.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.whereareyounow.data.globalvalue.DAILY_BRIEF_SCHEDULE_VIEW_HEIGHT
import com.whereareyounow.ui.theme.lato
import java.util.Calendar

@Composable
fun DateBox2(
    anchorOffset: Float,
    year: Int,
    month: Int,
    date: Int,
    scheduleCount: Int,
    isSelected: Boolean = false,
    textColor: Color
) {
    val density = LocalDensity.current
    var componentWidth by remember { mutableIntStateOf(0) }
    val calendar = remember{ Calendar.getInstance() }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .onGloballyPositioned {
                componentWidth = (it.size.width / density.density).toInt()
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .width((componentWidth * 0.5).dp)
                .height((componentWidth * 0.5).dp)
                .background(
                    color = if (isSelected) {
                        Color(0xFF1D1A7D)
                    } else {
                        if (calendar.get(Calendar.MONTH) + 1 == month && calendar.get(Calendar.DATE) == date) Color(0xFFFFD990) else Color(0x00000000)
                    },
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = date.toString(),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = when (isSelected) {
                    true -> Color(0xFFFFFFFF)
                    false -> textColor
                },
                fontFamily = lato,
            )
        }
        Spacer(Modifier.height(4.dp))
        if (scheduleCount > 0) {
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .alpha(1f - anchorOffset / DAILY_BRIEF_SCHEDULE_VIEW_HEIGHT)
                    .background(
                        shape = CircleShape,
                        color = Color(0xFF5B58B3)
                    )
            )
        } else {
            Spacer(Modifier.height(8.dp))
        }
//        Column(
//            modifier = Modifier
//                .width((componentWidth * 0.3).dp)
//                .height((componentWidth * 0.2).dp)
//        ) {
//            for (i in 0..1) {
//                Row(modifier = Modifier.weight(1f)) {
//                    for (j in 0..2) {
//                        Box(
//                            modifier = Modifier
//                                .weight(1f)
//                                .fillMaxHeight(),
//                            contentAlignment = Alignment.Center
//                        ) {
//                            if (i * 3 + j < scheduleCount) {
//                                Box(
//                                    modifier = Modifier
//                                        .size(4.dp)
//                                        .background(
//                                            shape = CircleShape,
//                                            color = Color(0xFF8C9EFF)
//                                        )
//                                )
//                            }
//                        }
//                    }
//                }
//            }
//        }
    }
}

@Preview(showBackground = true)
@Composable
fun DateBoxPreview() {
//    Column() {
//        Row() {
//            Box(
//                modifier = Modifier
//                    .size(50.dp)
//            ) {
//                DateBox(
//                    anchorOffset = 0f,
//                    year = 2024,
//                    month = 3,
//                    date = 2,
//                    scheduleCount = 0,
//                    isSelected = false,
//                    textColor = Color(0xFF000000)
//                )
//            }
//            Box(
//                modifier = Modifier
//                    .size(50.dp)
//            ) {
//                DateBox(
//                    anchorOffset = 0f,
//                    year = 2024,
//                    month = 3,
//                    date = 20,
//                    scheduleCount = 3,
//                    isSelected = false,
//                    textColor = Color(0xFF000000)
//                )
//            }
//        }
//        Row() {
//            Box(
//                modifier = Modifier
//                    .size(50.dp)
//            ) {
//                DateBox(
//                    anchorOffset = 0f,
//                    year = 2024,
//                    month = 3,
//                    date = 20,
//                    scheduleCount = 4,
//                    isSelected = false,
//                    textColor = Color(0xFF000000)
//                )
//            }
//            Box(
//                modifier = Modifier
//                    .size(50.dp)
//            ) {
//                DateBox(
//                    anchorOffset = 0f,
//                    year = 2024,
//                    month = 3,
//                    date = 20,
//                    scheduleCount = 6,
//                    isSelected = true,
//                    textColor = Color(0xFFFFFFFF)
//                )
//            }
//        }
//    }
}