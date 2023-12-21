package com.whereareyounow.ui.home.friend.addfriend

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun AddFriendScreen(
    moveToBackScreen: () -> Unit,
    viewModel: AddFriendViewModel = hiltViewModel()
) {
    val inputId = viewModel.inputId.collectAsState().value
    val friendInfo = viewModel.friendInfo.collectAsState().value
    val buttonState = viewModel.buttonState.collectAsState().value

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AddFriendScreenTopBar(moveToBackScreen)
        Spacer(Modifier.height(20.dp))
        FriendIdTextField(
            inputId = inputId,
            updateInputId = { viewModel.updateInputId(it) },
            clearInputId = { viewModel.clearInputId() }
        )
        Spacer(Modifier.height(20.dp))
        if (friendInfo != null) {
            UserInfoContent(
                imageUrl = friendInfo.profileImgUrl,
                userName = friendInfo.name
            )
        }
        BottomButton(
            searchFriend = {
                when (buttonState) {
                    AddFriendViewModel.ButtonState.SEARCH -> viewModel.searchFriend()
                    AddFriendViewModel.ButtonState.REQUEST -> viewModel.sendFriendRequest()
                }
            },
            text = when (buttonState) {
                AddFriendViewModel.ButtonState.SEARCH -> "검색"
                AddFriendViewModel.ButtonState.REQUEST -> "친구추가"
            }
        )
    }
}