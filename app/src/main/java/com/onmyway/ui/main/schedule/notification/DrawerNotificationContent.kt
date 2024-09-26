package com.onmyway.ui.main.schedule.notification

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.onmyway.R
import com.onmyway.data.globalvalue.BOTTOM_NAVIGATION_BAR_HEIGHT
import com.onmyway.data.globalvalue.SCREEN_WIDTH
import com.onmyway.data.globalvalue.TOP_BAR_HEIGHT
import com.onmyway.data.notification.DrawerNotificationContentUIState
import com.onmyway.data.notification.ScheduleInvitationInfo
import com.onmyway.domain.entity.friend.FriendRequest
import kotlinx.coroutines.launch

@Composable
fun DrawerNotificationContent(
    drawerNotificationContentUIState: DrawerNotificationContentUIState,
    updateCalendar: () -> Unit,
    updateBriefCalendar: () -> Unit,
    acceptFriendRequest: (FriendRequest) -> Unit,
    refuseFriendRequest: (FriendRequest) -> Unit,
    acceptScheduleRequest: (String, () -> Unit, () -> Unit) -> Unit,
    refuseScheduleRequest: (String, () -> Unit, () -> Unit) -> Unit,
    drawerState: DrawerState
) {
    val coroutineScope = rememberCoroutineScope()
    BackHandler(enabled = !drawerState.isClosed) {
        coroutineScope.launch { drawerState.close() }
    }
    val density = LocalDensity.current.density
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
        Column(
            modifier = Modifier
                .width((SCREEN_WIDTH * 4 / 5 / density).dp)
                .fillMaxHeight()
                .background(color = Color(0xFFFAFAFA))
                .pointerInput(Unit) {
                    detectTapGestures(onLongPress = {})
                }
        ) {
            Box(modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF5F5F5))
            ) {
                LazyColumn {
                    item {
                        Column(
                            modifier = Modifier.background(Color(0xFFFFFFFF))
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height((TOP_BAR_HEIGHT / density).dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Spacer(Modifier.width(20.dp))
                                Image(
                                    modifier = Modifier
                                        .size((TOP_BAR_HEIGHT / density / 2).dp)
                                        .clip(RoundedCornerShape(50))
                                        .clickable {
                                            coroutineScope.launch { drawerState.close() }
                                        },
                                    painter = painterResource(id = R.drawable.ic_back),
                                    contentDescription = null
                                )
                                Spacer(Modifier.weight(1f))
                                Text(
                                    text = "${drawerNotificationContentUIState.todayScheduleCount}",
                                    color = Color(0xFFF3A204)
                                )
                                Spacer(Modifier.width(10.dp))
                                Text(
                                    text = "오늘의 일정",
                                    color = Color(0xFF5B5B5B),
                                    fontWeight = FontWeight.Medium,
                                    letterSpacing = 0.em
                                )
                                Spacer(Modifier.width(20.dp))
                            }
                            Spacer(Modifier.height(20.dp))
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height((0.6).dp)
                                    .background(color = Color(0xFFBEBEBE))
                            )
                        }
                        Spacer(Modifier.height(20.dp))
                        Text(
                            modifier = Modifier.padding(start = 20.dp),
                            text = "알림",
                            fontSize = 26.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF5C5C67)
                        )
                        Spacer(Modifier.height(10.dp))
                    }
                    item {
                        Spacer(Modifier.height(10.dp))
                        Text(
                            modifier = Modifier.padding(start = 20.dp),
                            text = "친구요청 ${drawerNotificationContentUIState.friendRequestsList.size}",
                            color = Color(0xFF999999),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                    itemsIndexed(drawerNotificationContentUIState.friendRequestsList) { _, friendRequest ->
                        FriendRequestBox(
                            friendRequest = friendRequest,
                            acceptFriendRequest = {
                                acceptFriendRequest(friendRequest.first)
                                updateCalendar()
                                updateBriefCalendar()
                            },
                            refuseFriendRequest = {
                                refuseFriendRequest(friendRequest.first)
                                updateCalendar()
                                updateBriefCalendar()
                            }
                        )
                    }
                    item {
                        Spacer(Modifier.height(20.dp))
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height((0.6).dp)
                                .background(color = Color(0xFFBEBEBE))
                        )
                        Spacer(Modifier.height(20.dp))
                        Text(
                            modifier = Modifier.padding(start = 20.dp),
                            text = "일정초대 ${drawerNotificationContentUIState.scheduleRequestsList.size}",
                            color = Color(0xFF999999),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                    itemsIndexed(drawerNotificationContentUIState.scheduleRequestsList) { _, scheduleRequest ->
                        ScheduleRequestBox(
                            scheduleRequest = scheduleRequest,
                            acceptScheduleRequest = {
                                acceptScheduleRequest(
                                    scheduleRequest.scheduleId,
                                    updateCalendar,
                                    updateBriefCalendar
                                )
                            },
                            refuseScheduleRequest = {
                                updateCalendar()
                                updateBriefCalendar()
                                refuseScheduleRequest(
                                    scheduleRequest.scheduleId,
                                    updateCalendar,
                                    updateBriefCalendar
                                )
                            }
                        )
                    }
                    // 하단 네비게이션 바에 가려지는 부분을 위한 여백
                    item {
                        Spacer(Modifier.height(((BOTTOM_NAVIGATION_BAR_HEIGHT / density) + 60).dp))
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 411, heightDp = 836, backgroundColor = 0XFF000000)
@Composable
fun DrawerNotificationPreview() {
//    val friendRequestsList = listOf(
//        FriendRequest("", "id1", "2024-03-20T23:43:13") to Friend(0, "memberId1", "name1"),
//        FriendRequest("", "id1", "2024-03-20T23:43:13") to Friend(0, "memberId1", "name2")
//    )
    val scheduleRequestsList = listOf(
        ScheduleInvitationInfo(
            scheduleId = "",
            title = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
            inviterUserName = "bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb",
            year = "2023",
            month = "12",
            date = "12",
            hour = "13",
            minute = "45",
            invitedTime = ""
        )
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        DrawerNotificationContent(
            drawerNotificationContentUIState = DrawerNotificationContentUIState(),
            updateCalendar = {},
            updateBriefCalendar = {},
            acceptFriendRequest = {},
            refuseFriendRequest = {},
            acceptScheduleRequest = { _, _, _ -> },
            refuseScheduleRequest = { _, _, _ -> },
            drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        )
    }
}