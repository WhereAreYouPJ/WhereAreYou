package com.whereareyou.ui.home.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp


@Composable
fun AddScheduleScreen(
    topBarHeight: Int = LocalConfiguration.current.screenHeightDp / 12
) {
    Column(
        modifier = Modifier
            .padding(start = 20.dp, end = 20.dp)

    ) {
        // 상단바
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(topBarHeight.dp)
                .background(
                    color = Color(0xFFCE93D8)
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {

        }
        BasicTextField(
            value = "",
            onValueChange = {

            }
        )
//        DatePickerDialog(
//            onDismissRequest = { /*TODO*/ },
//            confirmButton = { /*TODO*/ }
//        ) {
//
//        }
    }
}