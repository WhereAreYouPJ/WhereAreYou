package com.whereareyounow.ui.main.mypage.announcement

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.whereareyounow.R
import com.whereareyounow.data.globalvalue.TOP_BAR_HEIGHT
import com.whereareyounow.ui.main.mypage.withdrawl.Gap
import com.whereareyounow.ui.theme.medium14pt
import com.whereareyounow.ui.theme.medium18pt
import com.whereareyounow.ui.theme.notoSanskr
import com.whereareyounow.util.clickableNoEffect
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun AdminImageScreen(
    moveToBackScreen : () -> Unit
) {
    val scrollState = rememberScrollState()
    val currentDate : String = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd"))
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(top = TOP_BAR_HEIGHT.dp)
    ) {
        Gap(4)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 15.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_backarrow),
                contentDescription = "",
                modifier = Modifier.clickableNoEffect {
                    moveToBackScreen()
                }
            )
            Gap(7)
            Text(
                text = "공지사항",
                style = medium18pt,
            )
        }
        Gap(20)
        Row(
            modifier = Modifier.fillMaxWidth().padding(start = 18.dp , end = 18.dp , top = 4.dp)
        ) {
            Text(
                text = "온마이웨이가 전하는",
                fontSize = 22.sp,
                color = Color(0xFF222222),
                fontWeight = FontWeight.Bold,
                fontFamily = notoSanskr
            )
        }
        Gap(2)
        Row(
            modifier = Modifier.fillMaxWidth().padding(start = 18.dp , end = 18.dp , bottom = 4.dp)
        ) {
            Text(
                text = "온.마.웨 새출발 사용가이드",
                fontSize = 22.sp,
                color = Color(0xFF222222),
                fontWeight = FontWeight.Bold,
                fontFamily = notoSanskr
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth().padding(start = 18.dp , end = 18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = currentDate,
                color = Color(0xFF999999),
                style = medium14pt
            )
        }
        Gap(16)
        Image(
            painter = painterResource(id = R.drawable.ic_realangrygonee),
            contentDescription = "",
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        )
    }
}