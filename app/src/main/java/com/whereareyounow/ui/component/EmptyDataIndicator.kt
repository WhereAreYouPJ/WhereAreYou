package com.whereareyounow.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.whereareyounow.R
import com.whereareyounow.ui.main.mypage.withdrawl.Gap
import com.whereareyounow.ui.theme.medium14pt

@Composable
fun EmptyDataIndicator(
    indicateText : String
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 15.dp, end = 15.dp),
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Gap(120)
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.TopCenter
            ) {
                Image(
                    modifier = Modifier.width(300.dp),
                    painter = painterResource(R.drawable.img_feed_empty),
                    contentDescription = null
                )
                Text(
                    text = indicateText,
                    style = medium14pt,
                    color = Color(0xFF767676),
                    modifier = Modifier.offset(y = 72.dp)
                )
            }
        }
    }
}