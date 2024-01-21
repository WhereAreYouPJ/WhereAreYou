package com.whereareyounow.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.whereareyounow.ui.theme.WhereAreYouTheme

@Composable
fun BottomOKButton(
    text: String,
    onClick: () -> Unit,
    isLoading: Boolean = false
) {
    val focusManager = LocalFocusManager.current
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(Color(0xFF2D2573))
            .clickable {
                focusManager.clearFocus()
                onClick()
            },
        contentAlignment = Alignment.Center
    ) {
        if (isLoading) {
            InfinityLoader(
                modifier = Modifier
                    .width(40.dp)
                    .height(40.dp),
                brush = SolidColor(Color.White)
            )
        } else {
            Text(
                text = text,
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun BottomOkButtonPreview() {
    WhereAreYouTheme {
        BottomOKButton(
            text = "완료",
            onClick = {  }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun LoadingBottomOkButtonPreview() {
    WhereAreYouTheme {
        BottomOKButton(
            text = "완료",
            onClick = {  },
            isLoading = true
        )
    }
}