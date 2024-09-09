package com.whereareyounow.ui.main.mypage.byebye

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.whereareyounow.R
import com.whereareyounow.data.globalvalue.TOP_BAR_HEIGHT
import com.whereareyounow.ui.component.CustomTopBar
import com.whereareyounow.ui.component.tobbar.DefaultTopBar
import com.whereareyounow.ui.theme.medium14pt
import com.whereareyounow.ui.theme.medium20pt
import com.whereareyounow.util.clickableNoEffect

@Composable
fun ByeScreen2(
    moveToBackScreen: () -> Unit,
    moveToByeScreen3: () -> Unit,

    ) {
    ByeScreen2(
        isContent = true,
        moveToBackScreen = moveToBackScreen,
        moveToByeScreen3 = moveToByeScreen3
    )
}


@Composable
private fun ByeScreen2(
    isContent: Boolean,
    moveToBackScreen: () -> Unit,
    moveToByeScreen3: () -> Unit

) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = TOP_BAR_HEIGHT.dp)
    ) {
        DefaultTopBar(
            title = "회원탈퇴"
        ) {
            moveToBackScreen()
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = TOP_BAR_HEIGHT.dp, start = 15.dp, end = 15.dp)
        ) {

            Spacer(
                Modifier.height(30.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "좋은 추억은 많이 남기셨나요?",
                    style = medium20pt
                )
                Spacer(modifier = Modifier.width(2.dp))
                Image(
                    painter = painterResource(id = R.drawable.ic_iwantsleep), 
                    contentDescription = "",
                    modifier = Modifier.size(23.dp)
                )
            }
            Spacer(modifier = Modifier.height(7.dp))
            Text(
                text = "회원님께서 계정을 삭제하시게 된 이유를 알려주시면,\n" +
                        "귀중한 의견을 반영하여 더욱 노력하겠습니다",
                style = medium14pt,
                color = Color(0xFF767676)
            )






//            Image(
//                painter = painterResource(id = R.drawable.ic_areyougetmemory),
//                contentDescription = "",
//            )

            Spacer(
                Modifier.height(20.dp)
            )


            val selectedStates =
                remember { mutableStateListOf(false, false, false, false, false) }
            val isChecked = remember { mutableStateOf(false) }
            val circles = listOf(
                R.drawable.ic_circlechecked,
                R.drawable.ic_emptycircle,
            )
            val reasons = listOf(
                R.drawable.ic_byereason1,
                R.drawable.ic_byereason2,
                R.drawable.ic_byereason3,
                R.drawable.ic_byereason4,
                R.drawable.ic_byereason5,
            )

            reasons.forEachIndexed { index, reason ->
                Spacer(Modifier.height(10.dp))
                DetailByeReaseons(
                    reason = reason,
                    circle = if (selectedStates[index]) circles[0] else circles[1],
                    circleClick = {
                        selectedStates[index] = !selectedStates[index]
                    }
                )
            }

            Spacer(Modifier.height(10.dp))

            if(!isChecked.value) {
                DetailByeReaseons(
                    reason = R.drawable.ic_byereason6,
                    circle = if (isChecked.value) circles[0] else circles[1],
                    circleClick = {
                        isChecked.value = !isChecked.value
                        Log.d("sfljsl" , "sss")
                    }
                )
            } else {
//            DetailByeReaseons(
//                reason = R.drawable.ic_byereason6,
//                circle = if (isChecked.value) circles[0] else circles[1],
//                circleClick = {
//                    isChecked.value = !isChecked.value
//                    Log.d("sfljsl" , "sss")
//                }
//            )
                Image(
                    painter = painterResource(id = R.drawable.ic_byebyeetc),
                    contentDescription = ""
                )
            }


            Spacer(modifier = Modifier.weight(1f))

            Image(
                painter = painterResource(id = R.drawable.ic_nextbutton),
                contentDescription = "",
                modifier = Modifier
                    .padding(bottom = 68.dp)
                    .clickableNoEffect {
                        moveToByeScreen3()
                    }
            )

        }
    }

}

@Composable
fun DetailByeReaseons(
    reason: Int,
    circle: Int,
    circleClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .border(
                border = BorderStroke(
                    width = 1.dp,
                    color = Color(0xFFDDDDDD)
                ),
                shape = RoundedCornerShape(10.dp)
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {




        Image(
            painter = painterResource(id = circle),
            contentDescription = "",
            modifier = Modifier
                .padding(start = 20.dp, end = 4.dp)
                .clickableNoEffect {
                    circleClick()
                }
        )
        Image(
            painter = painterResource(id = reason),
            contentDescription = "",
        )
    }
}
