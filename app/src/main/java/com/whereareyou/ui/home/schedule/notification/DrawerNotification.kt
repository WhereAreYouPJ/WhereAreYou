package com.whereareyou.ui.home.schedule.notification

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.whereareyou.data.GlobalValue
import com.whereareyou.ui.theme.WhereAreYouTheme

@Composable
fun DrawerNotification() {
    val density = LocalDensity.current
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
        Column(
            modifier = Modifier
                .width((GlobalValue.screenWidth * 4 / 5 / density.density).dp)
                .fillMaxHeight()
                .background(color = Color.LightGray)
        ) {
            LazyColumn() {
                item() {
                    FriendRequestBox(name = "이서영")
                }
                item() {
                    FriendRequestBox(name = "박종훈")
                }
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 411, heightDp = 836, backgroundColor = 0XFF000000)
@Composable
fun DrawerNotificationPreview() {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        DrawerNotification()
    }
}