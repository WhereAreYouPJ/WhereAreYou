package com.whereareyounow.ui.main.mypage

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import com.whereareyounow.R
import com.whereareyounow.data.globalvalue.TOP_BAR_HEIGHT
import com.whereareyounow.ui.component.CustomTopBar
import com.whereareyounow.ui.component.tobbar.OneTextTwoIconTobBar
import com.whereareyounow.ui.theme.medium12pt
import com.whereareyounow.ui.theme.medium14pt
import com.whereareyounow.util.popupmenu.CustomPopup
import com.whereareyounow.util.popupmenu.PopupPosition
import com.whereareyounow.util.popupmenu.PopupState

@Composable
fun MyInfoScreen(
    moveToMyPageScreen: () -> Unit
) {
    val chaneValuePermissionState = remember {
        mutableStateOf(false)
    }
    val popupState = remember { PopupState(true , PopupPosition.BottomLeft) }
//    val popupState = remember { PopupState(false, PopupPosition.BottomLeft) }

    MyInfoScreen(
        chaneValuePermissionState = chaneValuePermissionState,
        popupState = popupState,
        moveToMyPageScreen = moveToMyPageScreen
    )




}

class asdf : ViewModel() {
    val name = "d"
    val id = "d"
    val email = "d"
}
@Composable
private fun MyInfoScreen(
    chaneValuePermissionState: MutableState<Boolean>,
    popupState : PopupState,
    moveToMyPageScreen: () -> Unit,

) {
    val asdfViewModel : asdf = hiltViewModel()


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = TOP_BAR_HEIGHT.dp),
        verticalArrangement = Arrangement.Top,

        ) {
        OneTextTwoIconTobBar(
            title = "내 정보 관리",
            firstIconClicked = moveToMyPageScreen,
            firstIcon = R.drawable.ic_back,
            secondIconClicked = { popupState.isVisible = true },
            secondIcon = R.drawable.ic_back
        ) {
            ModifierIconPopUp(
                popupState = popupState,
                chaneValuePermissionState = chaneValuePermissionState
            )
        }
        InputData(
            title = "이름",
            content = "이름",
            myId = "이름",
            myEmail = "이름",
            chaneValuePermissionState = chaneValuePermissionState.value
        )



    }


//    CustomTopBar(
//        title = "내 정보 관리",
//        onBackButtonClicked = { moveToMyPageScreen() }
//    )
}

@Composable
fun InputData(
    title: String,
    content: String,
//    content: MutableState<String>,
    myId: String,
    myEmail: String,
    chaneValuePermissionState : Boolean
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = title, style = medium12pt)
//        CanChangeOutlinedTextField(
//            value = content.value,
//            onValueChange = {
//                content.value = it
//            },
//            modifier = Modifier.width(100.dp),
//            readOnly = chaneValuePermissionState
//            )

        CanChangeOutlinedTextField(
            value = content,
            onValueChange = {
//                content.value = it
            },
            modifier = Modifier.width(100.dp),
            readOnly = chaneValuePermissionState
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CanChangeOutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean,
    textStyle: TextStyle = LocalTextStyle.current,
    label: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    prefix: @Composable (() -> Unit)? = null,
    suffix: @Composable (() -> Unit)? = null,
    supportingText: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shape: Shape = OutlinedTextFieldDefaults.shape,
    colors: TextFieldColors = OutlinedTextFieldDefaults.colors()
) {

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        enabled = enabled,
        readOnly = readOnly,
        textStyle = textStyle,
        label = label,
        placeholder = placeholder,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        prefix = prefix,
        suffix = suffix,
        supportingText = supportingText,
        isError = isError,
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = singleLine,
        maxLines = maxLines,
        minLines = minLines,
        interactionSource = interactionSource,
        shape = shape,
        colors = colors
    )
}




@Composable
fun ModifierIconPopUp(
    popupState: PopupState,
    chaneValuePermissionState : MutableState<Boolean>,

) {
    val density = LocalDensity.current.density
    Box(
        contentAlignment = Alignment.CenterEnd,
        modifier = Modifier.padding(top = 10.dp)
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
                        .background(color = Color(0xFF7262A8))
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(
                                    start = 14.dp,
                                    top = 8.dp,
                                    bottom = 6.dp
                                )
                                .clickable {
                                    popupState.isVisible = false
                                    chaneValuePermissionState.value = false
                                },
                            text = "수정하기",
                            style = medium14pt,
                            color = Color.White
                        )

                    }
                }
            }
        }
    }
}