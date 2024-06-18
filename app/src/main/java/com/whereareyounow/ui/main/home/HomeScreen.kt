package com.whereareyounow.ui.main.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandIn
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.skydoves.landscapist.glide.GlideImage
import com.whereareyounow.R
import com.whereareyounow.data.ViewType
import com.whereareyounow.data.globalvalue.TOP_BAR_HEIGHT
import com.whereareyounow.ui.component.CustomTopBar
import com.whereareyounow.ui.main.MainViewModel
import com.whereareyounow.ui.theme.medium18pt
import com.whereareyounow.util.clickableNoEffect
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun HomeScreen(
    paddingValues: PaddingValues,
    viewModel: HomeViewModel = hiltViewModel(),
    mainViewModel: MainViewModel = hiltViewModel(),
) {
    val str = viewModel.string.collectAsState().value
    val testFourthData = viewModel.fourthData.collectAsState().value
    val testThirdData = viewModel.thirdData.collectAsState().value
    val testSecondImage = viewModel.secondImage.collectAsState().value
    val testSeventhData = viewModel.sevenData.collectAsState().value
    val isVisible1 by remember { mutableStateOf(MutableTransitionState(false)) }
    var isVisible2 by remember { mutableStateOf(false) }
    val density = LocalDensity.current

    val viewType = mainViewModel.viewType.collectAsState().value

    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = SheetState(
            initialValue = SheetValue.Hidden,
            skipPartiallyExpanded = false,
            density = density
        )
    )
    val scope = rememberCoroutineScope()
    val bottomSheetHeight by remember { mutableStateOf(0.dp) }


    HomeScreens(
        isContent = true,
        testSecondImage,
        testThirdData,
        testFourthData,
        testSeventhData,
        paddingValues,
        onAlarmIconClick = {
            // TODO 알림페이지로 이동
            isVisible1.targetState = isVisible1.currentState.not()
//            isVisible2 = !isVisible2

        },
        onMyIconClick = {
            // TODO 마이페이지로 이동
            mainViewModel.updateViewType(ViewType.MyPage)

        },
//        isVisible = isVisible,
        sheetState = scaffoldState,
        sheetHeignt = bottomSheetHeight
    )
    LaunchedEffect(isVisible1) {
        isVisible1.targetState = isVisible1.currentState
    }

//    if (isVisible1.currentState) {
    AnimatedVisibility(
        visibleState = isVisible1,
//            visibleState = MutableTransitionState<Boolean>(true),
        enter = slideInHorizontally(
            animationSpec = tween(durationMillis = 3000),
            initialOffsetX = { it }
        ),
        exit = slideOutHorizontally(
            animationSpec = tween(durationMillis = 3000),
            targetOffsetX = { it }

        ),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 100.dp)
        ) {

            TestDa(isVisible1)

            AlertDialog(onDismissRequest = { /*TODO*/ }, confirmButton = { /*TODO*/ })
            
        }
    }
//    }

}





/////////////////////////////////////////////////////////////////////////////////
// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!네비게이션루트해야되는지말아야되는지??????????????????????????? //
@Composable
fun TestDa(isVisible1 : MutableTransitionState<Boolean>) {
//    CustomTopBar(
//        title = "알림",
//        onBackButtonClicked = {
//        //TODO 값변경
//        }
//    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        AlarmTopBar(
            title = "알림",
            onBackButtonClicked = {
                isVisible1.targetState = isVisible1.currentState.not()

            }
        )

        Text("아날나러ㅣㄴ러ㅣ너리널", color = Color.White)
        Text("아날나러ㅣㄴ러ㅣ너리널", color = Color.White)
        Text("아날나러ㅣㄴ러ㅣ너리널", color = Color.White)
        Text("아날나러ㅣㄴ러ㅣ너리널", color = Color.White)
        Text("아날나러ㅣㄴ러ㅣ너리널", color = Color.White)
        Text("아날나러ㅣㄴ러ㅣ너리널", color = Color.White)
        Text("아날나러ㅣㄴ러ㅣ너리널", color = Color.White)
    }
}


@Composable
fun AlarmTopBar(
    modifier: Modifier = Modifier,
    title: String,
    onBackButtonClicked: () -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(TOP_BAR_HEIGHT.dp)
            .drawBehind {
                val strokeWidth = 1.dp.toPx()
                val y = size.height - strokeWidth / 2
                drawLine(
                    color = Color(0xFFC9C9C9),
                    start = Offset(0f, y),
                    end = Offset(size.width, y),
                    strokeWidth = strokeWidth
                )
            },
//            .padding(start = 15.dp, end = 15.dp),
        contentAlignment = Alignment.Center,

    ) {
        Image(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .size(30.dp)
                .padding(end = 8.dp)
                .clickableNoEffect { onBackButtonClicked() },
            painter = painterResource(id = R.drawable.icon_delete),
            contentDescription = null
        )
        Text(
            text = title,
            color = Color(0xFF000000),
            style = medium18pt,
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = 15.dp),
        )
    }
}


