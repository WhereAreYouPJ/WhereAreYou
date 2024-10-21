package com.whereareyounow.util

import android.util.Log
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.whereareyounow.domain.util.LogUtil
import kotlinx.coroutines.launch
import kotlin.random.Random

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DraggableList(
    list: SnapshotStateList<String>
) {
    // list[n] = n번째 위치에 있는 숫자
    val density = LocalDensity.current.density
    LogUtil.e("density", density.toString())
//    val list = remember { mutableStateListOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10) }
    // index[n] = n의 index
    val index = remember { mutableStateListOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10) }

    val offset = remember { mutableStateListOf(0f, 100 * density * 1, 100 * density * 2, 100 * density * 3, 100 * density * 4, 100 * density * 5, 100 * density * 6, 100 * density * 7, 100 * density * 8, 100 * density * 9, 100 * density * 10) }
    val lazyListState = rememberLazyListState()
    LazyColumn(
        modifier = Modifier
//            .padding(top = 100.dp, bottom = 100.dp)
            .fillMaxSize(),
        state = lazyListState
    ) {
        itemsIndexed(list, key = { idx, item -> "${item}"}) { idx, item ->
            val color = remember { Color(Random.nextInt(255), Random.nextInt(255), Random.nextInt(255)) }
            val coroutineScope = rememberCoroutineScope()
            val yPos = remember { Animatable(0f) }
            val onDrag = remember { mutableStateOf(false) }
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .offset {
                        IntOffset(
                            0,
                            yPos.value.toInt()
                        )
                    }
                    .background(color)
                    .then(
                        if (onDrag.value) {
                            Modifier.zIndex(10f)
                        } else {
                            Modifier.animateItem(
                                fadeInSpec = null,
                                placementSpec = null,
                                fadeOutSpec = null
                            )
                        }
                    )
                    .pointerInput(Unit) {
                        detectDragGestures(
                            onDragStart = {
                                Log.e("onDragStart", "${it}")
                                onDrag.value = true
//                            xPos.intValue = it.x.toInt()
//                            yPos.intValue = it.y.toInt()
                            },
                            onDragEnd = {
                                coroutineScope.launch { yPos.animateTo(0f, tween(durationMillis = 200)) }
                                onDrag.value = false
                                list.forEach { Log.e("", "${it}") }
                            },
                            onDragCancel = {
                                list.forEach { Log.e("", "${it}") }
                            },
                            onDrag = { _, it ->
                                Log.e("onDrag", "idx: ${idx}, item: ${item} ${yPos.value.toInt()}")
//                                xPos.intValue += it.x.toInt()
                                coroutineScope.launch { yPos.snapTo(yPos.value + it.y) }
//                                coroutineScope.launch { yPos.animateTo(yPos.value + (it.y), tween(durationMillis = 0)) }
                                if (index[idx] < list.size - 1) {
                                    if ((yPos.value) + offset[index[idx]] + 50 * density > offset[index[idx] + 1]) {
                                        val nextItem = list[index[idx] + 1]
                                        list[index[idx] + 1] = item
                                        list[index[idx]] = nextItem
                                        index[idx]++
                                        index[idx + 1]--
                                        coroutineScope.launch { yPos.snapTo(-50 * density) }
//                                        coroutineScope.launch { yPos.animateTo(0f, tween(durationMillis = 0)) }
                                    }
                                }
                                if (index[idx] > 0) {
                                    if ((yPos.value) + offset[index[idx]] - 50 * density < offset[index[idx] - 1]) {
                                        val prevItem = list[index[idx] - 1]
                                        list[index[idx] - 1] = item
                                        list[index[idx]] = prevItem
                                        index[idx]--
                                        index[idx]++
                                        coroutineScope.launch { yPos.snapTo(50 * density) }
//                                        coroutineScope.launch { yPos.animateTo(0f, tween(durationMillis = 0)) }
                                    }
                                }
                            }
                        )
                    },
                contentAlignment = Alignment.Center
            ) {
                Text("${item}")
            }
        }
    }
}