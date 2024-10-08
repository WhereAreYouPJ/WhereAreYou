package com.whereareyounow.ui.main.friend.model


data class FriendModel(
    var memberSeq: Int,
    var userName: String = "",
    var profileImage: String = "",
    var Favorites: Boolean? = false,
)
