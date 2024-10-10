package com.whereareyounow.ui.main.friend.feed.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.skydoves.landscapist.glide.GlideImage
import com.whereareyounow.ui.theme.getColor
import com.whereareyounow.ui.theme.notoSanskr

@Composable
fun FeedImagePager(
    imageList: List<String>
) {
    val pageCount = remember { imageList.size }
    val pagerState = rememberPagerState(
        initialPage = imageList.size * 100,
        pageCount = { imageList.size * 200 }
    )
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(290.dp)
            .clip(RoundedCornerShape(6.dp))
    ) {
        HorizontalPager(state = pagerState) { page ->
            GlideImage(
                modifier = Modifier.fillMaxSize(),
                imageModel = { imageList[page % imageList.size] }
            )
        }
        if(pageCount > 1) {
            Text(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp)
                    .clip(RoundedCornerShape(50))
                    .background(Color(0x661D1D1D))
                    .padding(horizontal = 9.dp, vertical = 1.dp),
                text = "${(pagerState.currentPage % pageCount) + 1} / $pageCount",
                fontFamily = notoSanskr,
                color = Color(0xFFDFDFDF),
                fontWeight = FontWeight.Medium,
                fontSize = 12.sp
            )
        }
    }
}