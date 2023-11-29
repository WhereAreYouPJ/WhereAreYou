package com.whereareyou.ui.home.friends

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun FriendBox(
    imageUrl: String,
    friendName: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp, bottom = 10.dp)
    ) {
        GlideImage(
            modifier = Modifier
                .width(40.dp)
                .height(40.dp)
                .clip(shape = RoundedCornerShape(50)),
            imageModel = { imageUrl },
            imageOptions = ImageOptions(
                contentScale = ContentScale.FillHeight,
            )
        )
        Text(
            text = friendName
        )
    }
}

//@Preview(showBackground = true)
//@Composable
//fun FriendBoxPreview() {
//    FriendBox(
//        "https://c8.alamy.com/comp/GJKAJ6/isolated-abstract-round-shape-green-color-plant-vector-logo-wheat-GJKAJ6.jpg"
//    )
//}