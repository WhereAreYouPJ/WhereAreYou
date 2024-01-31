package com.whereareyounow.ui.signup

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.whereareyounow.R
import com.whereareyounow.data.GlobalValue
import com.whereareyounow.ui.component.BottomOKButton
import com.whereareyounow.ui.component.CustomTopBar

@Composable
fun PolicyAgreeScreen(
    moveToBackScreen: () -> Unit,
    moveToSignUpScreen: () -> Unit,
    moveToTermsOfServiceDetailsScreen: () -> Unit,
    moveToPrivacyPolicyDetailsScreen: () -> Unit,
) {
    var isTermsOfServiceAgreed by rememberSaveable { mutableStateOf(false) }
    var isPrivacyPolicyAgreed by rememberSaveable { mutableStateOf(false) }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .padding(start = 20.dp, end = 20.dp)
            .fillMaxSize()
    ) {
        // 상단바
        TermsOfServiceScreenTopBar(moveToBackScreen)

        Spacer(Modifier.height(20.dp))

        Text(
            text = "약관에 동의하시면\n회원가입이 완료됩니다",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            style = TextStyle(
                lineHeight = 30.sp
            ),
            color = Color(0xFF373737)
        )

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Bottom
        ) {
            // 모두 동의하기
            Row(
                modifier = Modifier
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) {
                        if (!isTermsOfServiceAgreed || !isPrivacyPolicyAgreed) {
                            isTermsOfServiceAgreed = true
                            isPrivacyPolicyAgreed = true
                        } else {
                            isTermsOfServiceAgreed = false
                            isPrivacyPolicyAgreed = false
                        }
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(R.drawable.check_box2),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(
                        when (isTermsOfServiceAgreed && isPrivacyPolicyAgreed) {
                            true -> Color(0xFF2D2573)
                            false -> Color(0xFFC1C3CA)
                        }
                    )
                )
                Spacer(Modifier.width(10.dp))
                Text(
                    text = "모두 동의하기",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    color = when (isTermsOfServiceAgreed && isPrivacyPolicyAgreed) {
                        true -> Color(0xFF2D2573)
                        false -> Color(0xFFC1C3CA)
                    }
                )
            }

            Spacer(Modifier.height(40.dp))

            // 서비스 이용약관
            Row(
                modifier = Modifier
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) {
                        isTermsOfServiceAgreed = !isTermsOfServiceAgreed
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(R.drawable.check),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(
                        when (isTermsOfServiceAgreed) {
                            true -> Color(0xFF2D2573)
                            false -> Color(0xFFC1C3CA)
                        }
                    )
                )
                Spacer(Modifier.width(10.dp))
                Text(
                    text = "서비스 이용약관(필수)",
                    fontSize = 18.sp,
                    color = Color(0xFF4C4545)
                )
                Spacer(Modifier.weight(1f))
                Text(
                    modifier = Modifier.clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) { moveToTermsOfServiceDetailsScreen() },
                    text = "보기",
                    fontSize = 18.sp,
                    color = Color(0xFF6E6E6E)
                )
            }

            Spacer(Modifier.height(20.dp))

            // 개인정보 처리방침
            Row(
                modifier = Modifier
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) {
                        isPrivacyPolicyAgreed = !isPrivacyPolicyAgreed
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(R.drawable.check),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(
                        when (isPrivacyPolicyAgreed) {
                            true -> Color(0xFF2D2573)
                            false -> Color(0xFFC1C3CA)
                        }
                    )
                )
                Spacer(Modifier.width(10.dp))
                Text(
                    text = "개인정보 처리방침(필수)",
                    fontSize = 18.sp,
                    color = Color(0xFF4C4545)
                )
                Spacer(Modifier.weight(1f))
                Text(
                    modifier = Modifier.clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) { moveToPrivacyPolicyDetailsScreen() },
                    text = "보기",
                    fontSize = 18.sp,
                    color = Color(0xFF6E6E6E)
                )
            }

            Spacer(Modifier.height(100.dp))

            BottomOKButton(
                text = "확인",
                onClick = {
                    if (isTermsOfServiceAgreed && isPrivacyPolicyAgreed) {
                        moveToSignUpScreen()
                    } else {
                        Toast
                            .makeText(context, "약관에 모두 동의해주세요.", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            )

            Spacer(Modifier.height(20.dp))
        }
    }
}

@Composable
fun TermsOfServiceScreenTopBar(
    moveToBackScreen: () -> Unit,
) {
    CustomTopBar(
        title = "",
        onBackButtonClicked = moveToBackScreen
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun PolicyAgreeScreenPreview() {
    PolicyAgreeScreen(
        moveToBackScreen = {  },
        moveToSignUpScreen = {  },
        moveToTermsOfServiceDetailsScreen = {  },
        moveToPrivacyPolicyDetailsScreen = {  }
    )
}