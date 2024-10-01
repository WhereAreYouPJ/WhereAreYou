package com.whereareyounow.ui.main.schedule.scheduleedit

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
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
import com.whereareyounow.ui.component.CustomSurface
import com.whereareyounow.ui.component.CustomTopBar
import com.whereareyounow.ui.component.RoundedCornerButton
import com.whereareyounow.ui.theme.OnMyWayTheme
import com.whereareyounow.ui.theme.getColor
import com.whereareyounow.ui.theme.medium14pt
import com.whereareyounow.ui.theme.notoSanskr
import com.whereareyounow.util.clickableNoEffect

@Composable
fun FriendsListScreen(
    moveToBackScreen: () -> Unit,
    scheduleEditViewModel: ScheduleEditViewModel,
    friendsListScreenViewModel: FriendsListScreenViewModel = hiltViewModel(),
) {
    LaunchedEffect(Unit) {
        friendsListScreenViewModel.initialize(scheduleEditViewModel.uiState.value.selectedFriendsList.map { it.memberSeq })
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
    initialize: (List<Int>) -> Unit,
    updateFriendsList: (List<Int>) -> Unit,
    selectedFriendsList: List<Friend>,
    selectFriend: (Pair<Friend, Boolean>) -> Unit,
    inputFriendName: String,
    updateInputText: (String) -> Unit,
    searchedFriendsList: List<Pair<Friend, Boolean>>,
    moveToBackScreen: () -> Unit
) {
    CustomSurface {
        FriendsListScreenTopBar(
            moveToBackScreen = moveToBackScreen
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 20.dp, end = 20.dp)
        ) {

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

            RoundedCornerButton(
                onClick = {
                    updateFriendsList(selectedFriendsList.map { it.memberSeq })
                    moveToBackScreen()
                }
            ) {
                Text(
                    text = "확인",
                    color = Color(0xFFFFFFFF),
                    fontFamily = notoSanskr,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            }

            Spacer(Modifier.height(20.dp))
        }
    }
}

@Composable
fun FriendsListScreenTopBar(
    moveToBackScreen: () -> Unit
) {
    CustomTopBar(
        title = "친구 선택",
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
        textStyle = medium14pt.copy(color = Color(0xFF222222)),
        singleLine = true,
        decorationBox = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        border = BorderStroke(
                            width = 1.dp,
                            color = getColor().dark
                        ),
                        shape = RoundedCornerShape(6.dp)
                    )
                    .padding(start = 8.dp, top = 12.dp, end = 8.dp, bottom = 12.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                it()

                if (inputFriendName == "") {
                    Text(
                        text = "친구 검색",
                        color = Color(0xFFC5C5C5),
                        style = medium14pt
                    )
                }
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
                        .size(56.dp)
                        .clip(RoundedCornerShape(18.dp)),
                    imageModel = { friend.profileImgUrl ?: R.drawable.ic_profile },
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
                    .clickableNoEffect {
                        selectFriend(friend)
                    }
                    .padding(top = 10.dp, bottom = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                GlideImage(
                    modifier = Modifier
                        .size(58.dp)
                        .clip(RoundedCornerShape(18.dp)),
                    imageModel = { friend.first.profileImgUrl ?: R.drawable.ic_profile },
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
                        true -> painterResource(id = R.drawable.ic_check)
                        false -> painterResource(id = R.drawable.ic_uncheck)
                    },
                    contentDescription = null
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun FriendsListScreenPreview() {
    val initialFriendIdsList = listOf<String>()
//    val selectedFriendsList = listOf(
//        Friend(0, "", "가나다"),
//        Friend(0, "", "라마바사")
//    )
//    val searchedFriendsList = listOf(
//        Friend(0, "", "가나다") to true,
//        Friend(0, "", "라마바사") to true,
//        Friend(0, "", "아자차카") to false,
//        Friend(0, "", "타파하") to false,
//    )
//    OnMyWayTheme {
//        FriendsListScreen(
//            initialize = {},
//            updateFriendsList = {},
//            selectedFriendsList = selectedFriendsList,
//            selectFriend = {},
//            inputFriendName = "피그마",
//            updateInputText = {},
//            searchedFriendsList = searchedFriendsList,
//            moveToBackScreen = {}
//        )
//    }
}