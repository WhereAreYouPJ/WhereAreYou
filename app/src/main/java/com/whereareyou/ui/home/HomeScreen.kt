package com.whereareyou.ui.home

import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.whereareyou.R
import com.whereareyou.data.ViewType
import com.whereareyou.ui.home.friends.CommunityScreen
import com.whereareyou.ui.home.mypage.MyPageScreen
import com.whereareyou.ui.home.schedule.calendar.ScheduleScreen
import kotlinx.coroutines.flow.emptyFlow

@Composable
fun HomeScreen(
    moveToAddScheduleScreen: () -> Unit,
    toDetailScreen: (String) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val viewType = viewModel.viewType.collectAsState().value
    val isNavigationBarShowing = remember { mutableStateOf(true)}
    Scaffold(
        topBar = {},
        bottomBar = {
            if (isNavigationBarShowing.value) { HomeNavigationBar() }
        },
        floatingActionButton = {
            if (viewType == ViewType.Calendar) {
                FloatingActionButton(
                    contentColor = Color.White,
                    containerColor = Color.Black,
                    onClick = { moveToAddScheduleScreen() }
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.baseline_add_24),
                        contentDescription = null
                    )
                }
            }
        }
    ) {
        when (viewType) {
            ViewType.Calendar -> {
                ScheduleScreen(
                    paddingValues = it,
                    toDetailScreen = toDetailScreen
                )
            }
            ViewType.Friends -> { CommunityScreen(paddingValues = it) }
            ViewType.MyPage -> { MyPageScreen(paddingValues = it) }
        }
    }
}

@Composable
fun HomeNavigationBar(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val navigationItemContentList = viewModel.getNavigationItemContent()
    val screenHeight = LocalConfiguration.current.screenHeightDp

    NavigationBar(
        modifier = Modifier
            .height((screenHeight / 15).dp)
            .shadow(
                elevation = 40.dp,
                spotColor = Color.Blue
            ),
        containerColor = Color(0xFFFFFFFF)
    ) {
        navigationItemContentList.forEachIndexed { _, navItem ->
            NavigationBarItem(
                selected = viewModel.viewType.collectAsState().value == navItem.viewType,
                onClick = { viewModel.updateViewType(navItem.viewType) },
                icon = {
                    Icon(
                        imageVector = ImageVector.vectorResource(
                            id = when (viewModel.viewType.collectAsState().value == navItem.viewType) {
                                true -> navItem.iconSelected
                                false -> navItem.iconUnselected
                            }
                        ),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxHeight(0.7f)
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color(0xFF8C9EFF),
                    selectedTextColor = Color(0xFF8C9EFF),
                    indicatorColor = Color(0x00FFFFFF),
                    unselectedIconColor = Color(0xFFDDDDDD),
                    unselectedTextColor = Color(0xFFDDDDDD)
                ),
                interactionSource = NoRippleInteractionSource()
            )
        }
    }
}

class NoRippleInteractionSource : MutableInteractionSource {

    override val interactions = emptyFlow<Interaction>()
    override suspend fun emit(interaction: Interaction) {}
    override fun tryEmit(interaction: Interaction) = false
}