package com.whereareyounow.ui.main.home

import android.util.Log
import androidx.compose.animation.core.LinearEasing
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.skydoves.landscapist.glide.GlideImage
import com.whereareyounow.R
import com.whereareyounow.data.ViewType
import com.whereareyounow.ui.main.MainViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive

@OptIn(ExperimentalMaterial3Api::class)
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
    val density = LocalDensity.current
    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = SheetState(
            initialValue = SheetValue.Expanded,
            skipPartiallyExpanded = false,
            density = density,
            skipHiddenState = true
        )
    )
    HomeScreens(
        isContent = true,
        testSecondImage,
        testThirdData,
        testFourthData,
        testSeventhData,
        paddingValues,
        onAlarmIconClick = {
            // TODO 알림페이지로 이동 (내가 ㄴㄴ건들ㄴ) 친구 ㅇㅇ
        },
        onMyIconClick = {
            // TODO 마이페이지로 이동
            mainViewModel.updateViewType(ViewType.MyPage)
        },
        sheetState = scaffoldState,
        alarmBoolean = true
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
    onMyIconClick: () -> Unit,
    onAlarmIconClick: () -> Unit,
    sheetState: BottomSheetScaffoldState,
    // 서버알림 데이터에서따로봅아올거임 예삐
    alarmBoolean: Boolean
) {
    val horizontalPagerState = rememberPagerState(
        pageCount = {
            // 여기에도 지금 어디 사진.size
            5
        },
        initialPage = 0
    )
    LaunchedEffect(sheetState.bottomSheetState.currentValue) {
        snapshotFlow { sheetState.bottomSheetState.currentValue }
            .collect { value ->
                Log.d("sfjlsiefj", value.toString())
            }
    }
    BottomSheetScaffold(
        scaffoldState = sheetState,
        sheetContainerColor = Color.White,
        sheetContentColor = Color.Black,
        sheetContent = {
            // currentvlaue : 손대고 때면 state바낌
            // targetValue : 손대고움직이자마자 state바낌
            // hide는 상태에서 제거했음위에서
            when (sheetState.bottomSheetState.currentValue) {

                // 1~99 화면
                SheetValue.PartiallyExpanded -> {

                    when (sheetState.bottomSheetState.targetValue) {
                        // 위로 올릴때
                        SheetValue.Expanded -> {
                            SeventhScreen(testSeventhData)
                        }

                        else -> {
                            Column(
                                modifier = Modifier.height(442.dp)
                            ) {
                                // 여백처리
                            }
                        }
                    }
                }

                // 100 호면
                SheetValue.Expanded -> {
                    SeventhScreen(testSeventhData)
                }

                // 0 -> pass
                else -> {
                    SeventhScreen(testSeventhData)
                }

            }
        },
        modifier = Modifier.padding(paddingValues),
        sheetPeekHeight = 115.dp,
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        sheetDragHandle = {
            Column {
                Spacer(Modifier.height(10.dp))
                Column(
                    modifier = Modifier
                        .width(133.dp)
                        .height(6.dp)
                        .background(Color.Black),
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
                First(onAlarmIconClick, onMyIconClick, alarmBoolean)
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
fun First(onIconClick: () -> Unit, onMyIconClick: () -> Unit, alarmBoolean: Boolean) {
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
        //TODO 준성님 -> 아이콘빨간색다운
        if (alarmBoolean) {
            FirstIconBadge(
                onIconClick = { onIconClick() },
            )
        } else {
            Icon(
                painter = painterResource(id = R.drawable.icon_bell),
                contentDescription = "",
                modifier = Modifier.clickable { onIconClick() },
            )
        }
        Icon(
            painter = painterResource(id = R.drawable.ic_user_outlined),
            contentDescription = "",
            tint = Color(0xFF6236E9),
            modifier = Modifier.clickable { onMyIconClick() }
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Second(pagerState: PagerState, testSecondImages: List<HomeViewModel.SecondDataModel>) {
    val isVisible = remember { mutableStateOf(false) }
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
                        //TODO 사진 몇촌지 , 5번째->1번째스크롤하는지안하는지 : 부교님
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
                        .onSizeChanged {
                            if (it.width > 0 && it.height > 0) isVisible.value = true
                        }
                ) { page ->
                    Card(
                        modifier = Modifier.fillMaxSize(),
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
fun ThirdScreen(testThirdDatas: List<HomeViewModel.ThirdDataModel>) {
    if (testThirdDatas.isEmpty()) {
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
        val day = testThirdDatas[0].day
        val dayContent = testThirdDatas[0].dayContent
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
                    Row {
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
                    SixthText(data.content)
                    Spacer(Modifier.size(10.dp))
                    Box(modifier = Modifier
                        .background(Color.Gray)
                        .height(1.dp)
                        .fillMaxWidth())
                    Spacer(Modifier.size(12.dp))

                    //10 회색선 12
                    // TODO 마지막에도 회색선 + 여백-> 부교님

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


@Composable
fun SeventhScreen(
    sevenDataModel: List<HomeViewModel.SevenDataModel>,
) {
    LazyColumn(
        modifier = Modifier
            .height(442.dp)
            .padding(start = 20.dp, end = 20.dp)
    ) {
        item {
            //TODO 묶어서처리하기
            Spacer(Modifier.size(11.dp))
            Text(
                sevenDataModel[0].day,
                color = Color(0xFF7B50FF),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            // 시간되면 위치확인하기 버튼색상변경
            val timeAble by remember { mutableStateOf(true) }
            val timeOkColor = if (timeAble) Color(0xFF7B50FF) else Color(0xFFABABAB)
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column {
                    Text(sevenDataModel[0].title1, fontSize = 18.sp)
                    Text(sevenDataModel[0].subTitle1, fontSize = 14.sp, color = Color(0xFF999999))
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
                    Text(sevenDataModel[0].title2, fontSize = 18.sp)
                    Text(sevenDataModel[0].subTitle2, fontSize = 14.sp, color = Color(0xFF999999))
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
                    Text(sevenDataModel[0].title3, fontSize = 18.sp)
                    Text(sevenDataModel[0].subTitle3, fontSize = 14.sp, color = Color(0xFF999999))
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
                modifier = Modifier.fillMaxWidth()
            ) {
                Column {
                    Text(sevenDataModel[0].title4, fontSize = 18.sp)
                    Text(sevenDataModel[0].subTitle4, fontSize = 14.sp, color = Color(0xFF999999))
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
                modifier = Modifier.fillMaxWidth()
            ) {
                Column {
                    Text(sevenDataModel[0].title5, fontSize = 18.sp)
                    Text(sevenDataModel[0].subTitle5, fontSize = 14.sp, color = Color(0xFF999999))
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
                    Text(sevenDataModel[0].title6, fontSize = 18.sp)
                    Text(sevenDataModel[0].subTitle6, fontSize = 14.sp, color = Color(0xFF999999))
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
    style: TextStyle = LocalTextStyle.current,
) {
    val customEllipsis = "... 더보기"
    val textLayoutResultState = remember { mutableStateOf<TextLayoutResult?>(null) }
    val finalTextState = remember { mutableStateOf(text) }

    LaunchedEffect(Unit) {
        val textLayoutResult = textLayoutResultState.value
        if (textLayoutResult != null && textLayoutResult.lineCount == 3) {
            val lastTextIndexNumber = textLayoutResult.getLineEnd(2, true) - 4
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
                style = SpanStyle(color = Color(0xFF7B50FF), fontWeight = FontWeight.Bold),
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


@Composable
fun FirstIconBadge(
    onIconClick: () -> Unit,
) {
    Box(
        modifier = Modifier.clickable { onIconClick() }
    ) {
        Icon(
            painter = painterResource(id = R.drawable.icon_bell),
            contentDescription = "",
            modifier = Modifier
                .size(24.dp),
            tint = Color(0xFF6236E9)
        )
        Box(
            modifier = Modifier
                .size(10.dp)
                .background(Color.Red, shape = CircleShape)
                .align(Alignment.BottomEnd)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_redcircle),
                contentDescription = "",
                tint = Color.Red,
                modifier = Modifier
                    .size(8.dp)
                    .align(Alignment.Center)
            )
        }

    }
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

