package com.whereareyou.ui.home.friends

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun FriendScreen(
    paddingValues: PaddingValues,
    moveToAddFriendScreen: () -> Unit,
    moveToAddGroupScreen: () -> Unit,
    viewModel: FriendViewModel = hiltViewModel()
) {
    val isFriendPage = remember { mutableStateOf(true) }
    Column(
        modifier = Modifier
            .padding(paddingValues)
            .padding(start = 20.dp, end = 20.dp)
            .fillMaxSize()
            .background(
                color = Color(0xFFFAFAFA)
            )

    ) {
        // 상단바
        FriendScreenTopBar(
            isFriendPage = isFriendPage,
            moveToAddFriendScreen = moveToAddFriendScreen,
            moveToAddGroupScreen = moveToAddGroupScreen
        )
        if (isFriendPage.value) {
            FriendContent(viewModel.friendsList)
        } else {
            GroupContent()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FriendScreenPreview() {
    FriendScreen(PaddingValues(0.dp), {}, {})
}