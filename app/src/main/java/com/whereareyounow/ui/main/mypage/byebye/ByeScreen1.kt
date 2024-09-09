package com.whereareyounow.ui.main.mypage.byebye

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.whereareyounow.R
import com.whereareyounow.data.globalvalue.TOP_BAR_HEIGHT
import com.whereareyounow.ui.component.CustomTopBar
import com.whereareyounow.ui.component.tobbar.DefaultTopBar
import com.whereareyounow.util.clickableNoEffect

@Composable
fun ByeScreen1(
    moveToBackScreen : () -> Unit,
    moveToByeScreen2 : () -> Unit,
) {

    ByeScreen1(
        isContent = true,
        moveToBackScreen = moveToBackScreen,
        moveToByeScreen2 = moveToByeScreen2
    )
}


@Composable
private fun ByeScreen1(
    isContent : Boolean,
    moveToBackScreen : () -> Unit,
    moveToByeScreen2 : () -> Unit

) {
//    CustomTopBar(
//        title = "회원탈퇴"
//    ) {
//        moveToBackScreen()
//    }
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
//            DefaultTopBar(
//                title = "회원탈퇴"
//            ) {
//                moveToBackScreen()
//            }
            Spacer(
                Modifier.height(30.dp)
            )
            Image(
                painter = painterResource(id = R.drawable.ic_byeconfirm),
                contentDescription = "",
            )
            Spacer(
                Modifier.height(30.dp)
            )
            Image(
                painter = painterResource(id = R.drawable.ic_byeconfirm_detail),
                contentDescription = "",
            )
            Spacer(
                Modifier.weight(1f)
            )
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_circlechecked),
                    contentDescription = "",
                    modifier = Modifier.clickableNoEffect {

                    }
                )
                Image(
                    painter = painterResource(id = R.drawable.ic_readconfirm),
                    contentDescription = "",
                )
            }
            Spacer(
                Modifier.height(18.dp)
            )
            Image(
                painter = painterResource(id = R.drawable.ic_nextbutton),
                contentDescription = "",
                modifier = Modifier
                    .padding(bottom = 68.dp)
                    .clickableNoEffect {
                        moveToByeScreen2()
                    }
            )
        }
    }

}

@Preview
@Composable
fun PreviewByeScreen1() {
    ByeScreen1(
        true ,
        {},
        {}
    )
}

