package com.whereareyounow.ui.home.schedule.newschedule

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.whereareyounow.R
import com.whereareyounow.data.GlobalValue
import com.whereareyounow.domain.entity.schedule.Friend

@Composable
fun FriendsListScreen(
    updateFriendsList: (List<Friend>) -> Unit,
    moveToNewScheduleScreen: () -> Unit,
    viewModel: FriendsListScreenViewModel = hiltViewModel()
) {
    BackHandler {
        moveToNewScheduleScreen()
    }
    val density = LocalDensity.current.density
    val selectedFriendsList = viewModel.selectedFriendsList.collectAsState().value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 20.dp, end = 20.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height((GlobalValue.topBarHeight / density).dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .size((GlobalValue.topBarHeight / density / 3 * 2).dp)
                    .clip(RoundedCornerShape(50))
                    .clickable { moveToNewScheduleScreen() },
                painter = painterResource(id = R.drawable.arrow_back),
                contentDescription = null
            )
            Text(
                text = "친구추가",
                fontSize = 30.sp
            )
        }
        Spacer(Modifier.height(10.dp))
        LazyRow() {
            itemsIndexed(selectedFriendsList) { index, friend ->
                Text(
                    text = friend.name
                )
            }
        }
        Spacer(Modifier.height(10.dp))
        // 텍스트 입력창
        val inputFriendName = viewModel.inputText.collectAsState().value
//        BasicTextField(
//            modifier = Modifier
//                .fillMaxWidth()
//                .background(
//                    color = Color.Cyan
//                ),
//            value = viewModel.inputText.collectAsState().value,
//            onValueChange = {
//                viewModel.updateInputText(it)
//            },
//            textStyle = TextStyle(fontSize = 20.sp)
//        )
        BasicTextField(
            value = inputFriendName,
            onValueChange = { viewModel.updateInputText(it) },
            textStyle = TextStyle(fontSize = 30.sp),
            singleLine = true,
            decorationBox = {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .drawBehind {
                            val borderSize = 1.dp.toPx()
                            drawLine(
                                color = Color(0xFF858585),
                                start = Offset(0f, size.height),
                                end = Offset(size.width, size.height),
                                strokeWidth = borderSize
                            )
                        },
                    contentAlignment = Alignment.CenterStart
                ) {
                    it()
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.CenterEnd
                    ) {
                        Image(
                            modifier = Modifier.clickable {
//                                clearInputId()
                            },
                            painter = painterResource(id = R.drawable.baseline_cancel_24),
                            contentDescription = null,
                            colorFilter = ColorFilter.tint(color = Color(0xFF858585))
                        )
                    }
                }
            }
        )


        Spacer(Modifier.height(20.dp))
        val friendsList = viewModel.friendsListCopy.collectAsState().value
        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            itemsIndexed(friendsList) { _, friend ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            viewModel.selectFriend(friend)
                        }
                        .padding(top = 10.dp, bottom = 10.dp)
                ) {
                    Text(
                        modifier = Modifier.align(Alignment.CenterStart),
                        text = friend.first.name,
                        fontSize = 24.sp
                    )
                    Image(
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .size(30.dp),
                        painter = when (friend.second) {
                            true -> painterResource(id = R.drawable.check_yellow)
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
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(
                    color = Color(0xFF2A2550),
                    shape = RoundedCornerShape(10.dp)
                )
                .clickable {
                    updateFriendsList(selectedFriendsList)
                    moveToNewScheduleScreen()
                },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "완료",
                fontSize = 30.sp,
                color = Color.White,
                fontWeight = FontWeight.Medium
            )
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