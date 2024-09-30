package com.whereareyounow.ui.component.tobbar

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import com.whereareyounow.util.CustomPreview
import com.whereareyounow.util.clickableNoEffect

@Composable
fun DefaultTopBar(
    modifier: Modifier = Modifier,
    title: String,
    onBackButtonClicked: () -> Unit,
) {
    Box(
        modifier = modifier
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
    ) {
        Image(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = 15.dp , bottom = 10.dp)
                .clickableNoEffect { onBackButtonClicked() },
            painter = painterResource(id = R.drawable.ic_backarrow),
            contentDescription = null,
            colorFilter = ColorFilter.tint(Color(0xFFACACAC))
        )
        Image(
            modifier = Modifier
                .padding(start = 146.dp , top = 4.dp , bottom = 12.dp)
                .clickableNoEffect { onBackButtonClicked() },
            painter = painterResource(id = R.drawable.ic_titlebye),
            contentDescription = null,
        )

    }
}

@CustomPreview
@Composable
private fun CustomTopBarPreview() {
    DefaultTopBar(
        title = "아이디 찾기",
        onBackButtonClicked = {}
    )
}