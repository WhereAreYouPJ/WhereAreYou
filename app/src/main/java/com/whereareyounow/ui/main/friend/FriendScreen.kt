package com.whereareyounow.ui.main.friend

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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import com.whereareyounow.R
import com.whereareyounow.data.cached.FriendList
import com.whereareyounow.ui.main.friend.feed.FeedScreen
import com.whereareyounow.ui.main.friend.model.FriendModel
import com.whereareyounow.ui.main.mypage.byebye.Gap
import com.whereareyounow.ui.theme.getColor
import com.whereareyounow.ui.theme.medium14pt
import com.whereareyounow.ui.theme.medium20pt
import com.whereareyounow.util.clickableNoEffect
import com.whereareyounow.util.popupmenu.CustomPopup
import com.whereareyounow.util.popupmenu.PopupPosition
import com.whereareyounow.util.popupmenu.PopupState

@Composable
fun FriendScreen(
    moveToAddFriendScreen: () -> Unit,
    moveToAddGroupScreen: () -> Unit,
    moveToAddFeedScreen: () -> Unit,
    moveToDetailProfileScreen: (String, String) -> Unit,
    viewModel: FriendViewModel = hiltViewModel()
) {
    val friendsList = viewModel.friendsList.collectAsState().value
    FriendScreen(
        friendsList = friendsList,
        moveToAddFriendScreen = moveToAddFriendScreen,
        moveToAddGroupScreen = moveToAddGroupScreen,
        moveToAddFeedScreen = moveToAddFeedScreen,
        moveToDetailProfileScreen = moveToDetailProfileScreen
    )
}

@Composable
private fun FriendScreen(
    friendsList: List<FriendModel>,
    moveToAddFriendScreen: () -> Unit,
    moveToAddGroupScreen: () -> Unit,
    moveToAddFeedScreen: () -> Unit,
    moveToDetailProfileScreen: (String, String) -> Unit
) {
    val isFriendPage = remember { mutableStateOf(false) }

    // 이거 true 되면 프로필 사진 보이게
    val upProfileBoolean = remember { mutableStateOf(false) }
    FriendScreenTopBar(
        isFriendPage = isFriendPage,
        moveToAddFriendScreen = moveToAddFriendScreen,
        moveToAddGroupScreen = moveToAddGroupScreen,
        moveToAddFeedScreen = moveToAddFeedScreen
    )
    if (isFriendPage.value) {
        FriendContent(
            friendsList = friendsList,
            upProfileBoolean = upProfileBoolean,
            upProfile = moveToDetailProfileScreen
        )
    } else {
        FeedScreen()
    }
}

@Composable
fun FriendContent(
    friendsList: List<FriendModel>,
    upProfileBoolean: MutableState<Boolean>,
    upProfile: (String, String) -> Unit
) {
    val starExpand = remember { mutableStateOf(false) }
    val friendExpand = remember { mutableStateOf(false) }
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Spacer(Modifier.height(10.dp))

        MyProfileRow()

        GrayLine()

        Spacer(Modifier.height(10.dp))

        LazyColumn {
            item {
                ClickAleText(
                    { starExpand.value = !starExpand.value },
                    "즐겨찾기",
                    "${FriendList.list.filter { it.isFavorite }.size}",
                    starExpand
                ) {
                    if (starExpand.value) {
                        FriendList.list.filter { it.isFavorite }.forEachIndexed { idx, item ->
                            FriendBox(
                                imageUrl = item.profileImgUrl,
                                friendName = item.name,
                                upProfile = upProfile,
                            )
                        }
                    }
                }

                Gap(10)
                GrayLine()
                Gap(10)

                ClickAleText(
                    { friendExpand.value = !friendExpand.value },
                    "친구",
                    "${friendsList.size}",
                    friendExpand
                ) {
                    if (friendExpand.value) {
                        FriendList.list.filter { !it.isFavorite }.forEachIndexed { idx, item ->
                            FriendBox(
                                imageUrl = item.profileImgUrl,
                                friendName = item.name,
                                upProfile = upProfile,
                            )
                        }
                    }
                }

                Gap(10)
            }
        }
    }
}

