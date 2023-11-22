package com.whereareyou.ui.home.schedule.notification

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun FriendRequestBox(
    name: String,
) {
    Box(
        modifier = Modifier
            .width(200.dp)
            .height(100.dp)
            .background(
                color = Color(0xFFCE93D8),
                shape = RoundedCornerShape(8.dp)
            )
    ) {
        Column() {
            Text(
                text = name
            )
            Row() {
                ClickableBox(color = Color.Blue, text = "수락") {}
                ClickableBox(color = Color.Red, text = "거절") {}
            }
        }
    }
}

@Composable
fun ClickableBox(
    color: Color,
    text: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .width(50.dp)
            .height(30.dp)
            .background(
                color = color,
                shape = RoundedCornerShape(8.dp)
            )
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFAAAAAA)
@Composable
fun FriendRequestBoxPreview() {
    FriendRequestBox(
        name = "이서영"
    )
}