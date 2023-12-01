package com.whereareyou.ui.home.schedule.calendar

import android.util.Log
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.gestures.animateTo
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.whereareyou.data.GlobalValue
import com.whereareyou.ui.home.schedule.notification.DrawerNotification
import com.whereareyou.ui.home.schedule.notification.DrawerNotificationViewModel
import kotlinx.coroutines.launch
import java.util.Calendar
import kotlin.math.roundToInt


enum class DetailState {
    Open, Close
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ScheduleScreen(
    paddingValues: PaddingValues,
    moveToDetailScreen: (String) -> Unit,
    viewModel: CalendarViewModel = hiltViewModel(),
    notificationViewModel: DrawerNotificationViewModel = hiltViewModel(),
) {
    // 사이드바가 오른쪽에서 열리게 하기 위한 Rtl
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        val drawerState = rememberDrawerState(
            initialValue = DrawerValue.Closed,
            confirmStateChange = {
                notificationViewModel.loadFriendRequests()
                true
            }
        )
        ModalNavigationDrawer(
            drawerContent = { DrawerNotification() },
            drawerState = drawerState,
        ) {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                // 하단 콘텐츠 상태
                val bottomContentState = remember { anchoredDraggableState }
                Column(
                    modifier = Modifier
                        .padding(paddingValues)
                        .padding(start = 20.dp, end = 20.dp)
                        .fillMaxSize()
                        .background(color = Color(0xFFFAFAFA))
                ) {
                    // 상단바
                    TopBar(
                        drawerState = drawerState,
                        bottomContentState = bottomContentState
                    )
                    // 달력 뷰
                    CalendarContent(bottomContentState)
                }
                // 일별 간략한 일정
                BriefScheduleContent(
                    moveToDetailScreen = moveToDetailScreen,
                    state = bottomContentState
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
val anchoredDraggableState = AnchoredDraggableState(
    initialValue = DetailState.Open,
    positionalThreshold = { it: Float -> it * 0.5f },
    velocityThreshold = { 100f },
    animationSpec = tween(400)
)
    .apply {
        updateAnchors(
            DraggableAnchors {
                DetailState.Open at 0f
                DetailState.Close at GlobalValue.dailyScheduleViewHeight
            }
        )
    }