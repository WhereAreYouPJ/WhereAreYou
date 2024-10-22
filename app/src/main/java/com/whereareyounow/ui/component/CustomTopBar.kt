package com.whereareyounow.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
fun CustomTopBar(
    modifier: Modifier = Modifier,
    title: String,
    isBottomLineVisible: Boolean = true,
    onBackButtonClicked: () -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(TOP_BAR_HEIGHT.dp)
            .then(
                if (isBottomLineVisible) Modifier.drawBehind {
                    val strokeWidth = 1.dp.toPx()
                    val y = size.height - strokeWidth / 2
                    drawLine(
                        color = Color(0xFFC9C9C9),
                        start = Offset(0f, y),
                        end = Offset(size.width, y),
                        strokeWidth = strokeWidth
                    )
                }
                else Modifier
            )
            .padding(start = 15.dp, end = 15.dp),
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .size(30.dp)
                .clickableNoEffect { onBackButtonClicked() },
            painter = painterResource(id = R.drawable.ic_arrow_left),
            contentDescription = null,
            colorFilter = ColorFilter.tint(Color(0xFFACACAC))
        )
        Text(
            text = title,
            color = Color(0xFF000000),
            style = medium18pt
        )
    }
}

@CustomPreview
@Composable
private fun CustomTopBarPreview() {
    CustomTopBar(
        title = "아이디 찾기",
        onBackButtonClicked = {}
    )
}