@Composable
fun FriendScreenTopBar(
    isFriendPage: MutableState<Boolean>,
    moveToAddFriendScreen: () -> Unit,
    moveToAddGroupScreen: () -> Unit,
    moveToAddFeedScreen: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        val popupState = remember { PopupState(false, PopupPosition.BottomLeft) }
        val friendTextColor = if(isFriendPage.value) Color(0xFF222222) else Color(0xFF666666)
        val feedTextColor = if(isFriendPage.value) Color(0xFF666666) else Color(0xFF222222)

        Text(
            modifier = Modifier
                .padding(start = 6.dp, top = 4.dp, end = 6.dp, bottom = 4.dp)
                .clickableNoEffect {
                    isFriendPage.value = false
                },
            text = "피드",
            style = medium20pt,
            color = feedTextColor
        )
        Text(
            modifier = Modifier
                .padding(start = 6.dp, top = 4.dp, end = 6.dp, bottom = 4.dp)
                .clickableNoEffect {
                    isFriendPage.value = true
                },
            text = "친구",
            style = medium20pt,
            color = friendTextColor
        )
        Spacer(Modifier.width(12.dp))
        Spacer(modifier = Modifier.weight(1f))
        Image(
            painter = painterResource(id = R.drawable.ic_dodbogi),
            contentDescription = "",
            modifier = Modifier.clickableNoEffect {

            }
        )

        Image(
            painter = painterResource(id = R.drawable.ic_bellred),
            contentDescription = ""
        )

        Image(
            painter = painterResource(id = R.drawable.ic_plusbrandcolor),
            contentDescription = "",
            modifier = Modifier.clickableNoEffect {
                popupState.isVisible = true

            }
        )

        if (popupState.isVisible) {
            AddIconPopUp(
                isFriendPage = isFriendPage.value,
                popupState,
                moveToAddFriendScreen,
                moveToAddGroupScreen,
                moveToAddFeedScreen = moveToAddFeedScreen
            )
        }
    }
}

// 내사진 로우
@Composable
fun MyProfileRow() {
    val test: FriendViewModel = hiltViewModel()
    val myInfo = test.myInfo.collectAsState().value
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .padding(start = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        GlideImage(
            modifier = Modifier
                .size(56.dp)
                .clip(RoundedCornerShape(18.dp)),
            imageModel = { myInfo.image ?: R.drawable.ic_profile },
        )

        Spacer(Modifier.width(6.dp))

        Text(
            modifier = Modifier.padding(6.dp, 4.dp, 6.dp, 4.dp),
            text = myInfo.name,
            style = medium14pt,
            color = Color(0xFF222222)
        )
    }
}

// 더하기 누르면 "친구추가" , "친구관리" 양자택일 팝업
@Composable
fun AddIconPopUp(
    isFriendPage: Boolean,
    popupState: PopupState,
    moveToAddFriendScreen: () -> Unit,
    moveToAddGroupScreen: () -> Unit,
    moveToAddFeedScreen: () -> Unit,
) {
    val density = LocalDensity.current.density
    Box(
        contentAlignment = Alignment.CenterEnd,
        modifier = Modifier.padding(top = 30.dp)
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
                        .height((if (isFriendPage) 68 else 34).dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(color = Color(0xFF7262A8))
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.Start
                    ) {
                        if (isFriendPage) {
                            Text(
                                modifier = Modifier
                                    .padding(
                                        start = 14.dp,
                                        top = 8.dp,
                                        bottom = 6.dp
                                    )
                                    .clickable {
                                        popupState.isVisible = false
                                        moveToAddFriendScreen()
                                    },
                                text = "친구 추가",
                                style = medium14pt,
                                color = Color.White
                            )
                            Box(
                                modifier = Modifier
                                    .background(Color.Gray)
                                    .height(1.dp)
                                    .fillMaxWidth()
                            )
                            Text(
                                modifier = Modifier
                                    .padding(
                                        start = 14.dp,
                                        bottom = 10.dp,
                                        top = 4.dp
                                    )
                                    .clickable {
                                        popupState.isVisible = false
                                        moveToAddGroupScreen()
                                    },
                                text = "친구 관리",
                                style = medium14pt,
                                color = Color.White
                            )
                        } else {
                            Text(
                                modifier = Modifier
                                    .padding(
                                        start = 14.dp,
                                        top = 8.dp,
                                        bottom = 6.dp
                                    )
                                    .clickable {
                                        popupState.isVisible = false
                                        moveToAddFeedScreen()
                                    },
                                text = "새 피드 작성",
                                style = medium14pt,
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }
    }
}


////////////////////////////////////////////////////////////////////////////////////////////////////

// 1dp 짜리 회색선
@Composable
fun GrayLine() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(getColor().thinnest)
    )
}

