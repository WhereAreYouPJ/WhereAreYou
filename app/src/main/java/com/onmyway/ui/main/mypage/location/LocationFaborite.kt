package com.onmyway.ui.main.mypage.location

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.onmyway.R
import com.onmyway.data.globalvalue.TOP_BAR_HEIGHT
import com.onmyway.ui.component.tobbar.OneTextTwoIconTobBar
import com.onmyway.ui.main.mypage.MyPageViewModel
import com.onmyway.ui.main.mypage.byebye.Gap
import com.onmyway.ui.theme.medium14pt
import com.onmyway.util.clickableNoEffect
import com.onmyway.util.popupmenu.CustomPopup
import com.onmyway.util.popupmenu.PopupPosition
import com.onmyway.util.popupmenu.PopupState

@Composable
fun LocationFaboriteScreen(
    moveToBackScreen : () -> Unit,
    moveToEditLocationFaborite : () -> Unit
) {
    val myPageViewModel : MyPageViewModel = hiltViewModel()

    val myName = myPageViewModel.name.collectAsState().value
    val myEmail = myPageViewModel.email.collectAsState().value
    Log.d("sfjlsjfls" , myName!!)
    val popupState = remember {
        PopupState(false, PopupPosition.BottomLeft)
    }
    val isVerifyed = remember {
        mutableStateOf(false)
    }
    val isModifyClicked = remember {
        mutableStateOf(false)
    }
    val isReadOnly = remember {
        mutableStateOf(true)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = TOP_BAR_HEIGHT.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        OneTextTwoIconTobBar(
            title = "위치 즐겨찾기",
            firstIconClicked = moveToBackScreen,
            firstIcon = R.drawable.ic_back,
            secondIconClicked = { popupState.isVisible = true },
            secondIcon = R.drawable.ic_plusbrandcolor
        ) {
            DeleteIconPopUp(
                popupState = popupState,
                isModifyClicked = isModifyClicked,
                isReadOnly = isReadOnly,
                moveToEditMyInfoScreen = {  moveToEditLocationFaborite() },
                modifier = Modifier
                    .clickableNoEffect {
                        moveToEditLocationFaborite()
                    }
                    .align(Alignment.CenterEnd)
            )



        }

        Gap(120)

        Image(
            painter = painterResource(id = R.drawable.ic_nolocation),
            contentDescription = ""
        )
    }

}




@Composable
fun DeleteIconPopUp(
    modifier: Modifier = Modifier,
    popupState: PopupState,
    isModifyClicked: MutableState<Boolean>,
    isReadOnly: MutableState<Boolean>,
    moveToEditMyInfoScreen: () -> Unit,
) {
    val density = LocalDensity.current.density
    Box(
        contentAlignment = Alignment.CenterEnd,
        modifier = modifier.padding(end = 15.dp, top = 83.dp - 48.dp)
    ) {
        CustomPopup(
            popupState = popupState,
            onDismissRequest = {
                popupState.isVisible = false
            }
        ) {
            CompositionLocalProvider(LocalDensity provides Density(density, fontScale = 1f)) {
                Box(
                    modifier = Modifier
                        .width(160.dp)
                        .height(38.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(color = Color(0xFF514675))
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .clickableNoEffect {
//                                popupState.isVisible = false
//                                isModifyClicked.value = true
//                                isReadOnly.value = false
                                moveToEditMyInfoScreen()
                            },
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(
                                    start = 14.dp,
                                    top = 8.dp,
                                    bottom = 10.dp
                                ),
                            text = "위치 삭제",
                            style = medium14pt,
                            color = Color.White
                        )

                    }
                }
            }
        }
    }
}