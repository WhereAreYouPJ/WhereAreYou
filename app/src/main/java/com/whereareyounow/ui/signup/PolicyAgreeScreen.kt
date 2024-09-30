package com.whereareyounow.ui.signup

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.whereareyounow.R
import com.whereareyounow.ui.component.CustomSurface
import com.whereareyounow.ui.component.CustomTopBar
import com.whereareyounow.ui.component.RoundedCornerButton
import com.whereareyounow.ui.theme.bold18pt
import com.whereareyounow.ui.theme.bold22pt
import com.whereareyounow.ui.theme.getColor
import com.whereareyounow.ui.theme.medium16pt
import com.whereareyounow.util.CustomPreview

@Composable
fun PolicyAgreeScreen(
    moveToBackScreen: () -> Unit,
    moveToSignUpScreen: () -> Unit,
    moveToTermsOfServiceDetailsScreen: () -> Unit,
    moveToPrivacyPolicyDetailsScreen: () -> Unit,
    moveToLocationPolicyDetailScreen: () -> Unit
) {
    val isAllPolicyAgreed = remember { mutableStateOf(false) }
    CustomSurface {
        Column {
            TermsOfServiceScreenTopBar(onBackButtonClicked = moveToBackScreen)

            TopProgressContent(step = 1)

            Spacer(Modifier.height(40.dp))

            InstructionContent(text = "회원가입에 필요한\n약관에 동의해주세요")

            Spacer(Modifier.weight(1f))

            AgreementSelectionContent(
                isTermsOfServiceAgreed = isAllPolicyAgreed,
                moveToTermsOfServiceDetailsScreen = moveToTermsOfServiceDetailsScreen,
                moveToPrivacyPolicyDetailsScreen = moveToPrivacyPolicyDetailsScreen,
                moveToLocationPolicyDetailScreen = moveToLocationPolicyDetailScreen
            )

            AgreeAndSignUpButton(
                isAllPolicyAgreed = isAllPolicyAgreed,
                moveToSignUpScreen = moveToSignUpScreen
            )

            Spacer(Modifier.height(20.dp))
        }
    }
}

@Composable
private fun TermsOfServiceScreenTopBar(
    onBackButtonClicked: () -> Unit,
) {
    CustomTopBar(
        title = "회원가입",
        onBackButtonClicked = onBackButtonClicked
    )
}

@Composable
fun TopProgressContent(
    step: Int
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(4.dp)
            .background(Color(0xFFEBEBEB))
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(step.toFloat() / 3f)
                .fillMaxHeight()
                .background(
                    color = Color(0xFF9286FF),
                    shape = RoundedCornerShape(
                        topStartPercent = 0,
                        topEndPercent = 50,
                        bottomEndPercent = 50,
                        bottomStartPercent = 0
                    )
                )
        )
    }
}

@Composable
fun InstructionContent(
    text: String
) {
    Box(
        modifier = Modifier.padding(start = 6.dp, top = 4.dp, end = 6.dp, bottom = 4.dp)
    ) {
        Text(
            modifier = Modifier.padding(start = 15.dp, end = 15.dp),
            text = text,
            color = Color(0xFF222222),
            style = bold22pt
        )
    }
}

@Composable
private fun AgreementSelectionContent(
    isTermsOfServiceAgreed: MutableState<Boolean>,
    moveToTermsOfServiceDetailsScreen: () -> Unit,
    moveToPrivacyPolicyDetailsScreen: () -> Unit,
    moveToLocationPolicyDetailScreen: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .clip(GenericShape { size, _ ->
                moveTo(0f, -100f)
                lineTo(size.width, -100f)
                lineTo(size.width, 700f)
                lineTo(0f, 700f)
            })
            .shadow(
                elevation = 30.dp,
                shape = RoundedCornerShape(24.dp)
            )
            .background(
                color = Color(0xFFFFFFFF),
                shape = RoundedCornerShape(24.dp)
            )
            .padding(start = 22.dp, end = 22.dp)
    ) {
        Spacer(Modifier.height(26.dp))

        // 모두 동의하기
        AllAgreementSelectionContent(
            isAllPolicyAgreed = isTermsOfServiceAgreed,
        )

        Spacer(Modifier.height(16.dp))

        // 서비스 이용약관
        TermsOfServiceAgreementSelectionContent(
            moveToTermsOfServiceDetailsScreen = moveToTermsOfServiceDetailsScreen
        )

        Spacer(Modifier.height(2.dp))

        // 개인정보 처리방침
        PrivacyPolicyAgreementSelectionContent(
            moveToPrivacyPolicyDetailsScreen = moveToPrivacyPolicyDetailsScreen
        )

        Spacer(Modifier.height(2.dp))

        // 위치기반 서비스 이용약관
        LocationPolicyAgreementSelectionContent(
            moveToLocationPolicyDetailsScreen = moveToLocationPolicyDetailScreen
        )
    }
}

