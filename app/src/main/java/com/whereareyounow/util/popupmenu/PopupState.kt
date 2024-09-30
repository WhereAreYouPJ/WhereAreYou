package com.whereareyounow.util.popupmenu

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment

@Stable
class PopupState(
    isVisible: Boolean = false,
    popupPosition: PopupPosition = PopupPosition.BottomLeft
) {
    /**
     * Horizontal alignment from which the popup will expand from and shrink to.
     */
    var horizontalAlignment: Alignment.Horizontal by mutableStateOf(Alignment.CenterHorizontally)

    /**
     * Boolean that defines whether the popup is displayed above or below the anchor.
     */
    var isTop: Boolean by mutableStateOf(false)

    /**
     * Boolean that defines whether the popup is currently visible or not.
     */
    var isVisible: Boolean by mutableStateOf(isVisible)

    var popupPosition: PopupPosition by mutableStateOf(popupPosition)
}