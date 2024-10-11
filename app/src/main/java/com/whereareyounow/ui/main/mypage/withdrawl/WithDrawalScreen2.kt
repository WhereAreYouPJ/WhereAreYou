package com.whereareyounow.ui.main.mypage.withdrawl

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.whereareyounow.R
import com.whereareyounow.data.globalvalue.TOP_BAR_HEIGHT
import com.whereareyounow.ui.component.button.padding_yes.VariableButtonColorTextDefaultSizeButton
import com.whereareyounow.ui.component.tobbar.OneTextOneIconTobBar
import com.whereareyounow.ui.theme.medium14pt
import com.whereareyounow.ui.theme.medium20pt
import com.whereareyounow.util.clickableNoEffect

@Composable
fun WithDrawalScreen2(
    moveToBackScreen: () -> Unit,
    moveToWithDrawalScreen3: () -> Unit,
) {
    WithDrawalScreen2(
        isContent = true,
        moveToBackScreen = moveToBackScreen,
        moveToWithDrawalScreen3 = moveToWithDrawalScreen3
    )
}

@Composable
private fun WithDrawalScreen2(
    isContent: Boolean,
    moveToBackScreen: () -> Unit,
    moveToWithDrawalScreen3: () -> Unit
) {
    val scrollState = rememberScrollState()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = TOP_BAR_HEIGHT.dp)
            .imePadding()
    ) {
        OneTextOneIconTobBar(title = "회원탈퇴", firstIcon = R.drawable.ic_backarrow) {
            moveToBackScreen()
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = TOP_BAR_HEIGHT.dp)
                .verticalScroll(scrollState)
        ) {
            val selectedStates =
                remember { mutableStateListOf(false, false, false, false, false) }
            val inputReason = remember {
                mutableStateOf("")
            }
            val isGuitarChecked = remember { mutableStateOf(false) }
            val canMove = remember {
                mutableStateOf(false)
            }
            if (isGuitarChecked.value) {
                if (inputReason.value.length > 10) {
                    canMove.value = true
                } else {
                    if (selectedStates.contains(true)) {
                        canMove.value = false
                    } else {
                        canMove.value = false
                    }
                }
            } else {
                canMove.value = selectedStates.contains(true)
            }
            val circles = listOf(
                R.drawable.ic_circlechecked,
                R.drawable.ic_emptycircle,
            )
            val reasons = listOf(
                "사용 빈도가 낮아서",
                "기능이 부족해서",
                "오류가 자주 발생해서",
                "개인정보 보호 문제",
                "서비스에 불만이 생겨서",
            )
            Gap(30)
            WithDrawlScreenTopTitleAndImage(
                title = "좋은 추억은 많이 남기셨나요?",
                titleStyle = medium20pt,
                titleColor = Color.Black,
                titleImage = R.drawable.ic_iwantsleep
            )
            Gap(7)
            WithDrawlScreenTopTContent(
                content = "회원님께서 계정을 삭제하시게 된 이유를 알려주시면,\n" +
                        "귀중한 의견을 반영하여 더욱 노력하겠습니다",
                contentStyle = medium14pt,
                contentColor = Color(0xFF767676)
            )
            Gap(20)
            reasons.forEachIndexed { index, reason ->
                Gap(10)
                DetailByeReaseons(
                    reason = reason,
                    reasonStyle = medium14pt,
                    reasonColor = Color(0xFF222222),
                    circle = if (selectedStates[index]) circles[0] else circles[1],
                    isClicked = { selectedStates[index] = !selectedStates[index] }
                )
            }
            Gap(10)
            if (!isGuitarChecked.value) {
                DetailByeReaseons(
                    reason = "기타(직접입력)",
                    reasonStyle = medium14pt,
                    reasonColor = Color(0xFF222222),
                    circle = if (isGuitarChecked.value) circles[0] else circles[1],
                    isClicked = { isGuitarChecked.value = !isGuitarChecked.value }
                )
            } else {
                InputDetailByeReaseons(
                    inputReason = inputReason,
                    inputReasonStyle = medium14pt,
                    inputReasonColor = Color(0xFF222222),
                    circle = if (isGuitarChecked.value) circles[0] else circles[1],
                    isClicked = { isGuitarChecked.value = !isGuitarChecked.value }
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            VariableButtonColorTextDefaultSizeButton(
                text = "다음",
                isVerified = canMove,
                onClicked = {
                    if (canMove.value) {
                        moveToWithDrawalScreen3()
                    } else {

                    }
                }
            )
        }
    }
}

@Composable
fun DetailByeReaseons(
    reason: String,
    reasonStyle: TextStyle,
    reasonColor: Color,
    circle: Int,
    isClicked: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 15.dp, end = 15.dp)
            .height(50.dp)
            .border(
                border = BorderStroke(
                    width = 1.dp,
                    color = Color(0xFFDDDDDD)
                ),
                shape = RoundedCornerShape(10.dp)
            )
            .clickableNoEffect {
                isClicked()
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = circle),
            contentDescription = "",
            modifier = Modifier
                .padding(start = 20.dp, end = 4.dp)
        )
        Gap(10)
        Text(
            text = reason,
            style = reasonStyle,
            color = reasonColor
        )
    }
}

@Composable
fun InputDetailByeReaseons(
    inputReason: MutableState<String>,
    inputReasonStyle: TextStyle,
    inputReasonColor: Color,
    circle: Int,
    isClicked: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 15.dp, end = 15.dp)
            .wrapContentHeight()
            .border(
                border = BorderStroke(
                    width = 1.dp,
                    color = Color(0xFFDDDDDD)
                ),
                shape = RoundedCornerShape(10.dp)
            )
            .clickableNoEffect {
                isClicked()
            }
            .imePadding()
            .focusable(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .clickableNoEffect {
                    isClicked()
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = circle),
                contentDescription = "",
                modifier = Modifier
                    .padding(start = 20.dp, end = 4.dp)
            )
            Gap(10)
            Text(
                text = "기타(직접입력)",
                style = medium14pt,
                color = Color(0xFF222222)
            )
        }
        Gap(16)
        OutlinedTextField(
            value = inputReason.value,
            placeholder = {
                Text(
                    text = "기타(직접입력)",
                    style = medium14pt,
                    color = Color(0xFF222222)
                )
            },
            onValueChange = {
                inputReason.value = it
            },
            textStyle = inputReasonStyle,
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = inputReasonColor,
                unfocusedTextColor = inputReasonColor,
                focusedContainerColor = Color(0xFFEEEEEE),
                unfocusedContainerColor = Color(0xFFEEEEEE)
            ),
            modifier = Modifier
                .height(140.dp)
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp)
        )
        Gap(20)
    }
}
