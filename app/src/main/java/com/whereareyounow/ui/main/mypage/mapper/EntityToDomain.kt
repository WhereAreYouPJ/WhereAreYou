package com.whereareyounow.ui.main.mypage.mapper

import com.whereareyounow.domain.entity.member.DetailUserInfo
import com.whereareyounow.domain.entity.member.UserInfo
import com.whereareyounow.ui.main.mypage.model.UserInfoModel

fun DetailUserInfo.toModel(): UserInfoModel {
    return UserInfoModel(
        userName = this.userName,
        email = this.email,
        profileImage = this.profileImage
    )
}


