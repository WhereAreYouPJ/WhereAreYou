package com.whereareyounow.ui.main.schedule.scheduleedit

import androidx.activity.compose.BackHandler
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import com.whereareyounow.R
import com.whereareyounow.domain.entity.schedule.Friend
import com.whereareyounow.ui.component.CustomTopBar
import com.whereareyounow.ui.theme.OnMyWayTheme

@Composable
fun FriendsListScreen(
    moveToBackScreen: () -> Unit,
    scheduleEditViewModel: ScheduleEditViewModel,
    friendsListScreenViewModel: FriendsListScreenViewModel = hiltViewModel(),
) {
    LaunchedEffect(Unit) {
        friendsListScreenViewModel.initialize(scheduleEditViewModel.uiState.value.selectedFriendsList.map { it.memberId })
    }
    val selectedFriendsList = friendsListScreenViewModel.selectedFriendsList
    val inputFriendName = friendsListScreenViewModel.inputText.collectAsState().value
    val searchedFriendsList = friendsListScreenViewModel.searchedFriendsList
    FriendsListScreen(
        initialize = friendsListScreenViewModel::initialize,
        updateFriendsList = scheduleEditViewModel::updateSelectedFriendsList,
        selectedFriendsList = selectedFriendsList,
        selectFriend = friendsListScreenViewModel::selectFriend,
        inputFriendName = inputFriendName,
        updateInputText = friendsListScreenViewModel::updateInputText,
        searchedFriendsList = searchedFriendsList,
        moveToBackScreen = moveToBackScreen
    )
}

@Composable
private fun FriendsListScreen(
    initialize: (List<String>) -> Unit,
    updateFriendsList: (List<String>) -> Unit,
    selectedFriendsList: List<Friend>,
    selectFriend: (Pair<Friend, Boolean>) -> Unit,
    inputFriendName: String,
    updateInputText: (String) -> Unit,
    searchedFriendsList: List<Pair<Friend, Boolean>>,
    moveToBackScreen: () -> Unit
) {
    BackHandler {
        moveToBackScreen()
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 20.dp, end = 20.dp)
    ) {
        // 상단바
        FriendsListScreenTopBar(
            moveToBackScreen = moveToBackScreen
        )

        Spacer(Modifier.height(10.dp))

        // 추가된 친구 목록
        AddedFriendsList(selectedFriendsList)

        Spacer(Modifier.height(10.dp))

        // 친구 검색창
        FriendSearchTextField(
            inputFriendName = inputFriendName,
            updateInputText = updateInputText
        )

        Spacer(Modifier.height(20.dp))

        // 검색된 친구 목록
        SearchedFriendsList(
            searchedFriendsList = searchedFriendsList,
            selectFriend = selectFriend
        )

        Spacer(Modifier.weight(1f))

//        RoundedCornerButton(
//            text = "완료",
//            onClick = {
//                updateFriendsList(selectedFriendsList.map { it.memberId })
//                moveToBackScreen()
//            }
//        )

        Spacer(Modifier.height(20.dp))
    }
}

@Composable
fun FriendsListScreenTopBar(
    moveToBackScreen: () -> Unit
) {
    CustomTopBar(
        title = "친구선택",
        onBackButtonClicked = {
            moveToBackScreen()
        }
    )
}

@Composable
fun FriendSearchTextField(
    inputFriendName: String,
    updateInputText: (String) -> Unit,
) {
    BasicTextField(
        value = inputFriendName,
        onValueChange = updateInputText,
        textStyle = TextStyle(fontSize = 16.sp),
        singleLine = true,
        decorationBox = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        border = BorderStroke(
                            width = 1.dp,
                            color = Color(0xFF9B99AB)
                        ),
                        shape = RoundedCornerShape(50)
                    )
                    .padding(start = 20.dp, top = 10.dp, end = 20.dp, bottom = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.search_24px),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(color = Color(0xFF9B99AB))
                )
                Spacer(Modifier.width(10.dp))
                it()
            }
        }
    )
}

@Composable
fun AddedFriendsList(
    selectedFriendsList: List<Friend>
) {
    LazyRow(
        modifier = Modifier
            .animateContentSize { _, _ ->  }
    ) {
        itemsIndexed(selectedFriendsList) { _, friend ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                GlideImage(
                    modifier = Modifier
                        .size(30.dp)
                        .clip(RoundedCornerShape(50)),
                    imageModel = { friend.profileImgUrl ?: R.drawable.idle_profile },
                    imageOptions = ImageOptions(contentScale = ContentScale.Crop)
                )
                Text(
                    text = friend.name
                )
            }
            Spacer(Modifier.width(10.dp))
        }
    }
}

@Composable
fun ColumnScope.SearchedFriendsList(
    searchedFriendsList: List<Pair<Friend, Boolean>>,
    selectFriend: (Pair<Friend, Boolean>) -> Unit
) {
    LazyColumn(
        modifier = Modifier.weight(1f)
    ) {
        itemsIndexed(searchedFriendsList) { _, friend ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        selectFriend(friend)
                    }
                    .padding(top = 10.dp, bottom = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                GlideImage(
                    modifier = Modifier
                        .size(30.dp)
                        .clip(RoundedCornerShape(50)),
                    imageModel = { friend.first.profileImgUrl ?: R.drawable.idle_profile },
                    imageOptions = ImageOptions(contentScale = ContentScale.Crop)
                )
                Spacer(Modifier.width(10.dp))
                Text(
                    text = friend.first.name,
                    color = Color(0xFF4B4B4B),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
                Spacer(Modifier.weight(1f))
                Image(
                    modifier = Modifier
                        .size(26.dp),
                    painter = when (friend.second) {
                        true -> painterResource(id = R.drawable.check_yellow)
                        false -> painterResource(id = R.drawable.radio_unchecked)
                    },
                    contentDescription = null
                )
            }
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(0.5.dp)
                    .background(
                        color = Color(0xFFAAAAAA)
                    )
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun FriendsListScreenPreview() {
    val initialFriendIdsList = listOf<String>()
    val selectedFriendsList = listOf(
        Friend(0, "", "가나다"),
        Friend(0, "", "라마바사")
    )
    val searchedFriendsList = listOf(
        Friend(0, "", "가나다") to true,
        Friend(0, "", "라마바사") to true,
        Friend(0, "", "아자차카") to false,
        Friend(0, "", "타파하") to false,
    )
    OnMyWayTheme {
        FriendsListScreen(
            initialize = {},
            updateFriendsList = {},
            selectedFriendsList = selectedFriendsList,
            selectFriend = {},
            inputFriendName = "피그마",
            updateInputText = {},
            searchedFriendsList = searchedFriendsList,
            moveToBackScreen = {}
        )
    }
}