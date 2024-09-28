package com.whereareyounow.ui.main.friend

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import com.whereareyounow.R
import com.whereareyounow.data.globalvalue.TOP_BAR_HEIGHT
import com.whereareyounow.domain.entity.schedule.Friend
import com.whereareyounow.ui.main.home.FirstIconBadge
import com.whereareyounow.ui.main.mypage.byebye.Gap
import com.whereareyounow.ui.theme.WhereAreYouTheme
import com.whereareyounow.ui.theme.medium14pt
import com.whereareyounow.ui.theme.medium20pt
import com.whereareyounow.ui.theme.nanumSquareNeo
import com.whereareyounow.util.clickableNoEffect
import com.whereareyounow.util.popupmenu.CustomPopup
import com.whereareyounow.util.popupmenu.PopupPosition
import com.whereareyounow.util.popupmenu.PopupState

@Composable
fun FriendScreen(
    paddingValues: PaddingValues,
    moveToAddFriendScreen: () -> Unit,
    moveToAddGroupScreen: () -> Unit,
    moveToDetailProfileScreen: (String, String) -> Unit,
    viewModel: FriendViewModel = hiltViewModel()
) {
    val friendsList = viewModel.friendsList
    FriendScreen(
        paddingValues = paddingValues,
        friendsList = friendsList,
        moveToAddFriendScreen = moveToAddFriendScreen,
        moveToAddGroupScreen = moveToAddGroupScreen,
        moveToDetailProfileScreen = moveToDetailProfileScreen
    )
}

@Composable
private fun FriendScreen(
    paddingValues: PaddingValues,
    friendsList: List<Friend>,
    moveToAddFriendScreen: () -> Unit,
    moveToAddGroupScreen: () -> Unit,
    moveToDetailProfileScreen: (String, String) -> Unit
) {
    val isFriendPage = remember { mutableStateOf(false) }
//    val starExpand = remember { mutableStateOf(false) }
//    val friendExpand = remember { mutableStateOf(false) }

    // 이거 true 되면 프로필 사진 보이게
    val upProfileBoolean = remember { mutableStateOf(false) }
    FriendScreenTopBar(
        isFriendPage = isFriendPage,

        moveToAddFriendScreen = moveToAddFriendScreen,
        moveToAddGroupScreen = moveToAddGroupScreen
    )
    if (isFriendPage.value) {
        FriendContent(
            paddingValues = paddingValues,
            friendsList = friendsList,
            upProfileBoolean = upProfileBoolean,
            upProfile = moveToDetailProfileScreen
        )
    } else {
        GroupContent()
    }

//    if(upProfileBoolean.value) {
//        moveToDetailProfileScreen()
//    }

}

