package com.whereareyou.ui.home

import android.app.Application
import androidx.annotation.DrawableRes
import androidx.lifecycle.AndroidViewModel
import com.whereareyou.R
import com.whereareyou.data.ViewType
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
            icon = R.drawable.calendar,
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
        val icon: Int,
        val text: String
    )
}