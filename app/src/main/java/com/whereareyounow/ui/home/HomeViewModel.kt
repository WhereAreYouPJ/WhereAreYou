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
    private val application: Application
) : AndroidViewModel(application) {

    private val _viewType = MutableStateFlow(ViewType.Calendar)
    val viewType: StateFlow<ViewType> = _viewType
    private val navigationItemContent = listOf(
        NavigationItemContent(
            viewType = ViewType.Calendar,
            iconSelected = R.drawable.bottomnavbar_home,
            iconUnselected = R.drawable.bottomnavbar_home,
            label = "홈"
        ),
        NavigationItemContent(
            viewType = ViewType.Friends,
            iconSelected = R.drawable.bottomnavbar_friend,
            iconUnselected = R.drawable.bottomnavbar_friend,
            label = "친구목록"
        ),
        NavigationItemContent(
            viewType = ViewType.MyPage,
            iconSelected = R.drawable.bottomnavbar_mypage,
            iconUnselected = R.drawable.bottomnavbar_mypage,
            label = "마이페이지"
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
        val label: String
    )
}