@Composable
fun FriendContent(
    paddingValues: PaddingValues,
    friendsList: List<Friend>,
    upProfileBoolean: MutableState<Boolean>,
    upProfile: (String, String) -> Unit
) {
//    val isFriendPage = remember { mutableStateOf(true) }
    val starExpand = remember { mutableStateOf(false) }
    val friendExpand = remember { mutableStateOf(false) }

//    if(upProfileBoolean.value) {
//        Box(
//            modifier = Modifier.fillMaxSize()
//        ) {
//            Column(
//
//            ) {
//                Spacer(Modifier.size(TOP_BAR_HEIGHT.dp))
//
//                Image(
//                    painter = painterResource(id = R.drawable.ic_x),
//                    contentDescription = "",
//                    modifier = Modifier.padding(start = 20.dp , top = 11.dp),
//                    colorFilter = ColorFilter.tint(color = Color(0XFFEEEEEE))
//                )
//
//
//                Column(
//                    modifier = Modifier.fillMaxSize().padding(top  = 491.dp)
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
//                                .padding(start = 137.5.dp , end = 137.5.dp)
//                                .size(100.dp)
//                                .clip(RoundedCornerShape(18.dp)),
//                            imageModel = { "https://m.segye.com/content/image/2021/11/16/20211116509557.jpg" ?: R.drawable.idle_profile2 },
//                        )
//                    }
//                    Text( ?: "유민혁")
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

    Column(
        modifier = Modifier
            .fillMaxSize(),
//            .padding(top = 18.dp)
//            .padding(paddingValues)
    ) {
        Gap(7)
        MyRow()
        GrayLine()
        Spacer(Modifier.height(10.dp))
        //즐찾친구
        val pinnedFriendList = friendsList.filter {
            it.isPinned
        }
        ClickAleText(
            { starExpand.value = !starExpand.value },
            "즐겨찾기",
            "${pinnedFriendList.size}",
            starExpand
        ) {
            if (starExpand.value) {
                LazyColumn {
                    items(pinnedFriendList.size) {
                        FriendBox(
                            imageUrl = pinnedFriendList[it].profileImgUrl,
                            friendName = pinnedFriendList[it].userId,
                            upProfile = upProfile,
//                            upProfileBoolean = upProfileBoolean
                        )
                    }
                }
            }
        }

        Spacer(Modifier.height(10.dp))
        GrayLine()
        Spacer(Modifier.height(10.dp))
        ClickAleText(
            { friendExpand.value = !friendExpand.value },
            "친구",
            "${friendsList.size}",
            friendExpand
        ) {
            if (friendExpand.value) {
                LazyColumn {
                    items(friendsList.size) { index ->
                        FriendBox(
                            imageUrl = friendsList[index].profileImgUrl,
                            friendName = friendsList[index].userId,
                            upProfile = upProfile,
//                            upProfileBoolean = upProfileBoolean

                        )
                    }
                }
            }
        }
        Spacer(Modifier.height(10.dp))
        GrayLine()
    }
}


@Composable
fun GroupContent() {

}

@Composable
fun FriendScreenTopBar(
    isFriendPage: MutableState<Boolean>,
    moveToAddFriendScreen: () -> Unit,
    moveToAddGroupScreen: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 6.dp),

//            .height((TOP_BAR_HEIGHT / density).dp), TODO -> 준성님카톡
        verticalAlignment = Alignment.CenterVertically,
    ) {
        val popupState = remember { PopupState(false, PopupPosition.BottomLeft) }
        val friendTextColor = if (isFriendPage.value) Color(0xFF222222) else Color(0xFF666666)
        val feedTextColor = if (isFriendPage.value) Color(0xFF666666) else Color(0xFF222222)
        Text(
            modifier = Modifier.clickable {
                isFriendPage.value = false
            },
            text = "피드",
            style = medium20pt,
            color = feedTextColor
        )
        Spacer(Modifier.width(12.dp))
        Text(
            modifier = Modifier.clickable {
                isFriendPage.value = true

            },
            text = "친구",
            style = medium20pt,
            color = friendTextColor
        )
        Spacer(modifier = Modifier.weight(1f))
        Image(
            painter = painterResource(id = R.drawable.ic_dodbogi),
            contentDescription = "",
            modifier = Modifier.clickableNoEffect {

            }
        )
//        Icon(
//            painter = painterResource(id = R.drawable.search_24px),
//            contentDescription = "",
//            modifier = Modifier
//                .clickable {
//
//                },
//            tint = Color(0xFF6236E9)
//        )
//        Spacer(Modifier.size(2.dp))


//        Icon(
//            painter = painterResource(id = R.drawable.plus),
//            contentDescription = "",
//            modifier = Modifier.clickable {
//                popupState.isVisible = true
//                                          },
//            tint = Color(0xFF6236E9)
//        )
//        Spacer(Modifier.size(2.dp))
        //TODO -> 알림여부에따라서 아이콘보이는거다르게 -> 준성님카톡
//        Icon(
//            painter = painterResource(id = R.drawable.icon_bell),
//            contentDescription = "",
//            modifier = Modifier.clickable {
//
//            },
//            tint = Color(0xFF6236E9)
//        )
//        Spacer(Modifier.size(2.dp))
        Image(
            painter = painterResource(id = R.drawable.ic_bellred),
            contentDescription = ""
        )
//        Spacer(Modifier.size(2.dp))

        Image(
            painter = painterResource(id = R.drawable.ic_plusbrandcolor),
            contentDescription = "",
            modifier = Modifier.clickableNoEffect {
                popupState.isVisible = true

            }
        )

//        FirstIconBadge({
//            //TODO -> 알림버튼누르면뭐뜨는지부교님
//        })
        if (popupState.isVisible) {
            AddIconPopUp(
                popupState,
                moveToAddFriendScreen,
                moveToAddGroupScreen
            )
        }
    }
}


