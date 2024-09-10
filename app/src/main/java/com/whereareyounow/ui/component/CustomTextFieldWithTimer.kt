package com.whereareyounow.ui.component

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.whereareyounow.ui.theme.WhereAreYouTheme
import com.whereareyounow.ui.theme.getColor
import com.whereareyounow.ui.theme.medium12pt
import com.whereareyounow.ui.theme.medium14pt
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CustomTextFieldWithTimer(
    hint: String,
    inputText: String,
    onValueChange: (String) -> Unit,
    warningText: String,
    onSuccessText: String = "",
    textFieldState: CustomTextFieldState,
    leftTime: Int,
    isPassword: Boolean = false
) {
    val viewRequester = BringIntoViewRequester()
    val coroutineScope = rememberCoroutineScope()
    BasicTextField(
        modifier = Modifier
            .animateContentSize { _, _ -> }
            .bringIntoViewRequester(viewRequester),
        value = inputText,
        onValueChange = {
            onValueChange(it)
            coroutineScope.launch { viewRequester.bringIntoView() }
        },
        textStyle = medium14pt,
        singleLine = true,
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(42.dp)
                    .border(
                        border = when (textFieldState) {
                            CustomTextFieldState.Unsatisfied -> {
                                BorderStroke(
                                    width = (1.5).dp,
                                    color = getColor().warning
                                )
                            }
                            else -> {
                                BorderStroke(
                                    width = 1.dp,
                                    color = Color(0xFFD4D4D4)
                                )
                            }
                        },
                        shape = RoundedCornerShape(6.dp)
                    )
                    .padding(start = 8.dp, end = 11.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Text(
                        text = if (inputText == "") hint else "",
                        color = Color(0xFF666666),
                        style = medium14pt
                    )
                    it()
                }
                Spacer(Modifier.width(20.dp))
                when (textFieldState) {
                    CustomTextFieldState.Satisfied -> {}
                    else -> {
                        Text(
                            text = "${leftTime / 60}:${String.format("%02d", leftTime % 60)}",
                            color = Color(0xFFDF4343),
                            style = medium14pt
                        )
                    }
                }
            }
            if (textFieldState == CustomTextFieldState.Unsatisfied) {
                Box(
                    modifier = Modifier.padding(start = 2.dp, top = 4.dp, end = 2.dp, bottom = 4.dp)
                ) {
                    Text(
                        text = warningText,
                        color = getColor().warning,
                        style = medium12pt
                    )
                }
            }
            if (textFieldState == CustomTextFieldState.Satisfied) {
                Box(
                    modifier = Modifier.padding(start = 2.dp, top = 4.dp, end = 2.dp, bottom = 4.dp)
                ) {
                    Text(
                        text = onSuccessText,
                        color = getColor().brandColor,
                        style = medium12pt
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CustomTextFieldWithTimerPreview() {
    WhereAreYouTheme {
        CustomTextFieldWithTimer(
            hint = "hint",
            inputText = "123456",
            onValueChange = {},
            warningText = "GuideLine",
            textFieldState = CustomTextFieldState.Unsatisfied,
            leftTime = 120
        )
    }
}