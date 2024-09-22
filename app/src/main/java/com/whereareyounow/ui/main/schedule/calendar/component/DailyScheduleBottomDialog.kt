package com.whereareyounow.ui.main.schedule.calendar.component

import androidx.compose.animation.core.exponentialDecay
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import com.whereareyounow.globalvalue.type.AnchoredDraggableContentState
import com.whereareyounow.ui.theme.getColor
import com.whereareyounow.ui.theme.notoSanskr
import com.whereareyounow.util.clickableNoEffect
import kotlin.math.roundToInt

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DailyScheduleBottomDialog(
    anchoredDraggableState: AnchoredDraggableState<AnchoredDraggableContentState>,
    selectedMonth: Int,
    selectedDate: Int,
    dailyScheduleList: List<DailyScheduleInfo>,
    moveToDetailScheduleScreen: (Int) -> Unit,
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
                        .roundToInt() * density).roundToInt()
                )
            }
            .background(
                color = Color.White,
                shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)
            )
            .clickableNoEffect {}
            .padding(start = 15.dp, end = 15.dp)
    ) {
        Spacer(Modifier.height(6.dp))

        Spacer(
            Modifier
                .align(Alignment.CenterHorizontally)
                .width(130.dp)
                .height(4.dp)
                .clip(RoundedCornerShape(50))
                .background(Color.Black))

        Spacer(Modifier.height(14.dp))

        Text(
            modifier = Modifier.padding(start = 6.dp, top = 2.dp, end = 6.dp, bottom = 2.dp),
            text = "${selectedMonth}월 ${selectedDate}일",
            color = getColor().brandText,
            fontFamily = notoSanskr,
            fontWeight = FontWeight.Medium,
            fontSize = 22.sp
        )

        Spacer(Modifier.height(8.dp))

        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            itemsIndexed(dailyScheduleList) { idx, item ->
                Column(
                    modifier = Modifier.clickableNoEffect { moveToDetailScheduleScreen(item.scheduleSeq) }
                ) {
                    Spacer(Modifier.height(6.dp))

                    DailyScheduleBox(info = item)

                    Spacer(Modifier.height(6.dp))
                }

                if (idx != dailyScheduleList.size - 1) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(getColor().thinnest)
                    )
                }
            }
        }
    }
}

// anchoredDraggableState의 offset은 pixel단위로 측정됨.
// y offset이 0일 때 statusbar 바로 아래부터 시작됨.
@OptIn(ExperimentalFoundationApi::class)
fun getDailyScheduleAnchoredDraggableState() = AnchoredDraggableState(
    initialValue = AnchoredDraggableContentState.Closed,
    positionalThreshold = { it: Float -> it * 0.5f },
    velocityThreshold = { 100f },
    snapAnimationSpec = tween(400),
    decayAnimationSpec = exponentialDecay(400f),
).apply {
    updateAnchors(
        DraggableAnchors {
            AnchoredDraggableContentState.Open at TOTAL_SCREEN_HEIGHT - 428f - SYSTEM_STATUS_BAR_HEIGHT - SYSTEM_NAVIGATION_BAR_HEIGHT - BOTTOM_NAVIGATION_BAR_HEIGHT
            AnchoredDraggableContentState.Closed at TOTAL_SCREEN_HEIGHT - SYSTEM_STATUS_BAR_HEIGHT
        }
    )
}