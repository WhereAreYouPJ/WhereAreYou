package com.whereareyou.ui.home.mypage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun MyPageContent(
    paddingValues: PaddingValues
) {
    Box(
        modifier = androidx.compose.ui.Modifier
            .padding(paddingValues)
            .fillMaxSize()
            .background(
                color = Color(0xFFFF0000)
            )
    )
}