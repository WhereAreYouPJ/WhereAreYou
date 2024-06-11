package com.whereareyounow.ui.main.home

import android.util.Log
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.skydoves.landscapist.glide.GlideImage
import com.whereareyounow.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlin.math.absoluteValue

@Composable
fun HomeScreen(
    paddingValues: PaddingValues,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val str = viewModel.string.collectAsState().value
    val testFourthData = viewModel.fourthData.collectAsState().value
    val testThirdData = viewModel.thirdData.collectAsState().value
    val testSecondImage = viewModel.secondImage.collectAsState().value
    val testSeventhData = viewModel.sevenData.collectAsState().value
    var isVisible by remember { mutableStateOf(false) }


    HomeScreens(
        isContent = true,
        testSecondImage,
        testThirdData,
        testFourthData,
        testSeventhData,
        paddingValues,
        onIconClick = {
            isVisible = !isVisible
            Log.d("sfjlsefji", isVisible.toString())
        },
        isVisible = isVisible
    )
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
private fun HomeScreens(
    isContent: Boolean,
    testSecondImage: List<HomeViewModel.SecondDataModel>,
    testThirdData: List<HomeViewModel.ThirdDataModel>,

    testFourthData: List<HomeViewModel.FourthDataModel>,
    testSeventhData: List<HomeViewModel.SevenDataModel>,
    paddingValues: PaddingValues,
    isVisible: Boolean,
    onIconClick: () -> Unit
) {
    val horizontalPagerState = rememberPagerState(
        pageCount = {
            // 여기에도 지금 어디 사진.size
            5
        },
        initialPage = 0
    )

    val scaffoldState = rememberBottomSheetScaffoldState()

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetContainerColor = Color.White,
        sheetContentColor = Color.Black,
        sheetContent = {
            // 바텀 싯 내용
            if (isVisible) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(442.dp)
                        .padding(start = 20.dp, end = 20.dp)
                ) {
                    SeventhScreen(testSeventhData, isVisible, scaffoldState)
                }
            }

        },
        sheetPeekHeight = if (isVisible) 442.dp else 0.dp,
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 15.dp, end = 15.dp)
            ) {
                First(onIconClick)
            }
//            Column(
//                modifier = Modifier.height(442.dp),
//                verticalArrangement = Arrangement.Bottom
//            ) {
//                SeventhScreen(testSeventhData, isVisible , scaffoldState)
//
//            }
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {

                item {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(start = 15.dp, end = 15.dp)
                    ) {
                        Spacer(Modifier.size(10.dp))

                        Second(horizontalPagerState, testSecondImage)
                        Spacer(Modifier.size(16.dp))

                        ThirdScreen(testThirdData)
                    }
                }
                item {
                    Spacer(Modifier.size(14.dp))
                    FourthScreen()
                }
                item {
                    Spacer(Modifier.size(16.dp))
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(start = 15.dp, end = 25.dp)
                    ) {
                        FifthScreen()
                    }
                    Spacer(Modifier.size(12.dp))

                }
                item {
                    SixthScreen(testFourthData)
                }


            }
        }

    }


}

@Composable
fun First(onIconClick: () -> Unit) {

    Row(
        modifier = Modifier
            .height(46.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            "지금어디?", modifier = Modifier
                .weight(1f),
            fontSize = 20.sp,
            color = Color(0xFF6236E9),
            fontFamily = FontFamily(Font(R.font.ttangsbudaejjigae))
        )
        Icon(
            painter = painterResource(id = R.drawable.alarm),
            contentDescription = "",
            modifier = Modifier
                .size(width = 16.dp, height = 19.dp)
                .clickable {
                    onIconClick()
                },
            tint = Color(0xFF6236E9),

            )
        Spacer(Modifier.size(7.53.dp))
        Icon(
            painter = painterResource(id = R.drawable.person),
            contentDescription = "",
            tint = Color(0xFF6236E9),
            modifier = Modifier.size(width = 16.dp, height = 19.dp)
        )

    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Second(pagerState: PagerState, testSecondImage: List<HomeViewModel.SecondDataModel>) {
    val isVisible = remember { mutableStateOf(false) }
    val testSecondImages = testSecondImage

    if (testSecondImages.isEmpty()) {

        Box(
            modifier = Modifier.height(190.dp),
            contentAlignment = Alignment.Center,

            ) {

            Text("사지 없을 때", textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
        }


    } else {
        LaunchedEffect(key1 = Unit) {
            while (isActive) {
                delay(3000L)
                val nextPage =
                    if (pagerState.currentPage + 1 >= pagerState.pageCount) 0 else pagerState.currentPage + 1

                pagerState.animateScrollToPage(
                    nextPage,
                    animationSpec = tween(
                        durationMillis = 500,
                        easing = FastOutSlowInEasing
                    )
                )
            }

        }
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .height(190.dp)
                .onGloballyPositioned { isVisible.value = true }
                .onSizeChanged { if (it.width > 0 && it.height > 0) isVisible.value = true },
        ) { page ->
            Card(
                modifier = Modifier
                    .applyCubic(pagerState, page),
                shape = RoundedCornerShape(20.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White),
                    contentAlignment = Alignment.BottomEnd
                ) {
                    GlideImage(
                        imageModel = { testSecondImages[page].image },
                    )

                    Card(
                        modifier = Modifier
                            .width(60.dp)
                            .height(30.dp)
                            .padding(end = 12.dp, bottom = 12.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.Gray),
                        shape = RoundedCornerShape(20.dp),

                        ) {
                        Text(
                            text = AnnotatedString.Builder().apply {
                                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                    append("${page + 1}")
                                }
                                append(" / 5")
                            }.toAnnotatedString(),
                            color = Color.White,
                            fontSize = 12.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 2.dp)
                        )
                    }
                }
            }
        }
    }

}

