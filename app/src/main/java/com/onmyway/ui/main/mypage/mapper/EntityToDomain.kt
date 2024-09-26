package com.onmyway.ui.main.mypage.mapper

import com.onmyway.domain.entity.location.LocationFaboriteInfo
import com.onmyway.domain.entity.member.DetailUserInfo
import com.onmyway.ui.main.mypage.model.LocationFaboriteInfoModel
import com.onmyway.ui.main.mypage.model.UserInfoModel

fun DetailUserInfo.toModel(): UserInfoModel {
    return UserInfoModel(
        userName = this.userName,
        email = this.email,
        profileImage = this.profileImage
    )
}

fun LocationFaboriteInfo.toModel() : LocationFaboriteInfoModel {
    return LocationFaboriteInfoModel(
        locationSeq = this.locationSeq,
        location = this.location,
        streetName = this.streetName
    )
}

