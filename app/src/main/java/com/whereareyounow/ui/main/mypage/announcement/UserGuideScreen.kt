package com.whereareyounow.ui.main.mypage.announcement

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.whereareyounow.R
import com.whereareyounow.ui.main.mypage.withdrawl.Gap
import com.whereareyounow.ui.theme.medium18pt
import com.whereareyounow.ui.theme.notoSanskr

@Composable
fun UserGuideScreen() {
    UserGuideScreen(
        isContent = true
    )
}

@Composable
private fun UserGuideScreen(
    isContent : Boolean
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Gap(2)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 15.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_backarrow),
                contentDescription = ""
            )
            Gap(7)
            Text(
                text = "공지사항",
                style = medium18pt,
                modifier = Modifier.offset(y = 6.dp)
            )
        }
        Gap(18)
        Row(
            modifier = Modifier.fillMaxWidth().padding(start = 18.dp , end = 18.dp , top = 4.dp)
        ) {
            Gap(6)
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
            Gap(6)
            Text(
                text = "온.마.웨 새출발 사용가이드",
                fontSize = 22.sp,
                color = Color(0xFF222222),
                fontWeight = FontWeight.Bold,
                fontFamily = notoSanskr
            )
        }
    }
}

@Preview
@Composable
fun PreviewUserGuideScreen() {
    UserGuideScreen(

    )
}

//@Composable
//fun ScrollableImage() {
//    val scrollState = rememberScrollState()
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .verticalScroll(scrollState)
//    ) {
//        Image(
//            painter = painterResource(id = R.drawable.ic_realangrygonee),
//            contentDescription = "",
//            contentScale = ContentScale.FillWidth,
//            modifier = Modifier
//                .fillMaxWidth()
//                .wrapContentHeight()
//        )
//    }
//}