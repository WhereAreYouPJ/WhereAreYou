package com.whereareyounow.ui.main

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
class MainViewModel @Inject constructor(
    private val application: Application
) : AndroidViewModel(application) {

    private val _viewType = MutableStateFlow(ViewType.Home)
    val viewType: StateFlow<ViewType> = _viewType
    private val navigationItemContent = listOf(
        NavigationItemContent(
            viewType = ViewType.Home,
            iconSelected = R.drawable.ic_home_filled,
            iconUnselected = R.drawable.ic_home_outlined,
            label = "홈"
        ),
        NavigationItemContent(
            viewType = ViewType.Calendar,
            iconSelected = R.drawable.ic_schedule_filled,
            iconUnselected = R.drawable.ic_schedule_outlined,
            label = "일정"
        ),
        NavigationItemContent(
            viewType = ViewType.Friends,
            iconSelected = R.drawable.ic_users_filled,
            iconUnselected = R.drawable.ic_feed_outlined,
            label = "피드"
        ),
        NavigationItemContent(
            viewType = ViewType.MyPage,
            iconSelected = R.drawable.ic_mypage_filled,
            iconUnselected = R.drawable.ic_mypage_outlined,
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