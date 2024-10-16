package com.whereareyounow.ui.main.mypage.myinfo

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import com.whereareyounow.R
import com.whereareyounow.data.cached.AuthData
import com.whereareyounow.ui.component.CustomSurfaceState
import com.whereareyounow.ui.component.HorizontalDivider
import com.whereareyounow.ui.main.mypage.MyPageViewModel
import com.whereareyounow.ui.main.mypage.byebye.Gap
import com.whereareyounow.ui.theme.OnMyWayTheme
import com.whereareyounow.ui.theme.getColor
import com.whereareyounow.ui.theme.medium14pt
import com.whereareyounow.ui.theme.medium16pt
import com.whereareyounow.ui.theme.medium18pt
import com.whereareyounow.ui.theme.medium20pt
import com.whereareyounow.util.clickableNoEffect

@Composable
fun MyPageScreen(
    customSurfaceState: CustomSurfaceState,
    moveToSignInMethodSelectionScreen: () -> Unit,
    moveToMyInfoScreen: () -> Unit,
    moveToLocationFavorite: () -> Unit,
    moveToFeedBookmarks: () -> Unit,
    moveToFeedSaved: () -> Unit,
    moveToAccoument: () -> Unit,
    moveToAsk: () -> Unit,
    moveToBye: () -> Unit,
    viewModel: MyPageViewModel = hiltViewModel()
) {
    val brandColor = getColor().brandColor
    DisposableEffect(Unit) {
        customSurfaceState.statusBarColor = brandColor
        onDispose {
            customSurfaceState.statusBarColor = Color(0xFFFFFFFF)
        }
    }

    val name = viewModel.name.collectAsState().value
    val email = viewModel.email.collectAsState().value
    val profileImageUri = viewModel.profileImageUri.collectAsState().value
    val testst = AuthData.memberSeq

    MyPageScreen(
        name = name!!,
        email = email!!,
        profileImageUri = profileImageUri,
        getMyInfo = viewModel::getMyInfo,
        signOut = viewModel::signOut,
        deleteCalendar = viewModel::deleteCalendar,
        withdrawAccount = viewModel::withdrawAccount,
        moveToSignInMethodSelectionScreen = moveToSignInMethodSelectionScreen,
        moveToMyInfoScreen = moveToMyInfoScreen,
        moveToLocationFavorite = moveToLocationFavorite,
        moveToFeedBookmarks = moveToFeedBookmarks,
        moveToFeedSaved = moveToFeedSaved,
        moveToAccoument = moveToAccoument,
        moveToAsk = moveToAsk,
        moveToBye = moveToBye
    )
}

