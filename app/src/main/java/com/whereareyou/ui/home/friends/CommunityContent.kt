package com.whereareyou.ui.home.friends

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.whereareyou.R
import com.whereareyou.data.FriendProvider

@Composable
fun CommunityContent(
    paddingValues: PaddingValues,
    topBarHeight: Int = LocalConfiguration.current.screenHeightDp / 12
) {
    Column(
        modifier = Modifier
            .padding(paddingValues)
            .padding(start = 20.dp, end = 20.dp)
            .fillMaxSize()
            .background(
                color = Color(0xFFFAFAFA)
            )

    ) {
        val isFriendPage = remember { mutableStateOf(true) }

        // 상단바
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(topBarHeight.dp)
                .background(
                    color = Color(0xFFCE93D8)
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier
                    .padding(end = 20.dp)
                    .clickable {
                        isFriendPage.value = true
                    },
                text = "친구"
            )
            Text(
                modifier = Modifier
                    .padding(start = 20.dp)
                    .clickable {
                        isFriendPage.value = false
                    },
                text = "그룹"
            )
        }
        if (isFriendPage.value) {
            FriendContent()
        } else {
            GroupContent()
        }
    }
}