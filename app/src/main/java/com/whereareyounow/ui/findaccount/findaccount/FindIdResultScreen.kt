package com.whereareyounow.ui.findaccount.findaccount

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.whereareyounow.data.globalvalue.TOP_BAR_HEIGHT
import com.whereareyounow.ui.component.CustomSurface
import com.whereareyounow.ui.component.HorizontalDivider
import com.whereareyounow.ui.component.InfinityLoader
import com.whereareyounow.ui.component.RoundedCornerButton
import com.whereareyounow.ui.signup.policy.InstructionContent
import com.whereareyounow.ui.theme.OnMyWayTheme
import com.whereareyounow.ui.theme.bold18pt
import com.whereareyounow.ui.theme.getColor
import com.whereareyounow.ui.theme.medium14pt
import com.whereareyounow.ui.theme.medium18pt
import com.whereareyounow.util.CustomPreview
import com.whereareyounow.util.clickableNoEffect

@Composable
fun FindIdResultScreen(
    searchedUserId: String,
    moveToResetPasswordScreen: (String) -> Unit,
    moveToSignInScreen: () -> Unit
) {
    CustomSurface {
        Column {
            UserIdCheckingScreenTopBar()

            HorizontalDivider()

            Spacer(Modifier.height(40.dp))

            InstructionContent(text = "회원님의 아이디를\n확인해주세요")

            Spacer(Modifier.height(50.dp))

            Column(
                modifier = Modifier.padding(start = 15.dp, end = 15.dp)
            ) {
                if (searchedUserId == "") {
                    Text(
                        text = "해당 이메일과 연동된 계정 정보가 없습니다.",
                        fontSize = 20.sp
                    )
                } else {
                    Text(
                        modifier = Modifier.padding(start = 6.dp),
                        text = buildAnnotatedString {
                            withStyle(
                                style = medium14pt.toSpanStyle().copy(
                                    color = Color(0xFF222222)
                                ),
                            ) {
                                append("회원님의 아이디는\n")
                            }
                            withStyle(
                                style = bold18pt.toSpanStyle().copy(
                                    color = getColor().brandText
                                )
                            ) {
                                append(searchedUserId)
                            }
                            withStyle(
                                style = medium14pt.toSpanStyle().copy(
                                    color = Color(0xFF222222)
                                ),
                            ) {
                                append(" 입니다.")
                            }
                        }
                    )
                }

                Spacer(Modifier.height(10.dp))

                HorizontalDivider()

                Spacer(Modifier.height(10.dp))

                Text(
                    modifier = Modifier.padding(start = 6.dp),
                    text = "로그인 또는 비밀번호 찾기 버튼을 눌러주세요.",
                    color = Color(0xFF666666),
                    style = medium14pt
                )

                Spacer(Modifier.weight(1f))

                RoundedCornerButton(
                    onClick = moveToSignInScreen
                ) {
                    Text(
                        text = "로그인하기",
                        color = Color(0xFFF2F2F2),
                        style = bold18pt
                    )
                }

                Spacer(Modifier.height(10.dp))

                RoundedCornerOutlinedButton(
                    onClick = { moveToResetPasswordScreen(searchedUserId) }
                ) {
                    Text(
                        text = "비밀번호 찾기",
                        color = getColor().brandText,
                        style = bold18pt
                    )
                }

                Spacer(Modifier.height(20.dp))
            }
        }
    }
}

@Composable
private fun UserIdCheckingScreenTopBar() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(TOP_BAR_HEIGHT.dp)
            .padding(start = 15.dp, end = 15.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "아이디 찾기",
            color = Color(0xFF000000),
            style = medium18pt
        )
    }
}

@Composable
private fun RoundedCornerOutlinedButton(
    contentDescription: String = "",
    onClick: () -> Unit,
    isLoading: Boolean = false,
    textContent: @Composable () -> Unit
) {
    val focusManager = LocalFocusManager.current
    Box(
        modifier = Modifier
            .semantics { this.contentDescription = contentDescription }
            .fillMaxWidth()
            .height(50.dp)
            .clip(RoundedCornerShape(6.dp))
            .border(
                border = BorderStroke(
                    width = (1.5).dp,
                    color = getColor().brandColor
                ),
                shape = RoundedCornerShape(6.dp)
            )
            .clickableNoEffect {
                focusManager.clearFocus()
                onClick()
            },
        contentAlignment = Alignment.Center
    ) {
        if (isLoading) {
            InfinityLoader(
                modifier = Modifier
                    .width(40.dp)
                    .height(40.dp),
                brush = SolidColor(Color(0xFFFFFFFF))
            )
        } else {
            textContent()
        }
    }
}

@CustomPreview
@Composable
private fun UserIdCheckingScreenPreview() {
    OnMyWayTheme {
        FindIdResultScreen(
            searchedUserId = "test",
            moveToResetPasswordScreen = {},
            moveToSignInScreen = {}
        )
    }
}