package com.whereareyounow.data

import com.whereareyounow.domain.entity.schedule.Friend

object FriendProvider {
    val friendsList = mutableListOf<Friend>()
    val friendsMap = mutableMapOf<String, Friend>()
}