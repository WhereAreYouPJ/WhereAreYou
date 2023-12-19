package com.whereareyounow.ui.home.schedule.notification

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import com.whereareyounow.R
import com.whereareyounow.domain.entity.friend.FriendRequest
import com.whereareyounow.domain.entity.schedule.Friend

@Composable
fun FriendRequestBox(
    friendRequest: Pair<FriendRequest, Friend>,
    acceptFriendRequest: () -> Unit,
    refuseFriendRequest: () -> Unit
) {
    Row(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        GlideImage(
            modifier = Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(50)),
            imageModel = {
                friendRequest.second.profileImgUrl ?: R.drawable.account_circle_fill0_wght200_grad0_opsz24
            },
            imageOptions = ImageOptions(
                contentScale = ContentScale.FillWidth,
            )
        )
        Spacer(modifier = Modifier.width(10.dp))
        Column() {
            Text(
                text = friendRequest.second.name,
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = friendRequest.second.userId,
                fontSize = 16.sp,
                color = Color(0xFF999999)
            )
            Row() {
                ClickableBox(color = Color(0xFF2D2573), text = "수락", textColor = Color.White) { acceptFriendRequest() }
                Spacer(modifier = Modifier.width(10.dp))
                ClickableBox(color = Color(0xFFE4E4E6), text = "거절") { refuseFriendRequest() }
            }
        }
    }
}

@Composable
fun RowScope.ClickableBox(
    color: Color,
    text: String,
    textColor: Color = Color.Black,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxHeight()
            .weight(1f)
            .clip(RoundedCornerShape(10.dp))
            .background(
                color = color,
                shape = RoundedCornerShape(10.dp)
            )
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
            color = textColor
        )
    }
}

//@Preview(showBackground = true, backgroundColor = 0xFFAAAAAA)
//@Composable
//fun FriendRequestBoxPreview() {
//    FriendRequestBox(
//        FriendRequest("", "") to Friend(0, "홍길동", "hong", null),
//        {}, {}
//    )
//}