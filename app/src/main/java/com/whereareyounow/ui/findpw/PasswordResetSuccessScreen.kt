package com.whereareyounow.ui.findpw

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.whereareyounow.R
import com.whereareyounow.ui.component.BottomOKButton


@Composable
fun PasswordResetSuccessScreen(
    moveToSignInScreen: () -> Unit,
) {
    Column(
        modifier = Modifier
            .padding(start = 20.dp, end = 20.dp)
            .fillMaxSize()
    ) {
        Spacer(Modifier.height(150.dp))
        Column {
            Image(
                modifier = Modifier
                    .size(80.dp),
                painter = painterResource(id = R.drawable.check_box2),
                contentDescription = null,
                colorFilter = ColorFilter.tint(Color(0xFF2D2573))
            )
            Spacer(Modifier.height(10.dp))
            Text(
                text = "비밀번호 변경 완료",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF373737)
            )
            Spacer(Modifier.height(10.dp))
            Text(
                text = "시작화면에서 로그인해주세요. 최초 로그인 이후에는\n접속 시에 자동 로그인이 활성화 됩니다.",
                fontSize = 16.sp,
                style = TextStyle(
                    lineHeight = 18.sp
                ),
                color = Color(0xFF999999)
            )
        }
        Spacer(Modifier.weight(1f))
        BottomOKButton(
            text = "확인",
            onClick = { moveToSignInScreen() }
        )
        Spacer(Modifier.height(20.dp))
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun PasswordResetSuccessScreenPreview() {
    PasswordResetSuccessScreen(
        moveToSignInScreen = {}
    )
}