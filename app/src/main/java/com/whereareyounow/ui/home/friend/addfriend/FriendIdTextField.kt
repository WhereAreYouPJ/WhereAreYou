package com.whereareyounow.ui.home.friend.addfriend

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.whereareyounow.R

@Composable
fun FriendIdTextField(
    inputId: String,
    updateInputId: (String) -> Unit,
    clearInputId: () -> Unit
) {
    BasicTextField(
        value = inputId,
        onValueChange = { updateInputId(it) },
        textStyle = TextStyle(fontSize = 30.sp),
        singleLine = true,
        decorationBox = {
            Box(
                modifier = Modifier
                    .padding(start = 20.dp, end = 20.dp)
                    .fillMaxWidth()
                    .drawBehind {
                        val borderSize = 1.dp.toPx()
                        drawLine(
                            color = Color(0xFF858585),
                            start = Offset(0f, size.height),
                            end = Offset(size.width, size.height),
                            strokeWidth = borderSize
                        )
                    },
                contentAlignment = Alignment.CenterStart
            ) {
                it()
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    Image(
                        modifier = Modifier.clickable {
                                clearInputId()
                            },
                        painter = painterResource(id = R.drawable.baseline_cancel_24),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(color = Color(0xFF858585))
                    )
                }
            }
        }
    )
}

//@Preview(showBackground = true)
//@Composable
//fun FriendIdTextFieldPreview() {
//    FriendIdTextField(
//        inputId = "abc",
//        updateInputId = {},
//        clearInputId = {}
//    )
//}