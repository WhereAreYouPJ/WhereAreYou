package com.whereareyounow.ui.main.mypage.myinfo

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
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
import com.whereareyounow.ui.main.mypage.MyPageViewModel
import com.whereareyounow.ui.theme.WhereAreYouTheme
import com.whereareyounow.ui.theme.medium14pt
import com.whereareyounow.ui.theme.medium16pt
import com.whereareyounow.ui.theme.medium18pt
import com.whereareyounow.ui.theme.medium20pt
import com.whereareyounow.util.clickableNoEffect

@Composable
fun MyPageScreen(
    paddingValues: PaddingValues,
    moveToSignInScreen: () -> Unit,
    moveToMyInfoScreen: () -> Unit,
    moveToLocationFavorites: () -> Unit,
    moveToFeedBookmarks: () -> Unit,
    moveToFeedSaved: () -> Unit,
    moveToAccoument: () -> Unit,
    moveToAsk: () -> Unit,
    moveToBye: () -> Unit,
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
        moveToFeedSaved = moveToFeedSaved,
        moveToAccoument = moveToAccoument,
        moveToAsk = moveToAsk,
        moveToBye = moveToBye
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
    moveToMyInfoScreen: () -> Unit,
    moveToLocationFavorites: () -> Unit,
    moveToFeedBookmarks: () -> Unit,
    moveToFeedSaved: () -> Unit,
    moveToAccoument: () -> Unit,
    moveToAsk: () -> Unit,
    moveToBye: () -> Unit


) {
    LaunchedEffect(Unit) {
        getMyInfo()
    }
    val density = LocalDensity.current.density
    var isWarningDialogShowing by remember { mutableStateOf(false) }
    var warningState by remember { mutableStateOf(WarningState.SignOut) }
    val clickedIndication = remember { MutableInteractionSource() }

    if (isWarningDialogShowing) {
        MyPageWarningDialog(
            warningTitle = when (warningState) {
                WarningState.SignOut -> "로그아웃?"
//                WarningState.DeleteCalendar -> "캘린더를 삭제하시겠습니까?"
                WarningState.Withdrawal -> "회원탈퇴"
            },
            warningText = when (warningState) {
                WarningState.SignOut -> "로그아웃을 진행하시겠습니까?"
//                WarningState.DeleteCalendar -> "모든 일정 정보가 삭제됩니다."
                WarningState.Withdrawal -> "회원을 탈퇴하시겠습니까?"
            },
            okText = when (warningState) {
                WarningState.SignOut -> "확인"
//                WarningState.DeleteCalendar -> "삭제"
                WarningState.Withdrawal -> "확인"
            },
            onDismissRequest = { isWarningDialogShowing = false },
            onConfirm = {
                isWarningDialogShowing = false
                when (warningState) {
                    WarningState.SignOut -> signOut(moveToSignInScreen)
//                    WarningState.DeleteCalendar -> deleteCalendar()
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
//        Image(
//            painter = painterResource(id = R.drawable.ic_mypage_canvas),
//            contentDescription = "",
//            modifier = Modifier.height(280.dp)
//        )

        Box(
            modifier = Modifier
                .clip(shape = RoundedCornerShape(bottomEnd = 18.dp, bottomStart = 18.dp))
                .fillMaxWidth()
                .height(280.dp)
                .background(Color(0XFF6236E9))
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
                verticalAlignment = Alignment.CenterVertically,
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
                            .size(100.dp)
                            .clip(RoundedCornerShape(34.4.dp)),
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
//                    Image(
//                        painter = painterResource(id = R.drawable.ic_mypage_modify),
//                        contentDescription = "",
//                        modifier = Modifier.clickable {
//
//                        }
//                    )
                }
            }
        }
        Spacer(Modifier.size(14.dp))

        // 내정보관리부터
        Column(
            modifier = Modifier
                .padding(top = ((CALENDAR_VIEW_HEIGHT + TOP_BAR_HEIGHT) / density).dp)
                .fillMaxWidth(),
        ) {

            Spacer(Modifier.size(30.dp))

            // 내 정보 관리 , 위치 즐겨찾기 , 피드 책갈피 , 피드 보관함
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 17.dp, end = 18.dp)
                    .clip(
                        shape = RoundedCornerShape(
                            topStart = 20.dp,
                            topEnd = 12.dp,
                            bottomStart = 12.dp,
                            bottomEnd = 12.dp
                        )
                    )
                    .background(Color.White)
                    .height(192.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(
                            RoundedCornerShape(
                                topStart = 12.dp,
                                topEnd = 12.dp,
                                bottomStart = 12.dp,
                                bottomEnd = 12.dp
                            )
                        )
                ) {
                    val firstMypageList = listOf("내 정보 관리", "위치 즐겨찾기", "피드 책갈피", "피드 보관함")

                    firstMypageList.forEach {
                        // top 11 bottom 12
                        Spacer(Modifier.size(11.dp))
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 12.dp)
                                .clickable(
                                    interactionSource = remember { clickedIndication },
                                    indication = null
                                ) {

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
                        if (it != "피드 보관함") {

                            GrayLine()
                        }
                    }
                }
            }
            Spacer(Modifier.size(20.dp))
            // 공지사항 , 1:1 이용문의
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 17.dp, end = 18.dp)
                    .clip(
                        shape = RoundedCornerShape(
                            topStart = 20.dp,
                            topEnd = 12.dp,
                            bottomStart = 12.dp,
                            bottomEnd = 12.dp
                        )
                    )
                    .background(Color.White)
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
                                .padding(start = 12.dp)
                                .clickable(
                                    interactionSource = remember { clickedIndication },
                                    indication = null
                                ) {
                                    when (it) {
                                        "공지사항" -> moveToAccoument()
                                        "1:1 이용문의" -> moveToAsk()
                                    }
                                },
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(it, style = medium16pt)
                        }
                        Spacer(Modifier.size(12.dp))
                        if (it != "1:1 이용문의") {

                            GrayLine()
                        }
                    }
                }
            }
            Spacer(Modifier.weight(1f))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 119.dp, end = 120.dp, bottom = 20.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
