package com.whereareyounow.ui.main.friend.feed.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.skydoves.landscapist.glide.GlideImage
import com.whereareyounow.R
import com.whereareyounow.domain.entity.feed.FeedInfo
import com.whereareyounow.ui.theme.getColor
import com.whereareyounow.ui.theme.medium14pt
import com.whereareyounow.ui.theme.medium16pt
import com.whereareyounow.util.calendar.parseLocalDate
import com.whereareyounow.util.clickableNoEffect
import com.whereareyounow.util.drawColoredShadow

@Composable
fun DetailFeedScreen(
    feedInfo: FeedInfo?,
    selectedFeedMemberSeq: Int,
    updateSelectedMemberSeq: (Int) -> Unit,
) {
    feedInfo?.let {
        LazyRow(
            contentPadding = PaddingValues(15.dp)
        ) {
            itemsIndexed(feedInfo.memberInfoList) { idx, item ->
                Row(
                    modifier = Modifier
                        .width(100.dp)
                        .height(34.dp)
                        .drawColoredShadow(
                            shadowRadius = 3.dp,
                            alpha = 0.1f,
                            borderRadius = 16.dp
                        )
                        .clip(RoundedCornerShape(50))
                        .border(
                            border = BorderStroke(
                                width = (1.6).dp,
                                color = if (idx == selectedFeedMemberSeq) getColor().brandSubColor else getColor().thin
                            ),
                            shape = RoundedCornerShape(50)
                        )
                        .background(Color.White)
                        .clickableNoEffect { updateSelectedMemberSeq(idx) },
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Box(
                        contentAlignment = Alignment.Center
                    ) {
                        GlideImage(
                            modifier = Modifier
                                .size(26.dp)
                                .clip(RoundedCornerShape(10.dp)),
                            imageModel = { R.drawable.ic_profile }
                        )
                    }

                    Spacer(Modifier.width(6.dp))

                    Text(
                        text = item.userName
                    )
                }

                Spacer(Modifier.width(4.dp))
            }
        }

        Column(
            modifier = Modifier.padding(start = 15.dp, end = 15.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(74.dp)
                    .border(
                        border = BorderStroke(
                            width = (1.5).dp,
                            color = getColor().brandColor
                        ),
                        shape = RoundedCornerShape(14.dp)
                    )
                    .padding(start = 10.dp, end = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                GlideImage(
                    modifier = Modifier
                        .size(50.dp)
                        .clip(RoundedCornerShape(14.dp)),
                    imageModel = {
                        if (it.feedInfo[selectedFeedMemberSeq].memberInfo.profileImage.isNullOrEmpty()) R.drawable.ic_profile
                        else it.feedInfo[selectedFeedMemberSeq].memberInfo.profileImage
                    }
                )
                Column {
                    Row {
                        Text(
                            modifier = Modifier.padding(6.dp, 6.dp, 2.dp, 2.dp),
                            text = parseLocalDate(it.scheduleInfo.startTime).toString().replace("-", "."),
                            color = Color(0xFF767676),
                            style = medium14pt
                        )

                        Text(
                            modifier = Modifier.padding(6.dp, 6.dp, 6.dp, 2.dp),
                            text = it.scheduleInfo.location,
                            color = Color(0xFF767676),
                            style = medium14pt
                        )
                    }
                    Text(
                        modifier = Modifier.padding(start = 6.dp, end = 6.dp, bottom = 4.dp),
                        text = it.feedInfo[selectedFeedMemberSeq].feedInfo.title,
                        color = Color(0xFF222222),
                        style = medium16pt
                    )
                }
            }

            Spacer(Modifier.height(10.dp))

            FeedImagePager(imageList = it.feedInfo[selectedFeedMemberSeq].feedImageInfos.map { it.feedImageUrl })

            Spacer(Modifier.height(6.dp))

            Row {
                Spacer(Modifier.weight(1f))

                Image(
                    modifier = Modifier.size(30.dp),
                    painter = painterResource(if (it.feedInfo[selectedFeedMemberSeq].bookmarkInfo) R.drawable.ic_bookmark_filled_brandcolor else R.drawable.ic_bookmark_outlined_black),
                    contentDescription = null
                )
            }

            Spacer(Modifier.height(6.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(82.dp)
                    .background(
                        color = Color(0xFFF9F9F9),
                        shape = RoundedCornerShape(6.dp)
                    )
            ) {
                Text(
                    modifier = Modifier.padding(10.dp),
                    text = it.feedInfo[selectedFeedMemberSeq].feedInfo.content,
                    color = Color(0xFF666666),
                    style = medium14pt
                )
            }
        }
    }
}