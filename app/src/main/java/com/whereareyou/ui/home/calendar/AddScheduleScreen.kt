package com.whereareyou.ui.home.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddScheduleScreen(
    topBarHeight: Int = LocalConfiguration.current.screenHeightDp / 10
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
        Text(text = "일정을 입력하세요")
        DatePickerDialog(
            onDismissRequest = { /*TODO*/ },
            confirmButton = { /*TODO*/ }
        ) {

        }
    }
}