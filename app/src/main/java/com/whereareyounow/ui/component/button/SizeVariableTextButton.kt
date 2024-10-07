package com.whereareyounow.ui.component.button

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.whereareyounow.ui.theme.getColor
import com.whereareyounow.ui.theme.notoSanskr
import com.whereareyounow.util.clickableNoEffect

@Composable
fun SizeVariableTextButton(
    text: String,
    onClicked: () -> Unit,
    buttonWidth: Int,
    buttonHeight: Int
) {
    Row(
        modifier = Modifier
            .width(buttonWidth.dp)
            .height(buttonHeight.dp)
            .clip(RoundedCornerShape(6.dp))
            .background(getColor().brandColor)
            .clickableNoEffect {
                onClicked()
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = text,
            fontSize = 14.sp,
            fontFamily = notoSanskr,
            fontWeight = FontWeight.Medium,
            color = Color(0xFFFFFFFF)
        )
    }
}

@Preview
@Composable
private fun PreviewSizeVariableTextButton() {
    SizeVariableTextButton(
        text = "테스트",
        onClicked = {},
        buttonWidth = 345,
        buttonHeight = 50
    )
}