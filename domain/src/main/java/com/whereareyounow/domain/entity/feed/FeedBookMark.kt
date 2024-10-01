package com.whereareyounow.domain.entity.feed

data class FeedBookMarkResponse(
    val totalElements: Int,
    val totalPages: Int,
    val size: Int,
    val content: List<Content>,
    val number: Int,
    val sort: Sort,
    val numberOfElements: Int,
    val pageable: Pageable,
    val first: Boolean,
    val last: Boolean,
    val empty: Boolean
)

data class Content(
    val memberSeq: Int,
    val profileImage: String,
    val startTime: String,
    val location: String,
    val title: String,
    val bookMarkImageInfos: List<BookMarkImageInfo>,
    val content: String,
    val bookMark: Boolean
)

data class BookMarkImageInfo(
    val feedImageSeq: Int,
    val feedImageURL: String,
    val feedImageOrder: Int
)

data class Sort(
    val empty: Boolean,
    val sorted: Boolean,
    val unsorted: Boolean
)

data class Pageable(
    val offset: Int,
    val sort: Sort,
    val paged: Boolean,
    val pageNumber: Int,
    val pageSize: Int,
    val unpaged: Boolean
)