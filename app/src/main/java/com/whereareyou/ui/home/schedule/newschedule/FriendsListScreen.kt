package com.whereareyou.ui.home.schedule.newschedule

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.whereareyou.R
import com.whereareyou.data.GlobalValue
import com.whereareyou.domain.entity.schedule.Friend

@Composable
fun FriendsListScreen(
    updateFriendsList: (List<Friend>) -> Unit,
    moveToNewScheduleScreen: () -> Unit,
    viewModel: FriendsListScreenViewModel = hiltViewModel()
) {
    BackHandler() {
        moveToNewScheduleScreen()
    }
    val density = LocalDensity.current
    val selectedFriendsList = viewModel.selectedFriendsList.collectAsState().value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 20.dp, end = 20.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height((GlobalValue.topAppBarHeight / density.density).dp)
                .background(
                    color = Color.Yellow
                ),
            contentAlignment = Alignment.Center
        ) {
            Image(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .clickable {
                        moveToNewScheduleScreen()
                    },
                painter = painterResource(id = R.drawable.baseline_arrow_back_24),
                contentDescription = null
            )
            Text(
                text = "일정"
            )
            Text(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .clickable {
                        updateFriendsList(selectedFriendsList)
                        moveToNewScheduleScreen()
                    },
                text = "완료"
            )
        }
        BasicTextField(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = Color.Cyan
                ),
            value = viewModel.inputText.collectAsState().value,
            onValueChange = {
                viewModel.updateInputText(it)
            },
            textStyle = TextStyle(fontSize = 20.sp)
        )
        LazyRow() {
            itemsIndexed(selectedFriendsList) { index, friend ->
                Text(
                    text = friend.name
                )
            }
        }
        val friendsList = viewModel.friendsListCopy.collectAsState().value
        LazyColumn() {
            itemsIndexed(friendsList) { index, friend ->
                Row(
                    modifier = Modifier
                        .padding(top = 10.dp, bottom = 10.dp)
                        .clickable {
                            viewModel.selectFriend(friend)
                        },
                ) {
                    Text(
                        text = friend.first.name
                    )
                    Image(
                        painter = when (friend.second) {
                            true -> painterResource(id = R.drawable.baseline_check_circle_24)
                            false -> painterResource(id = R.drawable.baseline_radio_button_unchecked_24)
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
}

//@Preview(showBackground = true)
//@Composable
//fun AddFriendsScreenPreview() {
//    WhereAreYouTheme() {
//        AddFriendsScreen()
//    }
//}