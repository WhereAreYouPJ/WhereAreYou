package com.whereareyounow.ui.main.mypage.location

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.whereareyounow.R
import com.whereareyounow.data.globalvalue.TOP_BAR_HEIGHT
import com.whereareyounow.ui.main.friend.GrayLine
import com.whereareyounow.ui.main.mypage.MyPageViewModel
import com.whereareyounow.ui.main.mypage.byebye.Gap
import com.whereareyounow.ui.main.mypage.byebye.WithdrawlButton
import com.whereareyounow.ui.main.mypage.myinfo.OneTextOneIconTobBar
import com.whereareyounow.ui.theme.medium16pt
import com.whereareyounow.util.clickableNoEffect

@Composable
fun EditLocationFaboriteScreen(
    moveToBackScreen: () -> Unit

) {
    EditLocationFaboriteScreen(
        isContent = true,
        moveToBackScreen = moveToBackScreen
    )
}

@Composable
private fun EditLocationFaboriteScreen(
    isContent: Boolean,
    moveToBackScreen: () -> Unit
) {
    val myPageViewModel : MyPageViewModel = hiltViewModel()
    val defaultTrue = remember {
        mutableStateOf(true)
    }
    val selectedStates =
        remember {
            mutableStateListOf(
                false, false, false, false, false, false, false, false, false, false
            )
        }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = TOP_BAR_HEIGHT.dp)
    ) {
        OneTextOneIconTobBar(
            title = "위치 즐겨찾기",
            firstIcon = R.drawable.ic_back,
            firstIconClicked = { moveToBackScreen() }
        )

//        Gap(20)

        //즐겨찾기 사이즈 일단은 아무거나 데이터 있는게 없음 []이래됨.
        val sfsf = myPageViewModel.locationFaboriteList.collectAsState().value

        val circles = listOf(
            R.drawable.ic_circlechecked,
            R.drawable.ic_emptycircle,
        )
//        val circle = if (selectedStates[index]) circles[0] else circles[1]
        sfsf.forEachIndexed { index , love ->
            DetailFaboriteLocation(
                title = love!!.location!!,
//                isClickedState = selectedStates[index],
                isClicked = { selectedStates[index] = !selectedStates[index] },
                circle = if (selectedStates[index]) circles[0] else circles[1]
            )
            GrayLine()
        }

        Spacer(modifier = Modifier.weight(1f))

        WithdrawlButton(text = "삭제하기", canMove = defaultTrue) {
            moveToBackScreen()
            myPageViewModel.DeleteFavoriteLocation(
                1,
                listOf(1,2)
            )
        }
    }
}

@Composable
fun DetailFaboriteLocation(
    title: String,
//    isClickedState: MutableState<Boolean>,
    isClicked: () -> Unit,
    circle : Int
) {


    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 15.dp, end = 15.dp)
            .height(46.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = medium16pt,
            color = Color(0xFF222222)
        )
        Spacer(Modifier.weight(1f))
        Image(
            painter = painterResource(id = circle),
            contentDescription = "",
            modifier = Modifier.clickableNoEffect {
                isClicked()
            }
        )

    }
}

