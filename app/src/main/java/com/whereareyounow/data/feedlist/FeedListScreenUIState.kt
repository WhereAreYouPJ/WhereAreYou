package com.whereareyounow.data.feedlist

import com.whereareyounow.domain.entity.feed.FeedInfo
import com.whereareyounow.domain.entity.feed.FeedListData

data class FeedListScreenUIState(
    val feedListData: FeedListData,
    val detailFeedData: FeedInfo?,
    val selectedFeedMemberSeq: Int,
)