@Composable
private fun AllAgreementSelectionContent(
    isAllPolicyAgreed: MutableState<Boolean>,
) {
    Row(
        modifier = Modifier
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {
                isAllPolicyAgreed.value = !isAllPolicyAgreed.value
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        val contentColor = when (isAllPolicyAgreed.value) {
            true -> getColor().brandColor
            false -> Color(0xFFC1C3CA)
        }

        Image(
            modifier = Modifier.size(28.dp),
            painter = painterResource(R.drawable.check_box2),
            contentDescription = null,
            colorFilter = ColorFilter.tint(contentColor)
        )

        Spacer(Modifier.width(10.dp))

        Text(
            text = "모두 동의하기",
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF222222)
        )
    }
}

@Composable
private fun TermsOfServiceAgreementSelectionContent(
    moveToTermsOfServiceDetailsScreen: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.padding(start = 6.dp, top = 4.dp, end = 6.dp, bottom = 4.dp),
            text = "서비스 이용약관 (필수)",
            color = Color(0xFF222222),
            style = medium16pt
        )

        Spacer(Modifier.weight(1f))

        Text(
            modifier = Modifier
                .padding(start = 6.dp, top = 4.dp, end = 6.dp, bottom = 4.dp)
                .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) { moveToTermsOfServiceDetailsScreen() },
            text = "보기",
            color = Color(0xFF666666),
            style = medium16pt
        )
    }
}

@Composable
private fun PrivacyPolicyAgreementSelectionContent(
    moveToPrivacyPolicyDetailsScreen: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.padding(start = 6.dp, top = 4.dp, end = 6.dp, bottom = 4.dp),
            text = "개인정보 처리방침 (필수)",
            color = Color(0xFF222222),
            style = medium16pt
        )

        Spacer(Modifier.weight(1f))

        Text(
            modifier = Modifier
                .padding(start = 6.dp, top = 4.dp, end = 6.dp, bottom = 4.dp)
                .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) { moveToPrivacyPolicyDetailsScreen() },
            text = "보기",
            color = Color(0xFF666666),
            style = medium16pt
        )
    }
}

@Composable
private fun LocationPolicyAgreementSelectionContent(
    moveToLocationPolicyDetailsScreen: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.padding(start = 6.dp, top = 4.dp, end = 6.dp, bottom = 4.dp),
            text = "위치기반 서비스 이용약관 (필수)",
            color = Color(0xFF222222),
            style = medium16pt
        )

        Spacer(Modifier.weight(1f))

        Text(
            modifier = Modifier
                .padding(start = 6.dp, top = 4.dp, end = 6.dp, bottom = 4.dp)
                .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) { moveToLocationPolicyDetailsScreen() },
            text = "보기",
            color = Color(0xFF666666),
            style = medium16pt
        )
    }
}

@Composable
fun AgreeAndSignUpButton(
    isAllPolicyAgreed: MutableState<Boolean>,
    moveToSignUpScreen: () -> Unit
) {
    val context = LocalContext.current
    Box(
        modifier = Modifier.padding(start = 15.dp, end = 15.dp)
    ) {
        RoundedCornerButton(
            onClick = {
                if (isAllPolicyAgreed.value) {
                    moveToSignUpScreen()
                } else {
                    Toast.makeText(context, "약관에 모두 동의해주세요.", Toast.LENGTH_SHORT).show()
                }
            }
        ) {
            Text(
                text = "동의하고 시작하기",
                color = Color(0xFFF2F2F2),
                style = bold18pt
            )
        }
    }
}

@CustomPreview
@Composable
private fun PolicyAgreeScreenPreview() {
//    PolicyAgreeScreen(
//        moveToBackScreen = {},
//        moveToSignUpScreen = {},
//        moveToTermsOfServiceDetailsScreen = {},
//        moveToPrivacyPolicyDetailsScreen = {}
//    )
}

