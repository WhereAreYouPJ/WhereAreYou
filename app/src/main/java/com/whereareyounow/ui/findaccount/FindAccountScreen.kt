package com.whereareyounow.ui.findaccount

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.whereareyounow.R
import com.whereareyounow.ui.component.CustomSurface
import com.whereareyounow.ui.component.CustomTopBar
import com.whereareyounow.ui.component.HorizontalDivider
import com.whereareyounow.ui.theme.getColor
import com.whereareyounow.ui.theme.medium12pt
import com.whereareyounow.ui.theme.medium14pt
import com.whereareyounow.util.CustomPreview
import com.whereareyounow.util.clickableNoEffect

@Composable
fun FindAccountScreen(
    moveToBackScreen: () -> Unit,
    moveToFindIdScreen: () -> Unit,
    moveToResetPasswordScreen: () -> Unit,
) {
    FindAccountScreen(
        moveToBackScreen = moveToBackScreen,
        moveToFindIdScreen = moveToFindIdScreen,
        moveToResetPasswordScreen = moveToResetPasswordScreen,
        isContent = true
    )
}

@Composable
private fun FindAccountScreen(
    moveToBackScreen: () -> Unit,
    moveToFindIdScreen: () -> Unit,
    moveToResetPasswordScreen: () -> Unit,
    isContent: Boolean
) {
    CustomSurface {
        Column {
            CustomTopBar(
                title = "계정찾기",
                onBackButtonClicked = moveToBackScreen
            )

            HorizontalDivider()

            Spacer(Modifier.height(34.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 15.dp, end = 15.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(92.dp)
                        .border(
                            border = BorderStroke(
                                width = 1.dp,
                                color = getColor().thinnest
                            ),
                            shape = RoundedCornerShape(10.dp)
                        )
                        .clickableNoEffect { moveToFindIdScreen() }
                        .padding(15.dp)
                ) {
                    Text(
                        modifier = Modifier.align(Alignment.TopStart),
                        text = "아이디 찾기",
                        color = Color(0xFF222222),
                        style = medium14pt
                    )

                    Text(
                        modifier = Modifier.align(Alignment.BottomStart),
                        text = "가입한 지금어디 계정에 등록된 이메일 정보로 계정을\n찾을 수 있어요.",
                        color = Color(0xFF666666),
                        style = medium12pt
                    )

                    Image(
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .size(20.dp),
                        painter = painterResource(R.drawable.ic_next),
                        contentDescription = null
                    )
                }

                Spacer(Modifier.height(10.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(92.dp)
                        .border(
                            border = BorderStroke(
                                width = 1.dp,
                                color = getColor().thinnest
                            ),
                            shape = RoundedCornerShape(10.dp)
                        )
                        .clickableNoEffect { moveToResetPasswordScreen() }
                        .padding(15.dp)
                ) {
                    Text(
                        modifier = Modifier.align(Alignment.TopStart),
                        text = "비밀번호 찾기",
                        color = Color(0xFF222222),
                        style = medium14pt
                    )

                    Text(
                        modifier = Modifier.align(Alignment.BottomStart),
                        text = "지금어디 계정에 등록된 아이디 인증을 통해 비밀번호\n재설정을 할 수 있어요.",
                        color = Color(0xFF666666),
                        style = medium12pt
                    )

                    Image(
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .size(20.dp),
                        painter = painterResource(R.drawable.ic_next),
                        contentDescription = null
                    )
                }
            }
        }
    }
}

@CustomPreview
@Composable
private fun FindAccountScreenPreview() {
    FindAccountScreen(
        moveToBackScreen = {},
        moveToFindIdScreen = {},
        moveToResetPasswordScreen = {},
        isContent = true
    )
}