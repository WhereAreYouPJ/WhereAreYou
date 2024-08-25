package com.whereareyounow.ui.main.friend

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.bumptech.glide.Glide
import com.skydoves.landscapist.glide.GlideImage
import com.whereareyounow.R
import com.whereareyounow.data.globalvalue.TOP_BAR_HEIGHT
import com.whereareyounow.ui.theme.medium20pt


@Composable
fun DetailProfileScreen(
    friendImagrUrl: String,
    friendName: String,
    moveToBackScreen: () -> Unit
) {

    DetailProfileScreen(
        true,
        friendImagrUrl,
        friendName,
        moveToBackScreen
    )

}

@Composable
private fun DetailProfileScreen(
    isContent: Boolean,
    friendImagrUrl: String,
    friendName: String,
    moveToBackScreen: () -> Unit
) {
    Column(
        modifier = Modifier.background(Color(0XFFB5B5B5))
    ) {
        Spacer(Modifier.size(TOP_BAR_HEIGHT.dp))

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_x),
                contentDescription = "",
                modifier = Modifier
                    .padding(start = 20.dp, top = 11.dp)
                    .clickable {
                        moveToBackScreen()
                    },
                colorFilter = ColorFilter.tint(color = Color(0XFFEEEEEE))
            )
            Spacer(Modifier.weight(1f))
            Image(
                painter = painterResource(id = R.drawable.ic_detail_friend_star),
                contentDescription = "",
                modifier = Modifier.padding(start = 20.dp, top = 11.dp),
                colorFilter = ColorFilter.tint(color = Color(0XFFEEEEEE))
            )
            Image(
                painter = painterResource(id = R.drawable.ic_more),
                contentDescription = "",
                modifier = Modifier.padding(start = 6.dp, top = 11.dp ,end = 20.dp),
                colorFilter = ColorFilter.tint(color = Color(0XFFEEEEEE))
            )
        }



        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 491.dp),
//            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
//            Box(
////                modifier = Modifier.padding(491.dp)
//
//            ) {


            GlideImage(
                modifier = Modifier
                    .padding(start = 137.5.dp, end = 137.5.dp, bottom = 6.dp)
                    .size(100.dp)
                    .clip(RoundedCornerShape(18.dp)),
                imageModel = { friendImagrUrl ?: R.drawable.idle_profile2 },
            )
//            }
            Spacer(Modifier.size(6.dp))
            Text(friendName ?: "유민혁", style = medium20pt , color = Color(0XFFFFFFFF))


        }



        Text("sfsss")

    }

}


