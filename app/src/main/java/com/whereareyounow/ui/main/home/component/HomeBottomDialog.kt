package com.whereareyounow.ui.main.home.component

import androidx.compose.animation.core.exponentialDecay
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.whereareyounow.data.globalvalue.BOTTOM_NAVIGATION_BAR_HEIGHT
import com.whereareyounow.data.globalvalue.SYSTEM_NAVIGATION_BAR_HEIGHT
import com.whereareyounow.data.globalvalue.SYSTEM_STATUS_BAR_HEIGHT
import com.whereareyounow.data.globalvalue.TOTAL_SCREEN_HEIGHT
import com.whereareyounow.domain.entity.schedule.DailyScheduleInfo
import com.whereareyounow.globalvalue.globalDensity
import com.whereareyounow.globalvalue.type.AnchoredDraggableContentState
import com.whereareyounow.ui.theme.getColor
import com.whereareyounow.ui.theme.medium14pt
import com.whereareyounow.ui.theme.medium18pt
import com.whereareyounow.ui.theme.notoSanskr
import com.whereareyounow.util.clickableNoEffect
import com.whereareyounow.util.drawColoredShadow
import java.time.LocalDate
import kotlin.math.roundToInt

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeBottomDialog(
    anchoredDraggableState: AnchoredDraggableState<AnchoredDraggableContentState>,
    dailyScheduleList: List<DailyScheduleInfo>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(430.dp)
            .offset {
                IntOffset(
                    x = 0,
                    y = (anchoredDraggableState
                        .requireOffset()
                        .roundToInt())
                )
            }
            .drawColoredShadow(
                borderRadius = 20.dp,
                shadowRadius = 10.dp
            )
            .background(
                color = Color(0xFFFFFFFF),
                shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
            )
            .clickableNoEffect {}
            .padding(start = 22.dp, end = 22.dp)
    ) {
        Spacer(Modifier.height(6.dp))

        Box(
            Modifier
                .align(Alignment.CenterHorizontally)
                .width(130.dp)
                .height(6.dp)
                .clip(RoundedCornerShape(50))
                .background(Color.Black)
                .anchoredDraggable(anchoredDraggableState, Orientation.Vertical)
        )

        Spacer(Modifier.height(10.dp))

        Text(
            modifier = Modifier
                .padding(6.dp, 2.dp, 6.dp, 2.dp)
                .alpha(((anchoredDraggableState.anchors.maxAnchor()) - (anchoredDraggableState.offset)) /
                        ((anchoredDraggableState.anchors.maxAnchor()) - (anchoredDraggableState.anchors.minAnchor()))),
            text = "${LocalDate.now().monthValue}월 ${LocalDate.now().dayOfMonth}일",
            color = getColor().brandText,
            fontFamily = notoSanskr,
            fontWeight = FontWeight.Medium,
            fontSize = 22.sp
        )

        Spacer(Modifier.height(14.dp))

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .alpha(((anchoredDraggableState.anchors.maxAnchor()) - (anchoredDraggableState.offset)) /
                        ((anchoredDraggableState.anchors.maxAnchor()) - (anchoredDraggableState.anchors.minAnchor())))
        ) {
            itemsIndexed(dailyScheduleList) { idx, item ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(46.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            modifier = Modifier.padding(6.dp, 0.dp, 6.dp, 0.dp),
                            text = item.title,
                            color = Color(0xFF444444),
                            style = medium18pt
                        )

                        Text(
                            modifier = Modifier.padding(6.dp, 0.dp, 6.dp, 0.dp),
                            text = item.location,
                            color = Color(0xFF999999),
                            style = medium14pt
                        )
                    }

                    Box(
                        modifier = Modifier
                            .width(100.dp)
                            .height(42.dp)
                            .background(
                                color = getColor().brandColor,
                                shape = RoundedCornerShape(6.dp),
                            )
                            .clickableNoEffect { },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "위치 확인하기",
                            color = Color(0xFFFFFFFF),
                            style = medium14pt
                        )
                    }
                }
            }
        }
    }
}

// anchoredDraggableState의 offset은 pixel단위로 측정됨.
// y offset이 0일 때 statusbar 바로 아래부터 시작됨.
@OptIn(ExperimentalFoundationApi::class)
fun getHomeAnchoredDraggableState() = AnchoredDraggableState(
    initialValue = AnchoredDraggableContentState.Closed,
    positionalThreshold = { it: Float -> it * 1f },
    velocityThreshold = { 100f },
    snapAnimationSpec = tween(200),
    decayAnimationSpec = exponentialDecay(200f),
).apply {
    updateAnchors(
        DraggableAnchors {
            AnchoredDraggableContentState.Open at (TOTAL_SCREEN_HEIGHT - 428f - SYSTEM_STATUS_BAR_HEIGHT - SYSTEM_NAVIGATION_BAR_HEIGHT - BOTTOM_NAVIGATION_BAR_HEIGHT) * globalDensity
            AnchoredDraggableContentState.Closed at (TOTAL_SCREEN_HEIGHT - SYSTEM_STATUS_BAR_HEIGHT - SYSTEM_NAVIGATION_BAR_HEIGHT - BOTTOM_NAVIGATION_BAR_HEIGHT - 40f) * globalDensity
        }
    )
}