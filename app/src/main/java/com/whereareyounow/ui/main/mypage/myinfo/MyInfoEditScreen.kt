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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.whereareyounow.R
import com.whereareyounow.data.globalvalue.SYSTEM_STATUS_BAR_HEIGHT
import com.whereareyounow.data.myinfo.MyInfoScreenUIState
import com.whereareyounow.data.signup.SignUpUserNameState
import com.whereareyounow.ui.component.CustomSurface
import com.whereareyounow.ui.component.CustomTextField
import com.whereareyounow.ui.component.CustomTextFieldState
import com.whereareyounow.ui.component.CustomTopBar
import com.whereareyounow.ui.component.RoundedCornerButton
import com.whereareyounow.ui.component.tobbar.OneTextOneIconTobBar
import com.whereareyounow.ui.main.mypage.MyPageViewModel
import com.whereareyounow.ui.main.mypage.byebye.Gap
import com.whereareyounow.ui.main.mypage.byebye.WithdrawlButton
import com.whereareyounow.ui.theme.bold18pt
import com.whereareyounow.ui.theme.getColor
import com.whereareyounow.ui.theme.medium12pt
import com.whereareyounow.ui.theme.medium14pt
import com.whereareyounow.util.CustomPreview
import com.whereareyounow.util.clickableNoEffect

@Composable
fun MyInfoEditScreen(
    moveToBackScreen: () -> Unit,
    viewModel: MyInfoViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.getUserInfo()
    }
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
    MyInfoEditScreen(
        uiState = uiState,
        updateInputUserName = viewModel::updateInputUserName,
        updateUserName = viewModel::updateUserName,
        moveToBackScreen = moveToBackScreen,
        isContent = true,
    )
}

@Composable
private fun MyInfoEditScreen(
    uiState: MyInfoScreenUIState,
    updateInputUserName: (String) -> Unit,
    updateUserName: (() -> Unit) -> Unit,
    moveToBackScreen: () -> Unit,
    isContent: Boolean,
) {
    CustomSurface {
        CustomTopBar(
            title = "내 정보 관리",
            onBackButtonClicked = moveToBackScreen
        )

        Spacer(Modifier.height(34.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 15.dp, end = 15.dp)
        ) {
            Text(
                modifier = Modifier.padding(6.dp, 4.dp, 6.dp, 4.dp),
                text = "이름",
                style = medium12pt,
                color = Color(0xFF333333)
            )

            Spacer(Modifier.height(2.dp))

            CustomTextField(
                hint = "이름",
                inputText = uiState.name,
                onValueChange = updateInputUserName,
                warningText = "이름은 2~4자의 한글, 영문 대/소문자 조합으로 입력해주세요.",
                onSuccessText = "사용가능한 이름입니다.",
                textFieldState = when (uiState.nameState) {
                    SignUpUserNameState.Idle -> CustomTextFieldState.Idle
                    SignUpUserNameState.Valid -> CustomTextFieldState.Satisfied
                    SignUpUserNameState.Invalid -> CustomTextFieldState.Unsatisfied
                }
            )
        }

        Spacer(Modifier.height(10.dp))

        InputMyInfoBox(
            title = "이메일",
            content = uiState.email,
        )

        Spacer(Modifier.weight(1f))

        Box(
            modifier = Modifier.padding(start = 15.dp, end = 15.dp)
        ) {
            RoundedCornerButton(
                onClick = { updateUserName(moveToBackScreen) }
            ) {
                Text(
                    text = "수정하기",
                    color = Color(0xFFF2F2F2),
                    style = bold18pt
                )
            }
        }

        Spacer(Modifier.height(20.dp))
    }
}