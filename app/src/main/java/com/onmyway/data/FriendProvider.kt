package com.onmyway.data

import com.onmyway.domain.entity.schedule.Friend

object FriendProvider {
    val friendsList = mutableListOf<Friend>()
    val friendsMap = mutableMapOf<String, Friend>()
}