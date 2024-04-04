package com.whereareyounow.ui.component

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.whereareyounow.R
import com.whereareyounow.ui.theme.WhereAreYouTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CustomTextFieldWithTimer(
    hint: String,
    inputText: String,
    onValueChange: (String) -> Unit,
    guideLine: String,
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
        textStyle = TextStyle(
            color = Color(0xFF000000),
            fontSize = 16.sp
        ),
        singleLine = true,
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .background(
                        color = Color(0xFFF5F5F6),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(start = 10.dp, end = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.CenterStart
                ) {
                    it()
                    if (inputText == "") {
                        Text(
                            text = hint,
                            fontSize = 16.sp,
                            color = Color(0xFFC1C1C1)
                        )
                    }
                }
                Spacer(Modifier.width(20.dp))
                when (textFieldState) {
                    CustomTextFieldState.Satisfied -> {
                        Image(
                            modifier = Modifier.size(20.dp),
                            painter = painterResource(id = R.drawable.check_circle_fill0_wght300_grad0_opsz24),
                            contentDescription = null,
                            colorFilter = ColorFilter.tint(color = Color(0xFF78C480))
                        )
                    }
                    CustomTextFieldState.Unsatisfied -> {
                        Text(
                            text = "${leftTime / 60}:${String.format("%02d", leftTime % 60)}",
                            color = Color(0xFFE59090)
                        )
                        Spacer(Modifier.width(20.dp))
                        Image(
                            modifier = Modifier.size(20.dp),
                            painter = painterResource(id = R.drawable.cancel_fill0_wght300_grad0_opsz24),
                            contentDescription = null,
                            colorFilter = ColorFilter.tint(color = Color(0xFFE59090))
                        )
                    }
                    else -> {
                        Text(
                            text = "${leftTime / 60}:${String.format("%02d", leftTime % 60)}",
                            color = Color(0xFFE59090)
                        )
                    }
                }
            }
            if (textFieldState == CustomTextFieldState.Unsatisfied) {
                Spacer(Modifier.height(10.dp))
                Text(
                    text = guideLine,
                    fontSize = 14.sp,
                    color = Color(0xFFFF0000)
                )
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
            guideLine = "GuideLine",
            textFieldState = CustomTextFieldState.Unsatisfied,
            leftTime = 120
        )
    }
}