@Composable
fun ThirdScreen(testThirdData: List<HomeViewModel.ThirdDataModel>) {

    if (testThirdData.isEmpty()) {
        Card(
            modifier = Modifier
                .height(52.dp)
                .fillMaxWidth()
                .border(1.dp, Color.Black, RoundedCornerShape(4.dp)),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(4.dp)
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("일정이 없습니다.", color = Color.Gray)
            }
        }
    } else {
        val day = testThirdData[0].day
        val dayContent = testThirdData[0].dayContent
        Card(
            modifier = Modifier
                .height(52.dp)
                .fillMaxWidth()
                .border(1.dp, Color.Black, RoundedCornerShape(4.dp)),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(4.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(Modifier.size(12.dp))
                Text(day, color = Color.Red)
                Spacer(Modifier.size(19.dp))
                Text(dayContent, color = Color.Black)

            }
        }
    }

//    Image(
//        modifier = Modifier
//            .fillMaxWidth()
//            .height(52.dp),
//        painter = painterResource(id = R.drawable.bottomnavbar_home),
//        contentDescription = ""
//    )
}

@Composable
fun FourthScreen() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(4.dp)
            .background(Color(0xFFDDDDDD))
    ) {
    }
}

@Composable
fun FifthScreen() {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("함께한 추억을 확인해 보세요!", fontSize = 20.sp)
        Icon(
            painter = painterResource(id = R.drawable.arrow_forward_ios_fill0_wght100_grad0_opsz24),
            contentDescription = "",
            modifier = Modifier.size(30.dp)
        )
    }
}

@Composable
fun SixthScreen(testFourthData: List<HomeViewModel.FourthDataModel>) {


    if (testFourthData.isNotEmpty()) {
        testFourthData.forEach { data ->

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 20.dp, end = 20.dp),
            ) {
                Column {
                    Row(

                    ) {
                        GlideImage(
                            imageModel = { data.image1 },
                            modifier = Modifier
                                .size(50.dp)
                                .clip(RoundedCornerShape(4.dp)),
                        )
                        Spacer(Modifier.width(10.dp))
                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = data.title,
                                fontFamily = FontFamily(Font(R.font.notosanskr_medium)),
                                fontSize = 14.sp,
                                color = Color(0xFF666666)
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = data.subTitle,
                                fontSize = 16.sp,
                                fontFamily = FontFamily(Font(R.font.notosanskr_medium))

                            )
                            Spacer(modifier = Modifier.height(4.dp))
                        }
                    }
                    Spacer(Modifier.size(4.dp))


                    Text(
//                        text = data.content,
                        text = AnnotatedString.Builder().apply {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold , color = Color(0xFF999999) , fontFamily = FontFamily(Font(R.font.notosanskr_medium)) , fontSize = 14.sp)) {
                                append(
                                    if(data.content.length > 92) data.content.take(88) else data.content
//                                    data.content
                                )
                            }
                            if(data.content.length > 92) {
                                withStyle(style = SpanStyle(color = Color(0xFF6236E9) , fontFamily = FontFamily(Font(R.font.notosanskr_bold)))) {
                                    append("... 더 보기")

                                }
                            }

                        }.toAnnotatedString(),
                        maxLines = 3,
                        onTextLayout = { layoutResult ->
                            if(layoutResult.lineCount > 3) {
                                Log.d("sjflsejiljesflsj" , "3줄넘음")
                            }
                        },
                        overflow = TextOverflow.Clip
                    )
                    Spacer(Modifier.size(22.dp))

                }

            }

        }
    } else {
        Box(
            modifier = Modifier.height(200.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(top = 30.dp, start = 60.dp, end = 60.dp)
            ) {
                Text("오늘 하루 수고했던 일은", fontSize = 20.sp)
                Text("어떤 경험을 남기게 했나요?", fontSize = 20.sp)
                Text("", fontSize = 20.sp)
                Text("하루의 소중한 시간을 기록하고", fontSize = 20.sp)
                Text("오래 기억될 수 있도록 간직해보세요!", fontSize = 20.sp)

            }
        }
    }

}





