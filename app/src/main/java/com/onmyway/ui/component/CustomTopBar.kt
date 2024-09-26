package com.onmyway.ui.component

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.onmyway.R
import com.onmyway.data.globalvalue.TOP_BAR_HEIGHT
import com.onmyway.ui.theme.medium18pt
import com.onmyway.util.CustomPreview
import com.onmyway.util.clickableNoEffect

@Composable
fun CustomTopBar(
    modifier: Modifier = Modifier,
    title: String,
    onBackButtonClicked: () -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(TOP_BAR_HEIGHT.dp)
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