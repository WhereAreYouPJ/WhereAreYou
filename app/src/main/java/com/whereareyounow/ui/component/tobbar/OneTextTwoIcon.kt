package com.whereareyounow.ui.component.tobbar

import android.graphics.drawable.Icon
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.AbsoluteAlignment
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.whereareyounow.R
import com.whereareyounow.data.globalvalue.TOP_BAR_HEIGHT
import com.whereareyounow.ui.main.mypage.ModifierIconPopUp
import com.whereareyounow.ui.theme.medium18pt
import com.whereareyounow.util.CustomPreview
import com.whereareyounow.util.clickableNoEffect

@Composable
fun OneTextTwoIconTobBar(
    modifier: Modifier = Modifier,
    title: String,
    firstIconClicked: () -> Unit,
    firstIcon : Int,
    secondIconClicked: () -> Unit,
    secondIcon : Int,
    content: @Composable ColumnScope.() -> Unit

    ) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(TOP_BAR_HEIGHT.dp)
            .padding(start = 15.dp, end = 15.dp),
        horizontalAlignment = AbsoluteAlignment.Right

    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                modifier = Modifier
//                .align(Alignment.CenterStart)
                    .size(30.dp)
                    .clickableNoEffect { firstIconClicked() },
                painter = painterResource(id = firstIcon),
                contentDescription = null
            )
            Text(
                text = title,
                color = Color(0xFF000000),
                style = medium18pt
            )
            Image(
                modifier = Modifier
//                .align(Alignment.CenterStart)
                    .size(30.dp)
                    .clickableNoEffect { secondIconClicked() },
                painter = painterResource(id = secondIcon),
                contentDescription = null
            )
        }

        content()




    }
}

@CustomPreview
@Composable
private fun OneTextTwoIconTobBarPreview() {
    OneTextTwoIconTobBar(
        title = "아이디 찾기",
        firstIconClicked = {},
    firstIcon = R.drawable.ic_back,
    secondIconClicked = {},
    secondIcon = R.drawable.ic_back
    ) {

    }


}