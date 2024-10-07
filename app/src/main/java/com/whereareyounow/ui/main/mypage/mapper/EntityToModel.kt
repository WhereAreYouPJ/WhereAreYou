package com.whereareyounow.ui.main.mypage.mapper

import com.whereareyounow.domain.entity.feed.BookmarkImageInfo
import com.whereareyounow.domain.entity.feed.BookmarkedFeed
import com.whereareyounow.domain.entity.feed.BookmarkedFeedData
import com.whereareyounow.domain.entity.friend.FriendInfo

import com.whereareyounow.domain.entity.location.LocationFavoriteInfo
import com.whereareyounow.domain.entity.member.DetailUserInfo
import com.whereareyounow.domain.entity.member.UserInfo
import com.whereareyounow.domain.entity.schedule.Friend
import com.whereareyounow.ui.main.friend.model.FriendModel
import com.whereareyounow.ui.main.mypage.model.BookMarkImageInfoModel
import com.whereareyounow.ui.main.mypage.model.ContentModel
import com.whereareyounow.ui.main.mypage.model.FeedBookMarkResponseModel
import com.whereareyounow.ui.main.mypage.model.LocationFavoriteInfoModel
import com.whereareyounow.ui.main.mypage.model.OtherUserInfoModel
import com.whereareyounow.ui.main.mypage.model.PageableModel
import com.whereareyounow.ui.main.mypage.model.SortModel
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

// 검색된 유저 정보
fun UserInfo.toModel() : OtherUserInfoModel {
    return OtherUserInfoModel(
        userName = this.userName,
        memberSeq = this.memberSeq,
        profileImage = this.profileImage
    )
}

/**
 * [친구 목록 조회]
 */
fun FriendInfo.toModel() : FriendModel {
    return FriendModel(
        memberSeq = this.memberSeq,
        userName = this.userName,
        profileImage = this.profileImage,
        Favorites = this.Favorites
    )
}

// 북마크 피드
fun BookmarkedFeedData.toModel(): FeedBookMarkResponseModel {
    return FeedBookMarkResponseModel(
        totalElements = this.totalElements,
        totalPages = this.totalPages,
        size = this.size,
        content = this.content?.map { it.toModel() },
    )
}
fun BookmarkedFeed.toModel(): ContentModel {
    return ContentModel(
        memberSeq = this.memberSeq,
        profileImage = this.profileImage,
        startTime = this.startTime,
        location = this.location,
        title = this.title,
        bookMarkImageInfos = this.bookMarkImageInfos?.map { it.toModel() },
        content = this.content,
        bookMark = this.bookMark
    )
}
fun BookmarkImageInfo.toModel(): BookMarkImageInfoModel {
    return BookMarkImageInfoModel(
        feedImageSeq = this.feedImageSeq,
        feedImageURL = this.feedImageURL,
        feedImageOrder = this.feedImageOrder
    )
}
