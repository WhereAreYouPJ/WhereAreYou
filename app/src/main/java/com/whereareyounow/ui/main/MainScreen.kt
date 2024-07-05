package com.whereareyounow.ui.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.whereareyounow.R
import com.whereareyounow.data.ViewType
import com.whereareyounow.data.calendar.CalendarScreenUIState
import com.whereareyounow.data.globalvalue.BOTTOM_NAVIGATION_BAR_HEIGHT
import com.whereareyounow.ui.component.CustomSurface
import com.whereareyounow.ui.main.friend.FriendScreen
import com.whereareyounow.ui.main.home.HomeScreen
import com.whereareyounow.ui.main.mypage.MyPageScreen
import com.whereareyounow.ui.main.schedule.calendar.CalendarViewModel
import com.whereareyounow.ui.main.schedule.calendar.ScheduleScreen
import com.whereareyounow.ui.theme.WhereAreYouTheme
import com.whereareyounow.ui.theme.getColor
import com.whereareyounow.ui.theme.medium10pt
import com.whereareyounow.ui.theme.nanumSquareNeo
import com.whereareyounow.util.CustomPreview
import com.whereareyounow.util.clickableNoEffect
import kotlinx.coroutines.flow.emptyFlow

@Composable
fun MainScreen(
    moveToAddScheduleScreen: (Int, Int, Int) -> Unit,
    moveToDetailScreen: (String) -> Unit,
    moveToAddFriendScreen: () -> Unit,
    moveToAddGroupScreen: () -> Unit,
    moveToSignInScreen: () -> Unit,
    moveToModifyInfoScreen: () -> Unit,
    moveToMyPageScreen : () -> Unit,
    viewModel: MainViewModel = hiltViewModel(),
    calendarViewModel: CalendarViewModel = hiltViewModel()
) {
    val viewType = viewModel.viewType.collectAsState().value
    val navigationItemContentList = viewModel.getNavigationItemContent()
    val calendarScreenUIState = calendarViewModel.calendarScreenUIState.collectAsState().value
    MainScreen(
        viewType = viewType,
        calendarScreenUIState = calendarScreenUIState,
        navigationItemContentList = navigationItemContentList,
        updateViewType = viewModel::updateViewType,
        moveToAddScheduleScreen = moveToAddScheduleScreen,
        moveToDetailScreen = moveToDetailScreen,
        moveToAddFriendScreen = moveToAddFriendScreen,
        moveToAddGroupScreen = moveToAddGroupScreen,
        moveToSignInScreen = moveToSignInScreen,
        moveToModifyInfoScreen = moveToModifyInfoScreen,
        moveToMyPageScreen = moveToMyPageScreen
    )
}

