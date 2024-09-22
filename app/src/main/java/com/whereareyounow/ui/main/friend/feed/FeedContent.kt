package com.whereareyounow.ui.main.friend.feed

import android.graphics.Paint.Align
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.whereareyounow.R
import com.whereareyounow.ui.theme.getColor
import com.whereareyounow.util.drawColoredShadow

@Composable
fun FeedContent() {
    FeedContent(
        isContent = true
    )
}

@Composable
private fun FeedContent(
    isContent: Boolean
) {
    LazyRow(
//        modifier = Modifier.padding(start = 15.dp, end = 15.dp),
        contentPadding = PaddingValues(15.dp)
    ) {
        itemsIndexed(listOf("유민혁", "김지원", "임창균", "이주현")) { idx, item ->
            Row(
                modifier = Modifier
                    .width(100.dp)
                    .height(34.dp)
                    .drawColoredShadow(
                        shadowRadius = 4.dp,
                        alpha = 0.2f,
                        borderRadius = 16.dp
                    )
                    .clip(RoundedCornerShape(50))
                    .border(
                        border = BorderStroke(
                            width = (1.6).dp,
                            color = if (idx == 0) getColor().brandSubColor else getColor().thin
                        ),
                        shape = RoundedCornerShape(50)
                    )
                    .background(Color.White),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Image(
                    modifier = Modifier
                        .size(26.dp)
                        .clip(RoundedCornerShape(10.dp)),
                    painter = painterResource(R.drawable.ic_launcher_background),
                    contentDescription = null
                )

                Spacer(Modifier.width(6.dp))

                Text(
                    text = item
                )
            }

            Spacer(Modifier.width(4.dp))
        }
    }

    Spacer(Modifier.height(100.dp))

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 15.dp, end = 15.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier.width(300.dp),
            painter = painterResource(R.drawable.img_feed_empty),
            contentDescription = null
        )
    }
}