//////////////////////////////////////////////////////////////////////////////////

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
private fun HomeScreens(
    isContent: Boolean,
    testSecondImage: List<HomeViewModel.SecondDataModel>,
    testThirdData: List<HomeViewModel.ThirdDataModel>,

    testFourthData: List<HomeViewModel.FourthDataModel>,
    testSeventhData: List<HomeViewModel.SevenDataModel>,
    paddingValues: PaddingValues,
    onMyIconClick: () -> Unit,
    onAlarmIconClick: () -> Unit,
    sheetState: BottomSheetScaffoldState,
    sheetHeignt: Dp
) {
    val horizontalPagerState = rememberPagerState(
        pageCount = {
            // 여기에도 지금 어디 사진.size
            5
        },
        initialPage = 0
    )
    val scope = rememberCoroutineScope()


    BottomSheetScaffold(
        scaffoldState = sheetState,
        sheetContainerColor = Color.White,
        sheetContentColor = Color.Black,
        sheetContent = {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(442.dp)
                    .padding(paddingValues)
            ) {

                SeventhScreen(testSeventhData, sheetState, sheetHeignt)

            }

        },
        // TODO 물어보기
        modifier = Modifier.padding(paddingValues),
        sheetPeekHeight = 110.dp,
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        sheetDragHandle = {
            Column(
            ) {
                Spacer(Modifier.height(10.dp))
                Column(
                    modifier = Modifier
                        .width(133.dp)
                        .height(6.dp)
                        .background(Color.Black)
                        .clickable {
                            scope.launch {
                                sheetState.bottomSheetState.expand()
                            }
                        },
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    BottomSheetDefaults.DragHandle()
                }
            }

        }
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
                First(onAlarmIconClick, onMyIconClick)
            }
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
fun First(onIconClick: () -> Unit, onMyIconClick: () -> Unit) {
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
        Image(
            painter = painterResource(id = R.drawable.icon_bell),
            contentDescription = "",
            modifier = Modifier
                .size(width = 16.dp, height = 19.dp)
                .clickable {
                    onIconClick()
                },
            colorFilter = ColorFilter.tint(Color(0xFF6236E9)),

            )
        Spacer(Modifier.size(7.53.dp))
        Icon(
            painter = painterResource(id = R.drawable.person),
            contentDescription = "",
            tint = Color(0xFF6236E9),
            modifier = Modifier
                .size(width = 16.dp, height = 19.dp)
                .clickable {
                    onMyIconClick()
                }
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
                delay(2000L)
                val nextPage =
                    if (pagerState.currentPage + 1 == pagerState.pageCount) 0 else pagerState.currentPage + 1

                pagerState.animateScrollToPage(
                    nextPage,
                    animationSpec = tween(
                        durationMillis = 100,
                        easing = LinearEasing
                    )
                )
            }

        }
        Column {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(20.dp))
            ) {

                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier
                        .height(190.dp)
//                .onGloballyPositioned { isVisible.value = true }
                        .onSizeChanged {
                            if (it.width > 0 && it.height > 0) isVisible.value = true
                        },
                ) { page ->
                    Card(
                        modifier = Modifier.fillMaxSize(),
//                    .applyCubic(pagerState, page),
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


                        }
                    }
                }

                Box(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    Text(
                        text = AnnotatedString.Builder().apply {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                append("${pagerState.currentPage + 1}")
                            }
                            append(" / 5")
                        }.toAnnotatedString(),
                        color = Color.White,
                        fontSize = 12.sp,
                        textAlign = TextAlign.End,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 170.dp, end = 10.dp)
                    )
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

                    JJinText(data.content)
