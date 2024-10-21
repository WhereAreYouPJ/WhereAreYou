package com.whereareyounow.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.whereareyounow.ui.theme.OnMyWayTheme
import com.whereareyounow.ui.theme.notoSanskr
import com.whereareyounow.util.clickableNoEffect

@Composable
fun CustomDialog(
    title: String,
    content: String,
    leftText: String,
    onLeftClick: () -> Unit,
    rightText: String,
    onRightClick: () -> Unit,
    onDismiss: () -> Unit,
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {
        OnMyWayTheme {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clickableNoEffect { onDismiss() },
                contentAlignment = Alignment.TopCenter
            ) {

                Box(
                    modifier = Modifier
                        .padding(start = 40.dp, top = 210.dp, end = 40.dp)
                        .fillMaxWidth()
                        .clickableNoEffect {}
                        .clip(RoundedCornerShape(20.dp))
                        .background(Color(0xFF333333))
                ) {
                    Column(
                        modifier = Modifier.padding(start = 10.dp, end = 10.dp)
                    ) {
                        Spacer(Modifier.height(20.dp))

                        Text(
                            modifier = Modifier.padding(6.dp, 4.dp, 6.dp, 4.dp),
                            text = title,
                            color = Color(0xFFFFFFFF),
                            fontFamily = notoSanskr,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )

                        Spacer(Modifier.height(8.dp))

                        Text(
                            modifier = Modifier.padding(6.dp, 4.dp, 6.dp, 4.dp),
                            text = content,
                            color = Color(0xFFA0A0A0),
                            fontFamily = notoSanskr,
                            fontWeight = FontWeight.Medium,
                            fontSize = 14.sp
                        )

                        Spacer(Modifier.height(24.dp))

                        Row(
                            modifier = Modifier.padding(start = 10.dp, end = 10.dp)
                        ) {
                            Spacer(Modifier.weight(1f))

                            Text(
                                modifier = Modifier
                                    .clickableNoEffect { onLeftClick() }
                                    .padding(6.dp, 4.dp, 6.dp, 4.dp),
                                text = leftText,
                                color = Color(0xFFFFFFFF),
                                fontFamily = notoSanskr,
                                fontWeight = FontWeight.Medium,
                                fontSize = 14.sp
                            )

                            Spacer(Modifier.width(10.dp))

                            Text(
                                modifier = Modifier
                                    .clickableNoEffect { onRightClick() }
                                    .padding(6.dp, 4.dp, 6.dp, 4.dp),
                                text = rightText,
                                color = Color(0xFFD49EFF),
                                fontFamily = notoSanskr,
                                fontWeight = FontWeight.Medium,
                                fontSize = 14.sp
                            )

                            Spacer(Modifier.width(10.dp))
                        }

                        Spacer(Modifier.height(20.dp))
                    }
                }
            }
        }
    }
}