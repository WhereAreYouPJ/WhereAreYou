package com.whereareyounow.ui.component.tobbar

import android.util.Log
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.whereareyounow.R
import com.whereareyounow.data.globalvalue.TOP_BAR_HEIGHT
import com.whereareyounow.ui.theme.OnMyWayTheme
import com.whereareyounow.ui.theme.medium14pt
import com.whereareyounow.ui.theme.medium18pt
import com.whereareyounow.util.CustomPreview
import com.whereareyounow.util.clickableNoEffect
import com.whereareyounow.util.popupmenu.CustomPopup
import com.whereareyounow.util.popupmenu.PopupPosition
import com.whereareyounow.util.popupmenu.PopupState

@Composable
fun OneTextTwoIconTobBar(
    title: String,
    firstIconClicked: () -> Unit,
    @DrawableRes firstIcon: Int,
    secondIconClicked: () -> Unit,
    @DrawableRes secondIcon: Int,
) {
    val popupState = remember { PopupState(isVisible = false, popupPosition = PopupPosition.BottomLeft) }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(TOP_BAR_HEIGHT.dp)
            .drawBehind {
                val strokeWidth = 1.dp.toPx()
                val y = size.height - strokeWidth / 2
                drawLine(
                    color = Color(0xFFC9C9C9),
                    start = Offset(0f, y),
                    end = Offset(size.width, y),
                    strokeWidth = strokeWidth
                )
            }
            .padding(start = 15.dp, end = 15.dp)
    ) {
        Image(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .size(30.dp)
                .clickableNoEffect { firstIconClicked() },
            painter = painterResource(id = firstIcon),
            contentDescription = null,
            colorFilter = ColorFilter.tint(Color(0xFFACACAC))
        )

        Text(
            modifier = Modifier.align(Alignment.Center),
            text = title,
            style = medium18pt,
            color = Color(0xFF000000)
        )

        Box(
            modifier = Modifier.align(Alignment.CenterEnd)
        ) {
            Image(
                modifier = Modifier
                    .clickableNoEffect { popupState.isVisible = true },
                painter = painterResource(id = secondIcon),
                contentDescription = null
            )

            CustomPopup(
                popupState = popupState,
                onDismissRequest = { popupState.isVisible = false }
            ) {
                OnMyWayTheme {
                    Box(
                        modifier = Modifier
                            .width(160.dp)
                            .height(38.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(color = Color(0xFF7262A8))
                            .clickableNoEffect {
                                popupState.isVisible = false
                                secondIconClicked()
                            },
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Text(
                            modifier = Modifier
                                .height(30.dp)
                                .padding(start = 14.dp, top = 4.dp),
                            text = "수정하기",
                            style = medium14pt,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}

@CustomPreview
@Composable
private fun OneTextTwoIconTobBarPreview() {
//    OneTextTwoIconTobBar(
//        title = "아이디 찾기",
//        firstIconClicked = {},
//        firstIcon = R.drawable.ic_back,
//        secondIconClicked = {},
//        secondIcon = R.drawable.ic_back
//    ) {
//
//    }


}