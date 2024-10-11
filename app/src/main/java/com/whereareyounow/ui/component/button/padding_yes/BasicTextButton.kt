package com.whereareyounow.ui.component.button.padding_yes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.whereareyounow.ui.theme.getColor
import com.whereareyounow.ui.theme.medium16pt
import com.whereareyounow.util.clickableNoEffect

@Composable
fun BasicTextButton(
    text: String,
    onClicked: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 15.dp , end = 15.dp)
            .height(50.dp)
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
            style = medium16pt,
            color = Color(0xFFF2F2F2)
        )
    }
}

@Preview
@Composable
private fun PreviewBasicTextButton() {
    BasicTextButton(
        text = "테스트",
        onClicked = {}
    )
}