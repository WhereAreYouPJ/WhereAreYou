package com.whereareyounow.ui.main.mypage.myinfo

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.whereareyounow.R
import com.whereareyounow.data.globalvalue.SYSTEM_STATUS_BAR_HEIGHT
import com.whereareyounow.data.myinfo.MyInfoScreenUIState
import com.whereareyounow.ui.component.CustomSurface
import com.whereareyounow.ui.component.tobbar.OneTextTwoIconTobBar
import com.whereareyounow.ui.theme.getColor
import com.whereareyounow.ui.theme.medium12pt
import com.whereareyounow.ui.theme.medium14pt

@Composable
fun MyInfoScreen(
    moveToBackScreen: () -> Unit,
    moveToEditMyInfoScreen: () -> Unit,
    viewModel: MyInfoViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.getUserInfo()
    }
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
    MyInfoScreen(
        uiState = uiState,
        isContent = true,
        moveToMyPageScreen = moveToBackScreen,
        moveToEditMyInfoScreen = moveToEditMyInfoScreen
    )
}

@Composable
private fun MyInfoScreen(
    uiState: MyInfoScreenUIState,
    moveToMyPageScreen: () -> Unit,
    moveToEditMyInfoScreen: () -> Unit,
    isContent: Boolean,
) {
    CustomSurface {
        OneTextTwoIconTobBar(
            title = "내 정보 관리",
            firstIconClicked = moveToMyPageScreen,
            firstIcon = R.drawable.ic_back,
            secondIconClicked = moveToEditMyInfoScreen,
            secondIcon = R.drawable.ic_plusbrandcolor
        )

        Spacer(Modifier.height(34.dp))

        InputMyInfoBox(
            title = "이름",
            content = uiState.name,
        )

        Spacer(Modifier.height(10.dp))

        InputMyInfoBox(
            title = "이메일",
            content = uiState.email,
        )
    }
}

@Composable
fun InputMyInfoBox(
    title: String,
    content: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 15.dp, end = 15.dp)
    ) {
        Text(
            modifier = Modifier.padding(6.dp, 4.dp, 6.dp, 4.dp),
            text = title,
            style = medium12pt,
            color = Color(0xFF333333)
        )

        Spacer(Modifier.height(2.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(44.dp)
                .border(
                    border = BorderStroke(
                        width = 1.dp,
                        color = getColor().thin
                    ),
                    shape = RoundedCornerShape(6.dp)
                ),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                modifier = Modifier.padding(start = 8.dp),
                text = content,
                color = Color(0xFF666666),
                style = medium14pt
            )
        }
    }
}