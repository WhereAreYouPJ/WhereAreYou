package com.whereareyou.data

data class Friend(
    val number: Int,
    val name: String = "",
    val id: String = "",
    val profileImgUrl: String? = "",
    val isPinned: Boolean = true
)