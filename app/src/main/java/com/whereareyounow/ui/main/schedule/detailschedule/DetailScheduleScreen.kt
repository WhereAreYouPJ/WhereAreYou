package com.whereareyounow.ui.main.schedule.detailschedule

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import com.whereareyounow.R
import com.whereareyounow.data.detailschedule.DetailScheduleScreenUIState
import com.whereareyounow.data.detailschedule.MemberInfo
import com.whereareyounow.ui.theme.WhereAreYouTheme
import com.whereareyounow.ui.theme.nanumSquareNeo
import com.whereareyounow.util.popupmenu.CustomPopup
import com.whereareyounow.util.popupmenu.PopupPosition
import com.whereareyounow.util.popupmenu.PopupState

@Composable
fun DetailScheduleScreen(
    scheduleId: String,
    moveToDetailScheduleMapScreen: (String, Double, Double, List<MemberInfo>) -> Unit,
    moveToModifyScheduleScreen: (String, Double, Double, DetailScheduleScreenUIState) -> Unit,
    moveToBackScreen: () -> Unit,
    viewModel: DetailScheduleViewModel = hiltViewModel(),
) {
    val detailScheduleScreenUIState = viewModel.detailScheduleScreenUIState.collectAsState().value
    val isScheduleCreator = viewModel.isScheduleCreator.collectAsState().value
    DetailScheduleScreen(
        scheduleId = scheduleId,
        updateScheduleId = viewModel::updateScheduleId,
        detailScheduleScreenUIState = detailScheduleScreenUIState,
        isScheduleCreator = isScheduleCreator,
        deleteSchedule = viewModel::deleteSchedule,
        quitSchedule = viewModel::quitSchedule,
        moveToBackScreen = moveToBackScreen,
        moveToModifyScheduleScreen = {
            moveToModifyScheduleScreen(scheduleId, viewModel.destinationLatitude, viewModel.destinationLongitude, detailScheduleScreenUIState)
        },
        moveToDetailScheduleMapScreen = {
            moveToDetailScheduleMapScreen(scheduleId, viewModel.destinationLatitude, viewModel.destinationLongitude, detailScheduleScreenUIState.memberInfosList)
        }
    )
}

@Composable
private fun DetailScheduleScreen(
    scheduleId: String,
    updateScheduleId: (String) -> Unit,
    detailScheduleScreenUIState: DetailScheduleScreenUIState,
    isScheduleCreator: Boolean,
    deleteSchedule: (() -> Unit) -> Unit,
    quitSchedule: (() -> Unit) -> Unit,
    moveToBackScreen: () -> Unit,
    moveToModifyScheduleScreen: () -> Unit,
    moveToDetailScheduleMapScreen: () -> Unit,
) {
    val contentSpace = 30

    LaunchedEffect(Unit) {
        updateScheduleId(scheduleId)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        // 배경
        BackgroundContent()

        Column(
            modifier = Modifier
                .padding(start = 20.dp, top = 60.dp, end = 20.dp, bottom = 60.dp)
                .fillMaxSize()
                .background(
                    color = Color(0xFFFFFFFF),
                    shape = RoundedCornerShape(30.dp)
                )
                .padding(20.dp)
        ) {
            // 일정 제목
            ScheduleName(
                scheduleName = detailScheduleScreenUIState.scheduleName,
                isScheduleCreator = isScheduleCreator,
                moveToModifyScheduleScreen = moveToModifyScheduleScreen,
                deleteSchedule = { deleteSchedule(moveToBackScreen) },
                quitSchedule = { quitSchedule(moveToBackScreen) }
            )

            Spacer(Modifier.height(contentSpace.dp))

            // 날짜, 시간 정보
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier.size(30.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        modifier = Modifier.size(26.dp),
                        painter = painterResource(id = R.drawable.clock),
                        contentDescription = null
                    )
                }

                Spacer(Modifier.width(10.dp))

                Column {
                    // 날짜 정보
                    DateContent(
                        scheduleYear = detailScheduleScreenUIState.scheduleYear,
                        scheduleMonth = detailScheduleScreenUIState.scheduleMonth,
                        scheduleDate = detailScheduleScreenUIState.scheduleDate,
                        scheduleDayOfWeek = detailScheduleScreenUIState.scheduleDayOfWeek
                    )

                    // 시간 정보
                    TimeContent(
                        scheduleHour = detailScheduleScreenUIState.scheduleHour,
                        scheduleMinute = detailScheduleScreenUIState.scheduleMinute
                    )
                }
            }

            Spacer(Modifier.height(contentSpace.dp))

            // 목적지 정보
            DestinationContent(
                destinationName = detailScheduleScreenUIState.destinationName,
                destinationAddress = detailScheduleScreenUIState.destinationRoadAddress
            )

            Spacer(Modifier.height(contentSpace.dp))

            // 회원 리스트 정보
            MemberListContent(
                memberList = detailScheduleScreenUIState.memberInfosList,
                isLocationCheckEnabled = detailScheduleScreenUIState.isLocationCheckEnabled,
                moveToDetailScheduleMapScreen = moveToDetailScheduleMapScreen
            )

            Spacer(Modifier.height(contentSpace.dp))

            // 메모
            MemoContent(detailScheduleScreenUIState.memo)
        }
    }
}

