package com.whereareyounow.ui.main.mypage.myinfo

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.whereareyounow.R
import com.whereareyounow.data.globalvalue.TOP_BAR_HEIGHT
import com.whereareyounow.ui.component.CustomTopBar
import com.whereareyounow.ui.main.mypage.InputData

@Composable
fun EditMyInfoScreen(
    moveToMyInfoScreen : () -> Unit,
    moveToBackScreen : () -> Unit
) {
    EditMyInfoScreen(
        isContent = true,
        moveToMyInfoScreen = moveToMyInfoScreen,
        moveToBackScreen = moveToBackScreen
    )
}

@Composable
private fun EditMyInfoScreen(
    isContent : Boolean,
    moveToMyInfoScreen : () -> Unit,
    moveToBackScreen : () -> Unit

) {

    Column(
        modifier = Modifier.fillMaxSize().padding(top = TOP_BAR_HEIGHT.dp)
    ) {
        CustomTopBar(title = "내 정보 관리") {
            moveToMyInfoScreen()
        }

        Spacer(modifier = Modifier.size(34.dp))

        InputData(
            title = "이름",
            content = "유민혁"
        )
        Spacer(modifier = Modifier.height(10.dp))
        InputData(
            title = "이름",
            content = "유민혁"
        )

        Spacer(Modifier.weight(1f))
        Image(
            painter = painterResource(id = R.drawable.ic_modifybutton),
            contentDescription = "",
            modifier = Modifier.padding(start = 15.dp , end = 15.dp , bottom = 24.dp).clickable {
                moveToMyInfoScreen()
            }
        )
    }
}