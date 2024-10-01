package com.whereareyounow.ui.main.mypage.mapper

import com.whereareyounow.domain.entity.location.LocationFavoriteInfo
import com.whereareyounow.domain.entity.member.DetailUserInfo
import com.whereareyounow.ui.main.mypage.model.LocationFavoriteInfoModel
import com.whereareyounow.ui.main.mypage.model.UserInfoModel

fun DetailUserInfo.toModel(): UserInfoModel {
    return UserInfoModel(
        userName = this.userName,
        email = this.email,
        profileImage = this.profileImage
    )
}

fun LocationFavoriteInfo.toModel() : LocationFavoriteInfoModel {
    return LocationFavoriteInfoModel(
        locationSeq = this.locationSeq,
        location = this.location,
        streetName = this.streetName
    )
}