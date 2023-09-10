package com.whereareyou.ui.home

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.whereareyou.data.ViewType
import com.whereareyou.ui.home.calendar.CalendarContent
import com.whereareyou.ui.home.friends.FriendsContent
import com.whereareyou.ui.home.mypage.MyPageContent
import kotlinx.coroutines.flow.emptyFlow
import java.util.concurrent.Flow

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val viewType = viewModel.viewType.collectAsState().value

    Scaffold(
        topBar = {},
        bottomBar = {
            if (true) {
                HomeNavigationBar()
            }
        }
    ) {
        when (viewType) {
            ViewType.Calendar -> {
                CalendarContent(
                    paddingValues = it
                )
            }
            ViewType.Friends -> {
                FriendsContent(
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
            .height((screenHeight / 15).dp),
        containerColor = Color(0xFFBBDEFB)
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
                            .fillMaxHeight(1.0f)
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color(0xFF1976D2),
                    selectedTextColor = Color(0xFF1976D2),
                    indicatorColor = Color(0xFFBBDEFB),
                    unselectedIconColor = Color(0xFF1976D2),
                    unselectedTextColor = Color(0xFF1976D2)
                ),
                interactionSource = NoRippleInteractionSource()
            )
        }
    }
}

class NoRippleInteractionSource : MutableInteractionSource {

    override val interactions = emptyFlow<Interaction>()
    override suspend fun emit(interaction: Interaction) {}
    override fun tryEmit(interaction: Interaction) = true
}