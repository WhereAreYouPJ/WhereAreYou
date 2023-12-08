package com.whereareyounow.ui.home.schedule.notification

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.whereareyounow.data.GlobalValue

@Composable
fun DrawerNotification(
    updateCalendar: () -> Unit,
    updateBriefCalendar: () -> Unit,
    viewModel: DrawerNotificationViewModel = hiltViewModel()
) {
    val density = LocalDensity.current
    val friendRequestList = viewModel.friendRequestList
    val scheduleRequestList = viewModel.scheduleRequestList

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
        Column(
            modifier = Modifier
                .width((GlobalValue.screenWidth * 4 / 5 / density.density).dp)
                .fillMaxHeight()
                .background(color = Color(0xFFFAFAFA))
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                LazyColumn(
                    contentPadding = PaddingValues(top = 10.dp, bottom = 10.dp)
                ) {
                    item {
                        Text(
                            modifier = Modifier.padding(start = 20.dp, bottom = 20.dp),
                            text = "알림",
                            fontSize = 30.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            modifier = Modifier.padding(start = 20.dp),
                            text = "친구 요청 ${friendRequestList.size}",
                            color = Color(0xFF999999),
                            fontSize = 20.sp
                        )
                    }
                    itemsIndexed(friendRequestList) { _, friendRequest ->
                        Box(
                            modifier = Modifier
                                .padding(start = 20.dp, top = 10.dp, end = 20.dp, bottom = 10.dp)
                                .fillMaxWidth()
                                .height(100.dp)
                                .shadow(elevation = 4.dp, shape = RoundedCornerShape(10.dp))
                                .background(
                                    color = Color.White,
                                    shape = RoundedCornerShape(10.dp)
                                )
                        ) {
                            FriendRequestBox(
                                friendRequest = friendRequest,
                                acceptFriendRequest = {
                                    updateCalendar()
                                    updateBriefCalendar()
                                    viewModel.acceptFriendRequest(friendRequest.first)
                                },
                                refuseFriendRequest = {
                                    updateCalendar()
                                    updateBriefCalendar()
                                    viewModel.refuseFriendRequest(friendRequest.first)
                                }
                            )
                        }
                    }

                    item {
                        Text(
                            modifier = Modifier.padding(start = 20.dp),
                            text = "일정 초대 ${scheduleRequestList.size}",
                            color = Color(0xFF999999),
                            fontSize = 20.sp
                        )
                    }
                    itemsIndexed(scheduleRequestList) { _, scheduleRequest ->
                        Box(
                            modifier = Modifier
                                .padding(start = 20.dp, top = 10.dp, end = 20.dp, bottom = 10.dp)
                                .fillMaxWidth()
                                .height(180.dp)
                                .shadow(elevation = 4.dp, shape = RoundedCornerShape(10.dp))
                                .background(
                                    color = Color.White,
                                    shape = RoundedCornerShape(10.dp)
                                )
                        ) {
                            ScheduleRequestBox(
                                scheduleRequest = scheduleRequest,
                                acceptScheduleRequest = {
                                    viewModel.acceptScheduleRequest(
                                        scheduleId = scheduleRequest.scheduleId,
                                        updateCalendar = updateCalendar,
                                        updateBriefCalendar = updateBriefCalendar
                                    )
                                },
                                refuseScheduleRequest = {
                                    updateCalendar()
                                    updateBriefCalendar()
                                    viewModel.refuseScheduleRequest(
                                        scheduleId = scheduleRequest.scheduleId,
                                        updateCalendar = updateCalendar,
                                        updateBriefCalendar = updateBriefCalendar
                                    )
                                }
                            )
                        }
                    }
                    item {
                        Spacer(modifier = Modifier.height((GlobalValue.bottomNavBarHeight / density.density).dp))
                    }
                }
            }
        }
    }
}

//@Preview(showBackground = true, widthDp = 411, heightDp = 836, backgroundColor = 0XFF000000)
//@Composable
//fun DrawerNotificationPreview() {
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//    ) {
//        DrawerNotification()
//    }
//}