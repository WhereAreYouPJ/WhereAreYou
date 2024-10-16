package com.whereareyounow.ui.main.friend.feed

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.skydoves.landscapist.glide.GlideImage
import com.whereareyounow.R
import com.whereareyounow.data.feedlist.FeedListScreenUIState
import com.whereareyounow.ui.main.friend.feed.component.DetailFeedScreen
import com.whereareyounow.ui.main.friend.feed.component.FeedImagePager
import com.whereareyounow.ui.theme.getColor
import com.whereareyounow.ui.theme.medium14pt
import com.whereareyounow.ui.theme.medium16pt
import com.whereareyounow.util.calendar.parseLocalDate
import com.whereareyounow.util.clickableNoEffect

@Composable
fun FeedScreen(
    viewModel: FeedViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.getFeedList()
    }
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
    FeedListScreen(
        uiState = uiState,
        getDetailFeed = viewModel::getDetailFeed,
        bookmarkFeed = viewModel::bookmarkFeed,
        deleteFeedBookmark = viewModel::deleteBookmarkFeed,
        isContent = true
    )
}

@Composable
private fun FeedListScreen(
    uiState: FeedListScreenUIState,
    getDetailFeed: (Int, Int) -> Unit,
    bookmarkFeed: (Int) -> Unit,
    deleteFeedBookmark: (Int) -> Unit,
    isContent: Boolean
) {
    val isDetailContent = remember { mutableStateOf(false) }
    BackHandler(
        enabled = isDetailContent.value
    ) {
        if (isDetailContent.value) isDetailContent.value = false
    }
    if (!isDetailContent.value) {
        if (uiState.feedListData.content.isEmpty()) {
            Spacer(Modifier.height(100.dp))

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 15.dp, end = 15.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier.height(168.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .width(302.dp)
                            .height(140.dp)
                            .border(
                                border = BorderStroke(
                                    width = 2.dp,
                                    color = getColor().thinnest
                                ),
                                shape = RoundedCornerShape(15.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "아직은 기록하지 않은 것 같아요.\n" +
                                    "특별한 추억을 오래도록 기억할 수 있게\n" +
                                    "서로에게 얘기해 보세요!",
                            color = Color(0xFF767676),
                            style = medium14pt.copy(textAlign = TextAlign.Center)
                        )
                    }

                    Box(
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                            .width(110.dp)
                            .height(56.dp)
                            .background(
                                color = Color(0xFFF9EDB0),
                                shape = RoundedCornerShape(50)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            modifier = Modifier.width(70.dp),
                            painter = painterResource(R.drawable.img_logo),
                            contentDescription = null
                        )
                    }
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 15.dp, top = 20.dp, end = 15.dp)
            ) {
                itemsIndexed(uiState.feedListData.content) { idx, item ->
                    Column {
                        Box {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(74.dp)
                                    .border(
                                        border = BorderStroke(
                                            width = (1.5).dp,
                                            color = getColor().brandColor
                                        ),
                                        shape = RoundedCornerShape(14.dp)
                                    )
                                    .clickableNoEffect {
                                        getDetailFeed(
                                            item.scheduleInfo.scheduleSeq,
                                            item.feedInfo[0].feedInfo.feedSeq
                                        )
                                        isDetailContent.value = true
                                    }
                                    .padding(start = 10.dp, end = 10.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                GlideImage(
                                    modifier = Modifier
                                        .size(50.dp)
                                        .clip(RoundedCornerShape(14.dp)),
                                    imageModel = {
                                        if (item.feedInfo[0].memberInfo.profileImage.isNullOrEmpty()) R.drawable.ic_profile
                                        else item.feedInfo[0].memberInfo.profileImage
                                    }
                                )
                                Column {
                                    Row {
                                        Text(
                                            modifier = Modifier.padding(6.dp, 6.dp, 2.dp, 2.dp),
                                            text = parseLocalDate(item.scheduleInfo.startTime).toString().replace("-", "."),
                                            color = Color(0xFF767676),
                                            style = medium14pt
                                        )

                                        Text(
                                            modifier = Modifier.padding(6.dp, 6.dp, 6.dp, 2.dp),
                                            text = item.scheduleInfo.location,
                                            color = Color(0xFF767676),
                                            style = medium14pt
                                        )
                                    }
                                    Text(
                                        modifier = Modifier.padding(start = 6.dp, end = 6.dp, bottom = 4.dp),
                                        text = item.feedInfo[0].feedInfo.title,
                                        color = Color(0xFF222222),
                                        style = medium16pt
                                    )
                                }
                            }
                        }


                        Spacer(Modifier.height(10.dp))

                        FeedImagePager(imageList = item.feedInfo[0].feedImageInfos.map { it.feedImageUrl })

                        Spacer(Modifier.height(6.dp))

                        Row {
                            Spacer(Modifier.weight(1f))

                            Image(
                                modifier = Modifier
                                    .size(30.dp)
                                    .clickableNoEffect {
                                        if (item.feedInfo[0].bookmarkInfo) {
                                            deleteFeedBookmark(item.feedInfo[0].feedInfo.feedSeq)
                                        } else {
                                            bookmarkFeed(item.feedInfo[0].feedInfo.feedSeq)
                                        }
                                    },
                                painter = painterResource(if (item.feedInfo[0].bookmarkInfo) R.drawable.ic_bookmark_filled_brandcolor else R.drawable.ic_bookmark_outlined_black),
                                contentDescription = null
                            )
                        }

                        Spacer(Modifier.height(6.dp))

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(82.dp)
                                .background(
                                    color = Color(0xFFF9F9F9),
                                    shape = RoundedCornerShape(6.dp)
                                )
                        ) {
                            Text(
                                modifier = Modifier.padding(10.dp),
                                text = item.feedInfo[0].feedInfo.content,
                                color = Color(0xFF666666),
                                style = medium14pt
                            )
                        }

                        Spacer(Modifier.height(16.dp))
                    }
                }
            }
        }
    } else {
        DetailFeedScreen(uiState.detailFeedData)
    }
}