@Composable
private fun MainScreen(
    viewType: ViewType,
    calendarScreenUIState: CalendarScreenUIState,
    navigationItemContentList: List<MainViewModel.NavigationItemContent>,
    updateViewType: (ViewType) -> Unit,
    moveToAddScheduleScreen: (Int, Int, Int) -> Unit,
    moveToDetailScreen: (String) -> Unit,
    moveToAddFriendScreen: () -> Unit,
    moveToAddGroupScreen: () -> Unit,
    moveToSignInScreen: () -> Unit,
    moveToModifyInfoScreen: () -> Unit,
    moveToMyPageScreen : () -> Unit
) {
    CustomSurface {
        Scaffold(
            topBar = {},
            bottomBar = {
                HomeNavigationBar(
                    viewType,
                    navigationItemContentList,
                    updateViewType = updateViewType
                )
            },
            containerColor = Color(0xFFFFFFFF),
        ) {
            Column(
                modifier = Modifier
                    .padding(bottom = it.calculateBottomPadding())
            ) {
                when (viewType) {
                    ViewType.Home -> {
                        HomeScreen(
                            paddingValues = it,

                            )
                    }
                    ViewType.Calendar -> {
                        ScheduleScreen(
                            paddingValues = it,
                            moveToDetailScreen = moveToDetailScreen
                        )
                    }
                    ViewType.Friends -> {
                        FriendScreen(
                            paddingValues = it,
                            moveToAddFriendScreen = moveToAddFriendScreen,
                            moveToAddGroupScreen = moveToAddGroupScreen
                        )
                    }
                    ViewType.MyPage -> {
                        MyPageScreen(
                            paddingValues = it,
                            moveToSignInScreen = moveToSignInScreen,
                            moveToModifyInfoScreen = moveToModifyInfoScreen
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun HomeNavigationBar(
    viewType: ViewType,
    navigationItemContentList: List<MainViewModel.NavigationItemContent>,
    updateViewType: (ViewType) -> Unit,
) {
    NavigationBar(
        modifier = Modifier
            .height(BOTTOM_NAVIGATION_BAR_HEIGHT.dp)
            .shadow(elevation = 40.dp),
        containerColor = Color(0xFFFFFFFF),
        // 이걸 안해주면 navigationbar 아래가 system navigation bar만큼 가려진다.
        windowInsets = WindowInsets(0, 0, 0, 0)
    ) {
        navigationItemContentList.forEachIndexed { _, navItem ->
            Column(
                modifier = Modifier
                    .weight(1f)
                    .clickableNoEffect { updateViewType(navItem.viewType) },
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    modifier = Modifier.fillMaxHeight(0.5f),
                    painter = painterResource(id = if (viewType == navItem.viewType) navItem.iconSelected else navItem.iconUnselected),
                    contentDescription = null
                )
                Text(
                    text = navItem.label,
                    style = medium10pt,
                    color = Color(0xFF222222)
                )
            }
//            NavigationBarItem(
//                modifier = Modifier.fillMaxHeight(),
//                selected = viewType == navItem.viewType,
//                onClick = { updateViewType(navItem.viewType) },
//                icon = {
//                    Column(
//                        modifier = Modifier
//                            .fillMaxSize(),
//                        horizontalAlignment = Alignment.CenterHorizontally,
//                        verticalArrangement = Arrangement.Center
//                    ) {
//                        Icon(
//                            modifier = Modifier.fillMaxHeight(0.5f),
//                            painter = painterResource(id = when (viewType == navItem.viewType) {
//                                true -> navItem.iconSelected
//                                false -> navItem.iconUnselected
//                            }),
//                            contentDescription = null,
////                            tint = Color.Red
//                        )
//                        Text(
//                            text = navItem.label,
//                            fontSize = 12.sp,
//                            fontFamily = nanumSquareNeo,
//                            fontWeight = FontWeight.Bold,
//                            color = Color.Black
//                        )
//                    }
//                },
//                colors = NavigationBarItemDefaults.colors(
////                    selectedIconColor = getColor().brandColor,
//                    selectedTextColor = Color(0xFF222222),
//                    indicatorColor = Color(0x00FFFFFF),
//                    unselectedIconColor = getColor().brandColor,
//                    unselectedTextColor = Color(0xFF222222)
//                ),
//                interactionSource = NoRippleInteractionSource(),
//                alwaysShowLabel = true
//            )
        }
    }
}

class NoRippleInteractionSource : MutableInteractionSource {
    override val interactions = emptyFlow<Interaction>()
    override suspend fun emit(interaction: Interaction) {}
    override fun tryEmit(interaction: Interaction) = false
}

@CustomPreview
@Composable
private fun HomeNavigationBarPreview() {
    val navigationItemContentList = listOf(
        MainViewModel.NavigationItemContent(
            viewType = ViewType.Home,
            iconSelected = R.drawable.bottomnavbar_home,
            iconUnselected = R.drawable.bottomnavbar_home,
            label = "홈"
        ),
        MainViewModel.NavigationItemContent(
            viewType = ViewType.Calendar,
            iconSelected = R.drawable.bottomnavbar_home,
            iconUnselected = R.drawable.bottomnavbar_home,
            label = "캘린더"
        ),
        MainViewModel.NavigationItemContent(
            viewType = ViewType.Friends,
            iconSelected = R.drawable.bottomnavbar_friend,
            iconUnselected = R.drawable.bottomnavbar_friend,
            label = "친구목록"
        ),
        MainViewModel.NavigationItemContent(
            viewType = ViewType.MyPage,
            iconSelected = R.drawable.bottomnavbar_mypage,
            iconUnselected = R.drawable.bottomnavbar_mypage,
            label = "마이페이지"
        )
    )
    WhereAreYouTheme {
        HomeNavigationBar(
            viewType = ViewType.Calendar,
            navigationItemContentList = navigationItemContentList,
            updateViewType = {}
        )
    }
}