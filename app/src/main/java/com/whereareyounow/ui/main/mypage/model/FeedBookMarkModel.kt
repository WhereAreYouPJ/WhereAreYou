package com.whereareyounow.ui.main.mypage.model

data class FeedBookMarkResponseModel(
    val totalElements: Int,
    val totalPages: Int,
    val size: Int,
    val content: List<ContentModel>?,
    val number: Int,
    val sort: SortModel,
    val numberOfElements: Int,
    val pageable: PageableModel,
    val first: Boolean,
    val last: Boolean,
    val empty: Boolean
)

data class ContentModel(
    val memberSeq: Int,
    val profileImage: String,
    val startTime: String,
    val location: String,
    val title: String,
    val bookMarkImageInfos: List<BookMarkImageInfoModel>?,
    val content: String,
    val bookMark: Boolean
)

data class BookMarkImageInfoModel(
    val feedImageSeq: Int,
    val feedImageURL: String,
    val feedImageOrder: Int
)

data class SortModel(
    val empty: Boolean,
    val sorted: Boolean,
    val unsorted: Boolean
)

data class PageableModel(
    val offset: Int,
    val sort: SortModel,
    val paged: Boolean,
    val pageNumber: Int,
    val pageSize: Int,
    val unpaged: Boolean
)