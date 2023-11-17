package com.whereareyou.ui.home.schedule.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun DateContent(
    date: Int,
    scheduleNumber: Int,
    isSelected: Boolean = false
) {
    var componentWidth by remember { mutableStateOf(0) }
    val density = LocalDensity.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .onGloballyPositioned {
                componentWidth = (it.size.width / density.density).toInt()
            },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .width((componentWidth * 0.45).dp)
                .height((componentWidth * 0.45).dp)
                .background(
                    color = when (isSelected) {
                        true -> Color(0xFF8C9EFF)
                        false -> Color(0x00000000)
                    },
                    shape = RoundedCornerShape(25)
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = date.toString(),
                fontWeight = when (isSelected) {
                    true -> FontWeight.Black
                    false -> FontWeight.Normal
                },
                color = when (isSelected) {
                    true -> Color.White
                    false -> Color.Black
                }
            )
        }
        Spacer(modifier = Modifier.size(2.dp))
        Column(
            modifier = Modifier
                .width((componentWidth * 0.3).dp)
                .height((componentWidth * 0.2).dp)
        ) {
            for (i in 0..1) {
                Row(
                    modifier = Modifier
                        .weight(1f)
                ) {
                    for (j in 0..2) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight(),
                            contentAlignment = Alignment.Center
                        ) {
                            if (i * 3 + j < scheduleNumber) {
                                Box(
                                    modifier = Modifier
                                        .size(4.dp)
                                        .background(
                                            shape = CircleShape,
                                            color = Color(0xFF8C9EFF)
                                        )
                                )
                            }
                        }
                    }
                }
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun DateContentPreview() {
    Box(
        modifier = Modifier
            .width(50.dp)
            .height(40.dp)
            .background(
                color = Color(0xFFFFFFFF)
            )
    ) {
        DateContent(8, 3, true)
    }
}