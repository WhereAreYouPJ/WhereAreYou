package com.whereareyou.ui.home

import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.whereareyou.data.ViewType
import com.whereareyou.ui.home.calendar.CalendarContent
import com.whereareyou.ui.home.friends.CommunityContent
import com.whereareyou.ui.home.mypage.MyPageContent
import kotlinx.coroutines.flow.emptyFlow

@Composable
fun HomeScreen(viewModel: HomeViewModel = hiltViewModel()) {
    val viewType = viewModel.viewType.collectAsState().value

    Scaffold(
        topBar = {},
        bottomBar = {
            if (true) {
                HomeNavigationBar()
            }
        },
        floatingActionButton = {}
    ) {
        when (viewType) {
            ViewType.Calendar -> {
                CalendarContent(
                    paddingValues = it
                )
            }
            ViewType.Friends -> {
                CommunityContent(
                    paddingValues = it
                )
            }
            ViewType.MyPage -> {
                MyPageContent(
                    paddingValues = it
                )
            }
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