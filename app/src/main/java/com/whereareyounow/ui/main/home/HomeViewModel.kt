package com.whereareyounow.ui.main.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.whereareyounow.data.cached.AuthData
import com.whereareyounow.domain.request.home.GetHomeImageListRequest
import com.whereareyounow.domain.usecase.home.GetHomeImageListUseCase
import com.whereareyounow.domain.util.onError
import com.whereareyounow.domain.util.onException
import com.whereareyounow.domain.util.onSuccess
import com.whereareyounow.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getHomeImageListUseCase: GetHomeImageListUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState.Loading(null))
    val uiState = _uiState.asStateFlow()

    fun getBannerImageList() {
        val requestData = GetHomeImageListRequest(
            memberSeq = AuthData.memberSeq
        )
        getHomeImageListUseCase(requestData)
            .onEach { networkResult ->
                networkResult.onSuccess { code, message, data ->
                    data?.let {
                        _uiState.update {
                            it.
                        }
                    }
                }.onError { code, message ->

                }.onException {  }
            }
            .catch {  }
            .launchIn(viewModelScope)
    }

    init {
        getBannerImageList()
    }
}