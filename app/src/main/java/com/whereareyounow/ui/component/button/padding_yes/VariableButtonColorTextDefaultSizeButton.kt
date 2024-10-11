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
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.whereareyounow.data.globalvalue.SYSTEM_NAVIGATION_BAR_HEIGHT
import com.whereareyounow.ui.theme.getColor
import com.whereareyounow.ui.theme.notoSanskr
import com.whereareyounow.util.clickableNoEffect

@Composable
fun VariableButtonColorTextDefaultSizeButton(
    text: String,
    isVerified : MutableState<Boolean>,
    onClicked : () -> Unit,
) {
    val buttonColor = if (isVerified.value) getColor().brandColor else Color(0xFFABABAB)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 15.dp, end = 15.dp , bottom = SYSTEM_NAVIGATION_BAR_HEIGHT.dp)
            .height(50.dp)
            .clip(RoundedCornerShape(6.dp))
            .background(buttonColor)
            .clickableNoEffect {
                onClicked()
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = text,
            fontSize = 18.sp,
            fontFamily = notoSanskr,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
}