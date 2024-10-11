package com.whereareyounow.ui.main.mypage.myinfo

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.whereareyounow.R
import com.whereareyounow.data.globalvalue.SYSTEM_STATUS_BAR_HEIGHT
import com.whereareyounow.ui.component.button.padding_yes.VariableButtonColorTextDefaultSizeButton
import com.whereareyounow.ui.component.tobbar.OneTextTwoIconTobBar
import com.whereareyounow.ui.main.mypage.MyPageViewModel
import com.whereareyounow.ui.main.mypage.withdrawl.Gap
import com.whereareyounow.ui.theme.medium12pt
import com.whereareyounow.ui.theme.medium14pt
import com.whereareyounow.util.clickableNoEffect
import com.whereareyounow.util.popupmenu.CustomPopup
import com.whereareyounow.util.popupmenu.PopupPosition
import com.whereareyounow.util.popupmenu.PopupState

@Composable
fun MyInfoScreen(
    moveToMyPageScreen: () -> Unit,
    moveToEditMyInfoScreen: () -> Unit,
) {

    MyInfoScreen(
        isContent = true,
        moveToMyPageScreen = moveToMyPageScreen,
        moveToEditMyInfoScreen = moveToEditMyInfoScreen
    )

}

@Composable
private fun MyInfoScreen(
    isContent: Boolean,
    moveToMyPageScreen: () -> Unit,
    moveToEditMyInfoScreen: () -> Unit,
) {
    val myPageViewModel : MyPageViewModel = hiltViewModel()
    val myName = myPageViewModel.name.collectAsState().value
    val myEmail = myPageViewModel.email.collectAsState().value
    Log.d("sfjlsjfls" , myName!!)
    val popupState = remember {
        PopupState(false, PopupPosition.BottomLeft)
    }
    val isVerifyed = remember {
        mutableStateOf(false)
    }
    val isModifyClicked = remember {
        mutableStateOf(false)
    }
    val isReadOnly = remember {
        mutableStateOf(true)
    }
    val nameInputText = remember {
        mutableStateOf(myName)
    }
    val emailInputText = remember {
        mutableStateOf(myEmail)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = SYSTEM_STATUS_BAR_HEIGHT.dp),
    ) {
        OneTextTwoIconTobBar(
            title = "내 정보 관리",
            firstIconClicked = moveToMyPageScreen,
            firstIcon = R.drawable.ic_back,
            secondIconClicked = { popupState.isVisible = true },
            secondIcon = R.drawable.ic_plusbrandcolor
        ) {
            ModifierIconPopUp(
                popupState = popupState,
                isModifyClicked = isModifyClicked,
                isReadOnly = isReadOnly,
                moveToEditMyInfoScreen = moveToEditMyInfoScreen,
                modifier = Modifier
                    .clickableNoEffect {
                        moveToEditMyInfoScreen()
                    }
                    .align(Alignment.CenterEnd)
            )
        }
        Gap(34)
        InputMyInfoBox(
            title = "이름",
            content = myName,
            isReadOnly = isReadOnly,
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent,
            borderDp = 1f,
            borderColor = Color(0xFFD4D4D4)
        )
        Gap(10)
        InputMyInfoBox(
            title = "이메일",
            content = myEmail,
            isReadOnly = isReadOnly,
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent,
            borderDp = 1f,
            borderColor = Color(0xFFD4D4D4)
        )
        Spacer(modifier = Modifier.weight(1f))
        if (isModifyClicked.value) {
            VariableButtonColorTextDefaultSizeButton(
                "수정하기",
                isVerified = isModifyClicked,
                onClicked = {
                    isReadOnly.value = true
                }
            )
        } else {

        }
    }
}

@Composable
fun InputMyInfoBox(
    title: String,
    content: String?,
    isReadOnly: MutableState<Boolean>,
    focusedBorderColor : Color,
    unfocusedBorderColor : Color,
    borderDp : Float,
    borderColor : Color
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 15.dp, end = 15.dp)
    ) {
        Row(
            modifier = Modifier
                .width(35.dp)
                .height(24.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                style = medium12pt,
                color = Color(0xFF333333)
            )
        }
        Gap(2)
        OutlinedTextField(
            value = content!!,
            onValueChange = {
//                content = it
            },
            readOnly = isReadOnly.value,
            modifier = Modifier
                .border(
                    border = BorderStroke(
                        width = borderDp.dp,
                        color = borderColor
                    ),
                    shape = RoundedCornerShape(6.dp)
                )
                .height(52.dp)
                .fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = focusedBorderColor,
                unfocusedBorderColor = unfocusedBorderColor,
                focusedTextColor = Color(0xFF666666),
                unfocusedTextColor = Color(0xFF666666)
            ),
            textStyle = medium14pt
        )

    }
}



@Composable
fun EditMyInfoBox(
    title: String,
    content: MutableState<String?>,
    isReadOnly: MutableState<Boolean>,
    focusedBorderColor : Color,
    unfocusedBorderColor : Color,
    borderDp : Float,
    borderColor : Color
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 15.dp, end = 15.dp)
    ) {
        Row(
            modifier = Modifier
                .width(35.dp)
                .height(24.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                style = medium12pt,
                color = Color(0xFF333333)
            )
        }
        Gap(2)
        OutlinedTextField(
            value = content.value!!,
            onValueChange = {
                content.value = it
            },
            readOnly = isReadOnly.value,
            modifier = Modifier
                .border(
                    border = BorderStroke(
                        width = borderDp.dp,
                        color = borderColor
                    ),
                    shape = RoundedCornerShape(6.dp)
                )
                .height(52.dp)
                .fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = focusedBorderColor,
                unfocusedBorderColor = unfocusedBorderColor,
                focusedTextColor = Color(0xFF666666),
                unfocusedTextColor = Color(0xFF666666)
            ),
            textStyle = medium14pt
        )
    }
}

@Composable
fun ModifierIconPopUp(
    modifier: Modifier = Modifier,
    popupState: PopupState,
    isModifyClicked: MutableState<Boolean>,
    isReadOnly: MutableState<Boolean>,
    moveToEditMyInfoScreen: () -> Unit,
) {
    val density = LocalDensity.current.density
    Box(
        contentAlignment = Alignment.CenterEnd,
        modifier = modifier.padding(end = 15.dp, top = 83.dp - 48.dp)
    ) {
        CustomPopup(
            popupState = popupState,
            onDismissRequest = {
                popupState.isVisible = false
            }
        ) {
            CompositionLocalProvider(LocalDensity provides Density(density, fontScale = 1f)) {
                Box(
                    modifier = Modifier
                        .width(160.dp)
                        .height(38.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(color = Color(0xFF514675))
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .clickableNoEffect {
                                moveToEditMyInfoScreen()
                            },
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(
                                    start = 14.dp,
                                    top = 8.dp,
                                    bottom = 10.dp
                                ),
                            text = "수정하기",
                            style = medium14pt,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}