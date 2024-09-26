package com.onmyway.ui.signup.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.onmyway.ui.theme.getColor
import com.onmyway.ui.theme.medium14pt
import com.onmyway.util.clickableNoEffect

@Composable
fun CheckingButton(
    text: String,
    isActive: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .width(100.dp)
            .height(42.dp)
            .background(
                color = if (isActive) Color(0xFF7B50FF) else getColor().littleDark,
                shape = RoundedCornerShape(6.dp)
            )
            .clickableNoEffect { if (isActive) onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = Color(0xFFFFFFFF),
            style = medium14pt
        )
    }
}