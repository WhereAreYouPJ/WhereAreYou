package com.whereareyounow.ui.main.mypage.feedbookmark

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.skydoves.landscapist.glide.GlideImage
import com.whereareyounow.R
import com.whereareyounow.data.globalvalue.TOP_BAR_HEIGHT
import com.whereareyounow.ui.component.tobbar.OneTextOneIconTobBar
import com.whereareyounow.ui.main.mypage.MyPageViewModel
import com.whereareyounow.ui.main.mypage.withdrawl.Gap
import com.whereareyounow.ui.theme.getColor
import com.whereareyounow.ui.theme.medium10pt
import com.whereareyounow.ui.theme.medium12pt
import com.whereareyounow.ui.theme.medium14pt
import com.whereareyounow.ui.theme.medium16pt
import com.whereareyounow.util.clickableNoEffect
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun FeedBookMarkScreen(
    moveToBackScreen: () -> Unit
) {


    FeedBookMarkScreen(
        isContent = true,
        moveToBackScreen = moveToBackScreen
    )

}

@Composable
private fun FeedBookMarkScreen(
    isContent: Boolean,
    moveToBackScreen: () -> Unit
) {
    val myPageViewModel: MyPageViewModel = hiltViewModel()
    val data = myPageViewModel.feedBookMarkState.collectAsState().value
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = TOP_BAR_HEIGHT.dp)
    ) {
        item{
            OneTextOneIconTobBar(
                title = "피드 책갈피",
                firstIcon = R.drawable.ic_back,
                firstIconClicked = { moveToBackScreen() }
            )
            Gap(10)
        }

//        if(data ==null) {
//            item{
//
//                Box(
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .padding(start = 15.dp, end = 15.dp),
////                    horizontalAlignment = Alignment.CenterHorizontally
//                ) {
//                    Column(
//                        modifier = Modifier.fillMaxSize(),
//                        horizontalAlignment = Alignment.CenterHorizontally
//                    ) {
//                        Gap(120)
//                        Box(
//                            modifier = Modifier
//                                .fillMaxSize(),
//                            contentAlignment = Alignment.Center
//                        ) {
//                            Image(
//                                modifier = Modifier.width(300.dp),
//                                painter = painterResource(R.drawable.img_feed_empty),
//                                contentDescription = null
//                            )
//                            Text(
//                                text = "아직은 피드에 책갈피를 하지 않았어요.\n" +
//                                        "특별한 추억을 오래도록 기억할 수 있게\n" +
//                                        "피드를 책갈피 해보세요!",
//                                style = medium14pt,
//                                color = Color(0xFF767676),
//                                modifier = Modifier.align(Alignment.Center).offset(y = 21.74.dp)
//                            )
//                        }
//                    }
//                }
//            }
//        } else {
//            items(data!!.totalPages){ index ->
            items(100){ index ->


                Gap(10)

                Column(
                    modifier = Modifier
                        .padding(start = 15.dp, end = 15.dp)
                        .fillMaxWidth()
                        .wrapContentHeight()

                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .height(74.dp)
                            .border(
                                border = BorderStroke(
                                    width = 1.dp,
                                    color = getColor().brandColor
                                ),
                                shape = RoundedCornerShape(14.dp)
                            )
                    ) {
                        Gap(10)
                        GlideImage(
                            imageModel = { "https://m.segye.com/content/image/2021/11/16/20211116509557.jpg" },
                            modifier = Modifier
                                .size(50.dp)
                                .offset(y = 14.dp)
                                .clip(RoundedCornerShape(14.dp)),
                        )
                        Gap(6)
                        Column(
                            modifier = Modifier.padding(top  = 16.dp),
                            verticalArrangement = Arrangement.Center
                        ) {
                            Row{
                                Text(
                                text = "2024.06.05",
                                    //위에꺼지우고
//                                    text = formatStartTime(data.content!![index].startTime),
                                    style = medium14pt,
                                    color = Color(0xFF767676)
                                )
                                Gap(10)
                                Text(
                                text = "여의도 한강 공원",
//                                    text = data.content[index].location,
                                    style = medium14pt,
                                    color = Color(0xFF767676)
                                )
                            }
                            Row{
                                Text(
                                text = "96즈 네번째 피크닉 다녀온 날",
//                                    text = data.content!![index].title,
                                    style = medium16pt,
                                    color = Color(0xFF222222)
                                )
                                Spacer(Modifier.weight(1f))
                                Image(
                                    painter = painterResource(id = R.drawable.ic_vertical_dot),
                                    contentDescription = "",
                                    modifier = Modifier.clickableNoEffect {

                                    }
                                )
                            }
                        }
                    }
                    Gap(10)
                    val pagerState = rememberPagerState(
                        pageCount = {
                            // 여기에도 지금 어디 사진.size
                            5
                        },
                        initialPage = 0
                    )
                    val isVisible = remember { mutableStateOf(false) }
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .height(290.dp)
                            .fillMaxWidth(),
                        contentAlignment = Alignment.BottomEnd
                    ) {
                        HorizontalPager(
                            state = pagerState,
                            modifier = Modifier
                                .height(290.dp)
                                .onSizeChanged {
                                    if (it.width > 0 && it.height > 0) isVisible.value = true
                                }
                        ) { page ->
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(6.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(Color.White),
                                    contentAlignment = Alignment.BottomEnd
                                ) {

                                    GlideImage(
                                        imageModel = { "https://m.segye.com/content/image/2021/11/16/20211116509557.jpg" },
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .height(290.dp)
//                                        .offset(y = 14.dp)
                                            .clip(RoundedCornerShape(6.dp)),
                                    )
                                    // 삭제 하면 안됨.
//                            GlideImage(
//                                imageModel = { testSecondImages[page].image },
//                            )
                                }
                            }
                        }
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(end = 12.dp, bottom = 11.dp),
                            contentAlignment = Alignment.CenterEnd
                        ) {
                            Row(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(40.dp))
                                    .background(Color(0xFF444444))
                                    .width(42.dp)
                                    .height(18.dp)
                                    .alpha(0.6f),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "${pagerState.currentPage + 1}",
                                    style = medium12pt,
                                    color = Color(0xFFFFFFFF)
                                )
                                Text(
                                    text = " / ",
                                    style = medium10pt,
                                    color = Color(0xFFF0F0F0)
                                )
                                Text(
                                    text = "${pagerState.pageCount}",
                                    style = medium12pt,
                                    color = Color(0xFFF0F0F0)
                                )
                            }
                        }
                    }
                    Gap(6)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ){
                        Image(
                            painter = painterResource(id = R.drawable.ic_bookmark_filled),
                            contentDescription = "",
                            colorFilter = ColorFilter.tint(getColor().brandColor)
                        )
                    }
                    Gap(6)
                }
            }
            // 아래 중괄호가 else 문 끝
//        }
    }

}


fun formatStartTime(startTime: String): String {
    val dateTime = LocalDateTime.parse(startTime, DateTimeFormatter.ISO_DATE_TIME)
    val formatter = DateTimeFormatter.ofPattern("yy.MM.dd")
    return dateTime.format(formatter)
}

@Preview
@Composable
fun PreviewFeedBookMarkScreen() {
    FeedBookMarkScreen(
        isContent = true,
        moveToBackScreen = {}
    )
}

@Preview
@Composable
fun PreviewEmptyData() {
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
                contentAlignment = Alignment.Center
            ) {
                Image(
                    modifier = Modifier.width(300.dp),
                    painter = painterResource(R.drawable.img_feed_empty),
                    contentDescription = null
                )
                Text(
                    text = "아직은 피드에 책갈피를 하지 않았어요.\n" +
                            "특별한 추억을 오래도록 기억할 수 있게\n" +
                            "피드를 책갈피 해보세요!",
                    style = medium14pt,
                    color = Color(0xFF767676),
                    modifier = Modifier.align(Alignment.Center).offset(y = 21.74.dp)
                )
            }
        }
    }
}