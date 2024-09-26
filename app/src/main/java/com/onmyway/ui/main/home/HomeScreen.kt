package com.onmyway.ui.main.home

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
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
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import com.skydoves.landscapist.glide.GlideImage
import com.onmyway.R
import com.onmyway.data.ViewType
import com.onmyway.ui.main.MainViewModel
import com.onmyway.ui.main.mypage.byebye.Gap
import com.onmyway.ui.theme.getColor
import com.onmyway.ui.theme.medium10pt
import com.onmyway.ui.theme.medium12pt
import com.onmyway.ui.theme.medium14pt
import com.onmyway.ui.theme.medium16pt
import com.onmyway.ui.theme.medium20pt
import com.onmyway.util.clickableNoEffect
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

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

    HomeScreen(
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
//    sepeateTest()
}


@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
private fun HomeScreen(
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
//                    Column(
//                        modifier = Modifier.fillMaxWidth()
//                    ) {
//                        Text("gkgk")
//                    }
//                    SeventhScreen(testSeventhData)
                }

            }
        },
        modifier = Modifier,
        sheetPeekHeight = 45.dp,
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

        Column(
            modifier = Modifier
                .background(Color.White)

                .fillMaxWidth()
                .padding(start = 15.dp)
        ) {
            // ON MY WAY ROW
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                First(onAlarmIconClick, onMyIconClick, alarmBoolean)
            }

            Gap(10)

            Second(horizontalPagerState, testSecondImage)

            Gap(16)

            ThirdScreen(testThirdData)

            Gap(30)

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 12.dp , end = 10.dp)
            ) {
                Text(
                    text = "함께한 추억을 확인해보세요!",
                    style = medium20pt,
                    color = Color(0xFF222222)
                )
            }

            Gap(16)

            SixthScreen(testFourthData)

        }
    }
}

