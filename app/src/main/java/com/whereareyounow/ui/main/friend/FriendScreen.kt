package com.whereareyounow.ui.main.friend

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
import androidx.hilt.navigation.compose.hiltViewModel
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import com.whereareyounow.R
import com.whereareyounow.data.globalvalue.TOP_BAR_HEIGHT
import com.whereareyounow.domain.entity.schedule.Friend
import com.whereareyounow.ui.main.home.FirstIconBadge
import com.whereareyounow.ui.theme.WhereAreYouTheme
import com.whereareyounow.ui.theme.medium14pt
import com.whereareyounow.ui.theme.medium20pt
import com.whereareyounow.ui.theme.nanumSquareNeo
import com.whereareyounow.util.popupmenu.CustomPopup
import com.whereareyounow.util.popupmenu.PopupPosition
import com.whereareyounow.util.popupmenu.PopupState

@Composable
fun FriendScreen(
    paddingValues: PaddingValues,
    moveToAddFriendScreen: () -> Unit,
    moveToAddGroupScreen: () -> Unit,
    viewModel: FriendViewModel = hiltViewModel()
) {
    val friendsList = viewModel.friendsList
    FriendScreen(
        paddingValues = paddingValues,
        friendsList = friendsList,
        moveToAddFriendScreen = moveToAddFriendScreen,
        moveToAddGroupScreen = moveToAddGroupScreen
    )
}

@Composable
fun FriendScreen(
    paddingValues: PaddingValues,
    friendsList: List<Friend>,
    moveToAddFriendScreen: () -> Unit,
    moveToAddGroupScreen: () -> Unit,

) {
    val isFriendPage = remember { mutableStateOf(true) }
//    val starExpand = remember { mutableStateOf(false) }
//    val friendExpand = remember { mutableStateOf(false) }
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
            upProfile = {
                // 누르면 프로필사진보이게
                upProfileBoolean.value = true
            }
        )
    } else {
        GroupContent()
    }

}

@Composable
fun FriendContent(
    paddingValues: PaddingValues,
    friendsList: List<Friend>,
    upProfileBoolean : MutableState<Boolean>,
    upProfile : () -> Unit
) {
//    val isFriendPage = remember { mutableStateOf(true) }
    val starExpand = remember { mutableStateOf(false) }
    val friendExpand = remember { mutableStateOf(false) }

    if(upProfileBoolean.value) {

    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        Spacer(Modifier.height(6.dp))
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
                            upProfile = upProfile
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
                            upProfile = upProfile

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
        val friendTextColor = if(isFriendPage.value) Color(0xFF222222) else Color(0xFF666666)
        val feedTextColor = if(isFriendPage.value) Color(0xFF666666) else Color(0xFF222222)
        Text(
            modifier = Modifier.clickable {
                isFriendPage.value = true
            },
            text = "친구",
            style = medium20pt,
            color = friendTextColor
        )
        Spacer(Modifier.width(4.dp))
        Text(
            modifier = Modifier.clickable {
                isFriendPage.value = false
            },
            text = "피드",
            style = medium20pt,
            color = feedTextColor
        )
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            painter = painterResource(id = R.drawable.search_24px),
            contentDescription = "",
            modifier = Modifier.clickable {

            },
            tint = Color(0xFF6236E9)
        )
        Spacer(Modifier.size(2.dp))
        Icon(
            painter = painterResource(id = R.drawable.plus),
            contentDescription = "",
            modifier = Modifier.clickable {
                popupState.isVisible = true
                                          },
            tint = Color(0xFF6236E9)
        )
        Spacer(Modifier.size(2.dp))
        //TODO -> 알림여부에따라서 아이콘보이는거다르게 -> 준성님카톡
        Icon(
            painter = painterResource(id = R.drawable.icon_bell),
            contentDescription = "",
            modifier = Modifier.clickable {

            },
            tint = Color(0xFF6236E9)
        )
        Spacer(Modifier.size(2.dp))
        FirstIconBadge({
            //TODO -> 알림버튼누르면뭐뜨는지부교님
        })
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

@Composable
fun FriendBox(
    imageUrl: String?,
    friendName: String,
    upProfile : () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp, bottom = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        GlideImage(
            modifier = Modifier
                .size(30.dp)
                .clip(RoundedCornerShape(50))
                .clickable {
                    upProfile()
                },
            imageModel = { imageUrl ?: R.drawable.idle_profile2 },
            imageOptions = ImageOptions(contentScale = ContentScale.Crop)
        )
        Spacer(Modifier.width(10.dp))
        Text(
            text = friendName,
            fontSize = 20.sp
        )
    }
}

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
        Spacer(Modifier.size(6.dp))
        Text(myInfo.name, style = medium14pt)
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
        modifier = Modifier.padding(top = 45.dp)
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
                            text = "친구추가",
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
                            text = "친구관리",
                            style = medium14pt,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}


// 1dp 짜리 회색선
@Composable
fun GrayLine() {
    Box(
        modifier = Modifier
            .background(Color.Gray)
            .fillMaxWidth()
            .height(1.dp)
    )
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
                style = medium14pt
            )
            Spacer(Modifier.size(6.dp))
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


//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//private fun FriendScreenPreview() {
//    val friendsList = listOf(
//        Friend(0, "", "name1"),
//        Friend(0, "", "name2"),
//        Friend(0, "", "name3"),
//        Friend(0, "", "name4"),
//    )
//    WhereAreYouTheme {
//        FriendScreen(
//            friendsList = friendsList,
//            moveToAddFriendScreen = { },
//            moveToAddGroupScreen = { }
//        )
//    }
//}