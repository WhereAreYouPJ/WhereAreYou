package com.whereareyounow.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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

@Composable
fun CustomTextField(
    hint: String,
    inputText: String,
    onValueChange: (String) -> Unit,
    textFieldState: CustomTextFieldState,
    isPassword: Boolean = false
) {
    BasicTextField(
        value = inputText,
        onValueChange = onValueChange,
        textStyle = TextStyle(
            color = Color(0xFF000000),
            fontSize = 16.sp
        ),
        singleLine = true,
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
//                .border(
//                    border = BorderStroke(
//                        width = 1.dp,
//                        color = when (textFieldState) {
//                            CustomTextFieldState.IDLE -> Color(0xFF808080)
//                            CustomTextFieldState.SATISFIED -> Color(0xFF78C480)
//                            CustomTextFieldState.UNSATISFIED -> Color(0xFFE59090)
//                        }
//                    ),
//                    shape = RoundedCornerShape(10.dp)
//                )
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
                CustomTextFieldState.SATISFIED -> {
                    Image(
                        modifier = Modifier.size(20.dp),
                        painter = painterResource(id = R.drawable.check_circle_fill0_wght300_grad0_opsz24),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(color = Color(0xFF78C480))
                    )
                }
                CustomTextFieldState.UNSATISFIED -> {
                    Image(
                        modifier = Modifier.size(20.dp),
                        painter = painterResource(id = R.drawable.cancel_fill0_wght300_grad0_opsz24),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(color = Color(0xFFE59090))
                    )
                }
                else -> {}
            }
        }
    }
}

enum class CustomTextFieldState {
    IDLE, SATISFIED, UNSATISFIED
}

@Preview(showBackground = true)
@Composable
private fun IdleCustomTextFieldPreview() {
    WhereAreYouTheme {
        CustomTextField(
            hint = "Hint",
            inputText = "",
            onValueChange = {  },
            textFieldState = CustomTextFieldState.IDLE
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun IdlePasswordCustomTextFieldPreview() {
    WhereAreYouTheme {
        CustomTextField(
            hint = "Hint",
            inputText = "",
            onValueChange = {  },
            textFieldState = CustomTextFieldState.IDLE,
            isPassword = true
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SatisfiedCustomTextFieldPreview() {
    WhereAreYouTheme {
        CustomTextField(
            hint = "Hint",
            inputText = "Satisfied",
            onValueChange = {  },
            textFieldState = CustomTextFieldState.SATISFIED
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SatisfiedPasswordCustomTextFieldPreview() {
    WhereAreYouTheme {
        CustomTextField(
            hint = "Hint",
            inputText = "Satisfied",
            onValueChange = {  },
            textFieldState = CustomTextFieldState.SATISFIED,
            isPassword = true
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun UnsatisfiedCustomTextFieldPreview() {
    WhereAreYouTheme {
        CustomTextField(
            hint = "Hint",
            inputText = "Unsatisfied",
            onValueChange = {  },
            textFieldState = CustomTextFieldState.UNSATISFIED
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun UnsatisfiedPasswordCustomTextFieldPreview() {
    WhereAreYouTheme {
        CustomTextField(
            hint = "Hint",
            inputText = "Unsatisfied",
            onValueChange = {  },
            textFieldState = CustomTextFieldState.UNSATISFIED,
            isPassword = true
        )
    }
}