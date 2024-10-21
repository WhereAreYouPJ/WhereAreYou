package com.whereareyounow.ui.main.mypage.location

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.whereareyounow.R
import com.whereareyounow.data.globalvalue.TOP_BAR_HEIGHT
import com.whereareyounow.ui.component.CustomSurface
import com.whereareyounow.ui.component.EmptyDataIndicator
import com.whereareyounow.ui.component.tobbar.OneTextTwoIconTobBar
import com.whereareyounow.ui.main.friend.GrayLine
import com.whereareyounow.ui.main.mypage.MyPageViewModel
import com.whereareyounow.ui.main.mypage.model.LocationFavoriteInfoModel
import com.whereareyounow.ui.theme.medium14pt
import com.whereareyounow.ui.theme.medium16pt
import com.whereareyounow.util.DraggableList
import com.whereareyounow.util.clickableNoEffect
import com.whereareyounow.util.popupmenu.CustomPopup
import com.whereareyounow.util.popupmenu.PopupPosition
import com.whereareyounow.util.popupmenu.PopupState

@Composable
fun FavoriteLocationScreen(
    moveToBackScreen : () -> Unit,
    moveToEditLocationFavorite : () -> Unit
) {
    CustomSurface {
        OneTextTwoIconTobBar(
            title = "위치 즐겨찾기",
            firstIconClicked = {  },
            firstIcon = R.drawable.ic_back,
            secondIconClicked = {  },
            secondIcon = R.drawable.ic_plusbrandcolor
        )

        DraggableList(
            mutableStateListOf("가", "나", "다", "라", "마", "바", "사", "아", "자", "차")
        )
    }
}




@Composable
fun DeleteIconPopUp(
    modifier: Modifier = Modifier,
    popupState: PopupState,
    isModifyClicked: MutableState<Boolean>,
    isReadOnly: MutableState<Boolean>,
    moveToEditMyInfoScreen: () -> Unit,
//    toastMessage : () -> Unit,
    favoriteLocationList : List<LocationFavoriteInfoModel?>

) {
    val density = LocalDensity.current.density
    val context = LocalContext.current
    Box(
        contentAlignment = Alignment.CenterEnd,
        modifier = modifier.padding(end = 15.dp, top = 83.dp - 48.dp)
    ) {
        CustomPopup(
            popupState = popupState,
            onDismissRequest = {
                popupState.isVisible = false
            }
        ) {
            CompositionLocalProvider(LocalDensity provides Density(density, fontScale = 1f)) {
                Box(
                    modifier = Modifier
                        .width(160.dp)
                        .height(38.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(color = Color(0xFF514675))
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .clickableNoEffect {
//                                popupState.isVisible = false
//                                isModifyClicked.value = true
//                                isReadOnly.value = false
//                                moveToEditMyInfoScreen()
                                if (favoriteLocationList.isEmpty()) {
//                                    toastMessage()
                                    Toast
                                        .makeText(context, "추가된 즐겨찾기가 없습니다.", Toast.LENGTH_SHORT)
                                        .show()
                                } else {
                                    moveToEditMyInfoScreen()
                                }
                            }
                        ,
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(
                                    start = 14.dp,
                                    top = 8.dp,
                                    bottom = 10.dp
                                ),
                            text = "위치 삭제",
                            style = medium14pt,
                            color = Color.White
                        )

                    }
                }
            }
        }
    }
}

@Composable
fun DetailFavoriteLocation(
    title: String,
//    isClickedState: MutableState<Boolean>,
    isClicked: () -> Unit,
) {


    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 15.dp, end = 15.dp)
            .height(46.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = medium16pt,
            color = Color(0xFF222222)
        )
        Spacer(Modifier.weight(1f))
        Image(
            painter = painterResource(id = R.drawable.ic_hambuger),
            contentDescription = "",
            modifier = Modifier.clickableNoEffect {
                isClicked()
            }
        )

    }
}