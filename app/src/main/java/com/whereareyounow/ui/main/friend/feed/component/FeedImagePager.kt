package com.whereareyounow.ui.main.friend.feed.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun FeedImagePager(
    imageList: List<String>
) {
    val pagerState = rememberPagerState(
        initialPage = imageList.size * 100,
        pageCount = { imageList.size * 200 }
    )
    HorizontalPager(state = pagerState) { page ->
        GlideImage(
            modifier = Modifier
                .fillMaxWidth()
                .height(290.dp)
                .clip(RoundedCornerShape(6.dp)),
            imageModel = { imageList[page % imageList.size] }
        )
    }
}