// 친구 로우
@Composable
fun FriendBox(
    imageUrl: String?,
    friendName: String,
    upProfile: (String, String) -> Unit,
//    upProfileBoolean : MutableState<Boolean>
    // 친구사진전달하기
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(62.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        GlideImage(
            modifier = Modifier
                .size(50.dp)
                .clip(RoundedCornerShape(16.dp))
                .clickable {
                    upProfile(imageUrl ?: "", friendName)
//                    upProfileBoolean.value = !upProfileBoolean.value
                },
            imageModel = { imageUrl ?: R.drawable.ic_profile },
            imageOptions = ImageOptions(contentScale = ContentScale.Crop)
        )

        Gap(12)

        Text(
            text = friendName,
            color = Color(0xFF222222),
            style = medium14pt
        )
    }
}

// 누르면 즐겨찾기랑 친구 창 확장
@Composable
fun ClickAleText(
    expander: () -> Unit,
    text1: String,
    text2: String,
    expandedState: MutableState<Boolean>,
    content: @Composable () -> Unit
) {
    val clickedIndication = remember { MutableInteractionSource() }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(
                    indication = null,
                    interactionSource = remember { clickedIndication }
                ) {
                    expander()
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.padding(start = 4.dp),
                text = text1,
                style = medium14pt,
                color = Color(0xFF222222)
            )
            Gap(8)
            Text(
                text2,
                style = medium14pt,
                color = Color(0xFFC9C9C9)
            )
        }
        if (expandedState.value) {
            content()
        }
    }
}


//@Composable
//fun sfsfdjslefjsoefin(
//    imageUrl: String?,
//    friendName: String
//) {
//    Dialog(
//        onDismissRequest = {}
//    ) {
//        Box(
//            modifier = Modifier
//                .fillMaxSize()
//                .background(Color.White)
//        ) {
//            Column(
//
//            ) {
//                Spacer(Modifier.size(TOP_BAR_HEIGHT.dp))
//
//                Image(
//                    painter = painterResource(id = R.drawable.ic_x),
//                    contentDescription = "",
//                    modifier = Modifier.padding(start = 20.dp, top = 11.dp),
//                    colorFilter = ColorFilter.tint(color = Color(0XFFEEEEEE))
//                )
//
//
//                Column(
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .padding(top = 491.dp)
//                ) {
//                    Box(
////                modifier = Modifier.padding(491.dp)
//                    ) {
////            GlideImage(
////                imageModel = "https://m.segye.com/content/image/2021/11/16/20211116509557.jpg" ?: painterResource(id = R.drawable.ic_default_profile_image),
////                contentDescription = ""
////
////            )
//
//                        GlideImage(
//                            modifier = Modifier
//                                .padding(start = 137.5.dp, end = 137.5.dp)
//                                .size(100.dp)
//                                .clip(RoundedCornerShape(18.dp)),
//                            imageModel = { imageUrl ?: R.drawable.idle_profile2 },
//                        )
//                    }
//                    Text(friendName ?: "유민혁")
//
//
//                }
//
//
//
//                Text("sfsss")
//
//            }
//        }
//    }
//}