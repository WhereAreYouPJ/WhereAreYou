package com.whereareyounow.ui.component.tobbar

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.whereareyounow.R
import com.whereareyounow.data.globalvalue.TOP_BAR_HEIGHT
import com.whereareyounow.ui.theme.medium18pt
import com.whereareyounow.util.CustomPreview
import com.whereareyounow.util.clickableNoEffect

@Composable
fun OneTextTwoIconTobBar(
    title: String,
    firstIconClicked: () -> Unit,
    firstIcon: Int,
    secondIconClicked: () -> Unit,
    secondIcon: Int,
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(TOP_BAR_HEIGHT.dp)
            .drawBehind {
                val strokeWidth = 1.5.dp.toPx()
                val y = size.height - strokeWidth / 2
                drawLine(
                    color = Color(0xFFC9C9C9),
                    start = Offset(0f, y),
                    end = Offset(size.width, y),
                    strokeWidth = strokeWidth
                )
            },
        contentAlignment = Alignment.Center

    ) {

        Image(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = 15.dp, bottom = 10.dp)
                .clickableNoEffect {
                    firstIconClicked()
                }
                .offset(y = 4.dp),
            painter = painterResource(id = firstIcon),
            contentDescription = "",
            colorFilter = ColorFilter.tint(Color(0xFFACACAC))
        )

        Text(
            text = title,
            style = medium18pt,
            modifier = Modifier.offset(y = (-2).dp),
            color = Color(0xFF000000)
        )

        Image(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 11.dp)
                .clickableNoEffect {
                    secondIconClicked()
                },
            painter = painterResource(id = secondIcon),
            contentDescription = ""
        )
        content()

    }
}

@CustomPreview
@Composable
private fun OneTextTwoIconTobBarPreview() {
    OneTextTwoIconTobBar(
        title = "아이디 찾기",
        firstIconClicked = {},
        firstIcon = R.drawable.ic_back,
        secondIconClicked = {},
        secondIcon = R.drawable.ic_back
    ) {

    }


}