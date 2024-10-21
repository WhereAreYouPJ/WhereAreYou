package com.whereareyounow.ui.main.friend.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.whereareyounow.data.cached.AuthData
import com.whereareyounow.data.feedlist.FeedListScreenUIState
import com.whereareyounow.domain.entity.feed.FeedListData
import com.whereareyounow.domain.request.feed.BookmarkFeedRequest
import com.whereareyounow.domain.request.feed.DeleteFeedBookmarkRequest
import com.whereareyounow.domain.request.feed.DeleteFeedRequest
import com.whereareyounow.domain.request.feed.GetDetailFeedRequest
import com.whereareyounow.domain.request.feed.GetFeedListRequest
import com.whereareyounow.domain.usecase.feed.BookmarkFeedUseCase
import com.whereareyounow.domain.usecase.feed.DeleteFeedBookmarkUseCase
import com.whereareyounow.domain.usecase.feed.GetDetailFeedUseCase
import com.whereareyounow.domain.usecase.feed.GetFeedListUseCase
import com.whereareyounow.domain.util.onError
import com.whereareyounow.domain.util.onException
import com.whereareyounow.domain.util.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(
    private val getFeedListUseCase: GetFeedListUseCase,
    private val getDetailFeedUseCase: GetDetailFeedUseCase,
    private val bookmarkFeedUseCase: BookmarkFeedUseCase,
    private val deleteFeedBookmarkUseCase: DeleteFeedBookmarkUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(FeedListScreenUIState(FeedListData(
        totalElements = 0,
        totalPages = 0,
        size = 0,
        emptyList()
    ), null, 0))
    val uiState = _uiState.asStateFlow()

    fun getFeedList() {
        val requestData = GetFeedListRequest(
            memberSeq = AuthData.memberSeq,
            page = 0,
            size = 100
        )
        getFeedListUseCase(requestData)
            .onEach { networkResult ->
                networkResult.onSuccess { code, message, data ->
                    data?.let {
                        val newFeedGroupList = data.content.map { feedInfo ->
                            val newFeedList = feedInfo.feedInfo.sortedWith(
                                compareBy(
                                    { it.memberInfo.memberSeq == AuthData.memberSeq }
                                )
                            )
                            feedInfo.copy(
                                feedInfo = newFeedList
                            )
                        }
                        _uiState.update {
                            it.copy(
                                feedListData = data.copy(
                                    content = newFeedGroupList
                                )
                            )
                        }
                    }
                }.onError { code, message ->

                }.onException {  }
            }
            .catch {  }
            .launchIn(viewModelScope)
    }

    fun getDetailFeed(
        scheduleSeq: Int,
        feedSeq: Int
    ) {
        val requestData = GetDetailFeedRequest(
            memberSeq = AuthData.memberSeq,
            scheduleSeq = scheduleSeq,
            feedSeq = feedSeq
        )
        getDetailFeedUseCase(requestData)
            .onEach { networkResult ->
                networkResult.onSuccess { code, message, data ->
                    data?.let {
                        val newMemberList = data.memberInfoList.sortedWith(
                            compareBy(
                                { !data.feedInfo.map { it.memberInfo.memberSeq }.contains(it.memberSeq) },
                                { it.memberSeq != AuthData.memberSeq },
                            )
                        )
                        _uiState.update {
                            it.copy(
                                detailFeedData = data.copy(
                                    memberInfoList = newMemberList
                                )
                            )
                        }
                    }
                }.onError { code, message ->

                }.onException {  }
            }
            .catch {  }
            .launchIn(viewModelScope)
    }

    fun bookmarkFeed(feedSeq: Int) {
        val requestData = BookmarkFeedRequest(
            feedSeq = feedSeq,
            memberSeq = AuthData.memberSeq
        )
        bookmarkFeedUseCase(requestData)
            .onEach { networkResult ->
                networkResult.onSuccess { code, message, data ->
                    data?.let {
                        _uiState.update {
                            val newFeedList = _uiState.value.feedListData.content.map { scheduleFeedInfo ->
                                scheduleFeedInfo.copy(
                                    feedInfo = scheduleFeedInfo.feedInfo.map { feedDetailInfo ->
                                        if (feedDetailInfo.feedInfo.feedSeq == feedSeq) {
                                            feedDetailInfo.copy(
                                                bookmarkInfo = true
                                            )
                                        } else {
                                            feedDetailInfo
                                        }
                                    }
                                )
                            }

                            it.copy(
                                feedListData = it.feedListData.copy(
                                    content = newFeedList
                                )
                            )
                        }
                    }
                }.onError { code, message ->

                }.onException {  }
            }
            .catch {  }
            .launchIn(viewModelScope)
    }

    fun deleteBookmarkFeed(feedSeq: Int) {
        val requestData = DeleteFeedBookmarkRequest(
            feedSeq = feedSeq,
            memberSeq = AuthData.memberSeq
        )
        deleteFeedBookmarkUseCase(requestData)
            .onEach { networkResult ->
                networkResult.onSuccess { code, message, data ->
                    data?.let {
                        _uiState.update {
                            val newFeedList = _uiState.value.feedListData.content.map { scheduleFeedInfo ->
                                scheduleFeedInfo.copy(
                                    feedInfo = scheduleFeedInfo.feedInfo.map { feedDetailInfo ->
                                        if (feedDetailInfo.feedInfo.feedSeq == feedSeq) {
                                            feedDetailInfo.copy(
                                                bookmarkInfo = false
                                            )
                                        } else {
                                            feedDetailInfo
                                        }
                                    }
                                )
                            }

                            it.copy(
                                feedListData = it.feedListData.copy(
                                    content = newFeedList
                                )
                            )
                        }
                    }
                }.onError { code, message ->

                }.onException {  }
            }
            .catch {  }
            .launchIn(viewModelScope)
    }

    fun deleteFeed(feedSeq: Int) {
        val requestData = DeleteFeedRequest(
            memberSeq = AuthData.memberSeq,
            feedSeq = feedSeq
        )
    }

    fun updateSelectedMemberSeq(memberSeq: Int) {
        _uiState.update {
            it.copy(
                selectedFeedMemberSeq = memberSeq
            )
        }
    }

    init {
        getFeedList()
    }
}