package com.onmyway.ui.start

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.onmyway.R
import com.onmyway.ui.theme.OnMyWayTheme

@Composable
fun StartScreen(
    moveToSignUpScreen: () -> Unit,
    moveToSignInScreen: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 20.dp, end = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            modifier = Modifier
                .size(100.dp),
            painter = painterResource(R.drawable.logo),
            contentDescription = null
        )
        Spacer(Modifier.height(10.dp))
        Text(
            text = "지금어디",
            fontSize = 30.sp,
            fontWeight = FontWeight.Medium
        )
        Spacer(Modifier.height(100.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(
                    color = Color(0xFF2D3573),
                    shape = RoundedCornerShape(10.dp)
                )
                .clickable { moveToSignUpScreen() },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "시작하기",
                color = Color(0xFFFFFFFF),
                fontSize = 26.sp,
                fontWeight = FontWeight.Medium
            )
        }
        Spacer(Modifier.height(10.dp))
        Row {
            Text(
                text = "이미 계정이 있나요?",
                fontSize = 20.sp
            )
            ClickableText(
                text = AnnotatedString(" 로그인"),
                onClick = {
                    moveToSignInScreen()
                },
                style = TextStyle(
                    color = Color.Blue,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun StartScreenPreview() {
    OnMyWayTheme {
        StartScreen(
            moveToSignUpScreen = {  },
            moveToSignInScreen = {  }
        )
    }
}