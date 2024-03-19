package com.whereareyounow.ui.home.schedule.notification

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import com.whereareyounow.R
import com.whereareyounow.domain.entity.friend.FriendRequest
import com.whereareyounow.domain.entity.schedule.Friend
import com.whereareyounow.util.CalendarUtil

@Composable
fun FriendRequestBox(
    friendRequest: Pair<FriendRequest, Friend>,
    acceptFriendRequest: () -> Unit,
    refuseFriendRequest: () -> Unit
) {
    val timePassed = CalendarUtil.getMinuteDiffWithCurrentTime(friendRequest.first.createTime.split(".")[0])
    Row(
        modifier = Modifier
            .padding(start = 20.dp, top = 10.dp, end = 20.dp, bottom = 10.dp)
            .fillMaxWidth()
            .shadow(elevation = 4.dp, shape = RoundedCornerShape(20.dp))
            .background(
                color = Color(0xFFFFFFFF),
                shape = RoundedCornerShape(20.dp)
            )
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        GlideImage(
            modifier = Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(50)),
            imageModel = { friendRequest.second.profileImgUrl ?: R.drawable.idle_profile },
            imageOptions = ImageOptions(contentScale = ContentScale.Crop)
        )
        Spacer(Modifier.width(10.dp))
        Column {
            Row {
                Text(
                    text = friendRequest.second.name,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    style = TextStyle(
                        lineHeight = 18.sp
                    )
                )
                Spacer(Modifier.weight(1f))
                Text(
                    text = when {
                        timePassed < 60 -> "${timePassed}분"
                        timePassed < 1440 -> "${timePassed / 60}시간"
                        else -> "${timePassed / 1440}일"
                    },
                    fontSize = 14.sp,
                    style = TextStyle(
                        lineHeight = 14.sp
                    ),
                    color = Color(0xFFB3B3B3)
                )
            }
            Spacer(Modifier.height(2.dp))
            Text(
                text = friendRequest.second.userId,
                fontSize = 16.sp,
                color = Color(0xFF999999),
                style = TextStyle(
                    lineHeight = 16.sp
                )
            )
            Spacer(Modifier.height(10.dp))
            Row {
                ClickableBox(color = Color(0xFF9286FF), text = "수락", textColor = Color(0xFFFFFFFF)) { acceptFriendRequest() }
                Spacer(Modifier.width(10.dp))
                ClickableBox(color = Color(0xFFE4E4E6), text = "거절") { refuseFriendRequest() }
            }
        }
    }
}

@Composable
fun RowScope.ClickableBox(
    color: Color,
    text: String,
    textColor: Color = Color(0xFF000000),
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .weight(1f)
            .clip(RoundedCornerShape(10.dp))
            .background(
                color = color,
                shape = RoundedCornerShape(10.dp)
            )
            .clickable { onClick() }
            .padding(top = 10.dp, bottom = 10.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = textColor
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun FriendRequestBoxPreview() {
    FriendRequestBox(
        FriendRequest("", "", "2024-03-20T23:43:13") to Friend(0, "홍길동", "hong", "IdId"),
        {}, {}
    )
}