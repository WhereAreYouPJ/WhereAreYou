package com.whereareyounow.ui.main.home

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.whereareyounow.ui.component.CustomSurface

@Composable
fun HomeScreen(
    paddingValues: PaddingValues,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val str = viewModel.string.collectAsState().value
    HomeScreen(
        isContent = true
    )
}

@Composable
private fun HomeScreen(
    isContent: Boolean
) {
    Text(
        text = "abc"
    )
}