//                Image(
//                    painter = painterResource(id = R.drawable.ic_logout),
//                    contentDescription = "",
//                    modifier = Modifier.clickableNoEffect{
//
//                    }
//                )
                Box(
                    modifier = Modifier
                        .width(64.dp)
                        .height(28.dp)
                        .clickableNoEffect {
                            warningState = WarningState.SignOut
                            isWarningDialogShowing = true
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "로그아웃",
                        style = medium14pt,
                        color = Color(0xFFBEBEBE)
                    )
                }

                Spacer(Modifier.size(4.dp))
                Image(
                    painter = painterResource(id = R.drawable.ic_vertical_small_grayline),
                    contentDescription = ""
                )
                Spacer(Modifier.size(4.dp))

                Box(
                    modifier = Modifier
                        .width(64.dp)
                        .height(28.dp)
                        .clickableNoEffect {
                            moveToBye()
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "회원탈퇴",
                        style = medium14pt,
                        color = Color(0xFFBEBEBE)
                    )
                }
//                Spacer(Modifier.size(10.dp))
//                Image(
//                    painter = painterResource(id = R.drawable.ic_authout),
//                    contentDescription = "",
//                    modifier = Modifier.clickableNoEffect {
//                        moveToBye()
//                    }
//                )
            }

            Spacer(Modifier.height(20.dp))

//            Box(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(40.dp)
//                    .padding(start = 20.dp),
//                contentAlignment = Alignment.CenterStart
//            ) {
//                Text(
//                    text = "정보",
//                    fontSize = 18.sp
//                )
//            }
//            Box(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height((0.6).dp)
//                    .background(color = Color(0xFFBEBEBE))
//            )
//
//            SettingsMenuItem(
//                menuName = "캘린더 삭제",
//                color = Color(0xFFEC5C5C),
//                onClick = {
////                    warningState = WarningState.DeleteCalendar
//                    isWarningDialogShowing = true
//                }
//            )
//
//            SettingsMenuItem(
//                menuName = "회원탈퇴",
//                color = Color(0xFFEC5C5C),
//                onClick = {
//
//
//                    warningState = WarningState.Withdrawal
//                    isWarningDialogShowing = true
//                }
//            )
        }
    }
}

//@Composable
//fun SettingsMenuItem(
//    menuName: String,
//    color: Color = Color(0xFF000000),
//    onClick: () -> Unit
//) {
//    Box(
//        modifier = Modifier
//            .fillMaxWidth()
//            .height(40.dp)
//            .clickable {
//                onClick()
//            }
//            .padding(start = 20.dp, end = 20.dp),
//        contentAlignment = Alignment.CenterStart
//    ) {
//        Row(
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            Text(
//                text = menuName,
//                fontSize = 16.sp,
//                color = color,
//                letterSpacing = 0.em
//            )
//            Spacer(Modifier.weight(1f))
//            Image(
//                modifier = Modifier
//                    .size(20.dp),
//                painter = painterResource(id = R.drawable.arrow_forward_ios_fill0_wght100_grad0_opsz24),
//                contentDescription = null,
//                colorFilter = ColorFilter.tint(Color(0xFF909295))
//            )
//        }
//    }
//}

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
        properties = DialogProperties(),

        ) {
        CompositionLocalProvider(LocalDensity provides Density(density, fontScale = 1f)) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 120.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = Color(0xFF333333),
                            shape = RoundedCornerShape(12.dp)
                        )
                        .padding(start = 16.dp, end = 15.dp)
                        .height(164.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 10.dp, end = 10.dp, top = 20.dp)
                    ) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = warningTitle,
                            style = medium18pt,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 10.dp, end = 10.dp, top = 8.dp)
                    ) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = warningText,
                            style = medium14pt,
                            color = Color(0xFFA0A0A0)
                        )
                    }
                    Spacer(Modifier.height(24.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 24.dp),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Text(
                            text = "취소",
                            style = medium14pt,
                            color = Color.White,
                            modifier = Modifier.clickableNoEffect {
                                onDismissRequest()
                            }
                        )
                        Spacer(Modifier.width(24.dp))
                        Text(
                            text = okText,
                            style = medium14pt,
                            color = Color(0xFFE09EFF),
                            modifier = Modifier.clickableNoEffect {
                                onConfirm()
                            }
                        )
                    }
                }
            }

        }
    }
}

enum class WarningState {
    SignOut,
    Withdrawal
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
//            warningTitle = "로그아웃",
//            warningText = "로그아웃을 진행하기겠습니까?",
//            okText = "확인",
//            onDismissRequest = {},
//            onConfirm = {}
//        )
//    }
//}