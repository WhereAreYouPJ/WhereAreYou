package com.whereareyounow.ui.findaccount.findaccount

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.whereareyounow.R
import com.whereareyounow.ui.component.CustomSurface
import com.whereareyounow.ui.component.CustomTopBar
import com.whereareyounow.ui.component.CustomTopBarNoBackButton
import com.whereareyounow.ui.component.HorizontalDivider
import com.whereareyounow.ui.component.RoundedCornerButton
import com.whereareyounow.ui.signup.policy.InstructionContent
import com.whereareyounow.ui.theme.getColor
import com.whereareyounow.ui.theme.medium14pt
import com.whereareyounow.ui.theme.medium18pt
import com.whereareyounow.ui.theme.notoSanskr

@Composable
fun FindAccountResultScreen(
    email: String,
    typeList: List<String>,
    moveToSignInMethodSelectionScreen: () -> Unit,
    moveToFindPasswordScreen: () -> Unit,
) {
    CustomSurface {
        CustomTopBarNoBackButton(title = "계정 찾기")

        HorizontalDivider()

        Spacer(Modifier.height(40.dp))

        InstructionContent(text = "회원님의 가입정보를\n확인해주세요")

        Column(
            modifier = Modifier.padding(start = 15.dp, end = 15.dp)
        ) {
            Spacer(Modifier.height(40.dp))

            Text(
                modifier = Modifier.padding(6.dp, 4.dp, 6.dp, 4.dp),
                text = "회원님의 가입정보와 일치하는 이메일은",
                color = Color(0xFF222222),
                style = medium14pt
            )

            LazyColumn {
                itemsIndexed(typeList) { idx, item ->
                    Spacer(Modifier.height(8.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Spacer(Modifier.width(6.dp))

                        Image(
                            modifier = Modifier.size(40.dp),
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
                            style = medium18pt
                        )

                        if (idx == typeList.size - 1) {
                            Spacer(Modifier.width(8.dp))

                            Text(
                                text = "입니다.",
                                color = Color(0xFF222222),
                                style = medium14pt
                            )
                        }
                    }
                }
            }

            Spacer(Modifier.height(10.dp))

            HorizontalDivider(Color(0xFFE7E7E7))

            Spacer(Modifier.height(10.dp))

            Text(
                modifier = Modifier.padding(2.dp, 4.dp, 2.dp, 4.dp),
                text = "로그인 또는 비밀번호 재설정 버튼을 눌러주세요.",
                color = Color(0xFF666666),
                style = medium14pt
            )
            
            Spacer(Modifier.weight(1f))
            
            RoundedCornerButton(onClick = { moveToSignInMethodSelectionScreen() }) {
                Text(
                    text = "로그인하기",
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFFFFFFF),
                    fontFamily = notoSanskr,
                    fontSize = 18.sp
                )
            }

            if (typeList.contains("normal")) {
                Spacer(Modifier.height(10.dp))

                RoundedCornerButton(onClick = { moveToFindPasswordScreen() }) {
                    Text(
                        text = "비밀번호 재설정",
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFFFFFFF),
                        fontFamily = notoSanskr,
                        fontSize = 18.sp
                    )
                }
            }

            Spacer(Modifier.height(20.dp))
        }
    }
}