package com.whereareyounow.ui.main.mypage.byebye

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.whereareyounow.R
import com.whereareyounow.data.globalvalue.BOTTOM_NAVIGATION_BAR_HEIGHT
import com.whereareyounow.data.globalvalue.TOP_BAR_HEIGHT
import com.whereareyounow.ui.component.CustomTopBar
import com.whereareyounow.ui.component.tobbar.DefaultTopBar
import com.whereareyounow.util.clickableNoEffect

@Composable
fun ByeScreen5(
    moveToBackScreen : () -> Unit

) {
    ByeScreen5(
        isContent = true,
        moveToBackScreen = moveToBackScreen
    )
}


@Composable
private fun ByeScreen5(
    isContent : Boolean,
    moveToBackScreen : () -> Unit,

) {
    val contexxt = LocalContext.current
    val density = LocalDensity.current
    val ha = with(density) { 174f.toDp() + BOTTOM_NAVIGATION_BAR_HEIGHT.toDp() }
    Box(
        modifier = Modifier.fillMaxSize().padding(top = TOP_BAR_HEIGHT.dp)
    ) {
        DefaultTopBar(
            title = "회원탈퇴"
        ) {
            moveToBackScreen()
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = TOP_BAR_HEIGHT.dp , start = 15.dp , end = 15.dp)
        ) {

            Spacer(Modifier.height(30.dp))

            Image(
                painter = painterResource(id = R.drawable.ic_byebyecompleted),
                contentDescription = "",
            )
            Spacer(Modifier.weight(1f))


            Image(
                painter = painterResource(id = R.drawable.ic_omgomgomg),
                contentDescription = "",
                modifier = Modifier.padding(bottom = ha)
                    .aspectRatio(16f / 9f)
                    .fillMaxWidth()
                    .scale(1.1f)
            )
//            Spacer(Modifier.weight(1f))


            Image(
                painter = painterResource(id = R.drawable.ic_confirm),
                contentDescription = "",
                modifier = Modifier
                    .padding(bottom = 68.dp)
                    .clickableNoEffect {
                        (contexxt as? Activity)?.finish()

                    }
            )

        }
    }



}

