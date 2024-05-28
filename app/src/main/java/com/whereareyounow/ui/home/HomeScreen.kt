package com.whereareyounow.ui.home

import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
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
import com.whereareyounow.ui.home.friend.FriendScreen
import com.whereareyounow.ui.home.mypage.MyPageScreen
import com.whereareyounow.ui.home.schedule.calendar.CalendarViewModel
import com.whereareyounow.ui.home.schedule.calendar.ScheduleScreen
import com.whereareyounow.ui.theme.WhereAreYouTheme
import com.whereareyounow.ui.theme.nanumSquareNeo
import com.whereareyounow.util.CustomPreview
import kotlinx.coroutines.flow.emptyFlow

@Composable
fun HomeScreen(
    moveToAddScheduleScreen: (Int, Int, Int) -> Unit,
    moveToDetailScreen: (String) -> Unit,
    moveToAddFriendScreen: () -> Unit,
    moveToAddGroupScreen: () -> Unit,
    moveToSignInScreen: () -> Unit,
    moveToModifyInfoScreen: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel(),
    calendarViewModel: CalendarViewModel = hiltViewModel()
) {
    val viewType = viewModel.viewType.collectAsState().value
    val navigationItemContentList = viewModel.getNavigationItemContent()
    val calendarScreenUIState = calendarViewModel.calendarScreenUIState.collectAsState().value
    HomeScreen(
        viewType = viewType,
        calendarScreenUIState = calendarScreenUIState,
        navigationItemContentList = navigationItemContentList,
        updateViewType = viewModel::updateViewType,
        moveToAddScheduleScreen = moveToAddScheduleScreen,
        moveToDetailScreen = moveToDetailScreen,
        moveToAddFriendScreen = moveToAddFriendScreen,
        moveToAddGroupScreen = moveToAddGroupScreen,
        moveToSignInScreen = moveToSignInScreen,
        moveToModifyInfoScreen = moveToModifyInfoScreen
    )
}

@Composable
private fun HomeScreen(
    viewType: ViewType,
    calendarScreenUIState: CalendarScreenUIState,
    navigationItemContentList: List<HomeViewModel.NavigationItemContent>,
    updateViewType: (ViewType) -> Unit,
    moveToAddScheduleScreen: (Int, Int, Int) -> Unit,
    moveToDetailScreen: (String) -> Unit,
    moveToAddFriendScreen: () -> Unit,
    moveToAddGroupScreen: () -> Unit,
    moveToSignInScreen: () -> Unit,
    moveToModifyInfoScreen: () -> Unit
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
            floatingActionButton = {
                if (viewType == ViewType.Calendar) {
                    FloatingActionButton(
                        shape = CircleShape,
                        contentColor = Color(0xFFFFFFFF),
                        containerColor = Color(0xFF5448BC),
                        onClick = { moveToAddScheduleScreen(calendarScreenUIState.selectedYear, calendarScreenUIState.selectedMonth, calendarScreenUIState.selectedDate) }
                    ) {
                        Icon(
                            modifier = Modifier.size(20.dp),
                            imageVector = ImageVector.vectorResource(R.drawable.plus),
                            contentDescription = null
                        )
                    }
                }
            },
            containerColor = Color(0xFFFFFFFF),
        ) {
            when (viewType) {
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

@Composable
fun HomeNavigationBar(
    viewType: ViewType,
    navigationItemContentList: List<HomeViewModel.NavigationItemContent>,
    updateViewType: (ViewType) -> Unit,
) {
    NavigationBar(
        modifier = Modifier.height(BOTTOM_NAVIGATION_BAR_HEIGHT.dp)
            .shadow(elevation = 40.dp),
        containerColor = Color(0xFFFFFFFF),
        // 이걸 안해주면 navigationbar 아래가 system navigation bar만큼 가려진다.
        windowInsets = WindowInsets(0, 0, 0, 0)
    ) {
        navigationItemContentList.forEachIndexed { _, navItem ->
            NavigationBarItem(
                modifier = Modifier.fillMaxHeight(),
                selected = viewType == navItem.viewType,
                onClick = { updateViewType(navItem.viewType) },
                icon = {
                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            modifier = Modifier.fillMaxHeight(0.5f),
                            painter = painterResource(id = when (viewType == navItem.viewType) {
                                true -> navItem.iconSelected
                                false -> navItem.iconUnselected
                            }),
                            contentDescription = null,
                        )
                        Text(
                            text = navItem.label,
                            fontSize = 12.sp,
                            fontFamily = nanumSquareNeo,
                            fontWeight = FontWeight.Bold
                        )
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color(0xFF2D2573),
                    selectedTextColor = Color(0xFF2D2573),
                    indicatorColor = Color(0x00FFFFFF),
                    unselectedIconColor = Color(0xFF9F9EA7),
                    unselectedTextColor = Color(0xFF9F9EA7)
                ),
                interactionSource = NoRippleInteractionSource(),
                alwaysShowLabel = true
            )
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
        HomeViewModel.NavigationItemContent(
            viewType = ViewType.Calendar,
            iconSelected = R.drawable.bottomnavbar_home,
            iconUnselected = R.drawable.bottomnavbar_home,
            label = "홈"
        ),
        HomeViewModel.NavigationItemContent(
            viewType = ViewType.Friends,
            iconSelected = R.drawable.bottomnavbar_friend,
            iconUnselected = R.drawable.bottomnavbar_friend,
            label = "친구목록"
        ),
        HomeViewModel.NavigationItemContent(
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