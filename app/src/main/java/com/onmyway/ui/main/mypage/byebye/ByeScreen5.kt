package com.onmyway.ui.main.mypage.byebye

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.onmyway.R
import com.onmyway.data.globalvalue.BOTTOM_NAVIGATION_BAR_HEIGHT
import com.onmyway.data.globalvalue.TOP_BAR_HEIGHT
import com.onmyway.ui.component.tobbar.DefaultTopBar
import com.onmyway.ui.theme.medium14pt
import com.onmyway.ui.theme.medium20pt

@Composable
fun ByeScreen5(
    moveToBackScreen : () -> Unit,
    moveToLoginScreen : () -> Unit

) {
    ByeScreen5(
        isContent = true,
        moveToBackScreen = moveToBackScreen,
        moveToLoginScreen = moveToLoginScreen
    )
}


@Composable
private fun ByeScreen5(
    isContent : Boolean,
    moveToBackScreen : () -> Unit,
    moveToLoginScreen : () -> Unit

) {
    val contexxt = LocalContext.current
    val density = LocalDensity.current
    val ha = with(density) { 174f.toDp() + BOTTOM_NAVIGATION_BAR_HEIGHT.toDp() }
    val canMove = remember {
        mutableStateOf(true)
    }
    Box(
        modifier = Modifier.fillMaxSize().padding(top = TOP_BAR_HEIGHT.dp)
    ) {
        DefaultTopBar(
            title = "회원탈퇴"
        ) {
            moveToBackScreen()
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = TOP_BAR_HEIGHT.dp , start = 15.dp , end = 15.dp)
        ) {

            Spacer(Modifier.height(30.dp))

            ByeScreenTopTitle(
                title = "회원탈퇴가 완료되었어요.",
                titleStyle = medium20pt,
                titleColor = Color.Black
            )

            Gap(5)

            ByeScreenTopContent(
                content = "온마이웨이를 이용해주셔서 감사했습니다. \n" +
                        "더 좋은 서비스를 제공하는 온마이웨이가 되겠습니다.\n" +
                        "나중에 다시 만나요!",
                contentStyle = medium14pt,
                contentColor = Color(0xFF767676)
            )

            Image(
                painter = painterResource(id = R.drawable.ic_byebyecompleted),
                contentDescription = "",
            )
            Spacer(Modifier.weight(1f))


            Image(
                painter = painterResource(id = R.drawable.ic_omgomgomg),
                contentDescription = "",
                modifier = Modifier.padding(bottom = ha)
                    .aspectRatio(16f / 9f)
                    .fillMaxWidth()
                    .scale(1.1f)
            )

            WithdrawlButton(
                text = "확인",
                canMove = canMove,
                onClicked = {
                    (contexxt as? Activity)?.finish()
                }
            )

        }
    }



}