@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SeventhScreen(
    SevenDataModel: List<HomeViewModel.SevenDataModel>,
    isVisible: Boolean,
    scaffoldState: BottomSheetScaffoldState
) {
//    val scaffoldState = rememberBottomSheetScaffoldState()
    Log.d("sfsfsfsfsfsf5151515151", isVisible.toString())

    if (isVisible) {
//        Log.d("slfjslfjslf" , isVisible.toString())
//        BottomSheetScaffold(
//            scaffoldState = scaffoldState,
//            sheetContainerColor = Color.White,
//            sheetContentColor = Color.Black,
//            sheetContent = {


        // 바텀 싯 내용
//                Column(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(442.dp)
//                        .padding(start = 20.dp, end = 20.dp)
//                ) {
        Text(
            SevenDataModel[0].day,
            color = Color(0xFF7B50FF),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        val timeAble by remember { mutableStateOf(true) }
        val timeOkColor = if (timeAble) Color(0xFF7B50FF) else Color(0xFFABABAB)
        Column {

            Row(
                modifier = Modifier.fillMaxWidth(),

                ) {
                Column {
                    Text(SevenDataModel[0].title1, fontSize = 18.sp)
                    Text(SevenDataModel[0].subTitle1, fontSize = 14.sp, color = Color(0xFF999999))
                }
                Spacer(Modifier.weight(1f))
                Button(
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(containerColor = timeOkColor),
                    shape = RoundedCornerShape(4.dp)


                ) {
                    Text("위치 확인하기", color = Color.White)

                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),

                ) {
                Column {
                    Text(SevenDataModel[0].title2, fontSize = 18.sp)
                    Text(SevenDataModel[0].subTitle2, fontSize = 14.sp, color = Color(0xFF999999))
                }
                Spacer(Modifier.weight(1f))

                Button(
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(containerColor = timeOkColor),
                    shape = RoundedCornerShape(4.dp)


                ) {
                    Text("위치 확인하기", color = Color.White)

                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),

                ) {
                Column {
                    Text(SevenDataModel[0].title3, fontSize = 18.sp)
                    Text(SevenDataModel[0].subTitle3, fontSize = 14.sp, color = Color(0xFF999999))
                }
                Spacer(Modifier.weight(1f))

                Button(
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(containerColor = timeOkColor),
                    shape = RoundedCornerShape(4.dp)

                ) {
                    Text("위치 확인하기", color = Color.White)

                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),

                ) {
                Column {
                    Text(SevenDataModel[0].title4, fontSize = 18.sp)
                    Text(SevenDataModel[0].subTitle4, fontSize = 14.sp, color = Color(0xFF999999))
                }
                Spacer(Modifier.weight(1f))

                Button(
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(containerColor = timeOkColor),
                    shape = RoundedCornerShape(4.dp)

                ) {
                    Text("위치 확인하기", color = Color.White)

                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),

                ) {
                Column {
                    Text(SevenDataModel[0].title5, fontSize = 18.sp)
                    Text(SevenDataModel[0].subTitle5, fontSize = 14.sp, color = Color(0xFF999999))
                }
                Spacer(Modifier.weight(1f))

                Button(
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(containerColor = timeOkColor),
                    shape = RoundedCornerShape(4.dp)

                ) {
                    Text("위치 확인하기", color = Color.White)

                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),

                ) {
                Column {
                    Text(SevenDataModel[0].title6, fontSize = 18.sp)
                    Text(SevenDataModel[0].subTitle6, fontSize = 14.sp, color = Color(0xFF999999))
                }
                Spacer(Modifier.weight(1f))

                Button(
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(containerColor = timeOkColor),
                    shape = RoundedCornerShape(4.dp)

                ) {
                    Text("위치 확인하기", color = Color.White)

                }
            }
        }
//                }


//            },
//        )

    }

}


@OptIn(ExperimentalFoundationApi::class)
fun Modifier.applyCubic(state: PagerState, page: Int, horizontal: Boolean = true): Modifier {
    return graphicsLayer {
        val pageOffset = state.offsetForPage(page)
        val offScreenRight = pageOffset < 0f
        val def = if (horizontal) {
            105f
        } else {
            -90f
        }
        val interpolated = FastOutLinearInEasing.transform(pageOffset.absoluteValue)
        val rotation = (interpolated * if (offScreenRight) def else -def).coerceAtMost(90f)
        if (horizontal) {
            rotationY = rotation
        } else {
            rotationX = rotation
        }

        transformOrigin = if (horizontal) {
            TransformOrigin(
                pivotFractionX = if (offScreenRight) 0f else 1f,
                pivotFractionY = .5f
            )
        } else {
            TransformOrigin(
                pivotFractionY = if (offScreenRight) 0f else 1f,
                pivotFractionX = .5f
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
fun PagerState.offsetForPage(page: Int) = (currentPage - page) + currentPageOffsetFraction

//@Preview(showBackground = true)
//@Composable
//private fun HomeScreenPreview() {
//    WhereAreYouTheme {
//        HomeScreens(
//            true,
//            listOf(HomeViewModel.fourthDataModel(
//                "https://m.segye.com/content/image/2021/11/16/20211116509557.jpg",
//
//
//                "d", "d", "d"
//            ))
//
//        )
//    }
//}

