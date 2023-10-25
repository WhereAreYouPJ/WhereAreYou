package com.whereareyou.domain.entity.schedule

data class Friend(
    var memberId: String,
    var name: String,
    var profileImgUrl: String? = null,
    var isPinned: Boolean = false
)