package com.whereareyounow.ui.component

import androidx.annotation.FloatRange
import androidx.compose.animation.core.tween
import androidx.compose.animation.splineBasedDecay
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.SaverScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlin.math.round
import kotlin.math.roundToInt

open class SelectionLineStyle(
    val color: Color,
    @FloatRange(from = 0.0, to = 1.0)
    val lengthFraction: Float,
    val strokeWidth: Float
) {
    object Default: SelectionLineStyle(
        color = Color(0xff83cde6),
        lengthFraction = 1f,
        strokeWidth = 3f
    )
}

private class ScrollableSelectColumnItem<T>(var item: T, selected: Boolean = false) {
    var selected by mutableStateOf(selected)
}

@Composable
private fun ScrollableSelectColumn(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Layout(
        content = content,
        modifier = modifier
    ) { measurables, constraints ->
        val placeable = measurables.map {
            it.measure(constraints)
        }
        var needHeight = 0
        placeable.forEach { placeable ->
            needHeight += placeable.height
        }
        layout(
            constraints.maxWidth, needHeight
        ) {
            var currentY = 0
            placeable.forEach { placeable ->
                placeable.placeRelative(x = 0, y = currentY)
                currentY += placeable.height
            }
        }
    }
}

@Composable
private fun <E> ScrollableSelectColumnItemLayout(
    itemHeight: Dp,
    scrollableSelectItem: ScrollableSelectColumnItem<E>,
    content: @Composable RowScope.(E, Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(itemHeight),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        content(scrollableSelectItem.item, scrollableSelectItem.selected)
    }
}

class ScrollableSelectState(currentSwipeItemIndex: Int) {
    var currentSwipeItemIndex by mutableIntStateOf(currentSwipeItemIndex)

    companion object {
        val Saver = object : Saver<ScrollableSelectState, Int> {
            override fun restore(value: Int): ScrollableSelectState {
                return ScrollableSelectState(value)
            }

            override fun SaverScope.save(value: ScrollableSelectState): Int {
                return value.currentSwipeItemIndex
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun <E> ScrollableSelectLayout(
    items: List<E>,
    scrollableSelectState: ScrollableSelectState = rememberScrollableSelectState(),
    itemHeight: Dp,
    visibleAmount: Int = 3,
    selectionLineStyle: SelectionLineStyle = SelectionLineStyle.Default,
    content: @Composable RowScope.(item: E, Boolean) -> Unit
) {
    val density = LocalDensity.current

    val scrollableSelectColumnItems = remember(items) {
        items.map {
            ScrollableSelectColumnItem(it)
        }
    }

    var midItemIndexStart = remember(scrollableSelectColumnItems) {
        val midItemIndexStart = if (scrollableSelectState.currentSwipeItemIndex != -1) {
            scrollableSelectState.currentSwipeItemIndex - 1
        } else {
            (((scrollableSelectColumnItems.size - 1) / 2) - 1).coerceAtLeast(0).coerceAtMost(scrollableSelectColumnItems.size - 2)
        }
        scrollableSelectColumnItems[midItemIndexStart + 1].selected = true
        scrollableSelectState.currentSwipeItemIndex = midItemIndexStart + 1
        midItemIndexStart
    }

    val anchorDraggableState = remember {
        AnchoredDraggableState(
            initialValue = midItemIndexStart,
            positionalThreshold = { distance: Float -> distance * 0.5f },
            velocityThreshold = { with(density) { 50.dp.toPx() } },
            snapAnimationSpec = tween(),
            decayAnimationSpec = splineBasedDecay(density)
        ) {
            scrollableSelectColumnItems[midItemIndexStart + 1].selected = false
            scrollableSelectColumnItems[it + 1].selected = true
            midItemIndexStart = it
            scrollableSelectState.currentSwipeItemIndex = midItemIndexStart + 1
            true
        }.apply {
            updateAnchors(
                DraggableAnchors {
                    List(items.size) { index ->
                        index - 1 at with(density) { -round(itemHeight.toPx()) * index }
                    }
                }
            )
        }
    }

    val selectBoxOffset: Float = if (visibleAmount.mod(2) == 0) with(density) { itemHeight.toPx() } / 2f else 0f

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(itemHeight * visibleAmount)
            .anchoredDraggable(
                state = anchorDraggableState,
                orientation = Orientation.Vertical
            )
//            .drawWithContent {
//                val width = drawContext.size.width
//                val startFraction = (1 - selectionLineStyle.lengthFraction) / 2f
//                val endFraction = startFraction + selectionLineStyle.lengthFraction
//                drawContent()
//                drawLine(
//                    color = selectionLineStyle.color,
//                    start = Offset(
//                        width * startFraction,
//                        itemHeight.toPx() * ((visibleAmount - 1) / 2)
//                    ),
//                    end = Offset(
//                        width * endFraction,
//                        itemHeight.toPx() * ((visibleAmount - 1) / 2)
//                    ),
//                    strokeWidth = 3f
//                )
//                drawLine(
//                    color = selectionLineStyle.color,
//                    start = Offset(
//                        width * startFraction,
//                        itemHeight.toPx() * ((visibleAmount - 1) / 2 + 1)
//                    ),
//                    end = Offset(
//                        width * endFraction,
//                        itemHeight.toPx() * ((visibleAmount - 1) / 2 + 1)
//                    ),
//                    strokeWidth = selectionLineStyle.strokeWidth
//                )
//            }
            .graphicsLayer { clip = true }
    ) {
        ScrollableSelectColumn(
            modifier = Modifier
                .fillMaxWidth()
                .layout { measurable, constraints ->
                    val nonConstraints = Constraints(
                        minWidth = constraints.minWidth,
                        maxWidth = constraints.maxWidth
                    )
                    val placeable = measurable.measure(nonConstraints)
                    val currentY = placeable.height / 2 - (itemHeight.toPx() * 1.5).toInt()
                    layout(placeable.width, placeable.height) {
                        placeable.placeRelative(0, currentY - selectBoxOffset.toInt())
                    }
                }
                .offset {
                    IntOffset(
                        x = 0,
                        y = anchorDraggableState
                            .requireOffset()
                            .roundToInt(),
                    )
                }
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(itemHeight)
            )
            for (scrollableSelectItem in scrollableSelectColumnItems) {
                ScrollableSelectColumnItemLayout(
                    itemHeight = itemHeight,
                    scrollableSelectItem = scrollableSelectItem,
                    content = content
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(itemHeight)
            )
        }
    }
}

@Composable
fun rememberScrollableSelectState(
    initialItemIndex: Int = -1
) = rememberSaveable(saver = ScrollableSelectState.Saver) {
    ScrollableSelectState(initialItemIndex)
}