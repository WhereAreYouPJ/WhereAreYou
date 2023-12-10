package com.whereareyounow.ui.start

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun StartScreen(
    moveToSignUpScreen: () -> Unit,
    moveToSignInScreen: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        val context = LocalContext.current
        Spacer(
            modifier = Modifier
                .height(450.dp)
        )
        Button(
            onClick = { moveToSignUpScreen() },
            shape = RoundedCornerShape(3.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
        ) {
            Text(text = "시작하기", style = TextStyle(fontSize = 20.sp), fontWeight = FontWeight.Bold)
        }
        Spacer(modifier = Modifier.height(5.dp))

        Row {
            Spacer(modifier = Modifier.width(100.dp))
            Text(
                text = "이미 계정이 있나요?",
                style = TextStyle(fontSize = 16.sp)
            )
            ClickableText(
                text = AnnotatedString(" 로그인"),
                onClick = { offset ->
                    // 클릭할 때 수행할 동작 정의
                    // 예: 가입 화면으로 이동
                    moveToSignInScreen()
                },
                style = TextStyle(
                    color = Color.Blue,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            )
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun StartScreenPreview() {
//    StartScreen(
//        moveToSignUpScreen = {},
//        moveToSignInScreen = {}
//    )
//}