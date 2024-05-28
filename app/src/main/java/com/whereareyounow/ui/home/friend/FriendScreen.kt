package com.whereareyounow.ui.home.friend

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.MutableState
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
import com.whereareyounow.ui.theme.WhereAreYouTheme
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
        friendsList = friendsList,
        moveToAddFriendScreen = moveToAddFriendScreen,
        moveToAddGroupScreen = moveToAddGroupScreen
    )
}

@Composable
fun FriendScreen(
    friendsList: List<Friend>,
    moveToAddFriendScreen: () -> Unit,
    moveToAddGroupScreen: () -> Unit,
) {
    val isFriendPage = remember { mutableStateOf(true) }
    Column(
        modifier = Modifier
            .padding(start = 20.dp, end = 20.dp)
            .fillMaxSize()
            .background(Color(0xFFFAFAFA))
    ) {
        // 상단바
        FriendScreenTopBar(
            isFriendPage = isFriendPage,
            moveToAddFriendScreen = moveToAddFriendScreen,
            moveToAddGroupScreen = moveToAddGroupScreen
        )
        if (isFriendPage.value) {
            FriendContent(friendsList)
        } else {
            GroupContent()
        }
    }
}


@Composable
fun FriendScreenTopBar(
    isFriendPage: MutableState<Boolean>,
    moveToAddFriendScreen: () -> Unit,
    moveToAddGroupScreen: () -> Unit,
) {
    val density = LocalDensity.current.density
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height((TOP_BAR_HEIGHT / density).dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        val popupState = remember { PopupState(false, PopupPosition.BottomLeft) }
        Text(
//            modifier = Modifier.clickable { isFriendPage.value = true },
            text = "친구",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(Modifier.width(20.dp))
//        Text(
//            modifier = Modifier.clickable { isFriendPage.value = false },
//            text = "그룹",
//            fontSize = 30.sp,
//            fontWeight = FontWeight.Bold
//        )
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.CenterEnd
        ) {
            Box {
                CustomPopup(
                    popupState = popupState,
                    onDismissRequest = { popupState.isVisible = false }
                ) {
                    CompositionLocalProvider(LocalDensity provides Density(density, fontScale = 1f)) {
                        Row(
                            modifier = Modifier
                                .clip(RoundedCornerShape(50))
                                .height(IntrinsicSize.Min)
                        ) {
                            Box(
                                modifier = Modifier
                                    .background(color = Color(0xFFF9D889))
                                    .clickable {
                                        popupState.isVisible = false
                                        moveToAddFriendScreen()
                                    },
                            ) {
                                Text(
                                    modifier = Modifier.padding(10.dp),
                                    text = "친구추가",
                                    fontFamily = nanumSquareNeo
                                )
                            }
//                            Box(
//                                modifier = Modifier
//                                    .width(1.dp)
//                                    .fillMaxHeight()
//                                    .background(Color(0xFFF9D889))
//                                    .padding(top = 10.dp, bottom = 10.dp)
//                            ) {
//                                Box(
//                                    modifier = Modifier
//                                        .fillMaxSize()
//                                        .background(Color(0xFF000000))
//                                )
//                            }
//                            Box(
//                                modifier = Modifier
//                                    .clip(RoundedCornerShape(topEnd = 50f, bottomEnd = 50f))
//                                    .background(Color(0xFFF9D889))
//                                    .clickable {
//                                        popupState.isVisible = false
//                                        moveToAddGroupScreen()
//                                    }
//                            ) {
//                                Text(
//                                    modifier = Modifier.padding(10.dp),
//                                    text = "그룹추가",
//                                    fontFamily = nanumSquareNeo
//                                )
//                            }
                        }
                    }
                }
                Image(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .clickable { popupState.isVisible = true }
                        .padding(6.dp),
                    painter = painterResource(id = R.drawable.baseline_add_circle_outline_24),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(color = Color(0xFF545454))
                )
            }
        }
    }
}

@Composable
fun FriendContent(
    friendsList: List<Friend>
) {
    LazyColumn {
        item {
            Box(
                modifier = Modifier.height(40.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    text = "친구 ${friendsList.size}",
                    fontSize = 20.sp
                )
            }
        }
        itemsIndexed(friendsList) { _, friend ->
            FriendBox(
                imageUrl = friend.profileImgUrl,
                friendName = friend.name
            )
        }
    }
}

@Composable
fun FriendBox(
    imageUrl: String?,
    friendName: String
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
                .clip(RoundedCornerShape(50)),
            imageModel = { imageUrl ?: R.drawable.idle_profile2 },
            imageOptions = ImageOptions(contentScale = ContentScale.Crop,)
        )
        Spacer(Modifier.width(10.dp))
        Text(
            text = friendName,
            fontSize = 20.sp
        )
    }
}

@Composable
fun GroupContent() {

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun FriendScreenPreview() {
    val friendsList = listOf(
        Friend(0, "", "name1"),
        Friend(0, "", "name2"),
        Friend(0, "", "name3"),
        Friend(0, "", "name4"),
    )
    WhereAreYouTheme {
        FriendScreen(
            friendsList = friendsList,
            moveToAddFriendScreen = {  },
            moveToAddGroupScreen = {  }
        )
    }
}