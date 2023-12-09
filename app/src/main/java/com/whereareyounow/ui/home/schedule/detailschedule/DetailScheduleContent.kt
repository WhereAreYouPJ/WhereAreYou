package com.whereareyounow.ui.home.schedule.detailschedule

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import com.whereareyounow.R
import com.whereareyounow.util.popupmenu.CustomPopup
import com.whereareyounow.util.popupmenu.PopupState

@Composable
fun DetailScheduleContent(
    viewModel: DetailScheduleViewModel = hiltViewModel()
) {
    val popupState = remember { PopupState(false) }
    Box(modifier = Modifier.fillMaxSize()) {
        // 배경
        BackgroundContent()

        Column(
            modifier = Modifier
                .padding(start = 20.dp, top = 80.dp, end = 40.dp, bottom = 80.dp)
                .fillMaxSize()
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(20.dp)
                )
                .padding(20.dp)
        ) {
            // 일정 제목
            Row(modifier = Modifier.height(IntrinsicSize.Min)) {
                Text(
                    text = "${viewModel.title.collectAsState().value}",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF505050)
                )
                Spacer(modifier = Modifier.weight(1f))
                Box {
                    CustomPopup(
                        popupState = popupState,
                        onDismissRequest = { popupState.isVisible = false }
                    ) {
                        Box(
                            modifier = Modifier
                                .width(200.dp)
                                .background(color = Color.Red)
                        ) {
                            Text(
                                text = "나가기",
                                fontSize = 20.sp
                            )
                        }
                    }
                    Image(
                        modifier = Modifier
                            .fillMaxHeight()
//                            .size(30.dp)
                            .clip(shape = RoundedCornerShape(50f))
                            .clickable {
                                popupState.isVisible = true
                            },
                        painter = painterResource(id = R.drawable.more_vert_fill0_wght300_grad0_opsz24),
                        contentDescription = null
                    )
                }
            }
            Spacer(modifier = Modifier.height(30.dp))
            // 날짜, 시간 정보
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    modifier = Modifier.size(30.dp),
                    painter = painterResource(id = R.drawable.schedule_fill0_wght200_grad0_opsz24),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(color = Color(0xFFA9AAAC))
                )
                Spacer(modifier = Modifier.width(20.dp))
                Text(
                    text = "${viewModel.appointmentTime.collectAsState().value.replace("T", "\n")}",
                    fontSize = 20.sp
                )
                Image(
                    modifier = Modifier.padding(start = 10.dp, end = 10.dp),
                    painter = painterResource(id = R.drawable.baseline_arrow_forward_ios_24),
                    contentDescription = null
                )
                Text(
                    text = "${viewModel.appointmentTime.collectAsState().value.replace("T", "\n")}",
                    fontSize = 20.sp
                )
            }
            Spacer(modifier = Modifier.height(20.dp))

            // 목적지 정보
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier.size(30.dp),
                    painter = painterResource(id = R.drawable.location_on_fill0_wght200_grad0_opsz24),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(color = Color(0xFFA9AAAC))
                )
                Spacer(modifier = Modifier.width(20.dp))
                Text(
                    text = "${viewModel.place.collectAsState().value}",
                    fontSize = 20.sp
                )
            }

            Spacer(modifier = Modifier.height(20.dp))
            // 회원 리스트 정보
            val memberList = viewModel.userInfos
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier.size(30.dp),
                    painter = painterResource(id = R.drawable.group_fill0_wght200_grad0_opsz24),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(color = Color(0xFFA9AAAC))
                )
                Spacer(modifier = Modifier.width(20.dp))
                LazyRow() {
                    itemsIndexed(memberList) {_, userInfo ->
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            GlideImage(
                                modifier = Modifier
                                    .size(30.dp)
                                    .clip(shape = RoundedCornerShape(50)),
                                imageModel = {
                                    userInfo.profileImage ?: R.drawable.account_circle_fill0_wght200_grad0_opsz24
                                },
                                imageOptions = ImageOptions(
                                    contentScale = ContentScale.FillWidth,
                                )
                            )
                            Text(
                                text = "${userInfo.name}",
                                fontSize = 16.sp
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                    }
                }
            }
            // 지도 이동 버튼
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                modifier = Modifier
                    .padding(start = 50.dp)
                    .clip(shape = RoundedCornerShape(50f))
                    .background(
                        color = Color(0xFFF9D889),
                        shape = RoundedCornerShape(50)
                    )
                    .clickable {
                        viewModel.updateScreenState(DetailScheduleViewModel.ScreenState.UserMap)
                    }
                    .padding(10.dp),
                text = "네이버 지도"
            )
            Spacer(modifier = Modifier.height(20.dp))

            // 메모
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier.size(30.dp),
                    painter = painterResource(id = R.drawable.article_fill0_wght200_grad0_opsz24),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(color = Color(0xFFA9AAAC))
                )
                Spacer(modifier = Modifier.width(20.dp))
                Text(
                    text = "메모",
                    fontSize = 20.sp
                )
            }
            Spacer(modifier = Modifier.height(10.dp))

            // 메모 내용
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .background(
                        color = Color.LightGray,
                        shape = RoundedCornerShape(10.dp)
                    )
                    .verticalScroll(rememberScrollState())
                    .padding(10.dp),
                text = "${viewModel.memo.collectAsState().value}",
                fontSize = 20.sp
            )
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
                    brush = Brush.horizontalGradient(listOf(Color.Red, Color.Blue)),
                    alpha = 0.4f,
                    shape = RoundedCornerShape(10.dp)
                )
        )
    }
}