@Composable
private fun MyPageScreen(
    name: String,
    email: String,
    profileImageUri: String?,
    getMyInfo: (memberSeq: Int) -> Unit,
    signOut: (() -> Unit) -> Unit,
    deleteCalendar: () -> Unit,
    withdrawAccount: (() -> Unit) -> Unit,
    moveToSignInMethodSelectionScreen: () -> Unit,
    moveToMyInfoScreen: () -> Unit,
    moveToLocationFavorite: () -> Unit,
    moveToFeedBookmarks: () -> Unit,
    moveToFeedSaved: () -> Unit,
    moveToAccoument: () -> Unit,
    moveToAsk: () -> Unit,
    moveToBye: () -> Unit
) {
    LaunchedEffect(Unit) {
        getMyInfo(AuthData.memberSeq)
    }
    val density = LocalDensity.current.density
    val isWarningDialogShowing = remember { mutableStateOf(false) }
    val isPhotoDialogShowing = remember { mutableStateOf(false) }
    val isProfileClicked = remember { mutableStateOf(false) }
    val clickedIndication = remember { MutableInteractionSource() }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0XFFF0F0F0))
    ) {
        Box(
            modifier = Modifier
                .clip(shape = RoundedCornerShape(bottomEnd = 18.dp, bottomStart = 18.dp))
                .fillMaxWidth()
                .height(240.dp)
                .background(getColor().brandColor)
        )

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier
                    .padding(46.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Box {
                    GlideImage(
                        modifier = Modifier
                            .size(100.dp)
                            .clip(RoundedCornerShape(34.dp))
                            .clickableNoEffect {
                                isPhotoDialogShowing.value = true
                            },
                        imageModel = { profileImageUri ?: R.drawable.ic_profile },
                        imageOptions = ImageOptions(contentScale = ContentScale.Crop)
                    )

                    Image(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .size(24.dp)
                            .offset(x = 6.dp, y = 0.dp),
                        painter = painterResource(id = R.drawable.ic_mypage_camera),
                        contentDescription = null,
                    )
                }

                Spacer(Modifier.height(10.dp))

                Text(
                    text = name,
                    style = medium20pt,
                    color = Color(0xFFFFFFFF),
                )
            }
        }

        Column(
            modifier = Modifier
                .padding(start = 15.dp, top = 210.dp, end = 15.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(57.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(Color(0xFFFFFFFF)),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "내 코드",
                        style = medium14pt,
                        color = Color(0xFF222222)
                    )

                    Spacer(Modifier.width(6.dp))

                    Text(
                        text = AuthData.memberCode,
                        style = medium16pt,
                        color = getColor().brandColor
                    )
                }
            }

            Spacer(Modifier.height(14.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(51.dp)
                    .background(
                        color = Color(0xFFFFFFFF),
                        shape = RoundedCornerShape(topStart = 14.dp, topEnd = 14.dp)
                    )
                    .clickableNoEffect { moveToMyInfoScreen() },
                contentAlignment = Alignment.BottomStart
            ) {
                Text(
                    modifier = Modifier.padding(start = 14.dp, bottom = 12.dp),
                    text = "내 정보 관리",
                    color = Color(0xFF222222),
                    style = medium16pt
                )
            }

            HorizontalDivider(getColor().thinnest)

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(45.dp)
                    .background(
                        color = Color(0xFFFFFFFF),
                    )
                    .clickableNoEffect { moveToLocationFavorite() },
                contentAlignment = Alignment.BottomStart
            ) {
                Text(
                    modifier = Modifier.padding(start = 14.dp, bottom = 12.dp),
                    text = "위치 즐겨찾기",
                    color = Color(0xFF222222),
                    style = medium16pt
                )
            }

            HorizontalDivider(getColor().thinnest)

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(45.dp)
                    .background(
                        color = Color(0xFFFFFFFF),
                    )
                    .clickableNoEffect { moveToFeedBookmarks() },
                contentAlignment = Alignment.BottomStart
            ) {
                Text(
                    modifier = Modifier.padding(start = 14.dp, bottom = 12.dp),
                    text = "피드 책갈피",
                    color = Color(0xFF222222),
                    style = medium16pt
                )
            }

            HorizontalDivider(getColor().thinnest)

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(51.dp)
                    .background(
                        color = Color(0xFFFFFFFF),
                        shape = RoundedCornerShape(bottomStart = 14.dp, bottomEnd = 14.dp)
                    )
                    .clickableNoEffect { moveToMyInfoScreen() },
                contentAlignment = Alignment.TopStart
            ) {
                Text(
                    modifier = Modifier.padding(start = 14.dp, top = 12.dp),
                    text = "내 정보 관리",
                    color = Color(0xFF222222),
                    style = medium16pt
                )
            }

            Spacer(Modifier.size(12.dp))

            // 공지사항 , 1:1 이용문의
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(51.dp)
                    .background(
                        color = Color(0xFFFFFFFF),
                        shape = RoundedCornerShape(topStart = 14.dp, topEnd = 14.dp)
                    )
                    .clickableNoEffect { moveToAccoument() },
                contentAlignment = Alignment.BottomStart
            ) {
                Text(
                    modifier = Modifier.padding(start = 14.dp, bottom = 12.dp),
                    text = "공지사항",
                    color = Color(0xFF222222),
                    style = medium16pt
                )
            }

            HorizontalDivider(getColor().thinnest)

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(51.dp)
                    .background(
                        color = Color(0xFFFFFFFF),
                        shape = RoundedCornerShape(bottomStart = 14.dp, bottomEnd = 14.dp)
                    )
                    .clickableNoEffect { moveToAsk() },
                contentAlignment = Alignment.TopStart
            ) {
                Text(
                    modifier = Modifier.padding(start = 14.dp, top = 12.dp),
                    text = "내 정보 관리",
                    color = Color(0xFF222222),
                    style = medium16pt
                )
            }

            Spacer(Modifier.weight(1f))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .width(64.dp)
                        .height(28.dp)
                        .clickableNoEffect { isWarningDialogShowing.value = true },
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
                    contentDescription = null
                )

                Spacer(Modifier.size(4.dp))

                Box(
                    modifier = Modifier
                        .width(64.dp)
                        .height(28.dp)
                        .clickableNoEffect { moveToBye() },
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

    if (isWarningDialogShowing.value) {
        MyPageWarningDialog(
            onDismissRequest = { isWarningDialogShowing.value = false },
            onConfirm = { signOut(moveToSignInMethodSelectionScreen) }
        )
    }

    if (isPhotoDialogShowing.value) {
        Popup(
            onDismissRequest = { isPhotoDialogShowing.value = false },
            properties = PopupProperties(
                usePlatformDefaultWidth = false,
            )
        ) {
            OnMyWayTheme {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
//                        .background(Color(0x33000000))
                        .clickableNoEffect { isPhotoDialogShowing.value = false },
                    contentAlignment = Alignment.TopCenter
                ) {
                    Row(
                        modifier = Modifier
                            .padding(top = 160.dp)
                            .width(190.dp)
                            .height(38.dp)
                            .background(
                                color = Color(0xFF514675),
                                shape = RoundedCornerShape(10.dp)
                            )
                            .clickableNoEffect { },
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Spacer(Modifier.width(14.dp))

                        Text(
                            text = "사진 보관함",
                            color = Color(0xFFFFFFFF),
                            style = medium14pt
                        )

                        Spacer(Modifier.weight(1f))

                        Image(
                            modifier = Modifier.size(22.dp),
                            painter = painterResource(R.drawable.ic_picture),
                            contentDescription = null
                        )

                        Spacer(Modifier.width(14.dp))
                    }
                }
            }
        }
    }
}



