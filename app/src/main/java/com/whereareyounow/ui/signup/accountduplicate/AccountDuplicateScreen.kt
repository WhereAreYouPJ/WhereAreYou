package com.whereareyounow.ui.signup.accountduplicate

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.whereareyounow.R
import com.whereareyounow.ui.component.CustomSurface
import com.whereareyounow.ui.signup.SignUpScreenTopBar
import com.whereareyounow.ui.signup.policy.InstructionContent
import com.whereareyounow.ui.signup.policy.TopProgressContent
import com.whereareyounow.ui.theme.getColor
import com.whereareyounow.ui.theme.medium14pt
import com.whereareyounow.ui.theme.medium16pt
import com.whereareyounow.util.clickableNoEffect

@Composable
fun AccountDuplicateScreen(
    accountType: String,
    email: String,
    typeList: List<String>,
    userName: String,
    password: String,
    moveToBackScreen: () -> Unit,
    moveToSignUpSuccessScreen: () -> Unit,
    viewModel: AccountDuplicateViewModel = hiltViewModel()
) {
    AccountDuplicateScreen(
        accountType = accountType,
        email = email,
        typeList = typeList,
        userName = userName,
        password = password,
        linkAccount = viewModel::linkAccount,
        moveToBackScreen = moveToBackScreen,
        moveToSignUpSuccessScreen = moveToSignUpSuccessScreen,
        isContent = true
    )
}

@Composable
private fun AccountDuplicateScreen(
    accountType: String,
    email: String,
    typeList: List<String>,
    userName: String,
    password: String,
    linkAccount: (String, String, String, String, String, () -> Unit) -> Unit,
    moveToBackScreen: () -> Unit,
    moveToSignUpSuccessScreen: () -> Unit,
    isContent: Boolean
) {
    CustomSurface {
        SignUpScreenTopBar(moveToBackScreen = moveToBackScreen)

        Spacer(Modifier.height(10.dp))

        TopProgressContent(step = 2)

        Spacer(Modifier.height(40.dp))

        InstructionContent(text = "잠시만요!\n이미 가입된 이메일입니다")

        Spacer(Modifier.height(30.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 15.dp, end = 15.dp)
                .background(
                    color = Color(0xFFF6F6F6),
                    shape = RoundedCornerShape(12.dp)
                )
                .padding(start = 8.dp, top = 14.dp, end = 8.dp, bottom = 12.dp)
        ) {
            typeList.forEachIndexed { idx, item ->
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(Modifier.width(10.dp))

                    Image(
                        modifier = Modifier.size(30.dp),
                        painter = painterResource(when (item) {
                            "normal" -> R.drawable.ic_logo
                            else -> R.drawable.ic_logo_kakao
                        }),
                        contentDescription = null
                    )

                    Spacer(Modifier.width(10.dp))

                    Text(
                        text = email,
                        color = getColor().brandText,
                        style = medium16pt
                    )
                }
            }

            Spacer(Modifier.height(4.dp))

            Text(
                modifier = Modifier.padding(start = 12.dp, top = 4.dp, bottom = 4.dp),
                text = "계정을 연동하여 보다 간편하게 이용이 가능합니다.\n" +
                        "연동을 원하지 않을 경우 로그인 화면으로 돌아갑니다.",
                color = Color(0xFF666666),
                style = medium14pt
            )
        }

        Spacer(Modifier.height(16.dp))

        Row(
            modifier = Modifier.padding(start = 15.dp, end = 15.dp)
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(124.dp)
                    .border(
                        border = BorderStroke(
                            width = (1.5).dp,
                            color = getColor().thin
                        ),
                        shape = RoundedCornerShape(12.dp)
                    )
                    .clickableNoEffect { moveToBackScreen() },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "이전으로\n돌아가기",
                    color = Color(0xFF222222),
                    style = medium16pt
                )
            }

            if (!typeList.contains(accountType)) {
                Spacer(Modifier.width(12.dp))

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(124.dp)
                        .border(
                            border = BorderStroke(
                                width = (1.5).dp,
                                color = getColor().thin
                            ),
                            shape = RoundedCornerShape(12.dp)
                        )
                        .clickableNoEffect { linkAccount(userName, email, password, accountType, "a", moveToSignUpSuccessScreen) },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "기존 계정과\n연동하기",
                        color = Color(0xFF222222),
                        style = medium16pt,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}