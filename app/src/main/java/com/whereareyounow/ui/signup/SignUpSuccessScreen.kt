package com.whereareyounow.ui.signup

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.whereareyounow.data.globalvalue.TOP_BAR_HEIGHT
import com.whereareyounow.ui.component.CustomSurface
import com.whereareyounow.ui.component.RoundedCornerButton
import com.whereareyounow.ui.signup.policy.InstructionContent
import com.whereareyounow.ui.signup.policy.TopProgressContent
import com.whereareyounow.ui.theme.bold18pt
import com.whereareyounow.ui.theme.medium14pt
import com.whereareyounow.ui.theme.medium18pt
import com.whereareyounow.util.CustomPreview

@Composable
fun SignUpSuccessScreen(
    moveToBackScreen: () -> Unit,
) {
    CustomSurface {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            SignUpSuccessScreenTopBar()

            Spacer(Modifier.height(10.dp))

            TopProgressContent(step = 3)

            Spacer(Modifier.height(30.dp))

            InstructionContent(text = "회원가입이\n완료되었습니다")

            Spacer(Modifier.height(30.dp))

            Column(
                modifier = Modifier.padding(start = 15.dp, end = 15.dp)
            ) {
                Box(
                    modifier = Modifier.padding(start = 6.dp, top = 4.dp, end = 6.dp, bottom = 4.dp)
                ) {
                    Text(
                        text = "시작화면에서 로그인해주세요.\n최초 로그인 이후 접속 시, 자동 로그인이 활성화됩니다.",
                        color = Color(0xFF666666),
                        style = medium14pt
                    )
                }

                Spacer(Modifier.weight(1f))

                RoundedCornerButton(
                    onClick = { moveToBackScreen() }
                ) {
                    Text(
                        text = "로그인하기",
                        color = Color(0xFFF2F2F2),
                        style = bold18pt
                    )
                }

                Spacer(Modifier.height(20.dp))
            }
        }
    }
}

@Composable
fun SignUpSuccessScreenTopBar() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(TOP_BAR_HEIGHT.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "회원가입",
            color = Color(0xFF000000),
            style = medium18pt
        )
    }
}

@CustomPreview
@Composable
private fun SignUpSuccessScreenPreview() {
    SignUpSuccessScreen(
        moveToBackScreen = {}
    )
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
//                color = Color(0xFF5F5F5F)
//            )
//            Text(
//                modifier = Modifier.layoutId(TEXT_3),
//                text = "가입완료",
//                fontSize = 16.sp,
//                color = Color(0xFF5F5F5F)
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
//        val space = 10
//        val height = topProgressBar.height + space.dp.toPx() + text1.height
//
//        layout(constraint.maxWidth, height.toInt()) {
//            topProgressBar.place((constraint.maxWidth - topProgressBar.width) / 2, 0)
//            text1.place(0, topProgressBar.height + space.dp.toPx().toInt())
//            text2.place((constraint.maxWidth - text2.width) / 2, topProgressBar.height + space.dp.toPx().toInt())
//            text3.place(constraint.maxWidth - text3.width, topProgressBar.height + space.dp.toPx().toInt())
//        }
//    }
//}
//
//private const val CHECK_CIRCLE_1 = "CHECK_CIRCLE_1"
//private const val CHECK_CIRCLE_2 = "CHECK_CIRCLE_2"
//private const val CHECK_CIRCLE_3 = "CHECK_CIRCLE_3"
//
//@Composable
//private fun TopProgressBar(modifier: Modifier) {
//    val progressBarHeight = 20
//    Layout(
//        content = {
//            CheckCircle(
//                Modifier
//                    .layoutId(CHECK_CIRCLE_1)
//                    .size(progressBarHeight.dp)
//            )
//            CheckCircle(
//                Modifier
//                    .layoutId(CHECK_CIRCLE_2)
//                    .size(progressBarHeight.dp)
//            )
//            CheckCircle(
//                Modifier
//                    .layoutId(CHECK_CIRCLE_3)
//                    .size(progressBarHeight.dp)
//            )
//        },
//        modifier = modifier
//            .drawBehind {
//                drawRect(
//                    color = Color(0xFF726CA5),
//                    topLeft = Offset((progressBarHeight / 2).dp.toPx(), ((progressBarHeight - 2) / 2).dp.toPx()),
//                    size = Size(size.width - progressBarHeight.dp.toPx(), 2.dp.toPx())
//                )
//            }
//    ) { measurables, constraint ->
//        val checkCircle1 = measurables.first { it.layoutId == CHECK_CIRCLE_1 }.measure(constraint)
//        val checkCircle2 = measurables.first { it.layoutId == CHECK_CIRCLE_2 }.measure(constraint)
//        val checkCircle3 = measurables.first { it.layoutId == CHECK_CIRCLE_3 }.measure(constraint)
//        val height = listOf(checkCircle1.height, checkCircle2.height, checkCircle3.height).min()
//
//        layout(constraint.maxWidth, height) {
//            checkCircle1.place(0, 0)
//            checkCircle2.place((constraint.maxWidth - checkCircle2.width) / 2, 0)
//            checkCircle3.place(constraint.maxWidth - checkCircle3.width, 0)
//        }
//    }
//}