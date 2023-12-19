package com.whereareyounow.ui.home.friends

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import com.whereareyounow.R

@Composable
fun FriendBox(
    imageUrl: String?,
    friendName: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp, bottom = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        GlideImage(
            modifier = Modifier
                .width(40.dp)
                .height(40.dp)
                .clip(RoundedCornerShape(50)),
            imageModel = { imageUrl ?: R.drawable.account_circle_fill0_wght200_grad0_opsz24 },
            imageOptions = ImageOptions(
                contentScale = ContentScale.FillWidth,
            )
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = friendName,
            fontSize = 20.sp
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