//@Composable
//fun FriendContent(
//    friendsList: List<Friend>
//) {
//    LazyColumn {
//        item {
//            Box(
//                modifier = Modifier.height(40.dp),
//                contentAlignment = Alignment.CenterStart
//            ) {
//                Text(
//                    text = "친구 ${friendsList.size}",
//                    fontSize = 20.sp
//                )
//            }
//        }
//        itemsIndexed(friendsList) { _, friend ->
//            FriendBox(
//                imageUrl = friend.profileImgUrl,
//                friendName = friend.name
//            )
//        }
//    }
//}


// 내사진 로우
@Composable
fun MyRow() {
    val test: FriendViewModel = hiltViewModel()
    val myInfo = test.myInfo.collectAsState().value
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(89.dp)
            .padding(start = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(56.dp)
                .clip(RoundedCornerShape(18.dp))
        ) {
            GlideImage(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(18.dp)),
                imageModel = { myInfo.image ?: R.drawable.idle_profile2 },
            )
        }
        Gap(12)
        Text(myInfo.name, style = medium14pt , color = Color(0xFF222222))
    }
}


// 더하기 누르면 "친구추가" , "친구관리" 양자택일 팝업
@Composable
fun AddIconPopUp(
    popupState: PopupState,
    moveToAddFriendScreen: () -> Unit,
    moveToAddGroupScreen: () -> Unit,
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
                        .height(68.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(color = Color(0xFF7262A8))
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.Start
                    ) {
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
            .background(Color(0xFFDDDDDD))
            .fillMaxWidth()
            .height(1.dp)
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
            .padding(top = 10.dp, bottom = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(50.dp)
                .clip(RoundedCornerShape(16.dp))
        ) {
            GlideImage(
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .size(50.dp)
                    .clickable {
                        upProfile(imageUrl ?: "", friendName)
//                    upProfileBoolean.value = !upProfileBoolean.value
                    },
                imageModel = { imageUrl ?: R.drawable.idle_profile2 },
                imageOptions = ImageOptions(contentScale = ContentScale.Crop)
            )
        }
//        GlideImage(
//            modifier = Modifier
//                .clip(RoundedCornerShape(16.dp))
//                .size(30.dp)
//                .clickable {
//                    upProfile(imageUrl ?: "", friendName)
////                    upProfileBoolean.value = !upProfileBoolean.value
//                },
//            imageModel = { imageUrl ?: R.drawable.idle_profile2 },
//            imageOptions = ImageOptions(contentScale = ContentScale.Crop)
//        )
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
                text1,
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


@Composable
fun sfsfdjslefjsoefin(
    imageUrl: String?,
    friendName: String
) {
    Dialog(
        onDismissRequest = {}
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            Column(

            ) {
                Spacer(Modifier.size(TOP_BAR_HEIGHT.dp))

                Image(
                    painter = painterResource(id = R.drawable.ic_x),
                    contentDescription = "",
                    modifier = Modifier.padding(start = 20.dp, top = 11.dp),
                    colorFilter = ColorFilter.tint(color = Color(0XFFEEEEEE))
                )


                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 491.dp)
                ) {
                    Box(
//                modifier = Modifier.padding(491.dp)
                    ) {
//            GlideImage(
//                imageModel = "https://m.segye.com/content/image/2021/11/16/20211116509557.jpg" ?: painterResource(id = R.drawable.ic_default_profile_image),
//                contentDescription = ""
//
//            )

                        GlideImage(
                            modifier = Modifier
                                .padding(start = 137.5.dp, end = 137.5.dp)
                                .size(100.dp)
                                .clip(RoundedCornerShape(18.dp)),
                            imageModel = { imageUrl ?: R.drawable.idle_profile2 },
                        )
                    }
                    Text(friendName ?: "유민혁")


                }



                Text("sfsss")

            }
        }
    }
}