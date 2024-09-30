package com.whereareyounow.ui.main.friend

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import com.skydoves.landscapist.glide.GlideImage
import com.whereareyounow.R
import com.whereareyounow.data.globalvalue.TOP_BAR_HEIGHT
import com.whereareyounow.ui.main.mypage.byebye.Gap
import com.whereareyounow.ui.theme.medium14pt
import com.whereareyounow.ui.theme.medium20pt
import com.whereareyounow.util.clickableNoEffect
import com.whereareyounow.util.popupmenu.CustomPopup
import com.whereareyounow.util.popupmenu.PopupPosition
import com.whereareyounow.util.popupmenu.PopupState


@Composable
fun DetailProfileScreen(
    friendImagrUrl: String,
    friendName: String,
    moveToBackScreen: () -> Unit
) {

    DetailProfileScreen(
        true,
        friendImagrUrl,
        friendName,
        moveToBackScreen
    )

}

@Composable
private fun DetailProfileScreen(
    isContent: Boolean,
    friendImagrUrl: String,
    friendName: String,
    moveToBackScreen: () -> Unit
) {
    val popupState = remember {
        PopupState(false, PopupPosition.TopRight)
    }
    Column(
        modifier = Modifier.background(Color(0XFFB5B5B5))
    ) {
        Spacer(Modifier.size(TOP_BAR_HEIGHT.dp))

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_x),
                contentDescription = "",
                modifier = Modifier
                    .padding(start = 20.dp, top = 11.dp)
                    .clickable {
                        moveToBackScreen()
                    },
                colorFilter = ColorFilter.tint(color = Color(0XFFEEEEEE))
            )
            Spacer(Modifier.weight(1f))
            Image(
                painter = painterResource(id = R.drawable.ic_detail_friend_star),
                contentDescription = "",
                modifier = Modifier.padding(top = 11.dp),
                colorFilter = ColorFilter.tint(color = Color(0XFFEEEEEE))
            )
            Gap(6)
            Image(
                painter = painterResource(id = R.drawable.ic_more),
                contentDescription = "",
                modifier = Modifier.padding(top = 11.dp ,end = 20.dp).clickableNoEffect {
                    popupState.isVisible = true
                },
                colorFilter = ColorFilter.tint(color = Color(0XFFEEEEEE))
            )
        }

        Spacer(Modifier.weight(1f))

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 491.dp + TOP_BAR_HEIGHT.dp),
//            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            GlideImage(
                modifier = Modifier
                    .padding(start = 137.5.dp, end = 137.5.dp, bottom = 6.dp)
                    .size(100.dp)
                    .clip(RoundedCornerShape(34.4.dp)),
                imageModel = { friendImagrUrl ?: R.drawable.idle_profile2 },
            )
//            }
            Spacer(Modifier.size(6.dp))
            Text(friendName ?: "유민혁", style = medium20pt , color = Color(0XFFFFFFFF))


        }


        if(popupState.isVisible == true) {
            DeleteFriendIconPopUp(
                popupState = popupState,
                dialogAdd = {},
                modifier = Modifier.align(Alignment.CenterHorizontally)

            )
        }

//        MyPageWarningDialog(
//
//        )
    }

}

@Composable
fun DeleteFriendIconPopUp(
    modifier: Modifier = Modifier,
    popupState: PopupState,
//    isModifyClicked: MutableState<Boolean>,
//    isReadOnly: MutableState<Boolean>,
    dialogAdd: () -> Unit,
) {
    val density = LocalDensity.current.density
    Box(
        contentAlignment = Alignment.TopEnd,
        modifier = modifier.padding(end = 15.dp, top = 83.dp - 48.dp , bottom = 100.dp)
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
                                dialogAdd()
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
                            text = "친구 삭제",
                            style = medium14pt,
                            color = Color.White
                        )

                    }
                }
            }
        }
    }
}

