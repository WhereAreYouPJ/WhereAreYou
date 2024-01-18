package com.whereareyounow.ui.home.mypage

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import com.whereareyounow.R
import com.whereareyounow.data.GlobalValue
import com.whereareyounow.ui.theme.WhereAreYouTheme

@Composable
fun MyPageScreen(
    paddingValues: PaddingValues,
    moveToStartScreen: () -> Unit,
    moveToModifyInfoScreen: () -> Unit,
    viewModel: MyPageViewModel = hiltViewModel()
) {
    val name = viewModel.name.collectAsState().value
    val email = viewModel.email.collectAsState().value
    val profileImageUri = viewModel.profileImageUri.collectAsState().value
    MyPageScreen(
        name = name,
        email = email,
        profileImageUri = profileImageUri,
        getMyInfo = viewModel::getMyInfo,
        signOut = viewModel::signOut,
        deleteCalendar = viewModel::deleteCalendar,
        withdrawAccount = viewModel::withdrawAccount,
        moveToStartScreen = moveToStartScreen,
        moveToModifyInfoScreen = moveToModifyInfoScreen
    )
}

@Composable
fun MyPageScreen(
    name: String,
    email: String,
    profileImageUri: String?,
    getMyInfo: () -> Unit,
    signOut: (() -> Unit) -> Unit,
    deleteCalendar: () -> Unit,
    withdrawAccount: (() -> Unit) -> Unit,
    moveToStartScreen: () -> Unit,
    moveToModifyInfoScreen: () -> Unit
) {
    LaunchedEffect(true) {
        getMyInfo()
    }
    val density = LocalDensity.current.density
    var isWarningDialogShowing by remember { mutableStateOf(false) }
    var warningState by remember { mutableStateOf(WarningState.SignOut) }

    if (isWarningDialogShowing) {
        MyPageWarningDialog(
            warningTitle = when (warningState) {
                WarningState.SignOut -> "로그아웃 하시겠습니까?"
                WarningState.DeleteCalendar -> "캘린더를 삭제하시겠습니까?"
                WarningState.Withdrawal -> "회원을 탈퇴하시겠습니까?"
            },
            warningText = when (warningState) {
                WarningState.SignOut -> "현재 계정에서 로그아웃됩니다."
                WarningState.DeleteCalendar -> "모든 일정 정보가 삭제됩니다."
                WarningState.Withdrawal -> "모든 계정 정보가 삭제됩니다."
            },
            okText = when (warningState) {
                WarningState.SignOut -> "로그아웃"
                WarningState.DeleteCalendar -> "삭제"
                WarningState.Withdrawal -> "탈퇴"
            },
            onDismissRequest = { isWarningDialogShowing = false },
            onConfirm = {
                isWarningDialogShowing = false
                when (warningState) {
                    WarningState.SignOut -> signOut(moveToStartScreen)
                    WarningState.DeleteCalendar -> deleteCalendar()
                    WarningState.Withdrawal -> withdrawAccount(moveToStartScreen)
                }
            }
        )
    }


    Box(modifier = Modifier.fillMaxSize()) {
        // 파란 원 배경
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawCircle(
                brush = Brush.horizontalGradient(listOf(Color(0xFF362A9C), Color(0xFF214BB7))),
                center = Offset(size.width / 2, GlobalValue.calendarViewHeight + GlobalValue.topBarHeight - size.width),
                radius = size.width,
                style = Fill
            )
        }
        // 상단 유저 정보
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height((GlobalValue.topBarHeight / density).dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "설정",
                    color = Color.White,
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height((GlobalValue.calendarViewHeight / density).dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                GlideImage(
                    modifier = Modifier
                        .width(((GlobalValue.calendarViewHeight / density) / 2).dp)
                        .height(((GlobalValue.calendarViewHeight / density) / 2).dp)
                        .clip(RoundedCornerShape(50)),
                    imageModel = { profileImageUri ?: R.drawable.account_circle_fill0_wght200_grad0_opsz24 },
                    imageOptions = ImageOptions(contentScale = ContentScale.Crop,)
                )
                Spacer(Modifier.height(10.dp))
                Text(
                    text = name,
                    fontSize = 20.sp,
                    color = Color.White
                )
                Text(
                    text = email,
                    fontSize = 16.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Light
                )
            }
        }
        // 하단 설정 메뉴
        Column(
            modifier = Modifier
                .padding(top = ((GlobalValue.calendarViewHeight + GlobalValue.topBarHeight) / density).dp)
                .fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .padding(start = 20.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    text = "일반",
                    fontSize = 24.sp
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height((0.6).dp)
                    .background(color = Color.Black)
            )

            SettingsMenuItem(
                menuName = "프로필 변경",
                onClick = moveToModifyInfoScreen
            )

            SettingsMenuItem(
                menuName = "로그아웃",
                color = Color.Red,
                onClick = {
                    warningState = WarningState.SignOut
                    isWarningDialogShowing = true
                }
            )

            Spacer(Modifier.height(20.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .padding(start = 20.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    text = "정보",
                    fontSize = 24.sp
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height((0.6).dp)
                    .background(color = Color.Black)
            )

            SettingsMenuItem(
                menuName = "캘린더 삭제",
                color = Color.Red,
                onClick = {
                    warningState = WarningState.DeleteCalendar
                    isWarningDialogShowing = true
                }
            )

            SettingsMenuItem(
                menuName = "회원탈퇴",
                color = Color.Red,
                onClick = {
                    warningState = WarningState.Withdrawal
                    isWarningDialogShowing = true
                }
            )
        }
    }
}

@Composable
fun SettingsMenuItem(
    menuName: String,
    color: Color = Color.Black,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .clickable {
                onClick()
            }
            .padding(start = 20.dp, end = 20.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Row {
            Text(
                text = menuName,
                fontSize = 24.sp,
                color = color
            )
            Spacer(Modifier.weight(1f))
            Image(
                modifier = Modifier
                    .size(30.dp),
                painter = painterResource(id = R.drawable.arrow_forward_ios_fill0_wght100_grad0_opsz24),
                contentDescription = null
            )
        }
    }
}

@Composable
fun MyPageWarningDialog(
    warningTitle: String,
    warningText: String,
    okText: String,
    onDismissRequest: () -> Unit,
    onConfirm: () -> Unit,
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(10.dp)
                )
                .padding(20.dp)
        ) {
            Text(
                text = warningTitle,
                fontSize = 24.sp,
                fontWeight = FontWeight.Medium
            )
            Spacer(Modifier.height(20.dp))
            Text(
                text = warningText,
                fontSize = 20.sp
            )
            Spacer(Modifier.height(20.dp))
            Row {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color(0xFF2D2573))
                        .padding(10.dp)
                        .clickable { onConfirm() },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = okText,
                        fontSize = 20.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Medium
                    )
                }
                Spacer(Modifier.width(20.dp))
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color(0xFFCCCCCC))
                        .padding(10.dp)
                        .clickable { onDismissRequest() },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "취소",
                        fontSize = 20.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

enum class WarningState {
    SignOut, DeleteCalendar, Withdrawal
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun MyPageScreenPreview() {
    WhereAreYouTheme {
        MyPageScreen(
            name = "Name",
            email = "Email",
            profileImageUri = null,
            getMyInfo = {  },
            signOut = {  },
            deleteCalendar = {  },
            withdrawAccount = {  },
            moveToStartScreen = {  },
            moveToModifyInfoScreen = {  }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MyPageWarningDialogPreview() {
    WhereAreYouTheme {
        MyPageWarningDialog(
            warningTitle = "로그아웃하시겠습니까?",
            warningText = "경고",
            okText = "확인",
            onDismissRequest = {},
            onConfirm = {}
        )
    }
}