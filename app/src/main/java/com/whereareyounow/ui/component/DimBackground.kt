package com.whereareyounow.ui.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.animateTo
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.whereareyounow.data.globalvalue.SYSTEM_STATUS_BAR_HEIGHT
import com.whereareyounow.data.globalvalue.TOTAL_SCREEN_HEIGHT
import com.whereareyounow.globalvalue.type.AnchoredDraggableContentState
import com.whereareyounow.util.clickableNoEffect
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DimBackground(
    anchoredDraggableState: AnchoredDraggableState<AnchoredDraggableContentState>,
    closeBottomDialog: () -> Unit,
) {
    val density = LocalDensity.current.density
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(((TOTAL_SCREEN_HEIGHT + SYSTEM_STATUS_BAR_HEIGHT) * density).dp)
            .offset {
                IntOffset(x = 0, y = (-SYSTEM_STATUS_BAR_HEIGHT * density).roundToInt())
            }
            .alpha(
                ((anchoredDraggableState.anchors.maxAnchor()) - (anchoredDraggableState.offset)) /
                        ((anchoredDraggableState.anchors.maxAnchor()) - (anchoredDraggableState.anchors.minAnchor()))
            )
            .background(color = Color(0xAA000000))
            .clickableNoEffect { closeBottomDialog() }
    )
}