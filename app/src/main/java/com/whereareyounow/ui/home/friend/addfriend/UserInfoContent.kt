package com.whereareyounow.ui.home.friend.addfriend

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
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
fun UserInfoContent(
    imageUrl: String?,
    userName: String
) {
    GlideImage(
        modifier = Modifier
            .size(200.dp)
            .clip(RoundedCornerShape(50)),
        imageModel = { imageUrl ?: R.drawable.account_circle_fill0_wght200_grad0_opsz24 },
        imageOptions = ImageOptions(contentScale = ContentScale.Crop)
    )
    Spacer(Modifier.height(20.dp))
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = userName,
            fontSize = 30.sp
        )
    }
}

//@Preview(showBackground = true)
//@Composable
//fun UserInfoContentPreview() {
//    UserInfoContent(
//        imageUrl = null,
//        userName = "홍길동"
//    )
//}