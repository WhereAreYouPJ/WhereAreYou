package com.whereareyou.ui.home.friends

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.whereareyou.data.GlobalValue

@Composable
fun CommunityScreen(
    paddingValues: PaddingValues,
    moveToAddFriendScreen: () -> Unit,
    moveToAddGroupScreen: () -> Unit,
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
        CommunityScreenTopBar(
            isFriendPage = isFriendPage,
            moveToAddFriendScreen = moveToAddFriendScreen,
            moveToAddGroupScreen = moveToAddGroupScreen
        )
        if (isFriendPage.value) {
            FriendContent()
        } else {
            GroupContent()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CommunityScreenPreview() {
    CommunityScreen(PaddingValues(0.dp), {}, {})
}