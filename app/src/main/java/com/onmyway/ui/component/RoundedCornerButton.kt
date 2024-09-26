package com.onmyway.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.onmyway.ui.theme.getColor
import com.onmyway.ui.theme.OnMyWayTheme
import com.onmyway.ui.theme.bold18pt
import com.onmyway.util.clickableNoEffect

@Composable
fun RoundedCornerButton(
    contentDescription: String = "",
    onClick: () -> Unit,
    isLoading: Boolean = false,
    textContent: @Composable () -> Unit
) {
    val focusManager = LocalFocusManager.current
    Box(
        modifier = Modifier
            .semantics { this.contentDescription = contentDescription }
            .fillMaxWidth()
            .height(50.dp)
            .clip(RoundedCornerShape(6.dp))
            .background(getColor().brandColor)
            .clickableNoEffect {
                focusManager.clearFocus()
                onClick()
            },
        contentAlignment = Alignment.Center
    ) {
        if (isLoading) {
            InfinityLoader(
                modifier = Modifier
                    .width(32.dp)
                    .height(32.dp),
                brush = SolidColor(Color(0xFFFFFFFF))
            )
        } else {
            textContent()
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun BottomOkButtonPreview() {
    OnMyWayTheme {
        RoundedCornerButton(
            onClick = {}
        ) {
            Text(
                text = "로그인하기",
                color = Color(0xFFF2F2F2),
                style = bold18pt
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LoadingBottomOkButtonPreview() {
    OnMyWayTheme {
        RoundedCornerButton(
            onClick = {},
            isLoading = true
        ) {
            Text(
                text = "로그인하기",
                color = Color(0xFFF2F2F2),
                style = bold18pt
            )
        }
    }
}