//private const val TOP_PROGRESS_BAR = "TOP_PROGRESS_BAR"
//private const val TEXT_1 = "TEXT_1"
//private const val TEXT_2 = "TEXT_2"
//private const val TEXT_3 = "TEXT_3"
//
//@Composable
//private fun TopProgressContent() {
//    Layout(
//        content = {
//            TopProgressBar(Modifier.layoutId(TOP_PROGRESS_BAR))
//            Text(
//                modifier = Modifier.layoutId(TEXT_1),
//                text = "약관동의",
//                fontSize = 16.sp,
//                color = Color(0xFF5F5F5F)
//            )
//            Text(
//                modifier = Modifier.layoutId(TEXT_2),
//                text = "정보입력",
//                fontSize = 16.sp,
//                color = Color(0xFF959595)
//            )
//            Text(
//                modifier = Modifier.layoutId(TEXT_3),
//                text = "가입완료",
//                fontSize = 16.sp,
//                color = Color(0xFF959595)
//            )
//        },
//        modifier = Modifier.padding(start = 10.dp, end = 10.dp)
//    ) { measurables, constraint ->
//        val text1 = measurables.first { it.layoutId == TEXT_1 }.measure(constraint)
//        val text2 = measurables.first { it.layoutId == TEXT_2 }.measure(constraint)
//        val text3 = measurables.first { it.layoutId == TEXT_3 }.measure(constraint)
//        val topProgressBar = measurables.first { it.layoutId == TOP_PROGRESS_BAR }.measure(
//            Constraints(
//                minWidth = 0,
//                minHeight = 0,
//                maxWidth = constraint.maxWidth - text1.width + 20.dp.toPx().toInt(),
//                maxHeight = constraint.maxHeight
//            )
//        )
//        val space = 10.dp
//        val height = topProgressBar.height + space.toPx() + text1.height
//
//        layout(constraint.maxWidth, height.toInt()) {
//            topProgressBar.place((constraint.maxWidth - topProgressBar.width) / 2, 0)
//            text1.place(0, topProgressBar.height + space.toPx().toInt())
//            text2.place((constraint.maxWidth - text2.width) / 2, topProgressBar.height + space.toPx().toInt())
//            text3.place(constraint.maxWidth - text3.width, topProgressBar.height + space.toPx().toInt())
//        }
//    }
//}
//
//private const val DOUBLE_CIRCLE = "DOUBLE_CIRCLE"
//private const val GRAY_CIRCLE_1 = "GRAY_CIRCLE_1"
//private const val GRAY_CIRCLE_2 = "GRAY_CIRCLE_2"
//
//@Composable
//private fun TopProgressBar(modifier: Modifier) {
//    val progressBarHeight = 20
//    Layout(
//        content = {
//            DoubleCircle(
//                Modifier
//                    .layoutId(DOUBLE_CIRCLE)
//                    .size(progressBarHeight.dp)
//            )
//            GrayCircle(
//                Modifier
//                    .layoutId(GRAY_CIRCLE_1)
//                    .size(progressBarHeight.dp)
//            )
//            GrayCircle(
//                Modifier
//                    .layoutId(GRAY_CIRCLE_2)
//                    .size(progressBarHeight.dp)
//            )
//        },
//        modifier = modifier
//            .drawBehind {
//                drawRect(
//                    color = Color(0xFFBBBAB8),
//                    topLeft = Offset((progressBarHeight / 2).dp.toPx(), ((progressBarHeight - 2) / 2).dp.toPx()),
//                    size = Size(size.width - progressBarHeight.dp.toPx(), 2.dp.toPx())
//                )
//            }
//    ) { measurables, constraint ->
//        val doubleCircle = measurables.first { it.layoutId == DOUBLE_CIRCLE }.measure(constraint)
//        val grayCircle1 = measurables.first { it.layoutId == GRAY_CIRCLE_1 }.measure(constraint)
//        val grayCircle2 = measurables.first { it.layoutId == GRAY_CIRCLE_2 }.measure(constraint)
//        val height = listOf(doubleCircle.height, grayCircle1.height, grayCircle2.height).min()
//
//        layout(constraint.maxWidth, height) {
//            doubleCircle.place(0, 0)
//            grayCircle1.place((constraint.maxWidth - grayCircle1.width) / 2, 0)
//            grayCircle2.place(constraint.maxWidth - grayCircle2.width, 0)
//        }
//    }
//}