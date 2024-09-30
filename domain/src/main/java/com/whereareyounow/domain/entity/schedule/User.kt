package com.whereareyounow.domain.entity.schedule

data class User(
    var memberId: String,
    var name: String,
    var profileImgUrl: String? = null
)
