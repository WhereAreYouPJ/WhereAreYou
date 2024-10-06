package com.whereareyounow.ui.main.friend.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.whereareyounow.data.cached.AuthData
import com.whereareyounow.data.feedlist.FeedListScreenUIState
import com.whereareyounow.domain.entity.feed.FeedListData
import com.whereareyounow.domain.request.feed.GetFeedListRequest
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
class FeedListViewModel @Inject constructor(
    private val getFeedListUseCase: GetFeedListUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(FeedListScreenUIState(FeedListData(
        totalElements = 0,
        totalPages = 0,
        size = 0,
        emptyList()
    )))
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
                        _uiState.update {
                            it.copy(
                                feedListData = data
                            )
                        }
                    }
                }.onError { code, message ->

                }.onException {  }
            }
            .catch {  }
            .launchIn(viewModelScope)
    }

    init {
        getFeedList()
    }
}