package com.whereareyou.ui.home.friends

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.whereareyou.R
import com.whereareyou.data.FriendProvider
import com.whereareyou.domain.entity.schedule.Friend

@Composable
fun FriendContent(
    friendsList: List<Friend>
) {
//    Row(
//    ) {
//        Image(
//            painter = painterResource(id = R.drawable.mypage_filled),
//            contentDescription = null
//        )
//        Text(
//            text = "이서영"
//        )
//    }
    LazyColumn() {
        item {
            Box(
                modifier = Modifier.height(40.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    text = "친구 ${friendsList.size}",
                    fontSize = 20.sp
                )
            }
        }
        itemsIndexed(friendsList) { _, friend ->
            FriendBox(
                imageUrl = friend.profileImgUrl,
                friendName = friend.name
            )
//            Text(
//                modifier = Modifier
//                    .padding(top = 10.dp, bottom = 10.dp),
//                text = friend.name
//            )
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(0.5.dp)
                    .background(
                        color = Color(0xFFAAAAAA)
                    )
            )
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun FriendContentPreview() {
//    FriendContent()
//}