@Composable
fun BackgroundContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = Color.LightGray
            )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(
                    brush = Brush.horizontalGradient(listOf(Color(0xFF4D6EC5), Color(0xFF5E54AF))),
                    alpha = 1f,
                    shape = RoundedCornerShape(bottomStart = 10.dp, bottomEnd = 10.dp)
                )
        )
    }
}

@Composable
fun ScheduleName(
    scheduleName: String,
    isScheduleCreator: Boolean,
    moveToModifyScheduleScreen: () -> Unit,
    deleteSchedule: () -> Unit,
    quitSchedule: () -> Unit
) {
    val popupState = remember { PopupState(false, PopupPosition.BottomLeft) }
    val density = LocalDensity.current.density
    Row(modifier = Modifier.height(IntrinsicSize.Min)) {
        Text(
            text = scheduleName,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF505050)
        )
        Spacer(Modifier.weight(1f))
        Box {
            CustomPopup(
                popupState = popupState,
                onDismissRequest = { popupState.isVisible = false }
            ) {
                CompositionLocalProvider(LocalDensity provides Density(density, fontScale = 1f)) {
                    Column(
                        modifier = Modifier
                            .padding(top = 10.dp, bottom = 20.dp)
                            .width(100.dp)
                            .shadow(
                                elevation = 4.dp,
                                shape = RoundedCornerShape(20.dp)
                            )
                            .clip(RoundedCornerShape(20.dp))
                            .background(color = Color(0xFFFFFFFF),)
                    ) {
                        if (isScheduleCreator) {
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        popupState.isVisible = false
                                        moveToModifyScheduleScreen()
                                    }
                                    .padding(10.dp),
                                text = "수정",
                                fontSize = 20.sp,
                                textAlign = TextAlign.Center,
                                color = Color(0xFF414141)
                            )
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        popupState.isVisible = false
                                        deleteSchedule()
                                    }
                                    .padding(10.dp),
                                text = "삭제",
                                fontSize = 20.sp,
                                textAlign = TextAlign.Center,
                                color = Color(0xFFEC5C5C)
                            )
                        } else {
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        popupState.isVisible = false
                                        quitSchedule()
                                    }
                                    .padding(10.dp),
                                text = "나가기",
                                fontSize = 20.sp,
                                textAlign = TextAlign.Center,
                                color = Color(0xFFEC5C5C)
                            )
                        }
                    }
                }
            }
            Image(
                modifier = Modifier
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(50f))
                    .clickable {
                        popupState.isVisible = true
                    },
                painter = painterResource(id = R.drawable.more_vert_fill0_wght300_grad0_opsz24),
                contentDescription = null
            )
        }
    }
}

@Composable
fun DateContent(
    scheduleYear: Int,
    scheduleMonth: Int,
    scheduleDate: Int,
    scheduleDayOfWeek: String
) {
    Text(
        text = "${String.format("%04d", scheduleYear)}년 ${String.format("%02d", scheduleMonth.toInt())}월 ${String.format("%02d", scheduleDate)}일 (${scheduleDayOfWeek})",
        fontSize = 16.sp,
        color = Color(0xFF5F5F5F),
        fontWeight = FontWeight.Medium,
        letterSpacing = 0.em
    )
}

@Composable
fun TimeContent(
    scheduleHour: Int,
    scheduleMinute: Int
) {
    var hour = scheduleHour
    val AMPM: String = if (hour < 12) "오전" else { hour -= 12; "오후"}
    if (hour == 0) hour = 12
    Text(
        text = "$AMPM ${hour}:${String.format("%02d", scheduleMinute)}",
        fontSize = 14.sp,
        color = Color(0xFF7C7C7C),
        letterSpacing = 0.05.em
    )
}

@Composable
fun DestinationContent(
    destinationName: String,
    destinationAddress: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier.size(30.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                modifier = Modifier.size(30.dp),
                painter = painterResource(id = R.drawable.ic_location),
                contentDescription = null,
//            colorFilter = ColorFilter.tint(color = Color(0xFFA9AAAC))
            )
        }
        Spacer(Modifier.width(10.dp))
        Column {
            Text(
                text = destinationName,
                fontSize = 16.sp,
                color = Color(0xFF5F5F5F),
                fontWeight = FontWeight.Medium
            )
            Text(
                text = destinationAddress,
                fontSize = 14.sp,
                color = Color(0xFF8D8D8D)
            )
        }
    }
}

