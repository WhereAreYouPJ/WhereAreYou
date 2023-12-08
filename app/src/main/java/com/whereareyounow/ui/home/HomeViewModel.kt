package com.whereareyounow.ui.home

import android.app.Application
import androidx.annotation.DrawableRes
import androidx.lifecycle.AndroidViewModel
import com.whereareyounow.R
import com.whereareyounow.data.ViewType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    application: Application
) : AndroidViewModel(application) {

    private val _viewType = MutableStateFlow(ViewType.Calendar)
    val viewType: StateFlow<ViewType> = _viewType
    private val navigationItemContent = listOf(
        NavigationItemContent(
            viewType = ViewType.Calendar,
            iconSelected = R.drawable.calendar_filled,
            iconUnselected = R.drawable.calendar_filled,
            text = ""
        ),
        NavigationItemContent(
            viewType = ViewType.Friends,
            iconSelected = R.drawable.friends_filled,
            iconUnselected = R.drawable.friends_filled,
            text = ""
        ),
        NavigationItemContent(
            viewType = ViewType.MyPage,
            iconSelected = R.drawable.mypage_filled,
            iconUnselected = R.drawable.mypage_filled,
            text = ""
        )
    )

    fun getNavigationItemContent(): List<NavigationItemContent> {
        return navigationItemContent
    }

    fun updateViewType(viewType: ViewType) {
        _viewType.update { viewType }
    }

    data class NavigationItemContent(
        val viewType: ViewType,
        @DrawableRes
        val iconSelected: Int,
        @DrawableRes
        val iconUnselected: Int,
        val text: String
    )
}