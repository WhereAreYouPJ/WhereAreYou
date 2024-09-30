package com.whereareyounow.ui.signin

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.whereareyounow.R
import com.whereareyounow.ui.component.CustomSurface
import com.whereareyounow.ui.theme.getColor
import com.whereareyounow.ui.theme.OnMyWayTheme
import com.whereareyounow.ui.theme.medium14pt
import com.whereareyounow.util.CustomPreview
import com.whereareyounow.util.clickableNoEffect

@Composable
fun SignInMethodSelectionScreen(
    moveToSignInWithAccountScreen: () -> Unit,
    moveToSignUpScreen: () -> Unit,
    moveToFindAccountScreen: () -> Unit
) {
    SignInMethodSelectionScreen(
        moveToSignInWithEmailScreen = moveToSignInWithAccountScreen,
        moveToSignUpScreen = moveToSignUpScreen,
        moveToFindAccountScreen = moveToFindAccountScreen,
        tmp = true
    )
}

@Composable
private fun SignInMethodSelectionScreen(
    moveToSignInWithEmailScreen: () -> Unit,
    moveToSignUpScreen: () -> Unit,
    moveToFindAccountScreen: () -> Unit,
    tmp: Boolean
) {
    CustomSurface {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 40.dp, end = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                modifier = Modifier.width(107.dp),
                painter = painterResource(R.drawable.img_logo),
                contentDescription = null,
                colorFilter = ColorFilter.tint(getColor().brandColor)
            )

            Spacer(Modifier.height(12.dp))

            Text(
                text = "위치기반 일정관리 플랫폼",
                color = Color(0xFF444444),
                style = medium14pt
            )

            Spacer(Modifier.height(40.dp))

            KakaoLoginButton()

            Spacer(Modifier.height(10.dp))

            EmailLoginButton(
                moveToSignInWithEmailScreen = moveToSignInWithEmailScreen
            )

            Spacer(Modifier.height(24.dp))

            OrDivider()

            Spacer(Modifier.height(20.dp))

            OtherButtons(
                moveToSignUpScreen = moveToSignUpScreen,
                moveToFindAccountScreen = moveToFindAccountScreen
            )

            Spacer(Modifier.height(40.dp))
        }
    }
}

@Composable
private fun KakaoLoginButton() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(44.dp)
            .background(
                color = Color(0xFFF9E200),
                shape = RoundedCornerShape(6.dp)
            )
    ) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = "카카오로 3초 만에 시작하기",
            color = Color(0xFF222222),
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
        )
        Image(
            modifier = Modifier.padding(start = 18.dp, top = 13.dp, bottom = 13.dp),
            painter = painterResource(id = R.drawable.ic_kakao),
            contentDescription = null
        )
    }
}

@Composable
private fun EmailLoginButton(
    moveToSignInWithEmailScreen: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(44.dp)
            .border(
                border = BorderStroke(
                    width = 1.dp,
                    color = getColor().brandColor
                ),
                shape = RoundedCornerShape(6.dp)
            )
            .clickableNoEffect { moveToSignInWithEmailScreen() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "이메일 로그인",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = getColor().brandText
        )
    }
}

@Composable
private fun OrDivider() {
    Row(
        modifier = Modifier.height(IntrinsicSize.Max),
        horizontalArrangement = Arrangement.Center
    ) {
        Spacer(Modifier.width(22.dp))

        HorizontalDivider()

        Spacer(Modifier.width(10.dp))

        Text(
            text = "또는",
            color = Color(0xFF666666),
            style = medium14pt
        )

        Spacer(Modifier.width(10.dp))

        HorizontalDivider()

        Spacer(Modifier.width(22.dp))
    }
}

@Composable
private fun RowScope.HorizontalDivider() {
    Box(
        modifier = Modifier
            .weight(1f)
            .fillMaxHeight()
            .drawBehind {
                val borderSize = 1.dp.toPx()
                drawLine(
                    color = Color(0xFFEAEAEA),
                    start = Offset(0f, size.height / 2),
                    end = Offset(size.width, size.height / 2),
                    strokeWidth = borderSize,
                )
            }
    )
}

@Composable
private fun OtherButtons(
    moveToSignUpScreen: () -> Unit,
    moveToFindAccountScreen: () -> Unit
) {
    Row(
        modifier = Modifier.height(IntrinsicSize.Min)
    ) {
        SignUpButton(
            moveToSignUpScreen = moveToSignUpScreen,
            text = "회원가입"
        )

        Spacer(Modifier.width(10.dp))

        VerticalDivider()

        Spacer(Modifier.width(10.dp))

        FindAccountButton(
            moveToFindAccountScreen = moveToFindAccountScreen
        )

        Spacer(Modifier.width(10.dp))

        VerticalDivider()

        Spacer(Modifier.width(10.dp))

        AskButton()
    }
}


@Composable
fun VerticalDivider() {
    Box(
        modifier = Modifier
            .padding(top = 4.dp, bottom = 2.dp)
            .fillMaxHeight()
            .drawBehind {
                val borderSize = 1.dp.toPx()
                drawLine(
                    color = Color(0xFFEAEAEA),
                    start = Offset(0f, 0f),
                    end = Offset(0f, size.height),
                    strokeWidth = borderSize,
                )
            }
    )
}

@Composable
fun SignUpButton(
    moveToSignUpScreen: () -> Unit,
    text: String,
) {
    Text(
        modifier = Modifier
            .padding(top = 4.dp, bottom = 4.dp)
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) { moveToSignUpScreen() },
        text = text,
        color = Color(0xFF666666),
        style = medium14pt
    )
}

@Composable
fun FindAccountButton(
    moveToFindAccountScreen: () -> Unit
) {
    Text(
        modifier = Modifier
            .padding(top = 4.dp, bottom = 4.dp)
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) { moveToFindAccountScreen() },
        text = "계정찾기",
        color = Color(0xFF666666),
        style = medium14pt
    )
}

@Composable
private fun AskButton() {
    Text(
        modifier = Modifier
            .padding(top = 4.dp, bottom = 4.dp)
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {
//                    moveToSignUpScreen()
            },
        text = "문의하기",
        color = Color(0xFF666666),
        style = medium14pt
    )
}

@CustomPreview
@Composable
private fun SignInMethodSelectionScreenPreview() {
    OnMyWayTheme {
        SignInMethodSelectionScreen(
            moveToSignInWithEmailScreen = {},
            moveToSignUpScreen = {},
            moveToFindAccountScreen = {},
            tmp = true
        )
    }
}