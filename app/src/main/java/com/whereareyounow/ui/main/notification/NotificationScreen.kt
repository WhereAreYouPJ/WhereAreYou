package com.whereareyounow.ui.main.notification

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.whereareyounow.R
import com.whereareyounow.data.globalvalue.TOP_BAR_HEIGHT
import com.whereareyounow.ui.component.CustomSurface
import com.whereareyounow.ui.theme.getColor
import com.whereareyounow.ui.theme.medium14pt
import com.whereareyounow.ui.theme.medium16pt
import com.whereareyounow.ui.theme.medium18pt
import com.whereareyounow.ui.theme.medium20pt
import com.whereareyounow.util.clickableNoEffect

@Composable
fun NotificationScreen(
    moveToBackScreen: () -> Unit,
) {
    CustomSurface {
        Row(
            modifier = Modifier
                .padding(start = 15.dp, end = 15.dp)
                .fillMaxWidth()
                .height(TOP_BAR_HEIGHT.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.padding(4.dp),
                text = "알림",
                color = Color(0xFF222222),
                style = medium18pt
            )

            Spacer(Modifier.weight(1f))

            Image(
                modifier = Modifier
                    .size(30.dp)
                    .clickableNoEffect { moveToBackScreen() },
                painter = painterResource(R.drawable.ic_close2),
                contentDescription = null
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color(0xFFC9C9C9))
        )

        Spacer(Modifier.height(16.dp))

        Column(
            modifier = Modifier.padding(start = 15.dp, end = 15.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.padding(4.dp, 2.dp, 4.dp, 2.dp),
                    text = "일정",
                    color = Color(0xFF999999),
                    style = medium14pt
                )

                Spacer(Modifier.weight(1f))

                Image(
                    modifier = Modifier.size(22.dp),
                    painter = painterResource(R.drawable.ic_arrow_down),
                    contentDescription = null
                )
            }

            Spacer(Modifier.height(6.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .border(
                        border = BorderStroke(
                            width = 1.dp,
                            color = getColor().brandColor
                        ),
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(15.dp)
            ) {
                Row {
                    Text(
                        text = "일정에 초대되었습니다.",
                        color = Color(0xFF222222),
                        style = medium16pt
                    )

                    Spacer(Modifier.weight(1f))

                    Text(
                        text = "0분전",
                        color = Color(0xFF999999),
                        style = medium14pt
                    )
                }

                Spacer(Modifier.height(12.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            modifier = Modifier.padding(start = 6.dp, end = 6.dp),
                            text = "4월 5일",
                            color = getColor().brandText,
                            style = medium20pt
                        )

                        Text(
                            text = "D - 5",
                            color = Color(0xFFFC2F2F),
                            style = medium14pt
                        )
                    }

                    Column {
                        Text(
                            modifier = Modifier.padding(start = 6.dp, top = 2.dp, bottom = 2.dp),
                            text = "한강공원",
                            color = Color(0xFF222222),
                            style = medium18pt
                        )

                        Text(
                            modifier = Modifier.padding(start = 6.dp),
                            text = "여의도한강공원",
                            color = Color(0xFF999999),
                            style = medium14pt
                        )
                    }
                }

                Spacer(Modifier.height(12.dp))

                Row {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(46.dp)
                            .background(
                                color = getColor().brandColor,
                                shape = RoundedCornerShape(6.dp)
                            )
                            .clickableNoEffect { },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "수락하기",
                            color = Color(0xFFFFFFFF),
                            style = medium16pt
                        )
                    }

                    Spacer(Modifier.width(8.dp))

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(46.dp)
                            .border(
                                border = BorderStroke(
                                    width = 1.dp,
                                    color = Color(0xFF999999)
                                ),
                                shape = RoundedCornerShape(6.dp)
                            )
                            .clickableNoEffect { },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "수락하기",
                            color = Color(0xFF222222),
                            style = medium16pt
                        )
                    }
                }
            }
        }

        Spacer(Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color(0xFFC9C9C9))
        )

        Spacer(Modifier.height(10.dp))

        Column(
            modifier = Modifier.padding(start = 15.dp, end = 15.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.padding(4.dp, 2.dp, 4.dp, 2.dp),
                    text = "친구",
                    color = Color(0xFF999999),
                    style = medium14pt
                )

                Spacer(Modifier.weight(1f))

                Image(
                    modifier = Modifier.size(22.dp),
                    painter = painterResource(R.drawable.ic_arrow_down),
                    contentDescription = null
                )
            }
        }

    }
}