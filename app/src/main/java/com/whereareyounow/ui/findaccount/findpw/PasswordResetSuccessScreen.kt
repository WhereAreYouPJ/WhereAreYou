package com.whereareyounow.ui.findaccount.findpw

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.whereareyounow.data.globalvalue.TOP_BAR_HEIGHT
import com.whereareyounow.ui.component.CustomSurface
import com.whereareyounow.ui.component.HorizontalDivider
import com.whereareyounow.ui.component.RoundedCornerButton
import com.whereareyounow.ui.signup.InstructionContent
import com.whereareyounow.ui.theme.medium18pt
import com.whereareyounow.util.CustomPreview


@Composable
fun PasswordResetSuccessScreen(
    moveToSignInScreen: () -> Unit,
) {
    CustomSurface {
        Column {
            PasswordResetSuccessScreenTopBar()

            HorizontalDivider()

            Spacer(Modifier.height(40.dp))

            InstructionContent(text = "비밀번호 재설정이\n완료되었습니다")

            Column(
                modifier = Modifier.padding(start = 15.dp, end = 15.dp)
            ) {
                Spacer(Modifier.height(30.dp))

                Text(
                    modifier = Modifier.padding(start = 6.dp),
                    text = "시작화면에서 로그인해주세요.\n최초 로그인 이후 접속 시, 자동 로그인이 활성화됩니다.",
                    fontSize = 16.sp,
                    style = TextStyle(
                        lineHeight = 18.sp
                    ),
                    color = Color(0xFF999999)
                )

                Spacer(Modifier.weight(1f))

                RoundedCornerButton(
                    onClick = { moveToSignInScreen() }
                ) {
                    Text(
                        text = "로그인하기",
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFF2F2F2)
                    )
                }

                Spacer(Modifier.height(20.dp))
            }
        }
    }
}

@Composable
private fun PasswordResetSuccessScreenTopBar() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(TOP_BAR_HEIGHT.dp)
            .padding(start = 15.dp, end = 15.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "비밀번호 변경",
            color = Color(0xFF000000),
            style = medium18pt
        )
    }
}

@CustomPreview
@Composable
private fun PasswordResetSuccessScreenPreview() {
    PasswordResetSuccessScreen(
        moveToSignInScreen = {}
    )
}