@Composable
fun MemberListContent(
    memberList: List<MemberInfo>,
    isLocationCheckEnabled: Boolean,
    moveToDetailScheduleMapScreen: () -> Unit,
) {
    val context = LocalContext.current
    val density = LocalDensity.current.density
    val popupState = remember { PopupState(false, PopupPosition.BottomRight) }
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier.size(30.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                modifier = Modifier.size(26.dp),
                painter = painterResource(id = R.drawable.users),
                contentDescription = null,
    //            colorFilter = ColorFilter.tint(color = Color(0xFFA9AAAC))
            )
        }
        Spacer(Modifier.width(10.dp))
        Box {
            CustomPopup(
                popupState = popupState,
                onDismissRequest = { popupState.isVisible = false }
            ) {
                CompositionLocalProvider(LocalDensity provides Density(density, fontScale = 1f)) {
                    Box(
                        modifier = Modifier
                            .padding(top = 10.dp, bottom = 20.dp)
                            .height(if (memberList.size < 5) (memberList.size * 40 + 40).dp else 220.dp)
                            .width(240.dp)
                            .shadow(
                                elevation = 4.dp,
                                shape = RoundedCornerShape(20.dp)
                            )
                            .clip(RoundedCornerShape(20.dp))
                            .background(color = Color(0xFFFFFFFF),)
                    ) {
                        LazyColumn(
                            modifier = Modifier
                                .padding(20.dp)
                        ) {
                            itemsIndexed(memberList) { _, userInfo ->
                                Row(
                                    modifier = Modifier.height(40.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    GlideImage(
                                        modifier = Modifier
                                            .size(30.dp)
                                            .clip(RoundedCornerShape(50)),
                                        imageModel = {
                                            userInfo.profileImage ?: R.drawable.idle_profile
                                        },
                                        imageOptions = ImageOptions(contentScale = ContentScale.Crop)
                                    )
                                    Spacer(Modifier.width(10.dp))
                                    Text(
                                        modifier = Modifier.weight(1f),
                                        text = userInfo.name,
                                        fontSize = 16.sp
                                    )
                                    Image(
                                        painter = painterResource(
                                            when (userInfo.isArrived) {
                                                true -> R.drawable.check_circle_fill0_wght300_grad0_opsz24
                                                false -> R.drawable.cancel_fill0_wght300_grad0_opsz24
                                            }
                                        ),
                                        contentDescription = null,
                                        colorFilter = ColorFilter.tint(
                                            when (userInfo.isArrived) {
                                                true -> Color.Green
                                                false -> Color.Red
                                            }
                                        ),
                                    )
                                }
                            }
                        }
                    }
                }
            }
            Text(
                modifier = Modifier
                    .clip(RoundedCornerShape(50))
                    .background(Color(0xFFF9D889))
                    .clickable {
                        popupState.isVisible = true
                    }
                    .padding(start = 12.dp, top = 8.dp, end = 12.dp, bottom = 8.dp),
                text = "약속멤버",
                fontSize = 14.sp,
                fontFamily = nanumSquareNeo,
                color = Color(0xFF463119),
            )
        }

        Spacer(Modifier.width(10.dp))

        // 지도 이동 버튼
        Text(
            modifier = Modifier
                .clip(RoundedCornerShape(50))
                .background(if (isLocationCheckEnabled) Color(0xFFF9D889) else Color(0xFFAAAAAA))
                .clickable {
                    if (isLocationCheckEnabled) {
                        moveToDetailScheduleMapScreen()
                    } else {
                        Toast
                            .makeText(
                                context,
                                "위치 확인은 약속 시각 전후로 1시간만 가능합니다.",
                                Toast.LENGTH_SHORT
                            )
                            .show()
                    }
                }
                .padding(start = 12.dp, top = 8.dp, end = 12.dp, bottom = 8.dp),
            text = "위치확인",
            fontSize = 14.sp,
            fontFamily = nanumSquareNeo,
            color = Color(0xFF463119),
        )
    }
}

@Composable
fun MemoContent(
    memo: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier.size(30.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                modifier = Modifier.size(26.dp),
                painter = painterResource(id = R.drawable.memo),
                contentDescription = null,
//            colorFilter = ColorFilter.tint(color = Color(0xFFA9AAAC))
            )
        }
        Spacer(Modifier.width(20.dp))
        Text(
            text = "메모",
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF7C7C7C)
        )
    }

    Spacer(Modifier.height(10.dp))

    // 메모 내용
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .border(
                border = BorderStroke(
                    width = 1.dp,
                    color = Color(0xFFCCCCCC)
                ),
                shape = RoundedCornerShape(8.dp)
            )
            .padding(12.dp),
        text = memo,
        fontSize = 16.sp
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun DetailScheduleScreenPreview() {
    val membersList = listOf(
        MemberInfo(name = "홍길동")
    )
    WhereAreYouTheme {
        DetailScheduleScreen(
            scheduleId = "",
            updateScheduleId = {},
            detailScheduleScreenUIState = DetailScheduleScreenUIState(),
            isScheduleCreator = true,
            deleteSchedule = {},
            quitSchedule = {},
            moveToBackScreen = {},
            moveToModifyScheduleScreen = {},
            moveToDetailScheduleMapScreen = {}
        )
    }
}