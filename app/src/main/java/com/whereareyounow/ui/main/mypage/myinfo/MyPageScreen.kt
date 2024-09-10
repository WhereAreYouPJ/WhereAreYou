package com.whereareyounow.ui.main.mypage.myinfo

import androidx.compose.animation.animateContentSize
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
import com.whereareyounow.ui.main.mypage.byebye.Gap
import com.whereareyounow.ui.theme.WhereAreYouTheme
import com.whereareyounow.ui.theme.getColor
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
    val isProfileClicked = remember { mutableStateOf(false) }
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
    ) {

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
                .clip(RoundedCornerShape(14.dp))
                .background(Color.White)
                .fillMaxWidth()
                .height(57.dp),
            contentAlignment = Alignment.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "내코드",
                    style = medium14pt,
                    color = Color(0xFF222222)
                )
                Gap(6)
                Text(
                    text = "123456",
                    style = medium16pt,
                    color = getColor().brandColor
                )
            }
        }

        // 273 , 239
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Gap(13)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height((CALENDAR_VIEW_HEIGHT / density).dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    GlideImage(
                        modifier = Modifier
                            .size(110.dp)
                            .clip(RoundedCornerShape(34.4.dp))
                            .clickableNoEffect {
                                isProfileClicked.value = !isProfileClicked.value
                            },
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
//239
                Spacer(Modifier.height(6.dp))

                if (isProfileClicked.value) {
                    Column(
                        modifier = Modifier.height(37.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(start = 92.dp, end = 92.dp)
                                .height(38.dp)
                                .clip(
                                    RoundedCornerShape(10.dp)
                                )
                                .fillMaxWidth()
                                .background(Color(0xFF514675))
                                .clickableNoEffect {

                                },
                        ) {
                            Text(
                                text = "사진 보관함",
                                style = medium14pt,
                                color = Color.White,
                                modifier = Modifier.padding(start = 14.dp , top = 8.dp , bottom = 10.dp)
                            )
                            Gap(72)
                            Image(
                                painter = painterResource(id = R.drawable.ic_picturesved),
                                contentDescription = "",
                                modifier = Modifier.padding(top = 8.dp, bottom = 8.dp, end = 13.dp)
                            )
                        }
                    }
                } else {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(38.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = name,
                            style = medium20pt,
                            color = Color(0xFFFFFFFF),
                            letterSpacing = 0.05.em
                        )
                    }
                }
                Gap(3)
            }
        }
        Gap(14)

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
                            topStart = 14.dp,
                            topEnd = 14.dp,
                            bottomStart = 14.dp,
                            bottomEnd = 14.dp
                        )
                    )
                    .background(Color.White)
                    .height(192.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
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
                            topStart = 14.dp,
                            topEnd = 14.dp,
                            bottomStart = 14.dp,
                            bottomEnd = 14.dp
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
            }
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
