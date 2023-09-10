package com.whereareyou.ui.home

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.whereareyou.data.ViewType

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = {},
        bottomBar = { HomeNavigationBar() }
    ) {
        HomeContent(
            paddingValues = it
        )
    }

}

@Composable
fun HomeNavigationBar(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val navigationItemContentList = viewModel.getNavigationItemContent()
    NavigationBar(
        containerColor = Color(0xFFFF0000),
        contentColor = Color(0xFF00FF00)
    ) {
        navigationItemContentList.forEachIndexed { _, navItem ->
            NavigationBarItem(
                selected = viewModel.viewType.collectAsState().value == navItem.viewType,
                onClick = { viewModel.updateViewType(navItem.viewType) },
                icon = {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = navItem.icon),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize(0.5f)
                    )
                }
            )
        }
    }
}


@Composable
fun HomeContent(
    paddingValues: PaddingValues
) {
    Box(
        modifier = Modifier
            .padding(paddingValues)
            .background(
                color = Color(0xFF0000FF)
            )
    ) {

    }
}