@Composable
fun MyPageWarningDialog(
    onDismissRequest: () -> Unit,
    onConfirm: () -> Unit,
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        ),
    ) {
        OnMyWayTheme {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clickableNoEffect { onDismissRequest() }
            ) {
                Column(
                    modifier = Modifier
                        .padding(top = 160.dp)
                        .fillMaxWidth()
                        .height(164.dp)
                        .padding(start = 15.dp, end = 15.dp)
                        .background(
                            color = Color(0xFF333333),
                            shape = RoundedCornerShape(12.dp)
                        )
                        .clickableNoEffect {}
                        .padding(start = 10.dp, end = 10.dp)
                ) {
                    Spacer(Modifier.height(20.dp))

                    Text(
                        modifier = Modifier.padding(6.dp, 4.dp, 6.dp, 4.dp),
                        text = "로그아웃",
                        style = medium18pt,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFFFFFFF)
                    )

                    Spacer(Modifier.height(8.dp))

                    Text(
                        modifier = Modifier.padding(6.dp, 4.dp, 6.dp, 4.dp),
                        text = "로그아웃을 진행하시겠습니까?",
                        style = medium14pt,
                        color = Color(0xFFA0A0A0)
                    )

                    Spacer(Modifier.height(24.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 24.dp),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(start = 8.dp, top = 4.dp, 6.dp, 4.dp)
                                .clickableNoEffect { onDismissRequest() },
                            text = "취소",
                            style = medium14pt,
                            color = Color.White
                        )

                        Spacer(Modifier.width(10.dp))

                        Text(
                            modifier = Modifier
                                .padding(start = 8.dp, top = 4.dp, 6.dp, 4.dp)
                                .clickableNoEffect { onConfirm() },
                            text = "확인",
                            style = medium14pt,
                            color = Color(0xFFE09EFF),
                        )
                    }
                }
            }
        }
    }
}
