package com.whereareyou.ui.home.friends

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.unit.dp
import com.whereareyou.R
import com.whereareyou.data.GlobalValue
import com.whereareyou.util.popupmenu.CustomPopup
import com.whereareyou.util.popupmenu.PopupState

@Composable
fun FriendScreenTopBar(
    isFriendPage: MutableState<Boolean>,
    moveToAddFriendScreen: () -> Unit,
    moveToAddGroupScreen: () -> Unit,
) {
    val density = LocalDensity.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height((GlobalValue.topBarHeight / density.density).dp)
            .background(
                color = Color(0xFFCE93D8)
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        val popupState = remember { PopupState(false) }
        Text(
            modifier = Modifier
                .padding(end = 20.dp)
                .clickable {
                    isFriendPage.value = true
                },
            text = "친구"
        )
        Text(
            modifier = Modifier
                .padding(start = 20.dp)
                .clickable {
                    isFriendPage.value = false
                },
            text = "그룹"
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
                            .clip(shape = RoundedCornerShape(50))
                            .height(IntrinsicSize.Min)
                    ) {
                        Box(
                            modifier = Modifier
                                .background(color = Color.Yellow)
                                .clickable {
                                    popupState.isVisible = false
                                    moveToAddFriendScreen()
                                },
                        ) {
                            Text(
                                modifier = Modifier
                                    .padding(10.dp),
                                text = "친구추가"
                            )
                        }
                        Box(
                            modifier = Modifier
                                .padding(top = 10.dp, bottom = 10.dp)
                                .width(1.dp)
                                .fillMaxHeight()
                                .background(Color.Black)
                        )
                        Box(
                            modifier = Modifier
                                .clip(shape = RoundedCornerShape(topEnd = 50f, bottomEnd = 50f))
                                .background(Color.Cyan)
                                .clickable {
                                    popupState.isVisible = false
                                    moveToAddGroupScreen()
                                }
                        ) {
                            Text(
                                modifier = Modifier
                                    .padding(10.dp),
                                text = "그룹추가"
                            )
                        }
                    }
                }
                Image(
                    modifier = Modifier
                        .size(30.dp)
                        .clickable { popupState.isVisible = true },
                    painter = painterResource(id = R.drawable.baseline_add_circle_outline_24),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(color = Color(0xFF545454))
                )
            }
        }
    }
}