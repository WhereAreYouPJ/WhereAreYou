package com.whereareyounow.ui.main.mypage

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import com.whereareyounow.R
import com.whereareyounow.data.globalvalue.CALENDAR_VIEW_HEIGHT
import com.whereareyounow.data.globalvalue.TOP_BAR_HEIGHT
import com.whereareyounow.ui.main.friend.GrayLine
import com.whereareyounow.ui.theme.WhereAreYouTheme
import com.whereareyounow.ui.theme.medium14pt
import com.whereareyounow.ui.theme.medium16pt
import com.whereareyounow.ui.theme.medium20pt

@Composable
fun MyPageScreen(
    paddingValues: PaddingValues,
    moveToSignInScreen: () -> Unit,
    moveToMyInfoScreen: () -> Unit,
    moveToLocationFavorites : () -> Unit,
    moveToFeedBookmarks : () -> Unit,
    moveToFeedSaved : () -> Unit,


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
        moveToSignInScreen = moveToSignInScreen,
        moveToMyInfoScreen = moveToMyInfoScreen,
        moveToLocationFavorites = moveToLocationFavorites,
        moveToFeedBookmarks = moveToFeedBookmarks,
        moveToFeedSaved = moveToFeedSaved
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
    moveToSignInScreen: () -> Unit,
    moveToMyInfoScreen : () -> Unit,
    moveToLocationFavorites : () -> Unit,
    moveToFeedBookmarks : () -> Unit,
    moveToFeedSaved : () -> Unit

) {
    LaunchedEffect(Unit) {
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
                    WarningState.SignOut -> signOut(moveToSignInScreen)
                    WarningState.DeleteCalendar -> deleteCalendar()
                    WarningState.Withdrawal -> withdrawAccount(moveToSignInScreen)
                }
            }
        )
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0XFFF0F0F0))
    )
    {
        // 파란 원 배경
//        Canvas(modifier = Modifier.fillMaxSize()) {
//            drawCircle(
////                brush = Brush.horizontalGradient(listOf(Color(0xFF362A9C), Color(0xFF214BB7))),
//                color = Color(0xFF7B50FF),
//                center = Offset(size.width / 2, CALENDAR_VIEW_HEIGHT + TOP_BAR_HEIGHT - size.width),
//                radius = size.width,
//                style = Fill
//            )
//        }
        Image(
            painter = painterResource(id = R.drawable.ic_mypage_canvas),
            contentDescription = ""
        )
        Box(
            modifier = Modifier
                .padding(start = 17.dp, end = 18.dp, top = 253.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Color.White)
                .fillMaxWidth()
                .height(60.dp),
            contentAlignment = Alignment.Center
        ) {
            Row(
//                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
//                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "내코드",
                    style = medium14pt
                )
                Spacer(Modifier.size(6.dp))
                Text(
                    text = "123456",
                    style = medium16pt,
                    color = Color(0XFF6236E9)
                )
            }
        }


        //            Row{
//                Text("내 코드" , style = medium14pt)
//                Spacer(Modifier.size(6.dp))
//                Text("코드 입력창" , style = medium16pt)
//            }


        // 민혁쓰까지
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height((CALENDAR_VIEW_HEIGHT / density).dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Box(
                ) {
                    GlideImage(
                        modifier = Modifier
                            .width(((CALENDAR_VIEW_HEIGHT / density) / 3).dp)
                            .height(((CALENDAR_VIEW_HEIGHT / density) / 3).dp)
                            .clip(RoundedCornerShape(20)),
                        imageModel = { profileImageUri ?: R.drawable.idle_profile },
                        imageOptions = ImageOptions(contentScale = ContentScale.Crop)
                    )
                    Box(
                        modifier = Modifier
                            .padding(start = 82.dp)
                            .align(Alignment.BottomEnd)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_mypage_camera),
                            contentDescription = "",
                            modifier = Modifier
                                .align(Alignment.Center)
                        )
                    }

                }

                Spacer(Modifier.height(6.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = name,
                        style = medium20pt,
                        color = Color(0xFFFFFFFF),
                        letterSpacing = 0.05.em
                    )
                    Image(
                        painter = painterResource(id = R.drawable.ic_mypage_modify),
                        contentDescription = "",
                        modifier = Modifier.clickable {

                        }
                    )
                }
            }
        }
        // 내정보관리부터
        Column(
            modifier = Modifier
                .padding(top = ((CALENDAR_VIEW_HEIGHT + TOP_BAR_HEIGHT) / density).dp)
//                .padding(top = 280.dp)
                .fillMaxWidth(),
//            verticalArrangement = Arrangement.Center
        ) {
//            Box(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(40.dp)
////                    .padding(start = 17.dp , end = 18.dp),
////                contentAlignment = Alignment.CenterStart
//            ) {
//                Row(
//                    horizontalArrangement = Arrangement.Center,
//                    verticalAlignment = Alignment.CenterVertically,
//                    modifier = Modifier.fillMaxWidth()
//                ){
//                    Text(
//                        text = "내코드",
//                        style = medium14pt
//                    )
//                    Spacer(Modifier.size(6.dp))
//                    Text(
//                        text = "123456",
//                        style = medium16pt,
//                        color = Color(0XFF6236E9)
//                    )
//                }
//
//            }
            Spacer(Modifier.size(50.dp))

            // 내 정보 관리 , 위치 즐겨찾기 , 피드 책갈피 , 피드 보관함
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(50.dp))
                    .padding(start = 17.dp, end = 18.dp)
                    .background(Color.White)
                    .fillMaxWidth()
