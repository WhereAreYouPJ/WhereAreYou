package com.whereareyounow.ui.home.friend

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.whereareyounow.R
import com.whereareyounow.data.GlobalValue
import com.whereareyounow.ui.theme.nanumSquareNeo
import com.whereareyounow.util.popupmenu.CustomPopup
import com.whereareyounow.util.popupmenu.PopupPosition
import com.whereareyounow.util.popupmenu.PopupState

@Composable
fun FriendScreenTopBar(
    isFriendPage: MutableState<Boolean>,
    moveToAddFriendScreen: () -> Unit,
    moveToAddGroupScreen: () -> Unit,
) {
    val density = LocalDensity.current.density
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height((GlobalValue.topBarHeight / density).dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        val popupState = remember { PopupState(false, PopupPosition.BottomLeft) }
        Text(
            modifier = Modifier
                .clickable {
                    isFriendPage.value = true
                },
            text = "친구",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(Modifier.width(20.dp))
        Text(
            modifier = Modifier
                .clickable {
                    isFriendPage.value = false
                },
            text = "그룹",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )
        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.CenterEnd
        ) {
            Box {
                CustomPopup(
                    modifier = Modifier,
//                        .clip(shape = RoundedCornerShape(topStart = 50f, bottomStart = 50f)),
                    popupState = popupState,
                    onDismissRequest = { popupState.isVisible = false }
                ) {
                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(50))
                            .height(IntrinsicSize.Min)
                    ) {
                        Box(
                            modifier = Modifier
                                .background(color = Color(0xFFF9D889))
                                .clickable {
                                    popupState.isVisible = false
                                    moveToAddFriendScreen()
                                },
                        ) {
                            Text(
                                modifier = Modifier
                                    .padding(10.dp),
                                text = "친구추가",
                                fontFamily = nanumSquareNeo
                            )
                        }
                        Box(
                            modifier = Modifier
                                .width(1.dp)
                                .fillMaxHeight()
                                .background(Color(0xFFF9D889))
                                .padding(top = 10.dp, bottom = 10.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(Color.Black)
                            )
                        }
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(topEnd = 50f, bottomEnd = 50f))
                                .background(Color(0xFFF9D889))
                                .clickable {
                                    popupState.isVisible = false
                                    moveToAddGroupScreen()
                                }
                        ) {
                            Text(
                                modifier = Modifier
                                    .padding(10.dp),
                                text = "그룹추가",
                                fontFamily = nanumSquareNeo
                            )
                        }
                    }
                }
                Image(
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                        .clickable { popupState.isVisible = true }
                        .padding(10.dp),
                    painter = painterResource(id = R.drawable.baseline_add_circle_outline_24),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(color = Color(0xFF545454))
                )
            }
        }
    }
}