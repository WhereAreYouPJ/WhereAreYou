package com.whereareyou.ui.home.schedule.notification

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.whereareyou.domain.entity.apimessage.friend.GetFriendRequestListResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DrawerNotificationViewModel @Inject constructor(
    private val application: Application
) : AndroidViewModel(application) {

    fun loadRequests() {

    }
}