//                    .height(200.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    val firstMypageList = listOf("내 정보 관리", "위치 즐겨찾기", "피드 책갈피", "피드 보관함")

                    firstMypageList.forEach {
                        // top 11 bottom 12
                        Spacer(Modifier.size(11.dp))
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 12.dp)
                                .clickable {
                                    when (it) {
                                        "내 정보 관리" -> moveToMyInfoScreen()
                                        "위치 즐겨찾기" -> moveToLocationFavorites()
                                        "피드 책갈피" -> moveToFeedBookmarks()
                                        "피드 보관함" -> moveToFeedSaved()
                                    }
                                },
                            verticalAlignment = Alignment.CenterVertically
                            ) {
                            Text(it, style = medium16pt)
                        }
                        Spacer(Modifier.size(12.dp))
                        if(it != "피드 보관함") {

                            GrayLine()
                        }
                    }
                }
            }
            Spacer(Modifier.size(12.dp))
            // 공지사항 , 1:1 이용문의
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(50.dp))
//                    .border(
//                        BorderStroke(
//                            width = 2.dp,
//                            color = Color.White
//                        ),
//                        shape = RoundedCornerShape(14.dp)
//                    )
                    .padding(start = 17.dp, end = 18.dp)
                    .background(Color.White)
                    .fillMaxWidth()
//                    .height(200.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    val firstMypageList = listOf("공지사항", "1:1 이용문의")

                    firstMypageList.forEach {
                        // top 11 bottom 12
                        Spacer(Modifier.size(11.dp))
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(it, style = medium16pt)
                        }
                        Spacer(Modifier.size(12.dp))
                        if(it != "1:1 이용문의") {

                            GrayLine()
                        }
                    }
                }
            }
            Spacer(Modifier.size(34.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 119.dp, end = 120.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_logout),
                    contentDescription = ""
                )
                Spacer(Modifier.size(4.dp))
                Image(
                    painter = painterResource(id = R.drawable.ic_vertical_small_grayline),
                    contentDescription = ""
                )
                Spacer(Modifier.size(4.dp))
                Image(
                    painter = painterResource(id = R.drawable.ic_authout),
                    contentDescription = ""
                )
            }

//            SettingsMenuItem(
//                menuName = "프로필 변경",
//                onClick = moveToModifyInfoScreen,
//                color = Color(0xFF3E3E3E)
//            )

            SettingsMenuItem(
                menuName = "로그아웃",
                color = Color(0xFFEC5C5C),
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
                    fontSize = 18.sp
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height((0.6).dp)
                    .background(color = Color(0xFFBEBEBE))
            )

            SettingsMenuItem(
                menuName = "캘린더 삭제",
                color = Color(0xFFEC5C5C),
                onClick = {
                    warningState = WarningState.DeleteCalendar
                    isWarningDialogShowing = true
                }
            )

            SettingsMenuItem(
                menuName = "회원탈퇴",
                color = Color(0xFFEC5C5C),
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
    color: Color = Color(0xFF000000),
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
            .clickable {
                onClick()
            }
            .padding(start = 20.dp, end = 20.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = menuName,
                fontSize = 16.sp,
                color = color,
                letterSpacing = 0.em
            )
            Spacer(Modifier.weight(1f))
            Image(
                modifier = Modifier
                    .size(20.dp),
                painter = painterResource(id = R.drawable.arrow_forward_ios_fill0_wght100_grad0_opsz24),
                contentDescription = null,
                colorFilter = ColorFilter.tint(Color(0xFF909295))
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
    val density = LocalDensity.current.density
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties()
    ) {
        CompositionLocalProvider(LocalDensity provides Density(density, fontScale = 1f)) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = Color(0xFFFFFFFF),
                        shape = RoundedCornerShape(10.dp)
                    )
                    .padding(10.dp)
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = warningTitle,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center
                )
//            Spacer(Modifier.height(20.dp))
//            Text(
//                text = warningText,
//                fontSize = 20.sp
//            )
                Spacer(Modifier.height(20.dp))
                Row {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(10.dp))
                            .background(Color(0xFF2D2573))
                            .clickable { onConfirm() }
                            .padding(10.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = okText,
                            fontSize = 16.sp,
                            color = Color(0xFFFFFFFF),
                            fontWeight = FontWeight.Medium
                        )
                    }
                    Spacer(Modifier.width(10.dp))
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(10.dp))
                            .background(Color(0xFFCCCCCC))
                            .clickable { onDismissRequest() }
                            .padding(10.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "취소",
                            fontSize = 16.sp,
                            color = Color(0xFF000000),
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }
    }
}

enum class WarningState {
    SignOut, DeleteCalendar, Withdrawal
}

//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//private fun MyPageScreenPreview() {
//    WhereAreYouTheme {
//        MyPageScreen(
//            name = "Name",
//            email = "Email",
//            profileImageUri = null,
//            getMyInfo = { },
//            signOut = { },
//            deleteCalendar = { },
//            withdrawAccount = { },
//            moveToSignInScreen = { },
//            moveToModifyInfoScreen = { }
//        )
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//private fun MyPageWarningDialogPreview() {
//    WhereAreYouTheme {
//        MyPageWarningDialog(
//            warningTitle = "로그아웃하시겠습니까?",
//            warningText = "경고",
//            okText = "확인",
//            onDismissRequest = {},
//            onConfirm = {}
//        )
//    }
//}