//                    Text(
////                        text = data.content,
//                        text = AnnotatedString.Builder().apply {
//                            withStyle(
//                                style = SpanStyle(
//                                    fontWeight = FontWeight.Bold,
//                                    color = Color(0xFF999999),
//                                    fontFamily = FontFamily(Font(R.font.notosanskr_medium)),
//                                    fontSize = 14.sp
//                                )
//                            ) {
//                                append(
//                                    if (data.content.length > 92) data.content.take(88) else data.content
////                                    data.content
//                                )
//                            }
//                            if (data.content.length > 92) {
//                                withStyle(
//                                    style = SpanStyle(
//                                        color = Color(0xFF6236E9),
//                                        fontFamily = FontFamily(Font(R.font.notosanskr_bold))
//                                    )
//                                ) {
//                                    append("... 더 보기")
//
//                                }
//                            }
//
//                        }.toAnnotatedString(),
//                        maxLines = 3,
//                        onTextLayout = { layoutResult ->
//                            if (layoutResult.lineCount > 3) {
//                                Log.d("sjflsejiljesflsj", "3줄넘음")
//                            }
//                        },
//                        overflow = TextOverflow.Clip
//                    )
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
    scaffoldState: BottomSheetScaffoldState,
    sheetHeignt: Dp
) {
    // TODO 예비
    //    Spacer(Modifier.size(11.41.dp))
    LazyColumn(
        modifier = Modifier
            .height(442.dp)
            .padding(start = 20.dp, end = 20.dp)
    ) {
        item {
            Spacer(Modifier.size(11.dp))
            Text(
                SevenDataModel[0].day,
                color = Color(0xFF7B50FF),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            val timeAble by remember { mutableStateOf(true) }
            val timeOkColor = if (timeAble) Color(0xFF7B50FF) else Color(0xFFABABAB)


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
            Spacer(Modifier.size(12.dp))
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
            Spacer(Modifier.size(12.dp))

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
            Spacer(Modifier.size(12.dp))

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
            Spacer(Modifier.size(12.dp))

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
            Spacer(Modifier.size(12.dp))

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
            Spacer(Modifier.size(22.dp))
        }

    }

}


@Composable
fun JJinText(text: String) {
    SixthText(text = text)
}

@Composable
fun SixthText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontStyle: FontStyle? = null,
    fontWeight: FontWeight? = null,
    fontFamily: FontFamily? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    lineHeight: TextUnit = TextUnit.Unspecified,
    softWrap: Boolean = true,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    style: TextStyle = LocalTextStyle.current,
) {
    // 비포 -> 리컴포지션 ㅈㄹ ㅈ같네씨이발
//    val ellipsis = "... 더보기"
//    val textLayoutResultState = remember { mutableStateOf<TextLayoutResult?>(null) }
//
//    val finalText = remember(text, textLayoutResultState.value) {
//        val textLayoutResult = textLayoutResultState.value ?: return@remember text
//        if (textLayoutResult.lineCount == 3) {
//            val lastVisibleCharIndex = textLayoutResult.getLineEnd(2, true) - 5
//            val truncatedText = text.substring(0, lastVisibleCharIndex).trimEnd()
//            truncatedText + ellipsis
//        } else {
//            text
//        }
//    }

    val customEllipsis = "... 더보기"
    val textLayoutResultState = remember { mutableStateOf<TextLayoutResult?>(null) }
    val finalTextState = remember { mutableStateOf(text) }

    LaunchedEffect(Unit) {
        val textLayoutResult = textLayoutResultState.value
        if (textLayoutResult != null && textLayoutResult.lineCount == 3) {
            val lastTextIndexNumber = textLayoutResult.getLineEnd(2, true) - 5
            val truncatedText = text.substring(0, lastTextIndexNumber).trimEnd()
            finalTextState.value = truncatedText + customEllipsis
        } else {
            finalTextState.value = text
        }
    }

    val annotatedText = buildAnnotatedString {
        append(finalTextState.value)
        if (finalTextState.value.endsWith(customEllipsis)) {
            val ellipsisStartIndex = finalTextState.value.indexOf(customEllipsis)
            addStyle(
                style = SpanStyle(color = Color.Red),
                start = ellipsisStartIndex,
                end = ellipsisStartIndex + customEllipsis.length
            )
        }
    }

    Text(
        text = annotatedText,
        modifier = modifier,
        color = color,
        fontSize = fontSize,
        fontStyle = fontStyle,
        fontWeight = fontWeight,
        fontFamily = fontFamily,
        letterSpacing = letterSpacing,
        textDecoration = textDecoration,
        textAlign = textAlign,
        lineHeight = lineHeight,
        softWrap = softWrap,
        maxLines = 3,
        overflow = TextOverflow.Clip,
        onTextLayout = { textLayoutResultState.value = it },
        style = style
    )
}


@Preview(showBackground = true)
@Composable
private fun PreviewExpandableText() {
    SixthText(" 정말 간만에 다녀온 96즈끼리 다녀온 여의도한강공원! 너무 간만이라 치킨 피자 어디에서 가져오는지도 헷갈리고 돗자리 깔 타이밍에 뭔 바람이 그렇게 부는지도 몰랐는데 티원은 젠지를 언제쯤이길수있을까 하하슬프네")
}


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

