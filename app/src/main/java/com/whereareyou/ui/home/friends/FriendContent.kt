package com.whereareyou.ui.home.friends

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
        itemsIndexed(friendsList) { _, friend ->
            FriendBox(
                imageUrl = "https://c8.alamy.com/comp/GJKAJ6/isolated-abstract-round-shape-green-color-plant-vector-logo-wheat-GJKAJ6.jpg",
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