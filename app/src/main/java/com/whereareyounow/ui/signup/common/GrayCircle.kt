package com.whereareyounow.ui.signup.common

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.unit.dp

@Composable
fun GrayCircle(modifier: Modifier) {
    Layout(
        modifier = modifier
            .drawBehind {
                drawCircle(
                    color = Color(0xFFBBBAB8),
                    radius = this.size.width / 2 - 4.dp.toPx(),
                    center = Offset(
                        x = this.size.width / 2,
                        y = this.size.height / 2
                    )
                )
            }
    ) { _, constraint ->
        layout(constraint.maxWidth, constraint.maxHeight) {}
    }
}