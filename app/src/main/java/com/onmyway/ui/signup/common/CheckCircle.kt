package com.onmyway.ui.signup.common

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.res.painterResource
import com.onmyway.R

@Composable
fun CheckCircle(modifier: Modifier) {
    val painter = painterResource(id = R.drawable.check_yellow_2)
    Layout(
        modifier = modifier
            .drawBehind {
                with(painter) {
                    draw(size = Size(size.width, size.height))
                }
            }
    ) { _, constraint ->
        layout(constraint.maxWidth, constraint.maxHeight) {}
    }
}