@Composable
fun First(onIconClick: () -> Unit, onMyIconClick: () -> Unit, alarmBoolean: Boolean) {

    Row(
        modifier = Modifier
            .height(46.dp)
            .padding(end = 15.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Gap(7)

        Image(
            painter = painterResource(id = R.drawable.ic_onmyway),
            contentDescription = ""
        )

        Spacer(Modifier.weight(1f))

        //TODO 준성님 -> 아이콘빨간색다운
        if (alarmBoolean) {
            Image(
                painter = painterResource(id = R.drawable.ic_bellred),
                contentDescription = "",
                modifier = Modifier
                    .size(34.dp)
                    .clickableNoEffect {
                        onIconClick()
                    }
            )
        } else {
            Image(
                painter = painterResource(id = R.drawable.icon_bell),
                contentDescription = "",
                modifier = Modifier
                    .size(34.dp)
                    .clickableNoEffect {
                        onIconClick()
                    }
            )
        }

        Image(
            painter = painterResource(id = R.drawable.ic_userd),
            contentDescription = "",
            modifier = Modifier
                .size(34.dp)
                .clickableNoEffect {
                    onMyIconClick()
                }
        )

    }
}

@Composable
fun Second(pagerState: PagerState, testSecondImages: List<HomeViewModel.SecondDataModel>) {
    val isVisible = remember { mutableStateOf(false) }
    if (testSecondImages.isEmpty()) {
        Box(
            modifier = Modifier.height(190.dp).padding(end = 15.dp),
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
        Column(
            modifier = Modifier.fillMaxWidth().padding(end = 15.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp)),
                contentAlignment = Alignment.BottomEnd
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
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(20.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.White),
                            contentAlignment = Alignment.BottomEnd
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_default_banner),
                                contentDescription = "",
                                modifier = Modifier.fillMaxSize()
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
                .border(1.dp, Color.Black, RoundedCornerShape(6.dp))
                .padding(end = 15.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(6.dp)
        ) {
            Box(
                modifier = Modifier.fillMaxWidth(),
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
                .padding(end = 15.dp)
                .fillMaxWidth()
                .border(1.5.dp, getColor().brandColor, RoundedCornerShape(6.dp)),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(6.dp)
        ) {
            Row(
                modifier = Modifier
                    .padding(start = 12.dp, end = 94.dp)
                    .fillMaxHeight(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(day, color = Color.Red)
                Spacer(Modifier.size(18.dp))
                Text(dayContent, color = Color.Black)
            }
        }
    }
}

@Composable
fun SixthScreen(testFourthData: List<HomeViewModel.FourthDataModel>) {
    if (testFourthData.isNotEmpty()) {
        val horizontalPageNum = remember { mutableIntStateOf(testFourthData.size) }
        val horizontalState = rememberPagerState(
            pageCount = {
                horizontalPageNum.intValue
            },
            initialPage = 0
        )

        HorizontalPager(
            state = horizontalState,
            // pageSpacing : 다음 칸과 간격
            pageSpacing = 12.dp,
            contentPadding = PaddingValues(start = 8.dp, end = 23.dp),
            modifier = Modifier
                .background(Color.White)
                .fillMaxWidth(),
//            flingBehavior = PagerDefaults.flingBehavior(
//                state = horizontalState,
//                snapAnimationSpec = spring(stiffness = Spring.StiffnessMedium)
//            )
//            ,

        ) { index ->
            val detailFeedData = testFourthData[index]
            Box(
                modifier = Modifier
                    .background(Color.White)
                    .fillMaxWidth()
                    .border(
                        border = BorderStroke(
                            width = 1.dp,
                            color = Color(0xFFD4D4D4)
                        ),
                        shape = RoundedCornerShape(16.dp)
                    )


            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 12.dp, top = 12.dp)
                        .background(Color.White),
                    verticalArrangement = Arrangement.Bottom
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {

                        GlideImage(
                            imageModel = { detailFeedData.image1 },
                            modifier = Modifier
                                .size(50.dp)
                                .offset(y = 4.dp)
                                .clip(RoundedCornerShape(14.dp)),
                        )

                        Gap(4)

                        Column(
                            modifier = Modifier
                                .weight(1f)
                        ) {

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(28.dp)
                                    .padding(start = 6.dp, end = 6.dp, top = 6.dp, bottom = 2.dp)
                            ) {
                                Text(
                                    text = detailFeedData.title,
                                    style = medium14pt,
                                    color = Color(0xFF666666)
                                )
                            }

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(26.dp)
                                    .padding(start = 6.dp, end = 6.dp, bottom = 4.dp),
                                verticalAlignment = Alignment.Top
                            ) {
                                Text(
                                    text = detailFeedData.subTitle,
                                    style = medium16pt,
                                    color = Color(0xFF222222)
                                )
                            }

                        }
                    }
                    Gap(4)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 12.dp)
                    ) {
                        SixthText(text = detailFeedData.content, test = horizontalState)
                    }
                    Gap(10)
                }
            }
        }
        Spacer(Modifier.size(10.dp))
    } else {
        Box(
            modifier = Modifier.height(200.dp).background(Color.White)
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
fun SixthText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Color(0xFF767676),
    fontSize: TextUnit = TextUnit.Unspecified,
    fontStyle: FontStyle? = null,
    fontWeight: FontWeight? = null,
    fontFamily: FontFamily? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = TextAlign.Unspecified,
    lineHeight: TextUnit = TextUnit.Unspecified,
    softWrap: Boolean = true,
    style: TextStyle = medium14pt,
    test: PagerState
) {
    val customEllipsis = "더보기"
    val textLayoutState = remember { mutableStateOf<TextLayoutResult?>(null) }
    val finalTextState = remember { mutableStateOf(text) }
    val currentPage = remember { test.currentPage }

    val horizontalPagerViewModel: HorizontalPagerViewModel = hiltViewModel()

    LaunchedEffect(Unit) {
        withContext(Dispatchers.Main) {
            val textLayoutResult = textLayoutState.value
            if (textLayoutResult != null && textLayoutResult.lineCount == 4) {
//                    if (textLayoutResult?.lineCount == 4) {
                val lastTextIndexNumber = textLayoutResult.getLineEnd(3, true) - 3
                // 끝에짤라
                val truncatedText = text.substring(0, lastTextIndexNumber).trimEnd()
                finalTextState.value = truncatedText + "... " + customEllipsis
            } else {
                finalTextState.value = text
            }
        }
    }


    val annotatedText = buildAnnotatedString {

        append(finalTextState.value)

        // 더보기로끝나면 더해
        if (finalTextState.value.endsWith(customEllipsis)) {
            val ellipsisStartIndex = finalTextState.value.indexOf(customEllipsis)
            addStyle(
                style = SpanStyle(color = getColor().brandColor , fontWeight = FontWeight.Bold),
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
        maxLines = 4,
        overflow = TextOverflow.Clip,
        onTextLayout = { textLayoutState.value = it },
        style = style
    )
}

@Composable
fun SeventhScreen(
    sevenDataModel: List<HomeViewModel.SevenDataModel>,
) {

    LazyColumn(
        modifier = Modifier
            .wrapContentHeight()
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

@HiltViewModel
class HorizontalPagerViewModel @Inject constructor(
    private val counter: ImageCounter,

    ) : ViewModel() {

    val firstPage = counter.firstCount
    fun firstSave(pageNum: Int) {
        counter.firstSave(pageNum)
    }

    val page = counter.count
    fun save(pageNum: Int) {
        counter.save(pageNum)
    }
}

@Singleton
class ImageCounter @Inject constructor() {
    private val _firstCount = MutableStateFlow(0)
    val firstCount = _firstCount.asStateFlow()

    fun firstSave(pageNum: Int) {
        _firstCount.value = pageNum
    }

    private val _count = MutableStateFlow(0)
    val count = _count.asStateFlow()

    fun save(pageNum: Int) {
        _count.value = pageNum
    }

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
                .size(34.dp),
            tint = getColor().brandColor
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