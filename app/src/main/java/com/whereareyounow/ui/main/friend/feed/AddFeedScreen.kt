package com.whereareyounow.ui.main.friend.feed

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import com.whereareyounow.R
import com.whereareyounow.data.addfeed.AddFeedScreenUIState
import com.whereareyounow.domain.entity.schedule.ScheduleListItem
import com.whereareyounow.ui.component.CustomSurface
import com.whereareyounow.ui.component.CustomTopBar
import com.whereareyounow.ui.component.RoundedCornerButton
import com.whereareyounow.ui.component.ScrollableContent
import com.whereareyounow.ui.theme.getColor
import com.whereareyounow.ui.theme.medium12pt
import com.whereareyounow.ui.theme.medium14pt
import com.whereareyounow.ui.theme.medium16pt
import com.whereareyounow.ui.theme.notoSanskr
import com.whereareyounow.util.UriRequestBody
import com.whereareyounow.util.calendar.parseLocalDate
import com.whereareyounow.util.clickableNoEffect
import com.whereareyounow.util.popupmenu.PopupPosition
import com.whereareyounow.util.popupmenu.PopupState

@Composable
fun AddFeedScreen(
    uiState: AddFeedScreenUIState,
    updateSelectedSchedule: (ScheduleListItem) -> Unit,
    updateTitle: (String) -> Unit,
    updateContent: (String) -> Unit,
    addImages: (List<UriRequestBody>) -> Unit,
    removeImage: (String) -> Unit,
    createFeed: (() -> Unit) -> Unit,
    moveToBackScreen: () -> Unit,
) {
    val context = LocalContext.current
    val takePhotoFromAlbumLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.PickMultipleVisualMedia(10)) { uriList ->
            Log.e("PickImage", "success")
            Log.e("PickImage", "${uriList}")
            uriList.let {
                addImages(uriList.map { UriRequestBody(context, Uri.parse(it.toString())) })
            }
        }
    val isExpanded = remember { mutableStateOf(false) }
    CustomSurface {
        CustomTopBar(
            title = "새 피드 작성",
            onBackButtonClicked = moveToBackScreen
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color(0xFFC9C9C9))
        )

        ScrollableContent(
            modifier = Modifier.fillMaxSize(),
            content = {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 15.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .padding(start = 15.dp, end = 15.dp)
                            .zIndex(1f)
                            .border(
                                border = BorderStroke(
                                    width = 1.dp,
                                    color = getColor().brandColor
                                ),
                                shape = RoundedCornerShape(8.dp)
                            )
                            .background(Color(0xFFFFFFFF))
                            .animateContentSize()
                    ) {
                        Column(
                            modifier = Modifier.height((if (isExpanded.value) 460 else 50).dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .height(50.dp)
                                    .clickableNoEffect { isExpanded.value = !isExpanded.value }
                                    .padding(start = 14.dp, end = 14.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                if (uiState.selectedSchedule == null) {
                                    Text(
                                        modifier = Modifier.weight(1f),
                                        text = "일정 선택",
                                        color = Color(0xFF666666),
                                        style = medium16pt
                                    )
                                } else {
                                    Text(
                                        modifier = Modifier.width(36.dp),
                                        text = parseLocalDate(uiState.selectedSchedule.startTime).toString().replace("-", "."),
                                        color = Color(0xFF444444),
                                        style = medium12pt
                                    )

                                    Spacer(Modifier.width(4.dp))

                                    Text(
                                        modifier = Modifier.weight(1f),
                                        text = uiState.selectedSchedule.title,
                                        color = Color(0xFF444444),
                                        style = medium16pt
                                    )
                                }

                                Image(
                                    modifier = Modifier
                                        .size(30.dp),
                                    painter = painterResource(id = if (isExpanded.value) R.drawable.ic_arrow_up else R.drawable.ic_arrow_down),
                                    contentDescription = null
                                )
                            }

                            LazyColumn(
                                modifier = Modifier.weight(1f)
                            ) {
                                itemsIndexed(uiState.scheduleListMap.toList()) { idx, item ->
                                    Column {
                                        Spacer(Modifier.height(16.dp))

                                        Row(
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Spacer(Modifier.width(9.dp))

                                            Image(
                                                modifier = Modifier.size(12.dp),
                                                painter = painterResource(R.drawable.ic_dot),
                                                contentDescription = null
                                            )

                                            Spacer(Modifier.width(2.dp))

                                            Text(
                                                text = item.first.replace("-", "."),
                                                color = getColor().brandText,
                                                style = medium12pt
                                            )
                                        }

                                        item.second.forEachIndexed { idx, item ->
                                            Column(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .clickableNoEffect {
                                                        updateSelectedSchedule(item)
                                                        isExpanded.value = false
                                                    }
                                                    .padding(6.dp)
                                            ) {
                                                Text(
                                                    modifier = Modifier.padding(6.dp, 2.dp, 6.dp, 2.dp),
                                                    text = item.title,
                                                    color = Color(0xFF444444),
                                                    style = medium16pt
                                                )

                                                Text(
                                                    modifier = Modifier.padding(6.dp, 2.dp, 6.dp, 2.dp),
                                                    text = item.scheduleSeq.toString(),
                                                    color = Color(0xFF999999),
                                                    style = medium14pt
                                                )
                                            }

                                            Box(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .height(1.dp)
                                                    .background(getColor().thinnest)
                                            )
                                        }

                                        Box(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(6.dp)
                                                .background(Color(0xFFEEEEEE))
                                        )
                                    }
                                }
                            }
                        }
                    }

                    // 제목 입력
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 80.dp)
                    ) {
                        BasicTextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 15.dp, end = 15.dp),
                            value = uiState.title,
                            onValueChange = updateTitle,
                            textStyle = medium16pt.copy(color = Color(0xFF222222)),
                            maxLines = 1
                        ) {
                            Box(
                                modifier = Modifier
                                    .height(36.dp)
                                    .padding(6.dp, 4.dp, 6.dp, 10.dp),
                                contentAlignment = Alignment.CenterStart
                            ) {
                                it()

                                if (uiState.title == "") {
                                    Text(
                                        text = "제목",
                                        color = getColor().dark,
                                        style = medium16pt
                                    )
                                }
                            }
                        }

                        Box(
                            modifier = Modifier
                                .padding(start = 15.dp, end = 15.dp)
                                .fillMaxWidth()
                                .height(1.dp)
                                .background(getColor().thinnest)
                        )

                        Spacer(Modifier.height(8.dp))

                        LazyRow {
                            itemsIndexed(uiState.imageUris) { idx, item ->
                                if (idx == 0) {
                                    Spacer(Modifier.width(15.dp))
                                }

                                Box(
                                    modifier = Modifier
                                        .width(270.dp)
                                        .height(232.dp)
                                        .clip(RoundedCornerShape(6.dp))
                                ) {
                                    GlideImage(
                                        modifier = Modifier.fillMaxSize(),
                                        imageModel = { item.originalUri },
                                        imageOptions = ImageOptions(
                                            contentScale = ContentScale.Crop
                                        )
                                    )

                                    Image(
                                        modifier = Modifier
                                            .align(Alignment.TopEnd)
                                            .padding(top = 10.dp, end = 10.dp)
                                            .size(22.dp)
                                            .clickableNoEffect { removeImage(item.originalUri) },
                                        painter = painterResource(R.drawable.ic_close),
                                        contentDescription = null
                                    )
                                }

                                Spacer(Modifier.width((if (idx == uiState.imageUris.size - 1) 15 else 8).dp))
                            }
                        }

                        BasicTextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 15.dp, end = 15.dp)
                                .padding(8.dp),
                            value = uiState.content,
                            onValueChange = updateContent,
                            textStyle = medium14pt.copy(color = Color(0xFF222222))
                        ) {
                            it()

                            if (uiState.content == "") {
                                Text(
                                    text = "어떤 일이 있었나요?",
                                    color = getColor().dark,
                                    style = medium14pt
                                )
                            }
                        }

                        Spacer(Modifier.height(80.dp))

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(1.dp)
                                .background(getColor().thinnest)
                        )

                        Row(
                            modifier = Modifier
                                .padding(start = 15.dp, end = 15.dp)
                                .fillMaxWidth()
                                .height(46.dp)
                                .clickableNoEffect {
                                    takePhotoFromAlbumLauncher.launch(
                                        PickVisualMediaRequest(
                                            ActivityResultContracts.PickVisualMedia.ImageOnly
                                        )
                                    )
                                },
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                modifier = Modifier.size(30.dp),
                                painter = painterResource(R.drawable.ic_gallery),
                                contentDescription = null
                            )

                            Text(
                                modifier = Modifier.padding(4.dp, 2.dp, 4.dp, 2.dp),
                                text = "사진 추가",
                                color = Color(0xFF666666),
                                style = medium16pt
                            )
                        }

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(1.dp)
                                .background(getColor().thinnest)
                        )
                    }
                }
            },
            lastContent = {
                Box(
                    modifier = Modifier.padding(start = 15.dp, end = 15.dp)
                ) {
                    RoundedCornerButton(onClick = { createFeed(moveToBackScreen) }) {
                        Text(
                            text = "게시하기",
                            color = Color(0xFFFFFFFF),
                            fontFamily = notoSanskr,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                    }
                }

                Spacer(Modifier.height(20.dp))
            }
        )
    }
}