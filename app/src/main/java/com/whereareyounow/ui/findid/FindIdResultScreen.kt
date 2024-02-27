package com.whereareyounow.ui.findid

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.whereareyounow.ui.component.BottomOKButton
import com.whereareyounow.ui.component.CustomTopBar
import com.whereareyounow.ui.theme.WhereAreYouTheme

@Composable
fun FindIdResultScreen(
    searchedUserId: String,
    moveToSignInScreen: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 20.dp, end = 20.dp)
    ) {
        UserIdCheckingScreenTopBar(moveToSignInScreen)

        Spacer(Modifier.height(20.dp))

        if (searchedUserId == "") {
            Text(
                text = "해당 이메일과 연동된 계정 정보가 없습니다.",
                fontSize = 20.sp
            )
        } else {
            Text(
                text = buildAnnotatedString {
                    withStyle(
                        SpanStyle(
                            fontSize = 20.sp
                        )
                    ) {
                        append("해당 이메일과 연동된 아이디는 ")
                    }
                    withStyle(
                        SpanStyle(
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    ) {
                        append(searchedUserId)
                    }
                    withStyle(
                        SpanStyle(
                            fontSize = 20.sp
                        )
                    ) {
                        append(" 입니다.")
                    }
                }
            )
        }

        Spacer(Modifier.weight(1f))

        BottomOKButton(
            text = "로그인하러 가기",
            onClick = moveToSignInScreen
        )

        Spacer(Modifier.height(20.dp))
    }
}

@Composable
private fun UserIdCheckingScreenTopBar(
    moveToSignInScreen: () -> Unit
) {
    CustomTopBar(
        title = "아이디 찾기",
        onBackButtonClicked = moveToSignInScreen
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun UserIdCheckingScreenPreview() {
    WhereAreYouTheme {
        FindIdResultScreen(
            searchedUserId = "test",
            moveToSignInScreen = {}
        )
    }
}