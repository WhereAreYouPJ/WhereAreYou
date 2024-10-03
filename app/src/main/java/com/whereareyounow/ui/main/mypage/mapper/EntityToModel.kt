package com.whereareyounow.ui.main.mypage.mapper

import com.whereareyounow.domain.entity.feedbookmark.BookMarkImageInfo
import com.whereareyounow.domain.entity.feedbookmark.Content
import com.whereareyounow.domain.entity.feedbookmark.GetFeedBookMarkResponse
import com.whereareyounow.domain.entity.feedbookmark.Pageable
import com.whereareyounow.domain.entity.feedbookmark.Sort
import com.whereareyounow.domain.entity.location.LocationFavoriteInfo
import com.whereareyounow.domain.entity.member.DetailUserInfo
import com.whereareyounow.domain.entity.member.UserInfo
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

// feef book-mark
fun GetFeedBookMarkResponse.toModel(): FeedBookMarkResponseModel {
    return FeedBookMarkResponseModel(
        totalElements = this.totalElements,
        totalPages = this.totalPages,
        size = this.size,
        content = this.content?.map { it.toModel() },
        number = this.number,
        sort = this.sort.toModel(),
        numberOfElements = this.numberOfElements,
        pageable = this.pageable.toModel(),
        first = this.first,
        last = this.last,
        empty = this.empty
    )
}

fun Content.toModel(): ContentModel {
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

fun BookMarkImageInfo.toModel(): BookMarkImageInfoModel {
    return BookMarkImageInfoModel(
        feedImageSeq = this.feedImageSeq,
        feedImageURL = this.feedImageURL,
        feedImageOrder = this.feedImageOrder
    )
}

fun Sort.toModel(): SortModel {
    return SortModel(
        empty = this.empty,
        sorted = this.sorted,
        unsorted = this.unsorted
    )
}

fun Pageable.toModel(): PageableModel {
    return PageableModel(
        offset = this.offset,
        sort = this.sort.toModel(),
        paged = this.paged,
        pageNumber = this.pageNumber,
        pageSize = this.pageSize,
        unpaged = this.unpaged
    )
}